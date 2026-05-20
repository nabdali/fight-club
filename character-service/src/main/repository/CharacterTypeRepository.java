package com.fightclub.character_service.repository;

import com.fightclub.character_service.entity.CharacterType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CharacterTypeRepository extends JpaRepository<CharacterType, Long> {
    Optional<CharacterType> findByNameIgnoreCase(String name);
}
