package fightclub.characterservice.controllers;

import fightclub.characterservice.dto.CharacterCreateRequest;
import fightclub.characterservice.dto.CharacterResponse;
import fightclub.characterservice.mapper.CharacterMapper;
import fightclub.characterservice.service.CharacterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/characters")
@RequiredArgsConstructor
public class CharacterController {

    private final CharacterService characterService;
    private final CharacterMapper characterMapper;

    @PostMapping
    public ResponseEntity<CharacterResponse> createCharacter(@Valid @RequestBody CharacterCreateRequest request) {
        var created = characterService.createCharacter(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(characterMapper.toResponse(created));
    }

    @GetMapping("/search")
    public ResponseEntity<List<CharacterResponse>> searchByName(@RequestParam String name) {
        var found = characterService.findByName(name);
        return ResponseEntity.ok(characterMapper.toResponseList(found));
    }
}
