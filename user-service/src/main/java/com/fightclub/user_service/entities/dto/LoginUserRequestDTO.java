package com.fightclub.user_service.entities.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginUserRequestDTO {

    @NotBlank(message = "Pseudo obligatoire")
    private String pseudo;

    @NotBlank(message = "Password obligatoire")
    private String password;

}
