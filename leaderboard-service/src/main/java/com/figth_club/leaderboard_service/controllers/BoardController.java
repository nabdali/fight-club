package com.figth_club.leaderboard_service.controllers;

import com.figth_club.leaderboard_service.client.CharacterServiceClient;
import com.figth_club.leaderboard_service.client.dtos.CharacterDetailsDTO;
import com.figth_club.leaderboard_service.dtos.LeaderboardResponseDTO;
import com.figth_club.leaderboard_service.dtos.UserStatisticDTO;
import com.figth_club.leaderboard_service.entities.UserStatistic;
import com.figth_club.leaderboard_service.services.AppBoardService;
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

    private final AppBoardService appBoardService;

    private final CharacterServiceClient characterServiceClient;

    @RequestMapping("/leaderboard")
    public List<UserStatistic> getStats(){
        return appBoardService.getAllStatistics();
    }

    @PostMapping("/create")
    public UserStatistic createStats(){
        return appBoardService.createStatistic();
    }

    @GetMapping("/most-victories")
    public List<UserStatisticDTO> getMostVictories(){
        return appBoardService.getLeaderboardByVictories();
    }

    @GetMapping("/most-defeats")
    public List<UserStatisticDTO> getMostDefeats() {
        return appBoardService.getLeaderboardByDefeats();
    }

    @GetMapping("/most-defeats-character/{id}")
    public List<UserStatisticDTO> getMostDefeatsByIdC(@PathVariable  Integer id) {
        return appBoardService.getLeaderboardByCharacterByDefeats(id);
    }
    @GetMapping("/most-victories-character/{id}")
    public List<UserStatisticDTO> getMostVictoriesByIdC(@PathVariable Integer id) {
        return appBoardService.getLeaderboardByCharacterByVictories(id);
    }

    @GetMapping("/user/{id}/characters")
    public LeaderboardResponseDTO getCharactersByUserId(@PathVariable Integer id) {
        return appBoardService.getLeaderboardResponseByUserId(id);
    }


    @GetMapping("/test/{id}")
    public List<CharacterDetailsDTO> getCallTest(@PathVariable Integer id){
        return characterServiceClient.getCharacterInfo(id);
    }

}
