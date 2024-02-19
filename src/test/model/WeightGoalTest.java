package model;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

public class WeightGoalTest {

    WeightGoal wg = new WeightGoal("Lose", 150);

    @Test
    void constructorTest() {
        assertEquals(wg.getGoal(), "Lose");
        assertEquals(wg.getCurrentWeight(), 150);
    }

    @Test
    void calorieGoalTest() {
        assertEquals(1550, wg.calorieGoal(wg));
        wg.setGoal("Maintain");
        assertEquals(2250, wg.calorieGoal(wg));
        wg.setGoal("Gain");
        assertEquals(2950, wg.calorieGoal(wg));
        wg.setGoal("Maintain");
        wg.setCurrentWeight(190);
        assertEquals(2850, wg.calorieGoal(wg));
    }

}
