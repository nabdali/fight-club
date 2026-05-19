package com.fightclub.user_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.fightclub.user_service.entities.UserEntity;

import java.util.Optional;

public interface AppUserRepository extends JpaRepository<UserEntity, Long> {

    boolean existsByEmail(String email);

    boolean existsByPseudo(String pseudo);


}

