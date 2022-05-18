package com.company.GameStore.repository;

import com.company.GameStore.models.Console;
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

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ConsoleRepositoryTests {

    @Autowired
    ConsoleRepository consoleRepository;

    @Before
    public void setUp() throws Exception {
        consoleRepository.deleteAll();
    }

    @Test
    public void getAllConsoles() throws Exception {
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

//        Console console4 = new Console();
//        console4.setModel("Burger King Console");
//        console4.setManufacturer("Burger King");
//        console4.setMemory_amount("6000");
//        console4.setProcessor("BK Patented Processor");
//        console4.setPrice(new BigDecimal("4999.99"));
//        console4.setQuantity(3);

        consoleRepository.save(console);
        consoleRepository.save(console2);
        consoleRepository.save(console3);
//        consoleRepository.save(console4);

        List<Console> consoleList = consoleRepository.findAll();
        assertEquals(consoleList.size(), 3);
    }

    @Test
    public void shouldFindConsolesByManufacturer() throws Exception {
        Console console = new Console();
        console.setModel("Playstation 5");
        console.setManufacturer("Sony");
        console.setMemory_amount("99");
        console.setProcessor("Processor Test");
        console.setPrice(new BigDecimal("899.99"));
        console.setQuantity(31);
        console = consoleRepository.save(console);

        List<Console> consoleList = consoleRepository.findByManufacturer(console.getManufacturer());
        assertEquals(consoleList.size(), 1);
    }
}
