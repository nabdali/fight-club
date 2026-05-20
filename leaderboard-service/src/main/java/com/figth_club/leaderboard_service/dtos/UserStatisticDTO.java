package com.figth_club.leaderboard_service.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserStatisticDTO {
    private Integer id;

    private Integer idUser;

    private Integer idCharacter;

    private Integer victoryCounter;

    private Integer defeatCounter;
}