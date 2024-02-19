package model;

public class DailyTracker {

    private int dailyCount;
    private int previousCals;

    //EFFECTS: Creates a new daily tracker with the calories for the day set at 0
    public DailyTracker() {
        dailyCount = 0;
    }

    //REQUIRES: meal is one of the foods in the database
    //MODIFIES: This
    //EFFECTS: Calculates and adds the amount of calories consumed for that sitting and saves it as previousCals
    public void addFood(Food meal, int serving) {
        dailyCount += (meal.getCals() * (serving / meal.getServingSize()));
        previousCals = (meal.getCals() * (serving / meal.getServingSize()));
    }

    //REQUIRES: Food is not in the database
    //MODIFIES: This
    //EFFECTS: Calculates and adds the amount of calories consumed for that sitting and saves it as previousCals
    public void addFoodNotListed(int cals, int serving, int servingSize) {
        dailyCount += (int) (cals * ((double) serving / (double) servingSize));
        previousCals = (int) (cals * ((double) serving / (double) servingSize));
    }

    //REQUIRES: previousCals isn't at 0
    //MODIFIES: This
    //EFFECTS: Adds previousCals to the daily calorie count
    public void addPrevious() {
        dailyCount += previousCals;
    }

    //MODIFIES: This
    //EFFECTS: Removes calories burned from dailyCount
    public void removeCals(int cals) {
        dailyCount -= cals;
    }

    public int getDailyCount() {
        return dailyCount;
    }

    public int getPreviousCals() {
        return previousCals;
    }

}
