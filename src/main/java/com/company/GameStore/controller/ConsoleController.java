package com.company.GameStore.controller;

import com.company.GameStore.exception.InvalidRequestException;
import com.company.GameStore.exception.NoConsoleFoundException;
import com.company.GameStore.models.Console;
import com.company.GameStore.service.ServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/console")
public class ConsoleController {

    @Autowired
    private ServiceLayer serviceLayer;

    @GetMapping(value="/{id}")
    public Console getConsoleById(@PathVariable int id) {
        Console console = serviceLayer.findConsole(id);

        if (console == null) {
            throw new NoConsoleFoundException("No console found matching that id.");
        }
        return console;
    }

    @GetMapping
    public List<Console> getAllConsoles(@RequestParam(required = false) String manufacturer) {
        if (manufacturer != null) {
            return serviceLayer.findConsolesByManufacturer(manufacturer);
        } else return serviceLayer.findAllConsoles();
    }

    @PostMapping
    public Console addConsole(@RequestBody Console console) {
        return serviceLayer.addConsole(console);
    }

    @PutMapping(value="/{id}")
    public void updateConsole(@PathVariable int id, @RequestBody Console console) {
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
