package com.figth_club.leaderboard_service.repositories;

import com.figth_club.leaderboard_service.entities.UserStatistic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppBoardRepository extends JpaRepository<UserStatistic, Integer> {
    List<UserStatistic> findAllByOrderByVictoryCounterDesc();

    List<UserStatistic> findAllByOrderByDefeatCounterDesc();

    List<UserStatistic> findAllByIdCharacterOrderByVictoryCounterDesc(Integer idCharacter);

    List<UserStatistic> findAllByIdCharacterOrderByDefeatCounterDesc(Integer idCharacter);


}
