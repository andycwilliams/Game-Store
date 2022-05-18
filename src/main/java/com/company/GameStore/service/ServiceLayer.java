package com.company.GameStore.service;

import com.company.GameStore.exception.InvalidRequestException;
import com.company.GameStore.models.*;
import com.company.GameStore.repository.*;

import com.company.GameStore.models.Console;
import com.company.GameStore.models.Game;
import com.company.GameStore.models.TShirt;
import com.company.GameStore.repository.ConsoleRepository;
import com.company.GameStore.repository.GameRepository;
import com.company.GameStore.repository.TShirtRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import javax.transaction.Transactional;
import java.math.BigDecimal;

import java.util.List;
import java.util.Optional;

@Component
public class ServiceLayer {

    private ConsoleRepository consoleRepository;
    private GameRepository gameRepository;
    private TShirtRepository tShirtRepository;
    private SalesTaxRateRepository salesTaxRateRepository;
    private ProcessingFeeRepository processingFeeRepository;

    @Autowired
    public ServiceLayer(ConsoleRepository consoleRepository,GameRepository gameRepository,
                        TShirtRepository tShirtRepository) {
        this.consoleRepository = consoleRepository;
        this.gameRepository = gameRepository;
        this.tShirtRepository = tShirtRepository;
    }

    // --------------------------------- Console ---------------------------------

    public List<Console> findAllConsoles() {
        return consoleRepository.findAll();
    }

    public Console findConsole(int id) {

        Optional<Console> console = consoleRepository.findById(id);
        return console.isPresent() ? console.get() : null;
    }

    public List<Console> findConsolesByManufacturer(String manufacturer) {
//        Optional<Console> console = consoleRepository.findById(manufacturer;
//        return console.isPresent() ? findConsolesByManufacturer().get() : null;
        return consoleRepository.findByManufacturer(manufacturer);
    }

    public Console addConsole(Console console) {
        return consoleRepository.save(console);
    }

    public void updateConsole(Console console) {
        consoleRepository.save(console);
    }

    public void deleteConsole(int id){ consoleRepository.deleteById(id); }

    // --------------------------------- Game ---------------------------------

    public Game findGame(int id) {

        Optional<Game> game = gameRepository.findById(id);
        return game.isPresent() ? game.get() : null;
    }

    public List<Game> findAllGames() {

        return gameRepository.findAll();
    }

    public List<Game> findGamesByStudio(String studio) {
        return gameRepository.findByStudio(studio);
    }

    public List<Game> findGamesByEsrb(String esrb) {
        return gameRepository.findByEsrbRating(esrb);
    }

    public List<Game> findGamesByTitle(String title) {
        return gameRepository.findByTitle(title);
    }

    public Game saveGame(Game game) {

        return gameRepository.save(game);
    }

    public void updateGame(Game game) {

        gameRepository.save(game);
    }

    public void removeGame(int id) {

        gameRepository.deleteById(id);
    }

    // --------------------------------- T-shirt ---------------------------------

    public List<TShirt> findTShirtBySize(String size) {
        return tShirtRepository.findBySize(size);
    }

    public List<TShirt> findTShirtByColor(String color) {
        return tShirtRepository.findByColor(color);
    }

    public List<TShirt> findTShirtBySizeAndColor(String size, String color) {
        return tShirtRepository.findBySizeAndColor(size, color);
    }

    public List<TShirt> findAllTShirts() {

        return tShirtRepository.findAll();
    }

    public TShirt findTShirt(int id) {
        Optional<TShirt> tShirt = tShirtRepository.findById(id);
        return tShirt.isPresent() ? tShirt.get() : null;
    }

    public TShirt saveTShirt(@RequestBody TShirt tShirt) {
        return tShirtRepository.save(tShirt);
    }

    public void updateTShirt(TShirt tShirt) {

        tShirtRepository.save(tShirt);
    }

    public void removeTShirt(int id) {

        tShirtRepository.deleteById(id);
    }

    // --------------------------------- Invoice ---------------------------------
    public Invoice saveInvoice(Invoice invoice) {

        // --------------------------------- Initial business rule check ---------------------------------

        if (invoice.getQuantity() <= 0) {
            throw new InvalidRequestException(); // Placeholder error until testing
        }

        // --------------------------------- Match item type and set processing fee ---------------------------------

        if (invoice.getItemType().equals("Console")) {
            Console console = consoleRepository.getById(invoice.getId());

        } else if (invoice.getItemType().equals("Game")) {
            Game game = gameRepository.getById(invoice.getItemId());
            invoice.setUnitPrice(game.getPrice());

            // ------------------------------ Inventory management ------------------------------

            if (invoice.getQuantity() < game.getQuantity()) {
                throw new InvalidRequestException(); // Placeholder error until testing
            }
            game.setQuantity(game.getQuantity() - invoice.getQuantity());
            gameRepository.save(game);

            // ------------------------------ Processing fee management ------------------------------

            if (invoice.getQuantity() > 10) {
                invoice.setProcessingFee(processingFeeRepository.findById(invoice.getItemType()).get().getFee().add(new BigDecimal("15.49")));
            } else {
                invoice.setProcessingFee(processingFeeRepository.findById(invoice.getItemType()).get().getFee());
            }

        } else if (invoice.getItemType().equals("T-Shirt")) {
            TShirt tShirts = tShirtRepository.getById(invoice.getId());
        }

        // --------------------------------- Set invoice ---------------------------------

        Invoice purchase = new Invoice();
        purchase.setName(invoice.getName());
        purchase.setStreet(invoice.getStreet());
        purchase.setCity(invoice.getCity());
        purchase.setState(invoice.getState());
        purchase.setZipCode(invoice.getZipCode());
        purchase.setItemType(invoice.getItemType());
        purchase.setItemId(purchase.getItemId());
        purchase.setUnitPrice(invoice.getUnitPrice());
        purchase.setQuantity(invoice.getQuantity());
        purchase.setSubtotal(purchase.getUnitPrice().multiply(BigDecimal.valueOf(purchase.getQuantity())));
        purchase.setTax(salesTaxRateRepository.findById(purchase.getState()).get().getRate());
        purchase.setProcessingFee(invoice.getProcessingFee());
        purchase.setTotal(purchase.getSubtotal().add(purchase.getTax().add(purchase.getProcessingFee())));

        return purchase;
    }
}