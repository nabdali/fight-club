package com.fight_club.arena_service.dto;

public record CharacterDTO(
        Integer userId,
        String name,
        CharacterTypeDTO type
) {
    public record CharacterTypeDTO(
            String name,
            Integer strength,
            Integer health
    ) {}
}
