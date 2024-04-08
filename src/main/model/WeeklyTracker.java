package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.LinkedList;

//A list of the user's final daily tracker counts for the past week
public class WeeklyTracker {

    private LinkedList<Integer> weekCals;
    private int dayCount;

    //Creates a new empty week of daily calorie counts
    public WeeklyTracker() {
        weekCals = new LinkedList<Integer>();
    }

    public LinkedList<Integer> getWeekCals() {
        return weekCals;
    }

    public void setWeekCals(LinkedList<Integer> w) {
        weekCals = w;
    }

    //MODIFIES: This
    //EFFECTS: If there isn't 7 entries in the list then it just adds the calorie count for that day
    //If there is 7 entries in the list it adds the current daily count and removes the earliest entry
    public void addDay(DailyTracker dayCals) {
        if (weekCals.size() < 7) {
            dayCount = dayCals.getDailyCount();
            weekCals.add(dayCount);
        } else {
            weekCals.remove();
            dayCount = dayCals.getDailyCount();
            weekCals.add(dayCount);
        }
        String s = "added new day with "  + dayCals.getDailyCount() + " calories to week";
        Event addDay = new Event(s);
        EventLog.getInstance().logEvent(addDay);
    }

    private JSONObject dayCountToJson(int dayCount) {
        JSONObject json = new JSONObject();
        json.put("aDayCount", dayCount);
        return json;
    }

    public JSONArray toJson() {
        JSONArray jsonArray = new JSONArray();

        for (int d : weekCals) {
            jsonArray.put(dayCountToJson(d));
        }

        return jsonArray;
    }



}
