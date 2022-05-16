package com.company.GameStore.service;

import com.company.GameStore.models.Console;
import com.company.GameStore.models.Game;
import com.company.GameStore.repository.ConsoleRepository;
import com.company.GameStore.repository.GameRepository;
import com.company.GameStore.repository.TShirtRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
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

//    public Console findAllConsolesByManufacturer(int id) {
//        Optional<Console> console = consoleRepository.findById(id);
//        return console.isPresent() ? console.get() : null;
//    }

//    @RequestMapping(value="/record/{id}", method= RequestMethod.GET)
//    @ResponseStatus(HttpStatus.OK)
//    public AlbumViewModel getAlbumById(@PathVariable int id) {
//        AlbumViewModel avm = serviceLayer.findAlbum(id);
//        if (avm == null) {
//            throw new NoRecordFoundException("Album id " + id + " not found.");
//        }
//        return serviceLayer.findAlbum(id);
//    }

//    public void updateConsole(Console console) {
//        consoleRepository.save(console);
//    }

    @Transactional
    public void deleteConsole(int id){
//        List<Console> consoleList = gameRepository.findAllGamesByConsoleId(id);
//        consoleList.stream().forEach(game -> gameRepository.deleteById(game.getId()));

        consoleRepository.deleteById(id);
    }

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

    public List<Game> findGamesByESRB(String esrb) {
        return gameRepository.findByESRBRating(esrb);
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


}