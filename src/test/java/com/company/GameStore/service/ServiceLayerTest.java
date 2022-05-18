package com.company.GameStore.service;

import com.company.GameStore.controller.ConsoleController;
import com.company.GameStore.models.*;
import com.company.GameStore.repository.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ServiceLayerTest {

    ServiceLayer service;
    ConsoleRepository consoleRepository;
    GameRepository gameRepository;
    TShirtRepository tShirtRepository;
    SalesTaxRateRepository salesTaxRateRepository;
    ProcessingFeeRepository processingFeeRepository;

    @Before
    public void setUp() throws Exception {
        setUpConsoleRepositoryMock();
        setUpGameRepositoryMock();
        setUpProcessingFeeRepositoryMock();
        setUpSalesTaxRepositoryMock();

        service = new ServiceLayer(consoleRepository, gameRepository, tShirtRepository, salesTaxRateRepository, processingFeeRepository);
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
//        when(gameRepository.findByManufacturer("Sony")).thenReturn(consoleList);
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
        when(gameRepository.findByStudio("Santa Monica")).thenReturn(gameList);
        when(gameRepository.findByEsrbRating("MA")).thenReturn(gameList);
        when(gameRepository.findByTitle("God of War")).thenReturn(gameList);
    }
    private void setUpProcessingFeeRepositoryMock() {
        processingFeeRepository = mock(ProcessingFeeRepository.class);
        ProcessingFee processingFee = new ProcessingFee();
        processingFee.setProductType("Games");
        processingFee.setFee(new BigDecimal("1.49"));

        when(processingFeeRepository.findById("Games")).thenReturn(Optional.of(processingFee));
    }

    private void setUpSalesTaxRepositoryMock() {
        salesTaxRateRepository = mock(SalesTaxRateRepository.class);
        SalesTaxRate salesTaxRate = new SalesTaxRate();
        salesTaxRate.setState("IL");
        salesTaxRate.setRate(new BigDecimal(".05"));

        when(salesTaxRateRepository.findById("IL")).thenReturn(Optional.of(salesTaxRate));
    }

    // --------------------------------- Console ---------------------------------

    @Test
    public void shouldFindAllConsoles() {
        List<Console> fromService = service.findAllConsoles();
        assertEquals(1, fromService.size());
    }

    @Test
    public void shouldFindConsole() {
        Console expectedResult = new Console();
        expectedResult.setConsole_id(1);
        expectedResult.setModel("Playstation 5");
        expectedResult.setManufacturer("Sony");
        expectedResult.setMemory_amount("99");
        expectedResult.setProcessor("Processor Test");
        expectedResult.setPrice(new BigDecimal("899.99"));
        expectedResult.setQuantity(31);

        Console actualResult = service.findConsole(1);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldFindConsolesByManufacturer() throws Exception {
        List<Console> consoleList = consoleRepository.findByManufacturer("Sony");
        Assert.assertEquals(2, consoleList.size());
    }

    @Test
    @ResponseStatus(HttpStatus.CREATED)
    public void shouldAddConsole() {
        Console console = new Console();
        console.setModel("Playstation 5");
        console.setManufacturer("Sony");
        console.setMemory_amount("99");
        console.setProcessor("Processor Test");
        console.setPrice(new BigDecimal("899.99"));
        console.setQuantity(31);

        Console expectedResult = new Console();
        expectedResult.setConsole_id(1);
        expectedResult.setModel("Playstation 5");
        expectedResult.setManufacturer("Sony");
        expectedResult.setMemory_amount("99");
        expectedResult.setProcessor("Processor Test");
        expectedResult.setPrice(new BigDecimal("899.99"));
        expectedResult.setQuantity(31);

        Console actualResult = service.addConsole(console);

        assertEquals(expectedResult, actualResult);
    }

//    @Test
//    public void shouldUpdateConsole() {
//        Console console = new Console();
//        console.setModel("Burger King Console");
//        console.setManufacturer("Burger King");
//        console.setMemory_amount("6000");
//        console.setProcessor("BK Processor");
//        console.setPrice(new BigDecimal("999.99"));
//        console.setQuantity(3);
//
//        service.saveConsole(console);
//
//        expectedResult.setModel("BK Portable");
//        console.setManufacturer("Burger King");
//        console.setMemory_amount("6000");
//        console.setProcessor("BK Processor");
//        console.setPrice(new BigDecimal("999.99"));
//        console.setQuantity(3);
//
//        Console actualResult = service.saveConsole(console);
//
//        Assert.assertEquals(expectedResult, console);
//
//
//
//        Game game1 = new Game();
//        game1.setTitle("God of War");
//        game1.setEsrbRating("MA");
//        game1.setDescription("Father and son adventure.");
//        game1.setPrice(new BigDecimal("59.99"));
//        game1.setStudio("Santa Monica");
//        game1.setQuantity(100);
//
//        service.saveGame(game1);
//
//        game1.setTitle("Pokemon");
//        game1.setEsrbRating("E");
//        game1.setDescription("Roleplaying adventure game.");
//        game1.setPrice(new BigDecimal("59.99"));
//        game1.setStudio("Nintendo");
//        game1.setQuantity(200);
//
//        Game actualGame = service.saveGame(game2);
//
//        assertEquals(expectedGame, actualGame);
//    }

    @Test
    public void shouldDeleteConsole() {
        // Delete returns void, so no test
    }

    // --------------------------------- Game ---------------------------------

    @Test
    public void shouldFindAGame() {
        Game expectedGame = new Game();
        expectedGame.setGame_id(33);
        expectedGame.setTitle("God of War");
        expectedGame.setEsrbRating("MA");
        expectedGame.setDescription("Father and son adventure.");
        expectedGame.setPrice(new BigDecimal("59.99"));
        expectedGame.setStudio("Santa Monica");
        expectedGame.setQuantity(100);

        Game actualGame = service.findGame(33);

        assertEquals(expectedGame, actualGame);
    }

    @Test
    public void shouldFindAllGames() {
        List<Game> gameList = service.findAllGames();

        assertEquals(1, gameList.size());
    }

    @Test
    public void shouldFindGamesByStudio() {
        List<Game> gameList = service.findGamesByStudio("Santa Monica");

        assertEquals(1, gameList.size());
    }

    @Test
    public void shouldFindGameByEsrb() {
        List<Game> gameList = service.findGamesByEsrb("MA");

        assertEquals(1, gameList.size());
    }

    @Test
    public void shouldFindGamesByTitle() {
        List<Game> gameList = service.findGamesByTitle("God of War");

        assertEquals(1, gameList.size());
    }

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

    @Test
    public void shouldUpdateAGame() {
        Game game1 = new Game();
        game1.setTitle("God of War");
        game1.setEsrbRating("MA");
        game1.setDescription("Father and son adventure.");
        game1.setPrice(new BigDecimal("59.99"));
        game1.setStudio("Santa Monica");
        game1.setQuantity(100);

        service.saveGame(game1);

        game1.setTitle("Pokemon");
        game1.setEsrbRating("E");
        game1.setDescription("Roleplaying adventure game.");
        game1.setPrice(new BigDecimal("59.99"));
        game1.setStudio("Nintendo");
        game1.setQuantity(200);

        Game expectedGame = service.saveGame(game1);

        Game game2 = new Game();
        game2.setTitle("Pokemon");
        game2.setEsrbRating("E");
        game2.setDescription("Roleplaying adventure game.");
        game2.setPrice(new BigDecimal("59.99"));
        game2.setStudio("Nintendo");
        game2.setQuantity(200);

        Game actualGame = service.saveGame(game2);

        assertEquals(expectedGame, actualGame);
    }

    @Test
    public void shouldRemoveAGame() {
        // Delete returns void, so no test needed
    }

    // --------------------------------- T-Shirt ---------------------------------
//    private void setUpTShirtRepositoryMock() {
//        tShirtRepository = mock(TShirtRepository.class);
//        TShirt tShirt = new TShirt();
//        tShirt.setGame_id(33);
//        tShirt.setTitle("God of War");
//        tShirt.setEsrbRating("MA");
//        tShirt.setDescription("Father and son adventure.");
//        tShirt.setPrice(new BigDecimal("59.99"));
//        tShirt.setStudio("Santa Monica");
//        tShirt.setQuantity(100);
//
//        List tShirtList = new ArrayList();
//        tShirtList.add(tShirt);
//
//        TShirt tShirt2 = new TShirt();
//        tShirt2.setTitle("God of War");
//        tShirt2.setEsrbRating("MA");
//        tShirt2.setDescription("Father and son adventure.");
//        tShirt2.setPrice(new BigDecimal("59.99"));
//        tShirt2.setStudio("Santa Monica");
//        tShirt2.setQuantity(100);
//
//        TShirt actualResult = service.saveTShirt(tShirt);
//
//        assertEquals(expectedResult, actualResult);
//    }


    // --------------------------------- Invoice ---------------------------------

    @Test
    public void shouldSaveAnInvoice() {
        Invoice inputInvoice = new Invoice();
        inputInvoice.setName("George");
        inputInvoice.setStreet("Belmont");
        inputInvoice.setCity("Chicago");
        inputInvoice.setState("IL");
        inputInvoice.setZipCode("60645");
        inputInvoice.setItemType("Games");
        inputInvoice.setItemId(33);
        inputInvoice.setQuantity(1);

        Invoice expectedInvoice = new Invoice();
        expectedInvoice.setName("George");
        expectedInvoice.setStreet("Belmont");
        expectedInvoice.setCity("Chicago");
        expectedInvoice.setState("IL");
        expectedInvoice.setZipCode("60645");
        expectedInvoice.setItemType("Games");
        expectedInvoice.setItemId(33);
        expectedInvoice.setUnitPrice(new BigDecimal("59.99"));
        expectedInvoice.setQuantity(1);
        expectedInvoice.setSubtotal(new BigDecimal("59.99"));
        expectedInvoice.setTax(new BigDecimal(".05"));
        expectedInvoice.setProcessingFee(new BigDecimal("1.49"));
        expectedInvoice.setTotal(new BigDecimal("61.53"));

        Invoice actualInvoice = service.saveInvoice(inputInvoice);

        assertEquals(expectedInvoice, actualInvoice);

    }
}