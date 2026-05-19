package com.fightclub.gateway.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "gateway")
public record GatewayProperties(List<Route> routes) {

    public record Route(String path, String uri) {}
}
