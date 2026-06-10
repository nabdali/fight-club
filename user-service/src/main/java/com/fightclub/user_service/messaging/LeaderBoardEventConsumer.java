package com.fightclub.user_service.messaging;

import com.fightclub.user_service.entities.dto.LeaderBoardUpdated;
import com.fightclub.user_service.services.AppUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class LeaderBoardEventConsumer {

    private final AppUserService appUserService;

    @KafkaListener(topics = "", groupId = "")
    @KafkaListener(topics = "fight.ended", groupId = "leaderboard-group")
    public void onFightEnded(LeaderBoardUpdated event) {
        log.info("Fight ended received: userId={}", event.userId());
        try {
        } catch (Exception e) {
            log.error("Erreur lors du traitement du fight.ended {}", e.getMessage());
        }
    }
}
