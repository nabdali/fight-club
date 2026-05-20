package com.fightclub.gateway.proxy;

import com.fightclub.gateway.config.GatewayProperties;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

@Component
public class ProxyFilter implements WebFilter {

    private static final Set<String> HOP_BY_HOP = Set.of(
        "connection", "keep-alive", "transfer-encoding",
        "te", "trailer", "proxy-authorization", "proxy-authenticate", "upgrade"
    );

    private final WebClient webClient;
    private final List<GatewayProperties.Route> sortedRoutes;

    public ProxyFilter(WebClient webClient, GatewayProperties props) {
        this.webClient = webClient;
        // Longest path prefix wins (more specific routes take priority)
        this.sortedRoutes = props.routes().stream()
            .sorted(Comparator.comparingInt((GatewayProperties.Route r) -> r.path().length()).reversed())
            .toList();
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String path = exchange.getRequest().getPath().value();

        // Internal endpoints are never proxied
        if (path.startsWith("/gateway/") || path.startsWith("/actuator/")) {
            return chain.filter(exchange);
        }

        return sortedRoutes.stream()
            .filter(r -> path.startsWith(r.path()))
            .findFirst()
            .map(route -> proxy(exchange, route))
            .orElseGet(() -> chain.filter(exchange));
    }

    private Mono<Void> proxy(ServerWebExchange exchange, GatewayProperties.Route route) {
        ServerHttpRequest request = exchange.getRequest();

        String query = request.getURI().getRawQuery();
        String targetUri = route.uri() + request.getPath().value()
            + (query != null ? "?" + query : "");

        HttpHeaders forwardHeaders = new HttpHeaders();
        request.getHeaders().forEach((name, values) -> {
            if (!HOP_BY_HOP.contains(name.toLowerCase())) {
                forwardHeaders.addAll(name, values);
            }
        });

        return webClient.method(request.getMethod())
            .uri(targetUri)
            .headers(h -> h.addAll(forwardHeaders))
            .body(request.getBody(), DataBuffer.class)
            .exchangeToMono(clientResponse -> {
                ServerHttpResponse response = exchange.getResponse();
                response.setStatusCode(clientResponse.statusCode());
                clientResponse.headers().asHttpHeaders().forEach((name, values) -> {
                    if (!HOP_BY_HOP.contains(name.toLowerCase())) {
                        response.getHeaders().addAll(name, values);
                    }
                });
                return response.writeWith(clientResponse.bodyToFlux(DataBuffer.class));
            });
    }
}
