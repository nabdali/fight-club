package fightclub.characterservice.service;

import fightclub.characterservice.dto.CharacterCreateRequest;
import fightclub.characterservice.dto.CharacterDetailResponse;
import fightclub.characterservice.dto.CharacterResponse;
import fightclub.characterservice.entities.Character;
import fightclub.characterservice.entities.CharacterType;
import fightclub.characterservice.exception.CharacterAlreadyExistsException;
import fightclub.characterservice.exception.CharacterNotFoundException;
import fightclub.characterservice.exception.CharacterTypeNotFoundException;
import fightclub.characterservice.mapper.CharacterMapper;
import fightclub.characterservice.repository.CharacterRepository;
import fightclub.characterservice.repository.CharacterTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@RequiredArgsConstructor
@Service
public class CharacterService {

    private final CharacterRepository characterRepository;
    private final CharacterTypeRepository characterTypeRepository;
    private final CharacterMapper characterMapper;


    public Character createCharacter(CharacterCreateRequest request) {
        CharacterType type = characterTypeRepository
                .findByNameIgnoreCase(request.getCharacterTypeName())
                .orElseThrow(() -> new CharacterTypeNotFoundException(
                        "CharacterType '" + request.getCharacterTypeName() + "' not found"));

        if (characterRepository.existsByUserIdAndCharacterType_Name(
                request.getUserId(), type.getName())) {
            throw new CharacterAlreadyExistsException(
                    "User " + request.getUserId() + " already owns a " + type.getName());
        }

        Character character = new Character(request.getName(), request.getUserId(), type);
        return characterRepository.save(character);
    }

    public List<Character> getAll() {
        return characterRepository.findAll();
    }

    public List<Character> findByName(String name) {
        return characterRepository.findByNameContainingIgnoreCase(name);
    }

    public Character getById(Long id) {
        return characterRepository.findById(id)
                .orElseThrow(() -> new CharacterNotFoundException("Character " + id + " not found"));
    }

    public  List<CharacterResponse> getCharacterByUserId(Long userId) {
        List<Character> characters = characterRepository.findAllByUserId(userId);
        return characterMapper.toResponseList(characters);
    }
}
