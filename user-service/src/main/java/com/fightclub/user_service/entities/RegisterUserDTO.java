package com.fightclub.user_service.entities;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

@Builder
@AllArgsConstructor
public class RegisterUserDTO {

    @NotBlank(message = "email obligatoire")
    @Email(message = " format invalide")
    private String email;

    @NotBlank(message = "pseudo obligatoire")
    private String pseudo;

}
