package fightclub.characterservice.dto;

import lombok.Value;
import java.time.LocalDateTime;

@Value
public class CharacterResponse {
    Long id;
    String name;
    Long userId;
    String characterTypeName;
    int level;
    int experience;
    LocalDateTime createdAt;
}
