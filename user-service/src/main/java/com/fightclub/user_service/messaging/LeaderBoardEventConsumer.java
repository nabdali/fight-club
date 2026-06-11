package com.fightclub.user_service.messaging;

import com.fightclub.user_service.entities.dto.LeaderBoardUpdated;
import com.fightclub.user_service.services.AppUserService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class LeaderBoardEventConsumer {

    private final AppUserService appUserService;

    @PostConstruct
    public void init() {
        log.info("LeaderBoardEventConsumer started — listening on topic 'stats.update'");
    }

    @KafkaListener(topics = "stats.update", groupId = "user-service-group")
    public void onFightEnded(LeaderBoardUpdated event) {
        if (event == null) {
            log.warn("Message vide ou non désérialisable reçu sur stats.update, ignoré");
            return;
        }
        try {
            appUserService.updateFightStats(event.winnerId(), event.looserId());
        } catch (Exception e) {
            log.error("Erreur lors du traitement du stats.update {}", e.getMessage());
        }
    }
}
