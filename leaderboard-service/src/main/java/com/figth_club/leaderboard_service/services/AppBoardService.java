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

    public List<CharacterStatsDTO> getLeaderboardResponseByUserId(Integer idUser) {
        // 1. Récupération des stats de victoires/défaites en base locale
        List<UserStatistic> entities = appBoardRepository.findAllByIdUser(idUser);
        List<UserStatisticDTO> userStats = mapper.toUserStatisticDTOList(entities);

        // 2. Récupération de TOUS les personnages de l'utilisateur (un seul appel réseau)
        Map<Integer, CharacterDetailsDTO> cache = new HashMap<>();
        try {
            // On récupère la liste (car ton endpoint /by-user/{id} renvoie un tableau JSON)
            List<CharacterDetailsDTO> allCharacters = characterServiceClient.getCharacterInfo(idUser);

            // On remplit le cache : Clé = ID du personnage, Valeur = l'objet complet
            if (allCharacters != null) {
                for (CharacterDetailsDTO detail : allCharacters) {
                    cache.put(detail.getId(), detail);
                }
            }
        } catch (Exception e) {
            System.err.println("Erreur lors de la récupération des personnages pour l'user " + idUser + " : " + e.getMessage());
        }

        // 3. Fusion des données (Stats locales + Détails distants)
        List<CharacterStatsDTO> dtoList = new ArrayList<>();
        for (UserStatisticDTO stat : userStats) {
            // On récupère les détails via l'ID du personnage stocké dans la stat
            CharacterDetailsDTO characterDetail = cache.get(stat.getIdCharacter());

            // Le mapper va maintenant avoir un objet 'detail' non null
            CharacterStatsDTO dto = mapper.toCharacterStatsDTO(stat, characterDetail);
            dtoList.add(dto);
        }

        return dtoList;
    }
}