package com.company.GameStore.repository;

import com.company.GameStore.models.Game;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GameRepositoryTest {

    private Game game;
    private Game game2;
    private Game game3;
    private Game game4;

    @Autowired
    GameRepository gameRepository;

    @Before
    public void setUp() throws Exception {
        gameRepository.deleteAll();

        game = new Game();
        game.setTitle("God of War");
        game.setEsrbRating("MA");
        game.setDescription("Father and son adventure.");
        game.setPrice(new BigDecimal("59.99"));
        game.setStudio("Santa Monica");
        game.setQuantity(100);

        game2 = new Game();
        game2.setTitle("Pokemon");
        game2.setEsrbRating("E");
        game2.setDescription("Roleplaying adventure game.");
        game2.setPrice(new BigDecimal("59.99"));
        game2.setStudio("Nintendo");
        game2.setQuantity(200);

        game3 = new Game();
        game3.setTitle("Fortnite");
        game3.setEsrbRating("T");
        game3.setDescription("Battle royale.");
        game3.setPrice(new BigDecimal("39.99"));
        game3.setStudio("Epic Games");
        game3.setQuantity(500);

        game4 = new Game();
        game4.setTitle("Mario");
        game4.setEsrbRating("E");
        game4.setDescription("Adventure game.");
        game4.setPrice(new BigDecimal("59.99"));
        game4.setStudio("Nintendo");
        game4.setQuantity(500);

        gameRepository.save(game);
        gameRepository.save(game2);
        gameRepository.save(game3);
        gameRepository.save(game4);
    }

    @Test
    public void shouldGetAllGamesByStudio() throws Exception {

        List<Game> game = gameRepository.findByStudio("Nintendo");
        assertEquals(2, game.size());
    }

    @Test
    public void shouldGetAllGamesByEsrbRating() throws Exception {

        List<Game> game = gameRepository.findByEsrbRating("E");
        assertEquals(2, game.size());
    }

    @Test
    public void shouldGetAllGamesByTitle() throws Exception {
        List<Game> game = gameRepository.findByTitle("God of War");
        assertEquals(1, game.size());
    }

    @Test
    public void getAllGames() throws Exception {

        List<Game> gameList = gameRepository.findAll();
        assertEquals(4, gameList.size());
    }

    @Test
    public void getDeleteGame() {

        Optional<Game> game1 = gameRepository.findById(game.getGame_id());

        assertEquals(game1.get(), game);

        gameRepository.deleteById(game.getGame_id());

        game1 = gameRepository.findById(game.getGame_id());

        assertFalse(game1.isPresent());
    }

    @Test
    public void updateGame() {
        Game game = new Game();
        game.setTitle("God of War");
        game.setEsrbRating("MA");
        game.setDescription("Father and son adventure.");
        game.setPrice(new BigDecimal("59.99"));
        game.setStudio("Santa Monica");
        game.setQuantity(100);

        game = gameRepository.save(game);

        game.setTitle("Call of Duty");
        game.setEsrbRating("T");
        game.setDescription("Battle royale simulator.");
        game.setPrice(new BigDecimal("49.99"));
        game.setStudio("Activision");
        game.setQuantity(40);

        gameRepository.save(game);

        Optional<Game> game1 = gameRepository.findById(game.getGame_id());
        assertEquals(game1.get(), game);
    }
}