package com.fightclub.gateway.health;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ServiceHealth(String status, String url, String error) {

    public static ServiceHealth up(String url) {
        return new ServiceHealth("UP", url, null);
    }

    public static ServiceHealth of(String status, String url) {
        return new ServiceHealth(status, url, null);
    }

    public static ServiceHealth down(String url, String error) {
        return new ServiceHealth("DOWN", url, error);
    }
}
