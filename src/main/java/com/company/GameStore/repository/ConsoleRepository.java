package com.company.GameStore.repository;

import com.company.GameStore.models.Console;
import com.company.GameStore.models.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Component
public interface ConsoleRepository extends JpaRepository<Console, Integer> {

    List<Console> findConsolesByManufacturer(String manufacturer);

    List<Console> findGamesByConsoleId(int id);

}