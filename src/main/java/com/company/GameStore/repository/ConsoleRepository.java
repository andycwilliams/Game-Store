package com.company.GameStore.repository;

import com.company.GameStore.models.Console;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Component
public interface ConsoleRepository extends JpaRepository<Games, Integer> {}