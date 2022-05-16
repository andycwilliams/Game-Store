package com.company.GameStore.service;

import com.company.GameStore.models.Console;
import com.company.GameStore.models.Games;
import com.company.GameStore.repository.ConsoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.sound.midi.Track;
import java.util.List;
import java.util.Optional;

@Component
public class ServiceLayer {

    private ConsoleRepository consoleRepository;
    private GameRepository gameRepository;
    private TshirtRepository tshirtRepository;

    @Autowired
    public ServiceLayer(ConsoleRepository consoleRepository, GameRepository gameRepository, TshirtRepository tshirtRepository) {
        this.consoleRepository = consoleRepository;
        this.gameRepository = gameRepository;
        this.tshirtRepository = tshirtRepository;
    }

    // --------------------------------- Console ---------------------------------

    public List<Console> findAllConsoles() {
        return consoleRepository.findAll();
    }

    public Console findAllConsolesByManufacturer(int id) {

        Optional<Console> label = consoleRepository.findById(id);
        return label.isPresent() ? label.get() : null;
    }

//    @RequestMapping(value="/record/{id}", method= RequestMethod.GET)
//    @ResponseStatus(HttpStatus.OK)
//    public AlbumViewModel getAlbumById(@PathVariable int id) {
//        AlbumViewModel avm = serviceLayer.findAlbum(id);
//        if (avm == null) {
//            throw new NoRecordFoundException("Album id " + id + " not found.");
//        }
//        return serviceLayer.findAlbum(id);
//    }

    @Transactional
    public void deleteConsole(int id){
//        List<Console> consoleList = gameRepository.findAllGamesByConsoleId(id);
//        consoleList.stream().forEach(game -> gameRepository.deleteById(game.getId()));

        consoleRepository.deleteById(id);
    }


    public void updateConsole(int id) {
        consoleRepository.save(id);
    }

    // --------------------------------- Game ---------------------------------

    // --------------------------------- T-shirt ---------------------------------

}