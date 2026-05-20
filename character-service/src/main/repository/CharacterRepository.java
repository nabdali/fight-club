package com.fightclub.character_service.repository;

import com.fightclub.character_service.entity.Character;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CharacterRepository extends JpaRepository<Character, Long> {
    boolean existsByUserIdAndCharacterType_Name(Long userId, String characterTypeName);
}
