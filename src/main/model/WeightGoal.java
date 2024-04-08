package model;

import org.json.JSONObject;
import persistence.Writable;

//Represents what the user wants to do about their weight either lose, maintain, or gain
//and what their current weight is
public class WeightGoal implements Writable {

    private String goal;
    private int currentWeight;

    //REQUIRES: goal must be one of ["Lose", "Maintain", "Gain"]
    //EFFECTS: Creates a weightGoal with their weight goal and current weight
    public WeightGoal(String goal, int currentWeight) {
        this.goal = goal;
        this.currentWeight = currentWeight;
        String s = "created a new weight goal: " + goal + ", " + currentWeight + "lbs";
        Event weight = new Event(s);
        EventLog.getInstance().logEvent(weight);
    }

    public String getGoal() {
        return goal;
    }

    public void setGoal(String newGoal) {
        String s = "set goal to " + newGoal;
        Event x = new Event(s);
        EventLog.getInstance().logEvent(x);
        goal = newGoal;
    }

    public int getCurrentWeight() {
        return currentWeight;
    }

    public void setCurrentWeight(int newWeight) {
        String s = "set current weight to " + newWeight;
        Event x = new Event(s);
        EventLog.getInstance().logEvent(x);
        currentWeight = newWeight;
    }

    //EFFECTS: Returns approximately how many calories the user should eat per day
    public int calorieGoal(WeightGoal wg) {
        if (wg.getGoal().equals("maintain")) {
            return 15 * wg.getCurrentWeight();
        } else if (wg.getGoal().equals("lose")) {
            return (15 * wg.getCurrentWeight()) - 700;
        } else {
            return (15 * wg.getCurrentWeight()) + 700;
        }
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("goal", goal);
        json.put("currentWeight", currentWeight);
        return json;
    }

}
