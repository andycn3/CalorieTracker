package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TrackerManagerTest {

    TrackerManager tm;
    WeightGoal wg;

    @BeforeEach
    void runBefore() {
        wg = new WeightGoal("gain", 170);
        tm = new TrackerManager("tracker", wg);
    }


    @Test
    void setWeightGoalTest() {
        WeightGoal weightGoal2 = new WeightGoal("lose", 190);
        tm.setWeightGoal(weightGoal2);
        assertEquals(tm.getWeightGoal(), weightGoal2);
    }
}
