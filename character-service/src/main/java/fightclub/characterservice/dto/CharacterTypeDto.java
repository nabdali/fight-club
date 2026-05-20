package fightclub.characterservice.dto;

import lombok.Value;

@Value
public class CharacterTypeDto {
    String name;
    int strength;
    int health;
}