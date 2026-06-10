package com.figth_club.leaderboard_service.services;

import com.figth_club.leaderboard_service.dtos.CharacterStatsDTO;
import com.figth_club.leaderboard_service.dtos.UserStatisticDTO;
import com.figth_club.leaderboard_service.entities.UserStatistic;

import java.util.List;

public interface AppBoardService {

    UserStatistic createStatistic();

    List<UserStatistic> getAllStatistics();

    List<UserStatisticDTO> getLeaderboardByVictories();

    List<UserStatisticDTO> getLeaderboardByDefeats();

    List<UserStatisticDTO> getLeaderboardByCharacterByVictories(Integer id);

    List<UserStatisticDTO> getLeaderboardByCharacterByDefeats(Integer id);

    List<CharacterStatsDTO> getLeaderboardResponseByUserId(Integer idUser);

    void processMatchResult(long winnerId, long loserId);
}
