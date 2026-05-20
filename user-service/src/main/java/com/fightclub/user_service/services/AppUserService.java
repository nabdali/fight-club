package com.fightclub.user_service.services;

import com.fightclub.user_service.entities.UserEntity;
import com.fightclub.user_service.exception.custom.UserAlreadyExistsException;
import com.fightclub.user_service.repositories.AppUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@ControllerAdvice
public class AppUserService {

    private final AppUserRepository appUserRepository;

    public List<UserEntity> getUsers() {
        return appUserRepository.findAll();
    }

    public String registerUser(UserEntity user) {
        try {
            if (appUserRepository.existsByEmail(user.getEmail()) || appUserRepository.existsByPseudo(user.getPseudo())) {
                throw new UserAlreadyExistsException("L'utilisateur existe déjà en db");
            }
            UserEntity userEntity = appUserRepository.save(user);
            return userEntity.getEmail();
        } catch (Error e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
