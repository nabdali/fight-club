package com.fightclub.gateway.health;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Map;

@RestController
@RequestMapping("/gateway/services")
public class ServicesHealthController {

    private static final Map<String, String> SERVICES = Map.of(
        "user-service",       "http://localhost:8084",
        "character-service",  "http://localhost:8082",
        "arena-service",      "http://localhost:8083",
        "leaderboard-service","http://localhost:8085"
    );

    private static final Duration PROBE_TIMEOUT = Duration.ofSeconds(3);

    private final WebClient webClient;

    public ServicesHealthController(WebClient webClient) {
        this.webClient = webClient;
    }

    @GetMapping("/health")
    public Mono<Map<String, ServiceHealth>> servicesHealth() {
        return Flux.fromIterable(SERVICES.entrySet())
            .flatMap(e -> probe(e.getKey(), e.getValue()))
            .collectMap(Map.Entry::getKey, Map.Entry::getValue);
    }

    private Mono<Map.Entry<String, ServiceHealth>> probe(String name, String baseUrl) {
        return webClient.get()
            .uri(baseUrl + "/actuator/health")
            .retrieve()
            .bodyToMono(ActuatorHealthResponse.class)
            .map(r -> Map.entry(name, ServiceHealth.of(r.status(), baseUrl)))
            .timeout(PROBE_TIMEOUT)
            .onErrorResume(ex -> Mono.just(
                Map.entry(name, ServiceHealth.down(baseUrl, rootCause(ex)))
            ));
    }

    private static String rootCause(Throwable ex) {
        Throwable cause = ex;
        while (cause.getCause() != null) cause = cause.getCause();
        return cause.getClass().getSimpleName() + ": " + cause.getMessage();
    }

    private record ActuatorHealthResponse(String status) {}
}
