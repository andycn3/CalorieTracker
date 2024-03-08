package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WeightGoalTest {

    WeightGoal wg = new WeightGoal("lose", 150);

    @Test
    void constructorTest() {
        assertEquals(wg.getGoal(), "lose");
        assertEquals(wg.getCurrentWeight(), 150);
    }

    @Test
    void calorieGoalTest() {
        assertEquals(1550, wg.calorieGoal(wg));
        wg.setGoal("maintain");
        assertEquals(2250, wg.calorieGoal(wg));
        wg.setGoal("gain");
        assertEquals(2950, wg.calorieGoal(wg));
        wg.setGoal("maintain");
        wg.setCurrentWeight(190);
        assertEquals(2850, wg.calorieGoal(wg));
    }

}
