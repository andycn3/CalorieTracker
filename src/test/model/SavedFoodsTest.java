package model;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

public class SavedFoodsTest {

    SavedFoods sf = new SavedFoods();

    @Test
    void constructorTest() {
        assertEquals(sf.getSavedFoods().size(), 11);
    }

    @Test
    void addSavedFoodTest() {
        sf.addSavedFood("Apple", 50, 100);
        assertEquals(12, sf.getSavedFoods().size());
        String s = sf.getSavedFoods().get(11).getName();
        assertEquals("Apple", s);
    }
}
