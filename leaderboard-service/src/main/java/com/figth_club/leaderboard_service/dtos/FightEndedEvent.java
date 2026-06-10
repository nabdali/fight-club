package com.figth_club.leaderboard_service.dtos;

import java.time.LocalDateTime;

public record FightEndedEvent(
        Long fightId,
        Long character1Id,
        Long character2Id,
        Long winnerId,
        Long loserId,
        LocalDateTime endedAt
) {}