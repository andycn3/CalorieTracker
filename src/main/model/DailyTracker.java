


package model;

import org.json.JSONObject;
import persistence.Writable;

// //A counter that tracks the users caloric intake for the day and the calories of the previous entry
public class DailyTracker implements Writable {

    private int dailyCount;
    private int previousCals;

    //EFFECTS: Creates a new daily tracker with the calories for the day set at 0
    public DailyTracker() {
        dailyCount = 0;
        previousCals = 0;
    }

    //REQUIRES: meal is one of the foods in the database and serving > 0
    //MODIFIES: This
    //EFFECTS: Calculates and adds the amount of calories consumed for that sitting and saves it as previousCals
    public void addFood(Food meal, int serving) {
        dailyCount += (meal.getCals() * serving);
        previousCals = (meal.getCals() * serving);
        String s = "added " + previousCals + " to daily tracker";
        Event added = new Event(s);
        EventLog.getInstance().logEvent(added);

    }

    //REQUIRES: Food is not in the database and serving and servingSize > 0
    //MODIFIES: This
    //EFFECTS: Calculates and adds the amount of calories consumed for that sitting and saves it as previousCals
    public void addFoodNotListed(int cals, int serving, int servingSize) {
        dailyCount += (int) (cals * ((double) serving / (double) servingSize));
        previousCals = (int) (cals * ((double) serving / (double) servingSize));
        String s = "added " + previousCals + " to daily tracker";
        Event added = new Event(s);
        EventLog.getInstance().logEvent(added);
    }

    //REQUIRES: previousCals isn't at 0
    //MODIFIES: This
    //EFFECTS: Adds previousCals to the daily calorie count
    public void addPrevious() {
        dailyCount += previousCals;
        String s = "added " + previousCals + " to daily tracker";
        Event added = new Event(s);
        EventLog.getInstance().logEvent(added);
    }

    //REQUIRES: cals > 0
    //MODIFIES: This
    //EFFECTS: Removes calories burned from dailyCount
    public void removeCals(int cals) {
        dailyCount -= cals;
        String s = "removed " + previousCals + " to daily tracker";
        Event removed = new Event(s);
        EventLog.getInstance().logEvent(removed);
    }

    public int getDailyCount() {
        return dailyCount;
    }

    public int getPreviousCals() {
        return previousCals;
    }

    public void setDailyCount(int num) {
        dailyCount = num;
        String s = "set daily tracker to " + num;
        Event added = new Event(s);
        EventLog.getInstance().logEvent(added);
    }

    public void setPreviousCals(int num) {
        previousCals = num;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("dailyCount", dailyCount);
        json.put("previousCals", previousCals);
        return json;
    }

}
