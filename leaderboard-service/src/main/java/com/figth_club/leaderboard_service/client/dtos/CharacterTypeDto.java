package com.figth_club.leaderboard_service.client.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CharacterTypeDto {
    String name;
    int strength;
    int health;
}
