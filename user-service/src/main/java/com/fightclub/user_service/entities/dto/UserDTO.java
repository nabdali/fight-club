package com.fightclub.user_service.entities.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@AllArgsConstructor
@Data
public class UserDTO {

    private Integer id;

    private String email;

    private String pseudo;
}
