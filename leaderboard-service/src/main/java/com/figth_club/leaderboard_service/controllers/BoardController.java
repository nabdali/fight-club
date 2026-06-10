package com.figth_club.leaderboard_service.controllers;

import com.figth_club.leaderboard_service.client.CharacterServiceClient;
import com.figth_club.leaderboard_service.dtos.CharacterStatsDTO;
import com.figth_club.leaderboard_service.dtos.UserStatisticDTO;
import com.figth_club.leaderboard_service.entities.UserStatistic;
import com.figth_club.leaderboard_service.messaging.LeaderBoardEventProducer;
import com.figth_club.leaderboard_service.services.AppBoardServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
@RequestMapping("/api")
@RequiredArgsConstructor
public class BoardController {

    private final AppBoardServiceImpl appBoardServiceImpl;
    private final LeaderBoardEventProducer leaderBoardEventProducer;


    private final CharacterServiceClient characterServiceClient;

    @RequestMapping("/leaderboard")
    public List<UserStatistic> getStats(){
        return appBoardServiceImpl.getAllStatistics();
    }

    @PostMapping("/create")
    public UserStatistic createStats(){
        return appBoardServiceImpl.createStatistic();
    }

    @GetMapping("/most-victories")
    public List<UserStatisticDTO> getMostVictories(){
        return appBoardServiceImpl.getLeaderboardByVictories();
    }

    @GetMapping("/most-defeats")
    public List<UserStatisticDTO> getMostDefeats() {
        return appBoardServiceImpl.getLeaderboardByDefeats();
    }

    @GetMapping("/most-defeats-character/{id}")
    public List<UserStatisticDTO> getMostDefeatsByIdC(@PathVariable  Integer id) {
        return appBoardServiceImpl.getLeaderboardByCharacterByDefeats(id);
    }
    @GetMapping("/most-victories-character/{id}")
    public List<UserStatisticDTO> getMostVictoriesByIdC(@PathVariable Integer id) {
        return appBoardServiceImpl.getLeaderboardByCharacterByVictories(id);
    }

    @GetMapping("/user/{id}/characters")
    public List<CharacterStatsDTO> getCharactersByUserId(@PathVariable Integer id) {
        return appBoardServiceImpl.getLeaderboardResponseByUserId(id);
    }
}
