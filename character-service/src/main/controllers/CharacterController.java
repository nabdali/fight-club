package com.fightclub.character_service.controller;

import com.fightclub.character_service.dto.CharacterCreateRequest;
import com.fightclub.character_service.entity.Character;
import com.fightclub.character_service.service.CharacterService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/characters")
public class CharacterController {

    private final CharacterService characterService;

    public CharacterController(CharacterService characterService) {
        this.characterService = characterService;
    }

    @PostMapping
    public ResponseEntity<Character> createCharacter(@Valid @RequestBody CharacterCreateRequest request) {
        Character created = characterService.createCharacter(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
}
