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
                        TShirtRepository tShirtRepository, SalesTaxRateRepository salesTaxRateRepository,
                        ProcessingFeeRepository processingFeeRepository) {
        this.consoleRepository = consoleRepository;
        this.gameRepository = gameRepository;
        this.tShirtRepository = tShirtRepository;
        this.salesTaxRateRepository = salesTaxRateRepository;
        this.processingFeeRepository = processingFeeRepository;
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

    public TShirt saveTShirt(TShirt tShirt) {
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
            throw new InvalidRequestException("You cannot place an order without specifying a quantity.");
        }

        // --------------------------------- Match item type, set price, and update quantity  ---------------------------------

        if (invoice.getItemType().equals("Consoles")) {
            Console console = consoleRepository.findById(invoice.getItemId()).get();
            invoice.setUnitPrice(console.getPrice());

            if (invoice.getQuantity() > console.getQuantity()) {
                throw new InvalidRequestException("There are not enough items in our inventory to satisfy this request.");
            }
            console.setQuantity(console.getQuantity() - invoice.getQuantity());
            consoleRepository.save(console);

        } else if (invoice.getItemType().equals("Games")) {
            Game game = gameRepository.findById(invoice.getItemId()).get();
            invoice.setUnitPrice(game.getPrice());

            if (invoice.getQuantity() > game.getQuantity()) {
                throw new InvalidRequestException("There are not enough items in our inventory to satisfy this request.");
            }
            game.setQuantity(game.getQuantity() - invoice.getQuantity());
            gameRepository.save(game);

        } else if (invoice.getItemType().equals("T-Shirts")) {
            TShirt tShirts = tShirtRepository.findById(invoice.getItemId()).get();
            invoice.setUnitPrice(tShirts.getPrice());

            if (invoice.getQuantity() > tShirts.getQuantity()) {
                throw new InvalidRequestException("There are not enough items in our inventory to satisfy this request.");
            }
            tShirts.setQuantity(tShirts.getQuantity() - invoice.getQuantity());
            tShirtRepository.save(tShirts);
        }

        // ------------------------------ Processing fee management ------------------------------

        if (invoice.getQuantity() > 10) {
            invoice.setProcessingFee(processingFeeRepository.findById(invoice.getItemType()).get().getFee().add(new BigDecimal("15.49")));
        } else {
            invoice.setProcessingFee(processingFeeRepository.findById(invoice.getItemType()).get().getFee());
        }

        // --------------------------------- Set invoice ---------------------------------

        Invoice purchase = new Invoice();
        purchase.setName(invoice.getName());
        purchase.setStreet(invoice.getStreet());
        purchase.setCity(invoice.getCity());
        purchase.setState(invoice.getState());
        purchase.setZipCode(invoice.getZipCode());
        purchase.setItemType(invoice.getItemType());
        purchase.setItemId(invoice.getItemId());
        purchase.setUnitPrice(invoice.getUnitPrice());
        purchase.setQuantity(invoice.getQuantity());
        purchase.setSubtotal(purchase.getUnitPrice().multiply(BigDecimal.valueOf(purchase.getQuantity())));
        purchase.setTax(salesTaxRateRepository.findById(purchase.getState()).get().getRate());
        purchase.setProcessingFee(invoice.getProcessingFee());
        purchase.setTotal(purchase.getSubtotal().add(purchase.getTax().add(purchase.getProcessingFee())));

        return purchase;
    }
}