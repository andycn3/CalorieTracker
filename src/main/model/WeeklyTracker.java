package model;

import java.util.LinkedList;

public class WeeklyTracker {

    private LinkedList<Integer> weekCals;

    //Creates a new empty week of daily calorie counts
    public WeeklyTracker() {
        weekCals = new LinkedList<Integer>();
    }

    public LinkedList<Integer> getWeekCals() {
        return weekCals;
    }

    //MODIFIES: This
    //EFFECTS: If there isn't 7 entries in the list then it just adds the calorie count for that day
    //If there is 7 entries in the list it adds the current daily count and removes the earliest entry
    public void addDay(DailyTracker dayCals) {
        if (weekCals.size() < 7) {
            weekCals.add(dayCals.getDailyCount());
        } else {
            weekCals.remove();
            weekCals.add(dayCals.getDailyCount());
        }
    }
}
