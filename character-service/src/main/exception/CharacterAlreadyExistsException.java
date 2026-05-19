package com.fightclub.character_service.exception;

public class CharacterAlreadyExistsException extends RuntimeException {
    public CharacterAlreadyExistsException(String message) { super(message); }
}
