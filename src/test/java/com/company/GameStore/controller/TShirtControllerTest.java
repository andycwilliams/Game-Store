package com.company.GameStore.controller;

import com.company.GameStore.models.TShirt;
import com.company.GameStore.repository.TShirtRepository;
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
import java.util.Optional;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TShirtController.class)
public class TShirtControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    ServiceLayer serviceLayer;

    private TShirt inputTShirt;
    private String inputTShirtString;
    private String outputTShirtString;
    private List<TShirt> allTShirts;
    private String allTShirtsString;
    private int tShirtId = 1;
    private String color = "Blue";
    private String size = "Large";
    private int nonExistentTShirtId = 435;
    private  BigDecimal price = BigDecimal.valueOf(10.99);

    private ObjectMapper mapper = new ObjectMapper();

    @Before
    public void setUp() throws Exception {
        inputTShirt = new TShirt(1, "Medium", "Red", "This shirt is red.", price, 50);
        TShirt outputTShirt = new TShirt(tShirtId, "Medium", "Red", "This shirt is red.", price, 50);
        inputTShirtString = mapper.writeValueAsString(inputTShirt);
        outputTShirtString = mapper.writeValueAsString(outputTShirt);
        allTShirts = Arrays.asList(outputTShirt);
        allTShirtsString = mapper.writeValueAsString(allTShirts);

        when(serviceLayer.saveTShirt(inputTShirt)).thenReturn(outputTShirt);
        when(serviceLayer.findAllTShirts()).thenReturn(allTShirts);
        when(serviceLayer.findTShirt(tShirtId)).thenReturn(outputTShirt);
        when(serviceLayer.findTShirtBySize("Large")).thenReturn(allTShirts);
        ///////
        when(serviceLayer.findTShirtByColor("Blue")).thenReturn(allTShirts);

    }

    @Test
    public void shouldCreateTShirt() throws Exception {
        mockMvc.perform(post("/tshirts")
                        .content(inputTShirtString)
                        .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(outputTShirtString));
    }

    @Test
    public void shouldGetAllTShirts() throws Exception {
        mockMvc.perform(get("/tshirts"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(allTShirtsString));
    }

    @Test
    public void shouldGetTById() throws Exception {
        mockMvc.perform(get("/tshirts/" + tShirtId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputTShirtString));
    }

    @Test
    public void shouldUpdateTShirts() throws Exception {
        mockMvc.perform(put("/tshirts/" + tShirtId)
                        .content(outputTShirtString)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldDeleteTShirts() throws Exception {
        mockMvc.perform(delete("/tshirts/" + tShirtId))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    //Errors
    @Test
    public void shouldReport404WhenFindTShirtByInvalidId() throws Exception {
        mockMvc.perform(get("/tshirts/" + "12335"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldBeUnprocessableEntityWhenPutRequestContainsNonMatchingIds() throws Exception {
        mockMvc.perform(put("/tshirts/" + nonExistentTShirtId)
                        .content(outputTShirtString)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void shouldReturnAllTShirtsBySize() throws Exception {
        mockMvc.perform(get("/tshirts/size/" + size))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(allTShirtsString)
                );
    }

    @Test
    public void shouldReturnAllTShirtsByColor() throws Exception {
        mockMvc.perform(get("/tshirts/color/" + color))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(allTShirtsString)
                );
    }

//end
}
