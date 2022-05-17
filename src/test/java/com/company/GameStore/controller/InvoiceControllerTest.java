package com.company.GameStore.controller;

import com.company.GameStore.models.Invoice;
import com.company.GameStore.service.ServiceLayer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@WebMvcTest(InvoiceController.class)
public class InvoiceControllerTest {

    @MockBean
    ServiceLayer serviceLayer;

    private Invoice inputInvoice;
    private Invoice outputInvoice;
    private String inputInvoiceString;
    private String outputInvoiceString;
    private int invoiceId = 22;
    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        inputInvoice = new Invoice();
        inputInvoice.setName("George");
        inputInvoice.setStreet("Belmont");
        inputInvoice.setCity("Chicago");
        inputInvoice.setState("IL");
        inputInvoice.setZipCode("60645");
        inputInvoice.setItemType("Games");
        inputInvoice.setItemId(2);
        inputInvoice.setUnitPrice(new BigDecimal("59.99"));
        inputInvoice.setQuantity(1);

        outputInvoice = new Invoice();
        outputInvoice.setName("George");
        outputInvoice.setStreet("Belmont");
        outputInvoice.setCity("Chicago");
        outputInvoice.setState("IL");
        outputInvoice.setZipCode("60645");
        outputInvoice.setItemType("Games");
        outputInvoice.setItemId(2);
        outputInvoice.setUnitPrice(new BigDecimal("59.99"));
        outputInvoice.setQuantity(1);
        outputInvoice.setSubtotal(new BigDecimal(""));
    }
}