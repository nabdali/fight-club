package com.figth_club.leaderboard_service.mappers;

import com.figth_club.leaderboard_service.client.dtos.CharacterDetailsDTO;
import com.figth_club.leaderboard_service.dtos.CharacterStatsDTO;
import com.figth_club.leaderboard_service.dtos.UserStatisticDTO;
import com.figth_club.leaderboard_service.entities.UserStatistic;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LeaderboardMapper {

    @Mapping(target = "id", source = "details.id")
    @Mapping(target = "name", source = "details.name")
    @Mapping(target = "type", source = "details.characterType")
    @Mapping(target = "victoryCounter", source = "statistic.victoryCounter")
    @Mapping(target = "defeatCounter", source = "statistic.defeatCounter")
    CharacterStatsDTO toCharacterStatsDTO(UserStatisticDTO statistic, CharacterDetailsDTO details);

    UserStatisticDTO toUserStatisticDTO(UserStatistic statistic);

    List<UserStatisticDTO> toUserStatisticDTOList(List<UserStatistic> statistics);

}