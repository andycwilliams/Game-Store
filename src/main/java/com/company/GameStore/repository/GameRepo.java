package com.company.GameStore.repository;

import com.company.GameStore.models.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class GameRepo extends JpaRepository<Game, Integer> {
}
