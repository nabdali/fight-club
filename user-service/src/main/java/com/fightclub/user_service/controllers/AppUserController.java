package com.fightclub.user_service.controllers;

import com.fightclub.user_service.services.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fightclub.user_services.entities.AppUserEntity;

@RestController()
@RequestMapping("/user")
@RequiredArgsConstructor
public class AppUserController {

    AppUserService appUserService;

    @PostMapping("/register")
    public String registerUser(AppUserEntity user) {
        return "cccc";
    }

}
