package com.fight_club.arena_service;

import org.springframework.boot.SpringApplication;

public class TestArenaServiceApplication {

	public static void main(String[] args) {
		SpringApplication.from(ArenaServiceApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
