package fightclub.characterservice.mapper;

import fightclub.characterservice.dto.CharacterDetailResponse;
import fightclub.characterservice.dto.CharacterTypeDto;
import fightclub.characterservice.dto.CharacterResponse;
import fightclub.characterservice.entities.Character;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CharacterMapper {

    public CharacterResponse toResponse(Character character) {
        if (character == null) return null;
        return CharacterResponse.builder()
                .id(character.getId())
                .name(character.getName())
                .level(character.getLevel())
                .userId(character.getUserId())
                .level(character.getLevel())
                .createdAt(character.getCreatedAt())
                .characterType(CharacterTypeDto.
                        builder()
                        .name(character.getCharacterType().getName())
                        .health(character.getCharacterType().getHealth())
                        .strength(character.getCharacterType().getStrength())
                        .build())
                .build();
    }

    public List<CharacterResponse> toResponseList(List<Character> characters) {
        return characters.stream()
                .map(this::toResponse)
                .toList();
    }

    public CharacterDetailResponse toDetailResponse(Character character) {
        if (character == null) return null;
        CharacterTypeDto typeDto = null;
        if (character.getCharacterType() != null) {
            typeDto =  CharacterTypeDto.

                    builder()
                    .name(character.getCharacterType().getName())
                    .health(character.getCharacterType().getHealth())
                    .strength(character.getCharacterType().getStrength())
                    .build();
        }
        return new CharacterDetailResponse(
                character.getUserId(),
                character.getName(),
                typeDto
        );
    }
}
