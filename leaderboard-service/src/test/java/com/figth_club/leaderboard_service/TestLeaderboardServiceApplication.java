package com.figth_club.leaderboard_service;

import org.springframework.boot.SpringApplication;

public class TestLeaderboardServiceApplication {

	public static void main(String[] args) {
		SpringApplication.from(LeaderboardServiceApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
