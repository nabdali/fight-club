package com.figth_club.leaderboard_service.repositories;

import com.figth_club.leaderboard_service.entities.UserStatistic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppBoardRepository extends JpaRepository<UserStatistic, Integer> {

    // Renvoyer des entités brutes à chaque fois pour laisser MapStruct gérer le mapping dans le Service
    List<UserStatistic> findAllByOrderByVictoryCounterDesc();

    List<UserStatistic> findAllByOrderByDefeatCounterDesc();

    List<UserStatistic> findAllByIdCharacterOrderByVictoryCounterDesc(Integer idCharacter);

    List<UserStatistic> findAllByIdCharacterOrderByDefeatCounterDesc(Integer idCharacter);

    List<UserStatistic> findAllByIdUser(Integer idUser);

    Optional<UserStatistic> findByIdCharacter(Integer idCharacter);
}