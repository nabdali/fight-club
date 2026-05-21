package com.fightclub.user_service.entities.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CharacterTypeDTO {

    String name;
    Integer strength;
    Integer health;
}
