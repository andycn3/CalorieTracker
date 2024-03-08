package model;

import org.json.JSONObject;
import persistence.Writable;

//Represents the file holding the users day tracker, week tracker, weight goal, and common foods that they saved
public class TrackerManager implements Writable {
    private String name;
    private DailyTracker dt;
    private SavedFoods knownFoods;
    private WeeklyTracker weekCals;
    private WeightGoal wg;

    public TrackerManager(String name, WeightGoal wg) {
        this.name = name;
        dt = new DailyTracker();
        knownFoods = new SavedFoods();
        weekCals = new WeeklyTracker();
        this.wg = wg;
    }

    public String getName() {
        return name;
    }

    public DailyTracker getDailyTracker() {
        return dt;
    }

    public SavedFoods getSavedFoods() {
        return knownFoods;
    }

    public WeeklyTracker getWeeklyTracker() {
        return weekCals;
    }

    public WeightGoal getWeightGoal() {
        return wg;
    }

    public void setWeightGoal(WeightGoal goal) {
        wg = goal;
    }

    public void setSavedFoods(SavedFoods sv) {
        knownFoods = sv;
    }

    public void setWeeklyTracker(WeeklyTracker wt) {
        weekCals = wt;
    }

    public void setDailyTracker(DailyTracker dt) {
        this.dt = dt;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("dailyTracker", dt.toJson());
        json.put("knownFoods", knownFoods.toJson());
        json.put("weeklyTracker", weekCals.toJson());
        json.put("weightGoal", wg.toJson());
        return json;
    }
}
