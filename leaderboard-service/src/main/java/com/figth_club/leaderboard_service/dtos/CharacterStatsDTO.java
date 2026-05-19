package com.figth_club.leaderboard_service.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CharacterStatsDTO {
    private String name;
    private String type;
    private Integer victoryCounter;
    private Integer defeatCounter;
}