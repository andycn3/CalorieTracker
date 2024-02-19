package model;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import java.time.DayOfWeek;
import java.util.ArrayList;

public class WeeklyTrackerTest {

    WeeklyTracker wt;
    DailyTracker d1;
    DailyTracker d2;
    DailyTracker d3;
    DailyTracker d4;
    DailyTracker d5;
    DailyTracker d6;
    DailyTracker d7;
    DailyTracker d8;

    @BeforeEach
    void runBefore() {
        wt = new WeeklyTracker();
        d1 = new DailyTracker();
        d1.addFoodNotListed(1, 1, 1);
        d2 = new DailyTracker();
        d2.addFoodNotListed(2, 1, 1);
        d3 = new DailyTracker();
        d3.addFoodNotListed(3, 1, 1);
        d4 = new DailyTracker();
        d4.addFoodNotListed(4, 1, 1);
        d5 = new DailyTracker();
        d5.addFoodNotListed(5, 1, 1);
        d6 = new DailyTracker();
        d6.addFoodNotListed(6, 1, 1);
        d7 = new DailyTracker();
        d7.addFoodNotListed(7, 1, 1);
        d8 = new DailyTracker();
        d8.addFoodNotListed(8, 1, 1);
    }

    @Test
    void constructorTest() {
        assertEquals(wt.getWeekCals().size(), 0);
    }

    @Test
    void addDayTest() {
        wt.addDay(d1);
        assertEquals(1, wt.getWeekCals().size());
        wt.addDay(d2);
        assertEquals(2, wt.getWeekCals().size());
        wt.addDay(d3);
        wt.addDay(d4);
        wt.addDay(d5);
        wt.addDay(d6);
        assertEquals(6, wt.getWeekCals().size());
        assertEquals(d6.getDailyCount(), wt.getWeekCals().get(5));
        assertEquals(d1.getDailyCount(), wt.getWeekCals().get(0));
        wt.addDay(d7);
        assertEquals(7, wt.getWeekCals().size());
        assertEquals(d7.getDailyCount(), wt.getWeekCals().get(6));
        assertEquals(d1.getDailyCount(), wt.getWeekCals().get(0));
        wt.addDay(d8);
        assertEquals(7, wt.getWeekCals().size());
        assertEquals(d8.getDailyCount(), wt.getWeekCals().get(6));
        assertEquals(d2.getDailyCount(), wt.getWeekCals().get(0));
    }
}
