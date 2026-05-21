package com.figth_club.leaderboard_service.mappers;

import com.figth_club.leaderboard_service.client.dtos.CharacterDetailsDTO;
import com.figth_club.leaderboard_service.dtos.CharacterStatsDTO;
import com.figth_club.leaderboard_service.dtos.UserStatisticDTO;
import com.figth_club.leaderboard_service.entities.UserStatistic;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping; // <-- Import ajouté ici !

import java.util.List;

@Mapper(componentModel = "spring")
public interface LeaderboardMapper {
    @Mapping(target = "victoryCounter", source = "statistic.victoryCounter")
    @Mapping(target = "defeatCounter", source = "statistic.defeatCounter")
    @Mapping(target = "name", source = "details.name")
    @Mapping(target = "type", source = "details.type")
    CharacterStatsDTO toCharacterStatsDTO(UserStatisticDTO statistic, CharacterDetailsDTO details);

    // Convertit une entité seule en UserStatisticDTO
    UserStatisticDTO toUserStatisticDTO(UserStatistic statistic);

    // Convertit la liste d'entités reçue du Repository
    List<UserStatisticDTO> toUserStatisticDTOList(List<UserStatistic> statistics);
}