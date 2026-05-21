package com.fight_club.arena_service.client;

import com.fight_club.arena_service.dto.CharacterDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
public class CharacterServiceClient {

    private final RestClient characterRestClient;

    public CharacterDTO getCharacter(Long characterId) {
        return characterRestClient.get()
                .uri("/characters/{id}", characterId)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (req, res) -> {
                    throw new IllegalArgumentException("Character not found: " + characterId);
                })
                .body(CharacterDTO.class);
    }
}
