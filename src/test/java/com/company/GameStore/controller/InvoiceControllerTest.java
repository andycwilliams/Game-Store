package com.company.GameStore.controller;

import com.company.GameStore.models.Game;
import com.company.GameStore.models.Invoice;
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

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(InvoiceController.class)
public class InvoiceControllerTest {

    @MockBean
    ServiceLayer serviceLayer;

    private Invoice inputInvoice;
    private Invoice outputInvoice;
    private Game game;
    private String inputInvoiceString;
    private String outputInvoiceString;
    private int invoiceId = 22;
    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        game = new Game();
        game.setGame_id(2);
        game.setTitle("God of War");
        game.setEsrbRating("MA");
        game.setDescription("Father and son adventure.");
        game.setPrice(new BigDecimal("59.99"));
        game.setStudio("Santa Monica");
        game.setQuantity(100);

        inputInvoice = new Invoice();
        inputInvoice.setName("George");
        inputInvoice.setStreet("Belmont");
        inputInvoice.setCity("Chicago");
        inputInvoice.setState("IL");
        inputInvoice.setZipCode("60645");
        inputInvoice.setItemType("Games");
        inputInvoice.setItemId(2);
        inputInvoice.setQuantity(1);

        outputInvoice = new Invoice();
        outputInvoice.setId(invoiceId);
        outputInvoice.setName("George");
        outputInvoice.setStreet("Belmont");
        outputInvoice.setCity("Chicago");
        outputInvoice.setState("IL");
        outputInvoice.setZipCode("60645");
        outputInvoice.setItemType("Games");
        outputInvoice.setItemId(2);
        outputInvoice.setUnitPrice(new BigDecimal("59.99"));
        outputInvoice.setQuantity(1);
        outputInvoice.setSubtotal(new BigDecimal("59.99"));
        outputInvoice.setTax(new BigDecimal(".05"));
        outputInvoice.setProcessingFee(new BigDecimal("1.49"));
        outputInvoice.setTotal(new BigDecimal("61.53"));

        inputInvoiceString = mapper.writeValueAsString(inputInvoice);
        outputInvoiceString = mapper.writeValueAsString(outputInvoice);

        when(serviceLayer.saveInvoice(inputInvoice)).thenReturn(outputInvoice);
    }

    @Test
    public void shouldCreateAnInvoice() throws Exception {
//        outputInvoice.setQuantity(2);
//        outputInvoiceString = mapper.writeValueAsString(outputInvoice);
//
//        Invoice testInvoice = new Invoice();
//        String testInvoiceString = mapper.writeValueAsString(testInvoice);


        mockMvc.perform(post("/invoice")
                .content(inputInvoiceString)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(outputInvoiceString));
    }
}