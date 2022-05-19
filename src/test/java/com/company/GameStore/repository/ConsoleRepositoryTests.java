package com.company.GameStore.repository;

import com.company.GameStore.models.Console;
import com.company.GameStore.models.Game;
import com.company.GameStore.models.TShirt;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sound.midi.Track;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ConsoleRepositoryTests {

    private Console console;
    private Console console2;
    private Console console3;
    private Console console4;

    @Autowired
    ConsoleRepository consoleRepository;

    @Before
    public void setUp() throws Exception {
        consoleRepository.deleteAll();

        console = new Console();
        console.setModel("Playstation 5");
        console.setManufacturer("Sony");
        console.setMemory_amount("825 GB");
        console.setProcessor("Sony Processor");
        console.setPrice(new BigDecimal("499.99"));
        console.setQuantity(31);

        console2 = new Console();
        console2.setModel("Xbox One S");
        console2.setManufacturer("Microsoft");
        console2.setMemory_amount("1 TB");
        console2.setProcessor("Xbox Processor");
        console2.setPrice(new BigDecimal("499.99"));
        console2.setQuantity(15);

        console3 = new Console();
        console3.setModel("Switch");
        console3.setManufacturer("Nintendo");
        console3.setMemory_amount("64 GB");
        console3.setProcessor("Nintendo Processor");
        console3.setPrice(new BigDecimal("249.99"));
        console3.setQuantity(190);

        console4 = new Console();
        console4.setModel("Playstation 2");
        console4.setManufacturer("Sony");
        console4.setMemory_amount("8 MB");
        console4.setProcessor("Sony Processor");
        console4.setPrice(new BigDecimal("299.99"));
        console4.setQuantity(3);

        consoleRepository.save(console);
        consoleRepository.save(console2);
        consoleRepository.save(console3);
        consoleRepository.save(console4);
    }

    @Test
    public void shouldGetAllConsoles() throws Exception {
        List<Console> consoleList = consoleRepository.findAll();
        assertEquals(4, consoleList.size());
    }

    @Test
    public void shouldGetConsole() throws Exception {
        Console findConsole = consoleRepository.findById(console.getConsole_id()).get();
        assertEquals(console, findConsole);
    }

    @Test
    public void shouldUpdateConsole() throws Exception {
        Console console = new Console();
        console.setModel("Burger King Console");
        console.setManufacturer("Burger King");
        console.setMemory_amount("6000");
        console.setProcessor("BK Processor");
        console.setPrice(new BigDecimal("999.99"));
        console.setQuantity(3);

        console = consoleRepository.save(console);

        console.setModel("BK Portable");
        console.setManufacturer("Burger King");
        console.setMemory_amount("6000");
        console.setProcessor("BK Processor");
        console.setPrice(new BigDecimal("999.99"));
        console.setQuantity(3);

        consoleRepository.save(console);

        Optional<Console> compareConsole = consoleRepository.findById(console.getConsole_id());
        assertEquals(compareConsole.get(), console);
    }

    @Test
    public void shouldAddConsole() throws Exception {
        Console console = new Console();
        console.setConsole_id(202);
        console.setModel("Model Test");
        console.setManufacturer("Manufacturer Test");
        console.setMemory_amount("Memory Test");
        console.setProcessor("Processor Test");
        console.setPrice(new BigDecimal("19.99"));
        console.setQuantity(55);

        console = consoleRepository.save(console);
        Console addConsole = consoleRepository.findById(console.getConsole_id()).get();
        assertEquals(console, addConsole);
    }

    @Test
    public void shouldDeleteConsole() throws Exception {
        console = consoleRepository.save(console);
        consoleRepository.deleteById(console.getConsole_id());
        Optional<Console> deleteConsole = consoleRepository.findById(console.getConsole_id());
        assertFalse(deleteConsole.isPresent());
    }

    @Test
    public void shouldFindConsolesByManufacturer() throws Exception {
        List<Console> consoleList = consoleRepository.findByManufacturer("Sony");
        assertEquals(2, consoleList.size());
    }
}
