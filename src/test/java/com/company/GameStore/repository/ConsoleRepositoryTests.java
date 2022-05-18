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

@RunWith(SpringRunner.class)
@SpringBootTest
public class ConsoleRepositoryTests {

    @Autowired
    ConsoleRepository consoleRepository;

    @Before
    public void setUp() throws Exception {
        consoleRepository.deleteAll();

        Console console = new Console();
        console.setModel("Playstation 5");
        console.setManufacturer("Sony");
        console.setMemory_amount("99");
        console.setProcessor("Processor Test");
        console.setPrice(new BigDecimal("899.99"));
        console.setQuantity(31);

        Console console2 = new Console();
        console2.setModel("X Box");
        console2.setManufacturer("Microsoft");
        console2.setMemory_amount("77");
        console2.setProcessor("Processor Test 2");
        console2.setPrice(new BigDecimal("599.99"));
        console2.setQuantity(15);

        Console console3 = new Console();
        console3.setModel("Wii");
        console3.setManufacturer("Nintendo");
        console3.setMemory_amount("12");
        console3.setProcessor("Processor Test 3");
        console3.setPrice(new BigDecimal("1299.99"));
        console3.setQuantity(190);

        Console console4 = new Console();
        console4.setModel("Playstation 2");
        console4.setManufacturer("Sony");
        console4.setMemory_amount("5");
        console4.setProcessor("Processor Old");
        console4.setPrice(new BigDecimal("9.99"));
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
    public void shouldUpdateConsole() throws Exception {
        Console console = new Console();
        console.setModel("Burger King Console");
        console.setManufacturer("Burger King");
        console.setMemory_amount("6000");
        console.setProcessor("BK Patented Processor");
        console.setPrice(new BigDecimal("4999.99"));
        console.setQuantity(3);

        Console console2 = new Console();
        console2.setModel("BK Portable");
//        console2.setManufacturer("Burger King");
//        console2.setMemory_amount("6000");
//        console2.setProcessor("BK Patented Processor");
        console2.setPrice(new BigDecimal("9999.99"));
//        console2.setQuantity(3);

        consoleRepository.save(console);
        consoleRepository.save(console2);

        Optional<Console> consoleCompare = consoleRepository.findById((console.getConsole_id()));
        assertEquals(consoleCompare.get(), console2);
    }

    @Test
    public void shouldFindConsolesByManufacturer() throws Exception {
        List<Console> consoleList = consoleRepository.findByManufacturer("Sony");
//        assertEquals(consoleList.size(), 2);
        assertEquals(2, consoleList.size());

    }
}
