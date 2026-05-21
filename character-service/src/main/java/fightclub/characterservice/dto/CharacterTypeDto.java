package fightclub.characterservice.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CharacterTypeDto {
    String name;
    int strength;
    int health;
}