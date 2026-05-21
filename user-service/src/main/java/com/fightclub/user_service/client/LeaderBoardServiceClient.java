package com.fightclub.user_service.client;

import com.fightclub.user_service.entities.dto.CharacterStatsDTO;
import com.fightclub.user_service.exception.custom.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
@RequiredArgsConstructor
public class LeaderBoardServiceClient {

    private final RestClient leaderBoardRestClient;

    public List<CharacterStatsDTO> getUsersStatistics(Integer userId) {
        return leaderBoardRestClient.get()
                .uri("/characters/user/{userId}", userId)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (req, res) -> {
                    throw new NotFoundException("Characters not found for user: " + userId);
                })
                .body(new ParameterizedTypeReference<List<CharacterStatsDTO>>() {});
    }
}
