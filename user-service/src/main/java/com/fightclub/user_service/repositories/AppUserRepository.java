package com.fightclub.user_service.repositories;

import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import com.fightclub.user_service.entities.AppUserEntity;

import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUserEntity, Long> {

    boolean existsByEmail(String email);

    boolean existsByPseudo(String pseudo);


}

