package com.figth_club.leaderboard_service.client.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CharacterDetailsDTO {
    private Integer id;
    private String name;
    private Integer userId;
    private Integer level;
    private Integer experience;
    private String createdAt;
    private CharacterTypeDto characterType;
}