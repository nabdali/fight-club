package com.figth_club.leaderboard_service.consumers;

import com.figth_club.leaderboard_service.dtos.FightEndedEvent;
import com.figth_club.leaderboard_service.services.AppBoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class FightEventConsumer {

    private final AppBoardService appBoardService;

    @KafkaListener(topics = "fight.ended", groupId = "leaderboard-group")
    public void onFightEnded(FightEndedEvent event) {
        log.info("Fight ended received: fightId={}, winner={}, loser={}", event.fightId(), event.winnerId(), event.loserId());
        try {
            appBoardService.processMatchResult(event.winnerId(), event.loserId());
        } catch (Exception e) {
            log.error("Erreur lors du traitement du fight.ended fightId={}: {}", event.fightId(), e.getMessage());
        }
    }
}