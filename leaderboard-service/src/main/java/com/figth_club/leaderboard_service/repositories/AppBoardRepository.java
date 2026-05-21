package com.figth_club.leaderboard_service.repositories;

import com.figth_club.leaderboard_service.dtos.UserStatisticDTO;
import com.figth_club.leaderboard_service.entities.UserStatistic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppBoardRepository extends JpaRepository<UserStatistic, Integer> {
    List<UserStatisticDTO> findAllByOrderByVictoryCounterDesc();

    List<UserStatisticDTO> findAllByOrderByDefeatCounterDesc();

    List<UserStatisticDTO> findAllByIdCharacterOrderByVictoryCounterDesc(Integer idCharacter);

    List<UserStatisticDTO> findAllByIdCharacterOrderByDefeatCounterDesc(Integer idCharacter);

    List<UserStatisticDTO> findAllByIdUser(Integer idUser);




}
