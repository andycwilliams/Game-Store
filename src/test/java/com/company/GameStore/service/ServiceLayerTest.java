package com.company.GameStore.service;

import com.company.GameStore.controller.ConsoleController;
import com.company.GameStore.models.Console;
import com.company.GameStore.models.Game;
import com.company.GameStore.repository.ConsoleRepository;
import com.company.GameStore.repository.GameRepository;
import com.company.GameStore.repository.TShirtRepository;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class ServiceLayerTest {

    ServiceLayer service;
    ConsoleRepository consoleRepository;
    GameRepository gameRepository;
    TShirtRepository tShirtRepository;

    @Before
    public void setUp() throws Exception {

        setUpGameRepositoryMock();

        service = new ServiceLayer(consoleRepository, gameRepository, tShirtRepository);
    }
    private void setUpConsoleRepositoryMock() {
        consoleRepository = mock(ConsoleRepository.class);
        Console console = new Console();
        console.setConsole_id(1);
        console.setModel("Playstation 5");
        console.setManufacturer("Sony");
        console.setMemory_amount("99");
        console.setProcessor("Processor Test");
        console.setPrice(new BigDecimal("899.99"));
        console.setQuantity(31);

        List consoleList = new ArrayList();
        consoleList.add(console);

        Console console2 = new Console();
        console2.setModel("Playstation 5");
        console2.setManufacturer("Sony");
        console2.setMemory_amount("99");
        console2.setProcessor("Processor Test");
        console2.setPrice(new BigDecimal("899.99"));
        console2.setQuantity(31);

        doReturn(console).when(consoleRepository).save(console2);
        doReturn(Optional.of(console)).when(consoleRepository).findById(1);
        doReturn(consoleList).when(consoleRepository).findAll();
    }

    private void setUpGameRepositoryMock() {
        gameRepository = mock(GameRepository.class);
        Game game = new Game();
        game.setGame_id(33);
        game.setTitle("God of War");
        game.setEsrbRating("MA");
        game.setDescription("Father and son adventure.");
        game.setPrice(new BigDecimal("59.99"));
        game.setStudio("Santa Monica");
        game.setQuantity(100);

        List gameList = new ArrayList();
        gameList.add(game);

        Game game2 = new Game();
        game2.setTitle("God of War");
        game2.setEsrbRating("MA");
        game2.setDescription("Father and son adventure.");
        game2.setPrice(new BigDecimal("59.99"));
        game2.setStudio("Santa Monica");
        game2.setQuantity(100);

        doReturn(game).when(gameRepository).save(game2);
        doReturn(Optional.of(game)).when(gameRepository).findById(33);
        doReturn(gameList).when(gameRepository).findAll();
    }

    // --------------------------------- Console ---------------------------------

//    @Test
//    public void shouldFindAllConsoles() {
//        List<Console> fromService = service.findAll();
//        assertEquals(4, fromService.size());
//    }

//    @Test
//    public void shouldFindConsole() {}
//    @Test
//    public void shouldFindConsolesByManufacturer() {}
//    @Test
//    public void shouldAddConsole() {}
//    @Test
//    public void shouldUpdateConsole() {}
//    @Test
//    public void shouldDeleteConsole() {}

    // --------------------------------- Game ---------------------------------

    @Test
    public void shouldSaveAGame() {
        Game saveGame = new Game();
        saveGame.setTitle("God of War");
        saveGame.setEsrbRating("MA");
        saveGame.setDescription("Father and son adventure.");
        saveGame.setPrice(new BigDecimal("59.99"));
        saveGame.setStudio("Santa Monica");
        saveGame.setQuantity(100);

        Game expectedSave = new Game();
        expectedSave.setGame_id(33);
        expectedSave.setTitle("God of War");
        expectedSave.setEsrbRating("MA");
        expectedSave.setDescription("Father and son adventure.");
        expectedSave.setPrice(new BigDecimal("59.99"));
        expectedSave.setStudio("Santa Monica");
        expectedSave.setQuantity(100);

        Game actualGame = service.saveGame(saveGame);

        assertEquals(expectedSave, actualGame);
    }

    // --------------------------------- T-Shirt ---------------------------------

}