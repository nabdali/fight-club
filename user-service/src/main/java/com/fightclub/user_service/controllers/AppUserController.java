package com.fightclub.user_service.controllers;

import com.fightclub.user_service.entities.RegisterUserDTO;
import com.fightclub.user_service.services.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fightclub.user_service.entities.AppUserEntity;

import java.util.List;

@RestController()
@RequestMapping("/users")
@RequiredArgsConstructor
public class AppUserController {

    private final AppUserService appUserService;


    @GetMapping("/")
    public List<AppUserEntity> getUsers() {
        return appUserService.getUsers();
    }


    @PostMapping("/register")
    public String register(RegisterUserDTO dto) {
        AppUserEntity entity = AppUserEntity.builder()
                .email(dto.getEmail())
                .pseudo(dto.getPseudo())
                .password("test")
                .build();

        return appUserService.registerUser(entity);
    }
}
