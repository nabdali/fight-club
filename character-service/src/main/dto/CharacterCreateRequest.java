package com.fightclub.character_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CharacterCreateRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String characterTypeName;

    @NotNull
    private Long userId;

    public String getName() { return name; }
    public String getCharacterTypeName() { return characterTypeName; }
    public Long getUserId() { return userId; }

    public void setName(String name) { this.name = name; }
    public void setCharacterTypeName(String characterTypeName) { this.characterTypeName = characterTypeName; }
    public void setUserId(Long userId) { this.userId = userId; }
}
