package com.fight_club.arena_service.messaging;

import com.fight_club.arena_service.dto.FightCreatedEvent;
import com.fight_club.arena_service.dto.FightEndedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FightEventProducer {

    private static final String FIGHT_CREATED_TOPIC = "fight.created";
    private static final String FIGHT_ENDED_TOPIC = "fight.ended";

    private final KafkaTemplate<Object, Object> kafkaTemplate;

    public void publishFightCreated(FightCreatedEvent event) {
        kafkaTemplate.send(FIGHT_CREATED_TOPIC, String.valueOf(event.fightId()), event);
    }

    public void publishFightEnded(FightEndedEvent event) {
        kafkaTemplate.send(FIGHT_ENDED_TOPIC, String.valueOf(event.fightId()), event);
    }
}
