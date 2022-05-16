package com.company.GameStore.repository;

import com.company.GameStore.models.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<Game, Integer> {
    List<Game> findByStudio(String studio);
    List<Game> findByEsrbRating(String esrb);
    List<Game> findByTitle(String title);
}
