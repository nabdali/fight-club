package com.fightclub.user_service.mapper;

import com.fightclub.user_service.entities.UserDTO;
import com.fightclub.user_service.entities.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO toDto(UserEntity user);

    UserEntity toEntity(UserDTO user);
}
