package com.fight_club.arena_service;

import com.fight_club.arena_service.messaging.FightEventProducer;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class ArenaServiceApplicationTests {

	@MockitoBean
	FightEventProducer fightEventProducer;

	@Test
	void contextLoads() {
	}

}
