package com.company.GameStore.controller;

import com.company.GameStore.models.Console;
import com.company.GameStore.models.Games;
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

    @GetMapping(value="/{manufacturer}")
    public List<Console> findAllConsolesByManufacturer(@PathVariable String manufacturer) { return serviceLayer.findAllConsolesByManufacturer();}

//    @PostMapping
//    public addConsole() {
//        return serviceLayer.addConsole();
//    }

    @PutMapping(value="/{id}")
    public updateConsole(@PathVariable int id) {
        return serviceLayer.updateConsole(id);
    }

    @DeleteMapping(value="/{id}")
    public void deleteConsole(@PathVariable int id) {
        serviceLayer.deleteConsole(id);
    }
}
