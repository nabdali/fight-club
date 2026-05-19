package com.fightclub.user_service.exception;

import com.fightclub.user_service.exception.custom.InvalidPasswordException;
import com.fightclub.user_service.exception.custom.NotFoundException;
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

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorMessageDto> handleNotFoundException(NotFoundException exception) {
        return new ResponseEntity<>(
                ErrorMessageDto.builder().message("Ressource non trouvée.").description(exception.getMessage()).build(),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<ErrorMessageDto> handleInvalidPasswordException(InvalidPasswordException exception) {
        return new ResponseEntity<>(
                ErrorMessageDto.builder().message("Password invalid.").description(exception.getMessage()).build(),
                HttpStatus.BAD_REQUEST);
    }

    @Builder
    record ErrorMessageDto(String message, String description) {
    }
}
