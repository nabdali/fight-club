package com.fight_club.arena_service.dto;

import java.time.LocalDateTime;

public record FightEndedEvent(
        Long fightId,
        Long character1Id,
        Long character2Id,
        Long winnerId,
        Long loserId,
        LocalDateTime endedAt
) {}
