package com.figth_club.leaderboard_service.messaging;

import com.figth_club.leaderboard_service.dtos.FightEndedEvent;
import com.figth_club.leaderboard_service.dtos.StatsUpdated;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LeaderBoardEventProducer {

    private static final String STATS_UPDATE = "stats.update";
    private final KafkaTemplate<Object, Object> kafkaTemplate;


    public void publishStatsUpdated(StatsUpdated event) {
        kafkaTemplate.send(STATS_UPDATE, event.winnerId() + "-" + event.looserId(), event);
    }

}
