package fightclub.characterservice.controllers;

import fightclub.characterservice.dto.CharacterCreateRequest;
import fightclub.characterservice.dto.CharacterDetailResponse;
import fightclub.characterservice.dto.CharacterResponse;
import fightclub.characterservice.mapper.CharacterMapper;
import fightclub.characterservice.service.CharacterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/characters")
@RequiredArgsConstructor
public class CharacterController {

    private final CharacterService characterService;
    private final CharacterMapper characterMapper;

    @PostMapping
    public CharacterResponse createCharacter(@Valid @RequestBody CharacterCreateRequest request) {
        var created = characterService.createCharacter(request);
        return characterMapper.toResponse(created);
    }

    @GetMapping("/")
    public List<CharacterResponse> getAllCharacters() {
        return characterMapper.toResponseList(characterService.getAll());
    }

    @GetMapping("/search")
    public List<CharacterResponse> searchByName(@RequestParam String name) {
        var found = characterService.findByName(name);
        return characterMapper.toResponseList(found);
    }

    @GetMapping("/{characterId}")
    public CharacterDetailResponse getCharacterById(@PathVariable Long characterId) {
        var character = characterService.getById(characterId);
        return characterMapper.toDetailResponse(character);
    }
    @GetMapping("/by-user/{userId}")
    public  List<CharacterResponse> getCharacterByUserId(@PathVariable Long userId) {
        return characterService.getCharacterByUserId(userId);
    }
}
