package com.figth_club.leaderboard_service.mappers;

import com.figth_club.leaderboard_service.dtos.CharacterStatsDTO;
import com.figth_club.leaderboard_service.entities.UserStatistic;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LeaderboardMapper {

    @Mapping(target = "name", source = "idCharacter", qualifiedByName = "idToName")
    @Mapping(target = "type", source = "idCharacter", qualifiedByName = "idToType")
    CharacterStatsDTO toCharacterStatsDTO(UserStatistic statistic);

    List<CharacterStatsDTO> toCharacterStatsDTOList(List<UserStatistic> statistics);

    // Logique de simulation pour le Nom
    @Named("idToName")
    default String idToName(Integer idCharacter) {
        if (idCharacter == null) return "Inconnu";
        return switch (idCharacter) {
            case 0 -> "Zéro";
            case 1 -> "Un";
            default -> "Personnage #" + idCharacter;
        };
    }

    // Logique de simulation pour le Type
    @Named("idToType")
    default String idToType(Integer idCharacter) {
        if (idCharacter == null) return "Inconnu";
        return switch (idCharacter) {
            case 0 -> "Mage";
            case 1 -> "Archer";
            default -> "Assasin";
        };
    }
}