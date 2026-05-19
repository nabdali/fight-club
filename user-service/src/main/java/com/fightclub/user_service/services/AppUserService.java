package com.fightclub.user_service.services;

import com.fightclub.user_service.entities.UserDTO;
import com.fightclub.user_service.entities.UserEntity;
import com.fightclub.user_service.exception.custom.InvalidPasswordException;
import com.fightclub.user_service.exception.custom.NotFoundException;
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

    public UserEntity registerUser(UserEntity user) {
        try {
            if (appUserRepository.existsByEmail(user.getEmail()) || appUserRepository.existsByPseudo(user.getPseudo())) {
                throw new UserAlreadyExistsException("L'utilisateur existe déjà en db");
            }
            return appUserRepository.save(user);
        } catch (Error e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public Integer loginUser(String username, String password) {
        UserEntity user = appUserRepository.findUserEntityByPseudo(username);

        if (user == null) {
            throw new NotFoundException("Utilisateur introuvable dans notre système. Vérifiez votre pseudo");
        }

        if (!user.getPassword().equals(password)) {
            throw new InvalidPasswordException("Mot de passe invalide");
        }

        return user.getId();
    }
}
