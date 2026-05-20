package com.fightclub.character_service.service;

import com.fightclub.character_service.dto.CharacterCreateRequest;
import com.fightclub.character_service.entity.Character;
import com.fightclub.character_service.entity.CharacterType;
import com.fightclub.character_service.exception.CharacterAlreadyExistsException;
import com.fightclub.character_service.exception.CharacterTypeNotFoundException;
import com.fightclub.character_service.repository.CharacterRepository;
import com.fightclub.character_service.repository.CharacterTypeRepository;
import org.springframework.stereotype.Service;

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
}
