package com.fightclub.user_service.entities.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CharacterStatsDTO {

    private Integer id;

    private String name;

    private CharacterTypeDTO type;

    private Integer victoryCounter;

    private Integer defeatCounter;
}
