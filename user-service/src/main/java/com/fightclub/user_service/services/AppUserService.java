package com.fightclub.user_service.services;

import com.fightclub.user_service.client.LeaderBoardServiceClient;
import com.fightclub.user_service.entities.UserEntity;
import com.fightclub.user_service.entities.dto.CharacterStatsDTO;
import com.fightclub.user_service.entities.dto.UserStatisticsDTO;
import com.fightclub.user_service.exception.custom.InvalidPasswordException;
import com.fightclub.user_service.exception.custom.NotFoundException;
import com.fightclub.user_service.exception.custom.UserAlreadyExistsException;
import com.fightclub.user_service.mapper.UserMapper;
import com.fightclub.user_service.repositories.AppUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.fightclub.user_service.error.ErrorCode.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class AppUserService {

    private final AppUserRepository appUserRepository;
    private final LeaderBoardServiceClient leaderBoardServiceClient;
    private final UserMapper userMapper;

    public List<UserEntity> getUsers() {
        return appUserRepository.findAll();
    }

    public UserEntity registerUser(UserEntity user) {
        try {
            if (appUserRepository.existsByEmail(user.getEmail()) || appUserRepository.existsByPseudo(user.getPseudo())) {
                throw new UserAlreadyExistsException(USER_SERVICE_USER_ALREADY_EXISTS);
            }
            return appUserRepository.save(user);
        } catch (Error e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public Integer loginUser(String username, String password) {
        UserEntity user = appUserRepository.findUserEntityByPseudo(username)
                .orElseThrow(() -> new NotFoundException(USER_SERVICE_USER_NOT_FOUND));

        if (!user.getPassword().equals(password)) {
            throw new InvalidPasswordException(USER_SERVICE_INVALID_PASSWORD);
        }

        return user.getId();
    }

    public UserStatisticsDTO getUserWithStatistics(Integer userId) {
        UserEntity user = appUserRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(USER_SERVICE_USER_NOT_FOUND));

        List<CharacterStatsDTO> characterStats;
        try {
            characterStats = leaderBoardServiceClient.getUsersStatistics(userId);
        } catch (NotFoundException e) {
            characterStats = List.of();
        }

        return userMapper.toDto(user, characterStats);
    }

}
