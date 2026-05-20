package fightclub.characterservice.service;

import fightclub.characterservice.dto.CharacterCreateRequest;
import fightclub.characterservice.entities.Character;
import fightclub.characterservice.entities.CharacterType;
import fightclub.characterservice.exception.CharacterAlreadyExistsException;
import fightclub.characterservice.exception.CharacterTypeNotFoundException;
import fightclub.characterservice.repository.CharacterRepository;
import fightclub.characterservice.repository.CharacterTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CharacterService {

    private final CharacterRepository characterRepository;
    private final CharacterTypeRepository characterTypeRepository;

    public CharacterService(CharacterRepository characterRepository,
                            CharacterTypeRepository characterTypeRepository) {
        this.characterRepository = characterRepository;
        this.characterTypeRepository = characterTypeRepository;
    }

    public Character createCharacter(CharacterCreateRequest request) {
        // 1. Vérifier que le type existe
        CharacterType type = characterTypeRepository
                .findByNameIgnoreCase(request.getCharacterTypeName())
                .orElseThrow(() -> new CharacterTypeNotFoundException(
                        "CharacterType '" + request.getCharacterTypeName() + "' not found"));

        // 2. Vérifier l'unicité user/classe
        if (characterRepository.existsByUserIdAndCharacterType_Name(
                request.getUserId(), type.getName())) {
            throw new CharacterAlreadyExistsException(
                    "User " + request.getUserId() + " already owns a " + type.getName());
        }

        // 3. Créer et sauvegarder
        Character character = new Character(request.getName(), request.getUserId(), type);
        return characterRepository.save(character);
    }

    public List<Character> getAll() {
        return characterRepository.findAll();
    }

    public List<Character> findByName(String name) {
        return characterRepository.findByNameContainingIgnoreCase(name);
    }
}
