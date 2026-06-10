package com.fight_club.arena_service.controller;

import com.fight_club.arena_service.dto.FightResultDTO;
import com.fight_club.arena_service.service.FightService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/arena")
@RequiredArgsConstructor
public class FightController {

    private final FightService fightService;

    @PostMapping("/start/{characterId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, Long> startFight(@PathVariable Long characterId) {
        Long fightId = fightService.startFight(characterId);
        return Map.of("fightId", fightId);
    }

    @PostMapping("/join/{characterId}")
    public Map<String, Long> joinFight(@PathVariable Long characterId) {
        Long fightId = fightService.joinFight(characterId);
        return Map.of("fightId", fightId);
    }

    @GetMapping("/{fightId}/result")
    public FightResultDTO getFightResult(@PathVariable Long fightId) {
        return fightService.getFightResult(fightId);
    }
}
