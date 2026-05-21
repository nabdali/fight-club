package fightclub.characterservice.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class CharacterResponse {
    Long id;
    String name;
    Long userId;
    int level;
    int experience;
    LocalDateTime createdAt;
    CharacterTypeDto characterType;
}
