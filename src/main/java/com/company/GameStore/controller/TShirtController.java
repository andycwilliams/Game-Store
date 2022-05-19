package com.company.GameStore.controller;

import com.company.GameStore.exception.InvalidRequestException;
import com.company.GameStore.exception.NoTShirtFoundException;
import com.company.GameStore.models.TShirt;
import com.company.GameStore.service.ServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TShirtController {

    @Autowired
    private ServiceLayer serviceLayer;

    @GetMapping (value ="/tshirts")
    @ResponseStatus(HttpStatus.OK)
    public List<TShirt> getAllTShirts(@RequestParam (required = false) String size, @RequestParam(required = false) String color) {
       //checking to see if you can search tshirts by color
        if (color != null) {
            return serviceLayer.findTShirtByColor(color);
            // checking to see if search by size of shirt
        } else if (size != null) {
            return serviceLayer.findTShirtBySize(size);
            //return size and color.
        } else if(size != null && color != null) {
            return serviceLayer.findTShirtBySizeAndColor(size, color);
            //Returns all shirts
        }else {
            return serviceLayer.findAllTShirts();
        }
    }

    @GetMapping(value = "/tshirts/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TShirt getTShirtById(@PathVariable int id) throws NoTShirtFoundException {
        TShirt tShirt = serviceLayer.findTShirt(id);

        if (tShirt == null) {
            throw new NoTShirtFoundException("No TShirt found matching that id.");
        }
        return tShirt;
    }

    @PostMapping("/tshirts")
    @ResponseStatus(HttpStatus.CREATED)
    public TShirt createTShirt(@RequestBody TShirt tShirt) {
        return serviceLayer.saveTShirt(tShirt);
    }

    @PutMapping("/tshirts/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateTShirt(@PathVariable int id, @RequestBody TShirt tShirt) {

        if (tShirt.gettShirtId() == 0) {
            tShirt.gettShirtId();
        }
        if (tShirt.gettShirtId() != id) {
            throw new InvalidRequestException("The given id does not match any id within the database.");
        }
        serviceLayer.updateTShirt(tShirt);
    }

    @DeleteMapping("/tshirts/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeTShirt(@PathVariable int id) {
        serviceLayer.removeTShirt(id);
    }

    @GetMapping("/tshirts/size/{size}")
    public List<TShirt> getTShirtsBySize(@PathVariable String size) {
        return serviceLayer.findTShirtBySize(size);
    }

    @GetMapping("/tshirts/color/{color}")
    public List<TShirt> getTShirtsByColor(@PathVariable String color) {
        return serviceLayer.findTShirtByColor(color);
    }

    //end
}
