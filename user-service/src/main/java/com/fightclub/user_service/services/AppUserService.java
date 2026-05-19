package com.fightclub.user_service.services;

import com.fightclub.user_service.error.ErrorCode;
import com.fightclub.user_service.repositories.AppUserRepository;
import lombok.RequiredArgsConstructor;
import com.fightclub.user_service.entities.AppUserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AppUserService {

    private final AppUserRepository appUserRepository;

    public List<AppUserEntity> getUsers() {
        return appUserRepository.findAll();
    }

    public String registerUser(AppUserEntity user) {
        try {
            if (appUserRepository.existsByEmail(user.getEmail())) {
                return ErrorCode.USER_SERVICE_USER_ALREADY_EXISTS;
            }
            if (appUserRepository.existsByPseudo(user.getPseudo())) {
                return "Pseudo déjà utilisé";
            }
            
            appUserRepository.save(user);
            return "User " + user.getPseudo() + " enregistré avec succès";
        } catch (Error e) {
            log.error("Erreur lors de l'inser de l'utilisateur", e);
            return "Erreur lors de l'insert";
        }
    }
}
