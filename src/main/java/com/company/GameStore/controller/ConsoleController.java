package com.company.GameStore.controller;

import com.company.GameStore.exception.InvalidRequestException;
// import com.company.GameStore.exception.NoConsoleFoundException;
import com.company.GameStore.models.Console;
import com.company.GameStore.models.Game;
import com.company.GameStore.service.ServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/console")
public class ConsoleController {

    @Autowired
    private ServiceLayer serviceLayer;

    @GetMapping
    public List<Console> getAllConsoles() { return serviceLayer.findAllConsoles();}

    @GetMapping("/games")
    @ResponseStatus(HttpStatus.OK)
    public List<Game> getAllGames() {
        return serviceLayer.findAllGames();
    }
//    @GetMapping(value="/{manufacturer}")
//    public List<Console> findAllConsolesByManufacturer(@PathVariable String manufacturer) { return serviceLayer.findAllConsolesByManufacturer();}

//    @PostMapping
//    public addConsole() {
//        return serviceLayer.addConsole();
//    }

    @PutMapping(value="/{id}")
    public void updateConsole(@PathVariable int id, @RequestBody  Console console) {
        if (console.getConsole_id() == 0) {
            console.getConsole_id();
        }
        if (console.getConsole_id() != id) {
            throw new InvalidRequestException("The given id does not match any id within the database.");
        }
        serviceLayer.updateConsole(console);
    }

    @DeleteMapping(value="/{id}")
    public void deleteConsole(@PathVariable int id) {
        serviceLayer.deleteConsole(id);
    }
}
