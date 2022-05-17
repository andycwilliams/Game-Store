package com.company.GameStore.controller;

import com.company.GameStore.models.Console;
import com.company.GameStore.models.Game;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ConsoleController.class)
public class ConsoleControllerTest {

    Set<Game> gameSet;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper mapper = new ObjectMapper();

    @Before
    public void setUp() throws Exception {
        Set<Game> inputGames = new HashSet<Game>();
        inputGames.add(new Game("God of War", "MA", "Father and son adventure.", new BigDecimal("59.99"), "Santa Monica", 100));
        Console inputConsole = new Console(1, "Model", "Sony", "100", "Processor", new BigDecimal("499.99"), 7);

    }

//    @Test
//    public void shouldGetAllConsoles() throws Exception {
//        mockMvc.perform(get("/console"))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(content().json(allConsolesString));
//    }

}