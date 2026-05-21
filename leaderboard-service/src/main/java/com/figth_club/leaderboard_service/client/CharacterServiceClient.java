package com.figth_club.leaderboard_service.client;

import com.figth_club.leaderboard_service.client.dtos.CharacterDetailsDTO;
import com.figth_club.leaderboard_service.dtos.CharacterStatsDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CharacterServiceClient {
    private final RestClient characterRestClient;

    public List<CharacterDetailsDTO>  getCharacterInfo(Integer cId) {
        return characterRestClient.get()
                .uri("/api/characters/by-user/{userId}", cId)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,(req,res)->{
                    throw new RuntimeException("Pas de character pour l'id"+ cId);
                })
                .body(new ParameterizedTypeReference<List<CharacterDetailsDTO>>() {});
    }
}
