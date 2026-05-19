package com.figth_club.leaderboard_service.controllers;

import com.figth_club.leaderboard_service.dtos.LeaderboardResponseDTO;
import com.figth_club.leaderboard_service.entities.UserStatistic;
import com.figth_club.leaderboard_service.services.AppBoardService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
@RequestMapping("/api")
public class BoardController {

    private final AppBoardService appBoardService;

    public BoardController(AppBoardService appBoardService) {
        this.appBoardService = appBoardService;
    }

    @RequestMapping("/leaderboard")
    public List<UserStatistic> getStats(){
        return appBoardService.getAllStatistics();
    }

    @PostMapping("/create")
    public UserStatistic createStats() {
        return appBoardService.createStatistic();
    }

    @GetMapping("/most-victories")
    public List<UserStatistic> getMostVictories() {
        return appBoardService.getLeaderboardByVictories();
    }

    @GetMapping("/most-defeats")
    public List<UserStatistic> getMostDefeats() {
        return appBoardService.getLeaderboardByDefeats();
    }
    @GetMapping("/most-defeats-character/id")
    public List<UserStatistic> getMostDefeatsByIdC(@RequestParam Integer id) {
        return appBoardService.getLeaderboardByCharacterByDefeats(id);
    }
    @GetMapping("/most-victories-character/id")
    public List<UserStatistic> getMostVictoriesByIdC(@RequestParam Integer id) {
        return appBoardService.getLeaderboardByCharacterByVictories(id);
    }

//    @GetMapping("/characters/id")
//    public LeaderboardResponseDTO getCharactersByUserId(@RequestParam Integer id){
//        return appBoardService.
//    }

    @GetMapping("/user/characters")
    public LeaderboardResponseDTO getCharactersByUserId(@RequestParam Integer id) {
        return appBoardService.getLeaderboardResponseByUserId(id);
    }

}
