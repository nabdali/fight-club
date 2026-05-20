package com.fight_club.arena_service.service;

import com.fight_club.arena_service.client.CharacterServiceClient;
import com.fight_club.arena_service.dto.CharacterDTO;
import com.fight_club.arena_service.dto.FightCreatedEvent;
import com.fight_club.arena_service.dto.FightEndedEvent;
import com.fight_club.arena_service.dto.FightResultDTO;
import com.fight_club.arena_service.entity.Fight;
import com.fight_club.arena_service.entity.FightStatus;
import com.fight_club.arena_service.messaging.FightEventProducer;
import com.fight_club.arena_service.repository.FightRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class FightService {

    private final FightRepository fightRepository;
    private final FightEventProducer fightEventProducer;
    private final CharacterServiceClient characterServiceClient;

    @Transactional
    public Long startFight(Long characterId) {
        Fight fight = Fight.builder()
                .character1Id(characterId)
                .status(FightStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .build();
        fight = fightRepository.save(fight);

        fightEventProducer.publishFightCreated(new FightCreatedEvent(
                fight.getId(), fight.getCharacter1Id(), null, fight.getCreatedAt()
        ));

        return fight.getId();
    }

    @Transactional
    public Long joinFight(Long characterId) {
        Fight fight = fightRepository
                .findFirstByStatusAndCharacter1IdNot(FightStatus.PENDING, characterId)
                .orElseThrow(() -> new IllegalStateException("No available fight to join"));

        CharacterDTO character1 = characterServiceClient.getCharacter(fight.getCharacter1Id());
        CharacterDTO character2 = characterServiceClient.getCharacter(characterId);

        Long winnerId = character1.type().strength() >= character2.type().strength()
                ? fight.getCharacter1Id()
                : characterId;
        Long loserId = winnerId.equals(fight.getCharacter1Id()) ? characterId : fight.getCharacter1Id();

        fight.setCharacter2Id(characterId);
        fight.setWinnerId(winnerId);
        fight.setStatus(FightStatus.ENDED);
        fight.setEndedAt(LocalDateTime.now());
        fightRepository.save(fight);

        fightEventProducer.publishFightEnded(new FightEndedEvent(
                fight.getId(), fight.getCharacter1Id(), characterId, winnerId, loserId, fight.getEndedAt()
        ));

        return fight.getId();
    }

    public FightResultDTO getFightResult(Long fightId) {
        Fight fight = fightRepository.findById(fightId)
                .orElseThrow(() -> new IllegalArgumentException("Fight not found: " + fightId));
        return new FightResultDTO(
                fight.getId(), fight.getCharacter1Id(), fight.getCharacter2Id(),
                fight.getWinnerId(), fight.getStatus(), fight.getCreatedAt(), fight.getEndedAt()
        );
    }
}
