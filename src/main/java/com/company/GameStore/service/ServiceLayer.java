package com.company.GameStore.service;

import com.company.GameStore.models.Console;
import com.company.GameStore.models.Game;
import com.company.GameStore.models.TShirt;
import com.company.GameStore.repository.ConsoleRepository;
import com.company.GameStore.repository.GameRepository;
import com.company.GameStore.repository.TShirtRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Component
public class ServiceLayer {

    private ConsoleRepository consoleRepository;
    private GameRepository gameRepository;
    private TShirtRepository tShirtRepository;

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
        return consoleRepository.findConsolesByManufacturer(manufacturer);
    }

    public Console addConsole(Console console) {
        return consoleRepository.save(console);
    }

    public void updateConsole(Console console) {
        consoleRepository.save(console);
    }

//    @Transactional
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

}