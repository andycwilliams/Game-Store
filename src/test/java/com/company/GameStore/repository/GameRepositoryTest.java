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

    @Autowired
    GameRepository gameRepository;

    @Before
    public void setUp() throws Exception {
        gameRepository.deleteAll();
    }

    @Test
    public void getAllGames() throws Exception {

        Game game = new Game();
        game.setTitle("God of War");
        game.setEsrbRating("MA");
        game.setDescription("Father and son adventure.");
        game.setPrice(new BigDecimal("59.99"));
        game.setStudio("Santa Monica");
        game.setQuantity(100);

        game = gameRepository.save(game);

        game = new Game();
        game.setTitle("God of War 3");
        game.setEsrbRating("MA");
        game.setDescription("Son getting revenge on family.");
        game.setPrice(new BigDecimal("49.99"));
        game.setStudio("Santa Monica");
        game.setQuantity(50);

        game = gameRepository.save(game);

        List<Game> gameList = gameRepository.findAll();
        assertEquals(gameList.size(), 2);
    }

    @Test
    public void addGetDeleteGame() {

        Game game = new Game();
        game.setTitle("God of War");
        game.setEsrbRating("MA");
        game.setDescription("Father and son adventure.");
        game.setPrice(new BigDecimal("59.99"));
        game.setStudio("Santa Monica");
        game.setQuantity(100);

        game = gameRepository.save(game);

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