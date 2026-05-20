package com.fight_club.arena_service.repository;

import com.fight_club.arena_service.entity.Fight;
import com.fight_club.arena_service.entity.FightStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FightRepository extends JpaRepository<Fight, Long> {

    Optional<Fight> findFirstByStatusAndCharacter1IdNot(FightStatus status, Long characterId);
}
