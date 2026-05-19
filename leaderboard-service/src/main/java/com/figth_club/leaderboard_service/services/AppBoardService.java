package com.figth_club.leaderboard_service.services;

import com.figth_club.leaderboard_service.dtos.CharacterStatsDTO;
import com.figth_club.leaderboard_service.dtos.LeaderboardResponseDTO;
import com.figth_club.leaderboard_service.entities.UserStatistic;
import com.figth_club.leaderboard_service.mappers.LeaderboardMapper;
import com.figth_club.leaderboard_service.repositories.AppBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.concurrent.ThreadLocalRandom;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppBoardService{
    private final AppBoardRepository appBoardRepository;
    private final LeaderboardMapper mapper;

    //Create line for statistic (for test)
    public UserStatistic createStatistic() {
        UserStatistic newStats = createNewStatistic();
        return appBoardRepository.save(newStats);
    }

    private UserStatistic createNewStatistic() {
        UserStatistic newStats = new UserStatistic();
        int randomIdUser = ThreadLocalRandom.current().nextInt(0, 100);
        int randomIdCharacter = ThreadLocalRandom.current().nextInt(0, 2);
        int randomVictories = ThreadLocalRandom.current().nextInt(0, 51);
        int randomDefeats = ThreadLocalRandom.current().nextInt(0, 51);
        newStats.setIdUser(randomIdUser);
        newStats.setIdCharacter(randomIdCharacter);
        newStats.setVictoryCounter(randomVictories);
        newStats.setDefeatCounter(randomDefeats);
        return newStats;
    }

    //Get all statistics
    public List<UserStatistic> getAllStatistics() {
        return appBoardRepository.findAll();
    }

    //Get leaderboard by victories
    public List<UserStatistic> getLeaderboardByVictories() {
        return appBoardRepository.findAllByOrderByVictoryCounterDesc();
    }

    //Get leaderboard by defeats
    public List<UserStatistic> getLeaderboardByDefeats() {
        return appBoardRepository.findAllByOrderByDefeatCounterDesc();
    }

    //Get leaderboard by an idCharacter and by victories
    public List<UserStatistic> getLeaderboardByCharacterByVictories(Integer id){
        return appBoardRepository.findAllByIdCharacterOrderByVictoryCounterDesc(id);
    }


    //Get leaderboard by an idCharacter and by defeats
    public List<UserStatistic> getLeaderboardByCharacterByDefeats(Integer id){
        return appBoardRepository.findAllByIdCharacterOrderByDefeatCounterDesc(id);
    }

    public LeaderboardResponseDTO getLeaderboardResponseByUserId(Integer idUser) {

        List<UserStatistic> userStats = appBoardRepository.findAllByIdUser(idUser);

        List<CharacterStatsDTO> dtoList = mapper.toCharacterStatsDTOList(userStats);

        return new LeaderboardResponseDTO(dtoList);
    }
}
