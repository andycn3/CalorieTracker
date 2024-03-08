package persistence;

import model.Food;
import model.SavedFoods;
import model.TrackerManager;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            TrackerManager tm = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyTrackerManager() {
        try {
            JsonReader reader = new JsonReader("./data/testReaderEmptyTrackerManager.json");
            TrackerManager tm = reader.read();
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
            JsonReader reader = new JsonReader("./data/testReaderGeneralTrackerManager.json");
            TrackerManager tm = reader.read();
            assertEquals("Loaded File", tm.getName());

            checkWightGoal("maintain", 185, tm.getWeightGoal());

            SavedFoods initial = new SavedFoods();
            checkNewFoods(2, initial, tm.getSavedFoods());
            Food addedFirst = tm.getSavedFoods().getKnownFoods().get(11);
            Food addedSecond = tm.getSavedFoods().getKnownFoods().get(12);
            checkFood("Cake", 600, 1, addedFirst);
            checkFood("Banana", 45, 1, addedSecond);

            checkDayCount(3165, tm.getDailyTracker());
            checkPreviousCals(720, tm.getDailyTracker());

            assertEquals(3, tm.getWeeklyTracker().getWeekCals().size());
            assertEquals(4150, tm.getWeeklyTracker().getWeekCals().get(0));
            assertEquals(3333, tm.getWeeklyTracker().getWeekCals().get(1));
            assertEquals(4567, tm.getWeeklyTracker().getWeekCals().get(2));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}