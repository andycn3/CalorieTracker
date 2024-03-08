package persistence;

import model.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class JsonWriterTest extends JsonTest {
    //NOTE TO CPSC 210 STUDENTS: the strategy in designing tests for the JsonWriter is to
    //write data to a file and then use the reader to read it back in and check that we
    //read in a copy of what was written out.

    @Test
    void testWriterInvalidFile() {
        try {
            WeightGoal wg = new WeightGoal("gain", 0);
            TrackerManager tm = new TrackerManager("Previous load", wg);
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyTrackerManager() {
        try {
            WeightGoal wg = new WeightGoal("gain", 0);
            TrackerManager tm = new TrackerManager("Loaded file", wg);
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyTrackerManager.json");
            writer.open();
            writer.write(tm);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyTrackerManager.json");
            tm = reader.read();
            assertEquals("Loaded file", tm.getName());
            SavedFoods sf = new SavedFoods();
            assertEquals(sf.getKnownFoods().size(), tm.getSavedFoods().getKnownFoods().size());
            checkDayCount(0, tm.getDailyTracker());
            checkPreviousCals(0, tm.getDailyTracker());
            checkWightGoal("gain", 0, tm.getWeightGoal());
            assertEquals(0, tm.getWeeklyTracker().getWeekCals().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralTrackerManager() {
        try {
            WeightGoal wg = new WeightGoal("gain", 150);
            TrackerManager tm = new TrackerManager("Loaded File", wg);

            DailyTracker dt = new DailyTracker();
            dt.setDailyCount(2000);
            dt.setPreviousCals(450);
            tm.setDailyTracker(dt);

            WeeklyTracker wt = new WeeklyTracker();
            LinkedList<Integer> w = new LinkedList<Integer>();
            w.add(2500);
            w.add(2780);
            w.add(3150);
            wt.setWeekCals(w);
            tm.setWeeklyTracker(wt);

            SavedFoods sf = new SavedFoods();
            sf.addSavedFood("Bread", 100);
            sf.addSavedFood("Corn", 50);
            tm.setSavedFoods(sf);

            JsonWriter writer = new JsonWriter("./data/testWriterGeneralTrackerManager.json");
            writer.open();
            writer.write(tm);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralTrackerManager.json");
            tm = reader.read();
            assertEquals("Loaded File", tm.getName());

            checkWightGoal("gain", 150, tm.getWeightGoal());

            SavedFoods initial = new SavedFoods();
            checkNewFoods(2, initial, tm.getSavedFoods());
            Food addedFirst = tm.getSavedFoods().getKnownFoods().get(11);
            Food addedSecond = tm.getSavedFoods().getKnownFoods().get(12);
            checkFood("Bread", 100, 1, addedFirst);
            checkFood("Corn", 50, 1, addedSecond);

            checkDayCount(2000, tm.getDailyTracker());
            checkPreviousCals(450, tm.getDailyTracker());

            assertEquals(3, tm.getWeeklyTracker().getWeekCals().size());
            assertEquals(2500, tm.getWeeklyTracker().getWeekCals().get(0));
            assertEquals(2780, tm.getWeeklyTracker().getWeekCals().get(1));
            assertEquals(3150, tm.getWeeklyTracker().getWeekCals().get(2));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}