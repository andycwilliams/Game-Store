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
        setUpTShirtRepositoryMock();
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
        when(consoleRepository.findByManufacturer("Sony")).thenReturn(consoleList);
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

    private void setUpTShirtRepositoryMock() {
        tShirtRepository = mock(TShirtRepository.class);
        TShirt tShirt = new TShirt();
        tShirt.settShirtId(1);
        tShirt.setSize("Large");
//        tShirt.setPrice(BigDecimal.valueOf(10.99));
        tShirt.setColor("Purple");
        tShirt.setDescription("This shirt is purple");
        tShirt.setPrice(BigDecimal.valueOf(10.99));
        tShirt.setQuantity(100);

        List tShirtList = new ArrayList();
        tShirtList.add(tShirt);

        TShirt tShirt2 = new TShirt();
//        tShirt2.settShirtId(2);
//        tShirt2.setSize("Large");
//        tShirt2.setPrice(BigDecimal.valueOf(11.99));
//        tShirt2.setQuantity(105);
//        tShirt2.setDescription("This shirt is yellow");
//        tShirt2.setColor("Yellow");
        tShirt2.setSize("Large");
        tShirt.setColor("Purple");
        tShirt.setDescription("This shirt is purple");
        tShirt.setPrice(BigDecimal.valueOf(10.99));
        tShirt.setQuantity(100);

        doReturn(tShirt).when(tShirtRepository).save(tShirt2);
        doReturn(Optional.of(tShirt)).when(tShirtRepository).findById(1);
        doReturn(tShirtList).when(tShirtRepository).findAll();
    }

    private void setUpProcessingFeeRepositoryMock() {
        processingFeeRepository = mock(ProcessingFeeRepository.class);
        ProcessingFee consoleProcessingFee = new ProcessingFee();
        consoleProcessingFee.setProductType("Consoles");
        consoleProcessingFee.setFee(new BigDecimal("14.99"));

        ProcessingFee gameProcessingFee = new ProcessingFee();
        gameProcessingFee.setProductType("Games");
        gameProcessingFee.setFee(new BigDecimal("1.49"));

        ProcessingFee tShirtProcessingFee = new ProcessingFee();
        tShirtProcessingFee.setProductType("T-Shirts");
        tShirtProcessingFee.setFee(new BigDecimal("1.98"));

        when(processingFeeRepository.findById("Consoles")).thenReturn(Optional.of(consoleProcessingFee));
        when(processingFeeRepository.findById("Games")).thenReturn(Optional.of(gameProcessingFee));
        when(processingFeeRepository.findById("T-Shirts")).thenReturn(Optional.of(tShirtProcessingFee));
    }

    private void setUpSalesTaxRepositoryMock() {
        salesTaxRateRepository = mock(SalesTaxRateRepository.class);
        SalesTaxRate salesTaxRateNy = new SalesTaxRate();
        salesTaxRateNy.setState("NY");
        salesTaxRateNy.setRate(new BigDecimal(".06"));

        SalesTaxRate salesTaxRateIl = new SalesTaxRate();
        salesTaxRateIl.setState("IL");
        salesTaxRateIl.setRate(new BigDecimal(".05"));

        SalesTaxRate salesTaxRateTx = new SalesTaxRate();
        salesTaxRateTx.setState("TX");
        salesTaxRateTx.setRate(new BigDecimal(".03"));

        when(salesTaxRateRepository.findById("NY")).thenReturn(Optional.of(salesTaxRateNy));
        when(salesTaxRateRepository.findById("IL")).thenReturn(Optional.of(salesTaxRateIl));
        when(salesTaxRateRepository.findById("TX")).thenReturn(Optional.of(salesTaxRateTx));
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
        Assert.assertEquals(1, consoleList.size());
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

    @Test
    public void shouldFindTShirtsBySize() {

    }
    @Test
    public void shouldFindTShirtsByColor() {

    }
    @Test
    public void shouldFindAllTShirts() {
        List<TShirt> fromService = service.findAllTShirts();

        assertEquals(1, fromService.size());

    }
    @Test
    public void shouldFindTShirt() {

    }
    @Test
    public void shouldSaveTShirt() {
        // Arrange
//        TShirt tShirt = new TShirt();
//        tShirt.settShirtId(50);
//        tShirt.setDescription("This shirt has an ID of 2");
//        tShirt.setQuantity(100);
//        tShirt.setColor("Turquoise");
//        tShirt.setSize("Medium");
//        tShirt.setPrice(BigDecimal.valueOf(20.99));
//
//        TShirt expectedOutput = new TShirt();
//        expectedOutput.settShirtId(50);
//        expectedOutput.setDescription("This shirt has an ID of 2");
//        expectedOutput.setQuantity(100);
//        expectedOutput.setColor("Turquoise");
//        expectedOutput.setSize("Medium");
//        expectedOutput.setPrice(BigDecimal.valueOf(20.99));

        TShirt saveShirt = new TShirt();
        saveShirt.setSize("Large");
        saveShirt.setPrice(BigDecimal.valueOf(10.99));
        saveShirt.setQuantity(100);
        saveShirt.setDescription("This shirt is purple");
        saveShirt.setColor("Purple");

        TShirt expectedShirt = new TShirt();
        expectedShirt.settShirtId(1);
        expectedShirt.setSize("Large");
        expectedShirt.setPrice(BigDecimal.valueOf(10.99));
        expectedShirt.setQuantity(100);
        expectedShirt.setDescription("This shirt is purple");
        expectedShirt.setColor("Purple");

        // Act
        TShirt actualOutput = service.saveTShirt(saveShirt);

        // Assert
        assertEquals(expectedShirt, actualOutput);

    }
    @Test
    public void shouldUpdateTShirt() {

    }
    @Test
    public void shouldRemoveTShirt() {
    }

    // --------------------------------- Invoice ---------------------------------

    @Test
    public void shouldSaveAConsoleInvoice() {
        Invoice inputInvoice = new Invoice();
        inputInvoice.setName("Henry");
        inputInvoice.setStreet("Cross");
        inputInvoice.setCity("New York");
        inputInvoice.setState("NY");
        inputInvoice.setZipCode("40678");
        inputInvoice.setItemType("Consoles");
        inputInvoice.setItemId(1);
        inputInvoice.setQuantity(1);

        Invoice outputInvoice = new Invoice();
        outputInvoice.setName("Henry");
        outputInvoice.setStreet("Cross");
        outputInvoice.setCity("New York");
        outputInvoice.setState("NY");
        outputInvoice.setZipCode("40678");
        outputInvoice.setItemType("Consoles");
        outputInvoice.setItemId(1);
        outputInvoice.setUnitPrice(new BigDecimal("899.99"));
        outputInvoice.setQuantity(1);
        outputInvoice.setSubtotal(new BigDecimal("899.99"));
        outputInvoice.setTax(new BigDecimal(".06"));
        outputInvoice.setProcessingFee(new BigDecimal("14.99"));
        outputInvoice.setTotal(new BigDecimal("915.04"));

        Invoice actualInvoice = service.saveInvoice(inputInvoice);
        assertEquals(outputInvoice, actualInvoice);
    }

    @Test
    public void shouldSaveAGameInvoice() {
        Invoice inputInvoice = new Invoice();
        inputInvoice.setName("George");
        inputInvoice.setStreet("Belmont");
        inputInvoice.setCity("Chicago");
        inputInvoice.setState("IL");
        inputInvoice.setZipCode("60645");
        inputInvoice.setItemType("Games");
        inputInvoice.setItemId(33);
        inputInvoice.setQuantity(2);

        Invoice expectedInvoice = new Invoice();
        expectedInvoice.setName("George");
        expectedInvoice.setStreet("Belmont");
        expectedInvoice.setCity("Chicago");
        expectedInvoice.setState("IL");
        expectedInvoice.setZipCode("60645");
        expectedInvoice.setItemType("Games");
        expectedInvoice.setItemId(33);
        expectedInvoice.setUnitPrice(new BigDecimal("59.99"));
        expectedInvoice.setQuantity(2);
        expectedInvoice.setSubtotal(new BigDecimal("119.98"));
        expectedInvoice.setTax(new BigDecimal(".05"));
        expectedInvoice.setProcessingFee(new BigDecimal("1.49"));
        expectedInvoice.setTotal(new BigDecimal("121.52"));

        Invoice actualInvoice = service.saveInvoice(inputInvoice);
        assertEquals(expectedInvoice, actualInvoice);
    }

    @Test
    public void shouldSaveATshirtInvoice() {
        Invoice inputInvoice = new Invoice();
        inputInvoice.setName("George");
        inputInvoice.setStreet("Belmont");
        inputInvoice.setCity("Houston");
        inputInvoice.setState("TX");
        inputInvoice.setZipCode("85940");
        inputInvoice.setItemType("T-Shirts");
        inputInvoice.setItemId(1);
        inputInvoice.setQuantity(3);

        Invoice outputInvoice = new Invoice();
        outputInvoice.setName("George");
        outputInvoice.setStreet("Belmont");
        outputInvoice.setCity("Houston");
        outputInvoice.setState("TX");
        outputInvoice.setZipCode("85940");
        outputInvoice.setItemType("T-Shirts");
        outputInvoice.setItemId(1);
        outputInvoice.setUnitPrice(new BigDecimal("10.99"));
        outputInvoice.setQuantity(3);
        outputInvoice.setSubtotal(new BigDecimal("32.97"));
        outputInvoice.setTax(new BigDecimal(".03"));
        outputInvoice.setProcessingFee(new BigDecimal("1.98"));
        outputInvoice.setTotal(new BigDecimal("34.98"));

        Invoice actualInvoice = service.saveInvoice(inputInvoice);
        assertEquals(outputInvoice, actualInvoice);
    }
}