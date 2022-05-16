package com.company.GameStore.repository;

import com.company.GameStore.models.Games;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface ConsoleRepository extends JpaRepository<Games, Integer> {
}
