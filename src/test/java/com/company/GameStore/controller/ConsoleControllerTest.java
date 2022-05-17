package com.company.GameStore.controller;

import com.company.GameStore.models.Console;
import com.company.GameStore.service.ServiceLayer;
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

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ConsoleController.class)
public class ConsoleControllerTest {

    @MockBean
    ServiceLayer serviceLayer;

    private Console consoleInput;
    private Console consoleOutput;
    private String inputConsoleString;
    private String outputConsoleString;
    private List<Console> allConsoles;
    private String allConsolesString;

    private String manufacturer = "Sony";
    private int consoleId = 77;
    private int badConsoleId = 444;
    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {

        consoleInput = new Console("Playstation 5", "Sony", "100", "Processor", new BigDecimal("499.99"), 7);
        consoleOutput = new Console(consoleId,"Playstation 5", "Sony", "100", "Processor", new BigDecimal("499.99"), 7);
        inputConsoleString = mapper.writeValueAsString(consoleInput);
        outputConsoleString = mapper.writeValueAsString(consoleOutput);
        allConsoles = Arrays.asList(consoleOutput);
        allConsolesString = mapper.writeValueAsString(allConsoles);

        when(serviceLayer.addConsole(consoleInput)).thenReturn(consoleOutput);
        when(serviceLayer.findAllConsoles()).thenReturn(allConsoles);
        when(serviceLayer.findConsole(consoleId)).thenReturn(consoleOutput);
    }

    @Test
    public void shouldGetAllConsoles() throws Exception {
        mockMvc.perform(get("/console/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(allConsolesString));
    }

    @Test
    public void shouldGetConsoleById() throws Exception {
        mockMvc.perform(get("/console/" + consoleId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputConsoleString));
    }

    @Test
    public void shouldAddConsole() throws Exception {
        mockMvc.perform(post("/console/")
                    .content(inputConsoleString)
                    .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(outputConsoleString));
    }

    @Test
    public void shouldUpdateConsole() throws Exception {
        mockMvc.perform(put("/console/" + consoleId)
                    .content(outputConsoleString)
                    .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldDeleteConsole() throws Exception {
        mockMvc.perform(delete("/console/" + consoleId))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldGet404WhenGetConsoleByInvalidId() throws Exception {
        mockMvc.perform(get("/console/" + badConsoleId))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldGetUnprocessableEntityWhenPutConsoleByInvalidId() throws Exception {
        mockMvc.perform(put("/console/" + badConsoleId)
                        .content(outputConsoleString)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }
}