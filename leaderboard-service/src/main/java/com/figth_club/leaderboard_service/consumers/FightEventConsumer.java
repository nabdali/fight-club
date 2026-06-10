package com.figth_club.leaderboard_service.consumers;

import com.figth_club.leaderboard_service.dtos.FightEndedEvent;
import com.figth_club.leaderboard_service.services.AppBoardService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class FightEventConsumer {

    private final AppBoardService appBoardService;

    @PostConstruct
    public void init() {
        log.info("FightEventConsumer started — listening on topic 'fight.ended'");
    }

    @KafkaListener(topics = "fight.ended", groupId = "leaderboard-group")
    public void onFightEnded(FightEndedEvent event) {
        log.info("Message reçu — fightId={} winnerId={} loserId={}", event.fightId(), event.winnerId(), event.loserId());
        try {
            appBoardService.processMatchResult(event.winnerId(), event.loserId());
            log.info("Résultat traité — winnerId={} loserId={}", event.winnerId(), event.loserId());
        } catch (Exception e) {
            log.error("Erreur lors du traitement du fight.ended fightId={}: {}", event.fightId(), e.getMessage(), e);
        }
    }
}