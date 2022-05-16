package com.company.GameStore.repository;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ConsoleRepositoryTests {

    @Autowired
    ConsoleRepository consoleRepository;
    @Autowired
    GameRepository gameRepository;
    @Autowired
    TShirtRepository tShirtRepository;

    @Before
    public void setUp() throws Exception {

        consoleRepository.deleteAll();
        gameRepository.deleteAll();
        tShirtRepository.deleteAll();
    }
}
