package model;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class SavedFoodsTest {

    SavedFoods sf;

    @BeforeEach
    void runBefore() {
        sf = new SavedFoods();
    }


    @Test
    void constructorTest() {
        assertEquals(sf.getKnownFoods().size(), 11);
    }

    @Test
    void setKnownFoodsTest() {
        SavedFoods saved = new SavedFoods();
        saved.addSavedFood("Cake", 500);
        sf.setKnownFoods(saved.getKnownFoods());
        assertEquals(sf.getKnownFoods(), saved.getKnownFoods());
    }
    @Test
    void addSavedFoodTest() {
        sf.addSavedFood("Apple", 50);
        assertEquals(12, sf.getKnownFoods().size());
        String s = sf.getKnownFoods().get(11).getName();
        assertEquals("Apple", s);
    }

    @Test
    void addAlreadySavedFoodTest() {
        sf.addSavedFood("burger", 400);
        assertEquals(11, sf.getKnownFoods().size());
        String s = sf.getKnownFoods().get(10).getName();
        assertEquals("rice", s);
    }

    @Test
    void hasNameTest() {
        assertTrue(sf.hasName("alfredo pasta"));
        assertFalse(sf.hasName("bread"));
    }
}
