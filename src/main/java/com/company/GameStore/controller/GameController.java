package com.company.GameStore.controller;

import com.company.GameStore.exception.InvalidRequestException;
import com.company.GameStore.exception.NoGameFoundException;
import com.company.GameStore.models.Game;
import com.company.GameStore.service.ServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class GameController {

    @Autowired
    private ServiceLayer serviceLayer;

    @GetMapping("/games/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Game getGameById(@PathVariable int id) {
        Game game = serviceLayer.findGame(id);

        if (game == null) {
            throw new NoGameFoundException("No game found matching that id.");
        }
        return game;
    }

    @GetMapping("/games")
    @ResponseStatus(HttpStatus.OK)
    public List<Game> getAllGames (@RequestParam(required = false) String studio, @RequestParam(required = false) String esrb,
                                   @RequestParam(required = false) String title) {
        if (studio != null) {
            return serviceLayer.findGamesByStudio(studio);
        } else if (esrb != null) {
            return serviceLayer.findGamesByEsrb(esrb);
        } else if (title != null) {
            return serviceLayer.findGamesByTitle(title);
        } else {
            return serviceLayer.findAllGames();
        }
    }

    @PostMapping("/games")
    @ResponseStatus(HttpStatus.CREATED)
    public Game createGame(@RequestBody Game game) {
        return serviceLayer.saveGame(game);
    }

    @PutMapping("/games/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateGame(@PathVariable int id, @RequestBody Game game) {

        if (game.getGame_id() == 0) {
            game.getGame_id();
        }
        if (game.getGame_id() != id) {
            throw new InvalidRequestException("The given id does not match any id within the database.");
        }
        serviceLayer.updateGame(game);
    }

    @DeleteMapping("/games/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGame(@PathVariable int id) {
        serviceLayer.removeGame(id);
    }
}
