package com.company.GameStore.repository;

import com.company.GameStore.models.Console;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ConsoleRepository extends JpaRepository<Console, Integer> {
    List<Console> findConsolesByManufacturer(String manufacturer);

}