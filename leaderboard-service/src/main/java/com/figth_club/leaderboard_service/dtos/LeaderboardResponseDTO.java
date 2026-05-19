package com.figth_club.leaderboard_service.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LeaderboardResponseDTO {
    private List<CharacterStatsDTO> characters;
}