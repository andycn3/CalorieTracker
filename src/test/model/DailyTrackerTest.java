package model;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

class DailyTrackerTest {

    DailyTracker dt;
    SavedFoods s = new SavedFoods();

    @BeforeEach
    void runBefore() {
        dt = new DailyTracker();
    }

    @Test
    void constructorTest() {
        assertEquals(0, dt.getDailyCount());
    }

    @Test
    void addFoodTest() {
        dt.addFood(s.getSavedFoods().get(0), 1);
        assertEquals(400, dt.getDailyCount());
        assertEquals(400, dt.getPreviousCals());
        dt.addFood(s.getSavedFoods().get(3), 2);
        assertEquals(2800, dt.getDailyCount());
        assertEquals(2400, dt.getPreviousCals());
    }

    @Test
    void addFoodNotListedTest() {
        dt.addFoodNotListed(300, 5, 3);
        assertEquals(500, dt.getDailyCount());
        assertEquals(500, dt.getPreviousCals());
        dt.addFoodNotListed(20, 4,4);
        assertEquals(520, dt.getDailyCount());
        assertEquals(20, dt.getPreviousCals());
    }

    @Test
    void addPreviousTest() {
        dt.addFoodNotListed(20, 1, 1);
        assertEquals(20, dt.getDailyCount());
        assertEquals(20, dt.getPreviousCals());
        dt.addPrevious();
        assertEquals(40, dt.getDailyCount());
        assertEquals(20, dt.getPreviousCals());
    }

    @Test
    void removeCalsTest() {
        dt.removeCals(400);
        assertEquals(-400, dt.getDailyCount());
        dt.addFoodNotListed(500, 1, 1);
        dt.removeCals(100);
        assertEquals(0, dt.getDailyCount());
    }

}