package com.figth_club.leaderboard_service.services;

import com.figth_club.leaderboard_service.client.CharacterServiceClient;
import com.figth_club.leaderboard_service.client.dtos.CharacterDetailsDTO;
import com.figth_club.leaderboard_service.dtos.CharacterStatsDTO;
import com.figth_club.leaderboard_service.dtos.LeaderboardResponseDTO;
import com.figth_club.leaderboard_service.dtos.UserStatisticDTO;
import com.figth_club.leaderboard_service.entities.UserStatistic;
import com.figth_club.leaderboard_service.mappers.LeaderboardMapper;
import com.figth_club.leaderboard_service.repositories.AppBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AppBoardService {
    private final AppBoardRepository appBoardRepository;
    private final LeaderboardMapper mapper;
    private final CharacterServiceClient characterServiceClient;

    // Create line for statistic (for test)
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

    // Get all statistics
    public List<UserStatistic> getAllStatistics() {
        return appBoardRepository.findAll();
    }
    
    public List<UserStatisticDTO> getLeaderboardByVictories() {
        List<UserStatistic> entities = appBoardRepository.findAllByOrderByVictoryCounterDesc();
        return mapper.toUserStatisticDTOList(entities);
    }

    public List<UserStatisticDTO> getLeaderboardByDefeats() {
        List<UserStatistic> entities = appBoardRepository.findAllByOrderByDefeatCounterDesc();
        return mapper.toUserStatisticDTOList(entities);
    }

    public List<UserStatisticDTO> getLeaderboardByCharacterByVictories(Integer id) {
        List<UserStatistic> entities = appBoardRepository.findAllByIdCharacterOrderByVictoryCounterDesc(id);
        return mapper.toUserStatisticDTOList(entities);
    }

    public List<UserStatisticDTO> getLeaderboardByCharacterByDefeats(Integer id) {
        List<UserStatistic> entities = appBoardRepository.findAllByIdCharacterOrderByDefeatCounterDesc(id);
        return mapper.toUserStatisticDTOList(entities);
    }

    public LeaderboardResponseDTO getLeaderboardResponseByUserId(Integer idUser) {
        List<UserStatistic> entities = appBoardRepository.findAllByIdUser(idUser);

        List<UserStatisticDTO> userStats = mapper.toUserStatisticDTOList(entities);

        Map<Integer, CharacterDetailsDTO> cache = new HashMap<>();
        List<CharacterStatsDTO> dtoList = new ArrayList<>();

        for (UserStatisticDTO stat : userStats) {
            Integer characterId = stat.getIdCharacter();
            if (characterId != null && !cache.containsKey(characterId)) {
                try {
                    CharacterDetailsDTO details = characterServiceClient.getCharacterInfo(characterId);
                    cache.put(characterId, details);
                } catch (RuntimeException e) {
                    System.err.println("Impossible de récupérer le personnage " + characterId + " : " + e.getMessage());
                }
            }
        }

        for (UserStatisticDTO stat : userStats) {
            CharacterDetailsDTO characterDetail = cache.get(stat.getIdCharacter());
            CharacterStatsDTO dto = mapper.toCharacterStatsDTO(stat, characterDetail);
            dtoList.add(dto);
        }

        return new LeaderboardResponseDTO(dtoList);
    }
}