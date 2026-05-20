package com.fight_club.arena_service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fight_club.arena_service.client.CharacterServiceClient;
import com.fight_club.arena_service.dto.CharacterDTO;
import com.fight_club.arena_service.messaging.FightEventProducer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@Import(TestcontainersConfiguration.class)
class FightIntegrationTest {

    @Autowired
    private WebApplicationContext context;

    @MockitoBean
    private FightEventProducer fightEventProducer;

    @MockitoBean
    private CharacterServiceClient characterServiceClient;

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

        CharacterDTO char1 = new CharacterDTO(1, "Aragorn", new CharacterDTO.CharacterTypeDTO("Tank", 80, 200));
        CharacterDTO char2 = new CharacterDTO(2, "Legolas", new CharacterDTO.CharacterTypeDTO("Archer", 60, 150));
        when(characterServiceClient.getCharacter(eq(1L))).thenReturn(char1);
        when(characterServiceClient.getCharacter(eq(2L))).thenReturn(char2);
    }

    @Test
    void fullFightFlow_startJoinAndGetResult() throws Exception {
        MvcResult startResult = mockMvc.perform(post("/fights/start/1"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.fightId").isNumber())
                .andReturn();

        Map<?, ?> body = objectMapper.readValue(startResult.getResponse().getContentAsString(), Map.class);
        long fightId = ((Number) body.get("fightId")).longValue();

        mockMvc.perform(post("/fights/join/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fightId").value(fightId));

        mockMvc.perform(get("/fights/{id}/result", fightId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fightId").value(fightId))
                .andExpect(jsonPath("$.character1Id").value(1))
                .andExpect(jsonPath("$.character2Id").value(2))
                .andExpect(jsonPath("$.winnerId").isNumber())
                .andExpect(jsonPath("$.status").value("ENDED"))
                .andExpect(jsonPath("$.endedAt").isNotEmpty());

        verify(fightEventProducer).publishFightCreated(any());
        verify(fightEventProducer).publishFightEnded(any());
    }

    @Test
    void joinFight_shouldReturn409WhenNoFightAvailable() throws Exception {
        mockMvc.perform(post("/fights/join/888"))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.description").value("No available fight to join"));
    }

    @Test
    void getFightResult_shouldReturn404WhenNotFound() throws Exception {
        mockMvc.perform(get("/fights/99999/result"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.description").value("Fight not found: 99999"));
    }
}
