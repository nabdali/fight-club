package fightclub.characterservice.repository;

import fightclub.characterservice.entities.CharacterType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CharacterTypeRepository extends JpaRepository<CharacterType, Long> {
    Optional<CharacterType> findByNameIgnoreCase(String name);
}
