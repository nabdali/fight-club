package com.fightclub.user_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.fightclub.user_services.entities.AppUserEntity;

public interface AppUserRepository extends JpaRepository<AppUserEntity, Long> {
}
