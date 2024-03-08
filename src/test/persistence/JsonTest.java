package persistence;

import model.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {

    protected void checkWightGoal(String goal, int weight, WeightGoal wg) {
        assertEquals(goal, wg.getGoal());
        assertEquals(weight, wg.getCurrentWeight());
    }

    protected void checkNewFoods(int newFoods, SavedFoods x, SavedFoods y) {
        int i = y.getKnownFoods().size() - x.getKnownFoods().size();
        assertEquals(newFoods, i);
    }

    protected void checkFood(String name, int cals, int size, Food f) {
        assertEquals(name, f.getName());
        assertEquals(cals, f.getCals());
        assertEquals(size, f.getServingSize());
    }

    protected void checkDayCount(int dayCount, DailyTracker dt) {
        assertEquals(dayCount, dt.getDailyCount());
    }

    protected void checkPreviousCals(int prev, DailyTracker dt) {
        assertEquals(prev, dt.getPreviousCals());
    }

}