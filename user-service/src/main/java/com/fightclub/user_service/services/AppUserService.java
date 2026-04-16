package com.fightclub.user_service.services;

import com.fightclub.user_service.repositories.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.fightclub.user_services.entities.AppUserEntity;

@Service
@RequiredArgsConstructor
public class AppUserService {

    private final AppUserRepository appUserRepository;

    public String registerUser(AppUserEntity user) {
        return "yo";
    }


}
