package com.fight_club.arena_service.dto;

import java.time.LocalDateTime;

public record FightCreatedEvent(
        Long fightId,
        Long character1Id,
        Long character2Id,
        LocalDateTime createdAt
) {}
