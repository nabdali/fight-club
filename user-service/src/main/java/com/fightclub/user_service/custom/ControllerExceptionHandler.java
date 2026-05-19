package com.fightclub.user_service.custom;

import com.fightclub.user_service.exception.custom.UserAlreadyExistsException;
import lombok.Builder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorMessageDto> handleUserException(UserAlreadyExistsException exception) {
        return new ResponseEntity<>(
                ErrorMessageDto.builder().message("Impossible de créer l'utilisateur").description(exception.getMessage()).build(),
                HttpStatus.BAD_REQUEST);
    }

    @Builder
    record ErrorMessageDto(String message, String description) {
    }
}
