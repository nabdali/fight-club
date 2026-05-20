package com.fightclub.user_service.entities.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;


@Builder
@AllArgsConstructor
@Data
public class UserStatisticsDTO {

    private Integer victoryCounter;

    private Integer defeatCounter;

    private Integer bestCharacterId;

    private Integer worstCharacterId;

    private List<CharacterStatsDTO> characters;

}
