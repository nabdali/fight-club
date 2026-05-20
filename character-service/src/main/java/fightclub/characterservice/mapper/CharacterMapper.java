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
        return new CharacterResponse(
                character.getId(),
                character.getName(),
                character.getUserId(),
                character.getCharacterType() != null ? character.getCharacterType().getName() : null,
                character.getLevel(),
                character.getExperience(),
                character.getCreatedAt()
        );
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
            typeDto = new CharacterTypeDto(
                    character.getCharacterType().getName(),
                    character.getCharacterType().getStrength(),
                    character.getCharacterType().getHealth()
            );
        }
        return new CharacterDetailResponse(
                character.getUserId(),
                character.getName(),
                typeDto
        );
    }
}
