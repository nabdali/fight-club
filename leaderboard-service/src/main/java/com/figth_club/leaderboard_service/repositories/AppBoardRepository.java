package com.figth_club.leaderboard_service.repositories;

import com.figth_club.leaderboard_service.entities.UserStatistic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppBoardRepository extends JpaRepository<UserStatistic, Long> {
}
