package persistence;

import model.DailyTracker;
import model.TrackerManager;
import model.WeightGoal;
import model.Food;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Represents a reader that reads workroom from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads TrackerManager from file and returns it;
    // throws IOException if an error occurs reading data from file
    public TrackerManager read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseTrackerManager(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses TrackerManager from JSON object and returns it
    private TrackerManager parseTrackerManager(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        JSONObject weightGoal = jsonObject.getJSONObject("weightGoal");
        String goal = weightGoal.getString("goal");
        int currentWeight = weightGoal.getInt("currentWeight");
        WeightGoal wg = new WeightGoal(goal, currentWeight);
        DailyTracker dt = new DailyTracker();
        JSONObject dayTracker = jsonObject.getJSONObject("dailyTracker");
        int dc = dayTracker.getInt("dailyCount");
        int pc = dayTracker.getInt("previousCals");
        dt.setDailyCount(dc);
        dt.setPreviousCals(pc);
        TrackerManager tm = new TrackerManager(name, wg);
        JSONArray weekCount = jsonObject.getJSONArray("weeklyTracker");
        addWeeklyCount(jsonObject, tm);
        addFoods(jsonObject, tm);
        tm.setDailyTracker(dt);
        return tm;
    }

    // MODIFIES: tm
    // EFFECTS: parses savedFoods from JSON object and adds them to workroom
    private void addFoods(JSONObject jsonObject, TrackerManager tm) {
        JSONArray jsonArray = jsonObject.getJSONArray("knownFoods");
        for (Object json : jsonArray) {
            JSONObject nextFood = (JSONObject) json;
            String meal = nextFood.getString("name");
            int cals = nextFood.getInt("cals");
            int size = nextFood.getInt("servingSize");
            Food f = new Food(meal, cals, size);
            if (!(tm.getSavedFoods().getKnownFoods().contains(f))) {
                tm.getSavedFoods().addSavedFood(meal, cals);
            }
        }
    }

    // MODIFIES: tm
    // EFFECTS: parses weekly count from JSON object and adds them to workroom
    private void addWeeklyCount(JSONObject jsonObject, TrackerManager tm) {
        JSONArray jsonArray = jsonObject.getJSONArray("weeklyTracker");
        for (Object json : jsonArray) {
            JSONObject dayCount = (JSONObject) json;
            addDayCount(dayCount, tm);
        }
    }

    // MODIFIES: wr
    // EFFECTS: parses each day count in weekly count from JSON object and adds them to workroom
    private void addDayCount(JSONObject jsonObject, TrackerManager tm) {
        int d = jsonObject.getInt("aDayCount");
        DailyTracker day = new DailyTracker();
        day.setDailyCount(d);
        tm.getWeeklyTracker().addDay(day);
    }
}

