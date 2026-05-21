package fightclub.characterservice.dto;

import lombok.Value;

@Value
public class CharacterDetailResponse {
    Long userId;
    String name;
    CharacterTypeDto type;
}