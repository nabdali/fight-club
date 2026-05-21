package com.fightclub.user_service.entities.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@AllArgsConstructor
@Data
public class UserDTO {

    @Nullable
    private Integer id;

    @NotBlank(message = "Email obligatoire")
    @Email(message = " Format de l'email invalide")
    private String email;

    @NotBlank(message = "Pseudo obligatoire")
    private String pseudo;
}
