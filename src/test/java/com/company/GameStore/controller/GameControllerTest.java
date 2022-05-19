package com.company.GameStore.controller;

import com.company.GameStore.models.Game;
import com.company.GameStore.service.ServiceLayer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(GameController.class)
public class GameControllerTest {

    @MockBean
    ServiceLayer serviceLayer;

    private Game gameInput;
    private Game gameOutput;
    private String inputGameString;
    private String outputGameString;
    private List<Game> allGames;
    private String allGamesString;
    private int gameId = 33;
    private int noGameFoundId = 77;
    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {

        gameInput = new Game("Mario", "E", "Adventure game.", new BigDecimal("59.99"), "Nintendo", 500);
        gameOutput = new Game(gameId, "Mario", "E", "Adventure game.", new BigDecimal("59.99"), "Nintendo", 500);
        inputGameString = mapper.writeValueAsString(gameInput);
        outputGameString = mapper.writeValueAsString(gameOutput);
        allGames = Arrays.asList(gameOutput);
        allGamesString = mapper.writeValueAsString(allGames);

        when(serviceLayer.saveGame(gameInput)).thenReturn(gameOutput);
        when(serviceLayer.findAllGames()).thenReturn(allGames);
        when(serviceLayer.findGame(gameId)).thenReturn(gameOutput);
        when(serviceLayer.findGamesByEsrb("E")).thenReturn(allGames);
        when(serviceLayer.findGamesByTitle("Mario")).thenReturn(allGames);
        when(serviceLayer.findGamesByStudio("Nintendo")).thenReturn(allGames);

    }

    @Test
    public void shouldGetGameById() throws Exception {
        mockMvc.perform(get("/games/" + gameId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputGameString));
    }

    @Test
    public void shouldThrowNoGameFoundExceptionForInvalidId() throws Exception {
        mockMvc.perform(get("/games/" + noGameFoundId))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldGetAllGames() throws Exception {
        mockMvc.perform(get("/games"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(allGamesString));
    }

    @Test
    public void shouldGetAllGamesByTitle() throws Exception {
        mockMvc.perform(get("/games" + "?title=Mario"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(allGamesString));
    }

    @Test
    public void shouldGetAllGamesByStudio() throws Exception {
        mockMvc.perform(get("/games" + "?studio=Nintendo"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(allGamesString));
    }

    @Test
    public void shouldGetAllGamesEsrb() throws Exception {
        mockMvc.perform(get("/games" + "?esrb=E"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(allGamesString));
    }

    @Test
    public void shouldCreateAGame() throws Exception {
        mockMvc.perform(post("/games")
                .content(inputGameString)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(outputGameString));
    }

    @Test
    public void shouldUpdateAGame() throws Exception {
        mockMvc.perform(put("/games/" + gameId)
                .content(outputGameString)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldThrowInvalidRequestExceptionWhenAttemptingToUpdateByWrongId() throws Exception {
        mockMvc.perform(put("/games/" + noGameFoundId)
                .content(outputGameString)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void shouldDeleteAGame() throws Exception {
        mockMvc.perform(delete("/games/" + gameId))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

}