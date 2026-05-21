package com.fight_club.arena_service.dto;

import com.fight_club.arena_service.entity.FightStatus;

import java.time.LocalDateTime;

public record FightResultDTO(
        Long fightId,
        Long character1Id,
        Long character2Id,
        Long winnerId,
        FightStatus status,
        LocalDateTime createdAt,
        LocalDateTime endedAt
) {}
