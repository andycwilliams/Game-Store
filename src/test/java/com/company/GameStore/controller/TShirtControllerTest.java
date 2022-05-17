package com.company.GameStore.controller;

import com.company.GameStore.repository.TShirtRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(GameController.class)
public class TShirtControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    TShirtRepository repo;

    private ObjectMapper mapper = new ObjectMapper();
}
