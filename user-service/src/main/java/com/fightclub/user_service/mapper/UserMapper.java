package com.fightclub.user_service.mapper;

import com.fightclub.user_service.entities.dto.CharacterStatsDTO;
import com.fightclub.user_service.entities.dto.LoginUserRequestDTO;
import com.fightclub.user_service.entities.dto.UserDTO;
import com.fightclub.user_service.entities.UserEntity;
import com.fightclub.user_service.entities.dto.UserStatisticsDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "id", target = "id")
    UserDTO toDto(UserEntity user);

    @Mapping(source = "id", target = "id")
    UserEntity toEntity(UserDTO user);

    UserEntity toEntity(LoginUserRequestDTO user);

    @Mapping(source = "user.victoryCounter", target = "victoryCounter")
    @Mapping(source = "user.defeatCounter", target = "defeatCounter")
    @Mapping(source = "characters", target = "characters")
    UserStatisticsDTO toDto(UserEntity user, List<CharacterStatsDTO> characters);
}
 