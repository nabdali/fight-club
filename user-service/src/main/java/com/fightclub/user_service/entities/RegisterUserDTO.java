package com.fightclub.user_service.entities;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@AllArgsConstructor
@Data
public class RegisterUserDTO {

    @NotBlank(message = "Email obligatoire")
    @Email(message = " Format de l'email invalide")
    private String email;

    @NotBlank(message = "Le pseudo est obligatoire")
    private String pseudo;

}
