package com.company.GameStore.repository;

import com.company.GameStore.models.TShirt;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TShirtRepositoryTest {

    @Autowired
    TShirtRepository tShirtRepository;

    @Before
    public void setUp()  throws Exception {
        tShirtRepository.deleteAll();
    }

    @Test
    public void getAllTshirts(){
        TShirt tShirt = new TShirt();
        tShirt.settShirtId(1);
        tShirt.setSize("Large");
        tShirt.setColor("Green");
        tShirt.setDescription("This shirt is green");
        tShirt.setPrice(BigDecimal.valueOf(15.99));
        tShirt.setQuantity(50);
        tShirtRepository.save(tShirt);

        tShirt = new TShirt();
        tShirt.settShirtId(2);
        tShirt.setSize("Medium");
        tShirt.setColor("Red");
        tShirt.setDescription("This shirt is red");
        tShirt.setPrice(BigDecimal.valueOf(16.99));
        tShirt.setQuantity(55);
        tShirtRepository.save(tShirt);

        List<TShirt> tShirtList = tShirtRepository.findAll();
        assertEquals(tShirtList.size(), 2);
    }

    @Test
    public void updateTShirt(){
        TShirt tShirt = new TShirt();
        tShirt.settShirtId(1);
        tShirt.setSize("Large");
        tShirt.setColor("Green");
        tShirt.setDescription("This shirt is green");
        tShirt.setPrice(BigDecimal.valueOf(15.99));
        tShirt.setQuantity(50);
        tShirtRepository.save(tShirt);

        tShirt = new TShirt();
        tShirt.settShirtId(1);
        tShirt.setSize("Medium");
        tShirt.setColor("Red");
        tShirt.setDescription("This shirt is red");
        tShirt.setPrice(BigDecimal.valueOf(16.99));
        tShirt.setQuantity(55);
        tShirtRepository.save(tShirt);

        Optional<TShirt> tShirts1 = tShirtRepository.findById((tShirt.gettShirtId()));
        assertEquals(tShirts1.get(), tShirt);
        
    }
    @Test
    public void addAndGetTShirtThenDelete() {

        TShirt tShirt = new TShirt();
        tShirt.settShirtId(1);
        tShirt.setSize("Large");
        tShirt.setColor("Green");
        tShirt.setDescription("This shirt is green");
        tShirt.setPrice(BigDecimal.valueOf(15.99));
        tShirt.setQuantity(50);

        tShirt = tShirtRepository.save(tShirt);

        Optional<TShirt> tShirt1 = tShirtRepository.findById(tShirt.gettShirtId());
        assertEquals(tShirt1.get(), tShirt);

        tShirtRepository.deleteById(tShirt.gettShirtId());
        tShirt1 = tShirtRepository.findById(tShirt.gettShirtId());
        assertFalse(tShirt1.isPresent());
    }

    @Test
    public void getAllTshirtsBySize(){
        TShirt tShirt = new TShirt();
        tShirt.settShirtId(1);
        tShirt.setSize("Large");
        tShirt.setColor("Green");
        tShirt.setDescription("This shirt is green");
        tShirt.setPrice(BigDecimal.valueOf(15.99));
        tShirt.setQuantity(50);
        tShirtRepository.save(tShirt);

        tShirt = new TShirt();
        tShirt.settShirtId(2);
        tShirt.setSize("Medium");
        tShirt.setColor("Red");
        tShirt.setDescription("This shirt is red");
        tShirt.setPrice(BigDecimal.valueOf(16.99));
        tShirt.setQuantity(55);
        tShirtRepository.save(tShirt);

        List<TShirt> tShirtList = tShirtRepository.findBySize("Large");
        assertEquals(tShirtList.size(), 1);
    }

    @Test
    public void getAllTshirtsByColor(){
        TShirt tShirt = new TShirt();
        tShirt.settShirtId(1);
        tShirt.setSize("Large");
        tShirt.setColor("Green");
        tShirt.setDescription("This shirt is green");
        tShirt.setPrice(BigDecimal.valueOf(15.99));
        tShirt.setQuantity(50);
        tShirtRepository.save(tShirt);

        tShirt = new TShirt();
        tShirt.settShirtId(2);
        tShirt.setSize("Medium");
        tShirt.setColor("Red");
        tShirt.setDescription("This shirt is red");
        tShirt.setPrice(BigDecimal.valueOf(16.99));
        tShirt.setQuantity(55);
        tShirtRepository.save(tShirt);

        List<TShirt> tShirtList = tShirtRepository.findByColor("Green");
        assertEquals(tShirtList.size(), 1);
    }

}
