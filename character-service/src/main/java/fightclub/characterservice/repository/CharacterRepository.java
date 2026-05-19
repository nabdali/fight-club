package fightclub.characterservice.repository;

import fightclub.characterservice.entities.Character;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CharacterRepository extends JpaRepository<Character, Long> {

    boolean existsByUserIdAndCharacterType_Name(Long userId, String name);

    List<Character> findByNameContainingIgnoreCase(String name);
}
