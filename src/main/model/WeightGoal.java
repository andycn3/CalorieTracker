package model;

public class WeightGoal {

    private String goal;
    private int currentWeight;

    //REQUIRES: goal must be one of ["Lose", "Maintain", "Gain"]
    //EFFECTS: Creates a weightGoal with their weight goal and current weight
    public WeightGoal(String goal, int currentWeight) {
        this.goal = goal;
        this.currentWeight = currentWeight;
    }

    public String getGoal() {
        return goal;
    }

    public void setGoal(String newGoal) {
        goal = newGoal;
    }

    public int getCurrentWeight() {
        return currentWeight;
    }

    public void setCurrentWeight(int newWeight) {
        currentWeight = newWeight;
    }

    //EFFECTS: Returns approximately how many calories the user should eat per day
    public int calorieGoal(WeightGoal wg) {
        if (wg.getGoal().equals("Maintain")) {
            return 15 * wg.getCurrentWeight();
        } else if (wg.getGoal().equals("Lose")) {
            return (15 * wg.getCurrentWeight()) - 700;
        } else {
            return (15 * wg.getCurrentWeight()) + 700;
        }
    }

}
