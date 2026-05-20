package com.fight_club.arena_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fight_club.arena_service.dto.FightResultDTO;
import com.fight_club.arena_service.entity.FightStatus;
import com.fight_club.arena_service.exception.ArenaExceptionHandler;
import com.fight_club.arena_service.service.FightService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class FightControllerTest {

    @Mock
    private FightService fightService;

    @InjectMocks
    private FightController fightController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        ObjectMapper mapper = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mockMvc = MockMvcBuilders.standaloneSetup(fightController)
                .setControllerAdvice(new ArenaExceptionHandler())
                .setMessageConverters(new MappingJackson2HttpMessageConverter(mapper))
                .build();
    }

    @Test
    void startFight_shouldReturn201WithFightId() throws Exception {
        when(fightService.startFight(1L)).thenReturn(42L);

        mockMvc.perform(post("/fights/start/1"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.fightId").value(42));
    }

    @Test
    void joinFight_shouldReturn200WithFightId() throws Exception {
        when(fightService.joinFight(2L)).thenReturn(42L);

        mockMvc.perform(post("/fights/join/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fightId").value(42));
    }

    @Test
    void getFightResult_shouldReturnResult() throws Exception {
        LocalDateTime now = LocalDateTime.now();
        FightResultDTO dto = new FightResultDTO(42L, 1L, 2L, 1L, FightStatus.ENDED, now.minusMinutes(1), now);
        when(fightService.getFightResult(42L)).thenReturn(dto);

        mockMvc.perform(get("/fights/42/result"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fightId").value(42))
                .andExpect(jsonPath("$.character1Id").value(1))
                .andExpect(jsonPath("$.character2Id").value(2))
                .andExpect(jsonPath("$.winnerId").value(1))
                .andExpect(jsonPath("$.status").value("ENDED"));
    }

    @Test
    void getFightResult_shouldReturn404WhenNotFound() throws Exception {
        when(fightService.getFightResult(99L)).thenThrow(new IllegalArgumentException("Fight not found: 99"));

        mockMvc.perform(get("/fights/99/result"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.description").value("Fight not found: 99"));
    }
}
