package model;

import org.json.JSONArray;

import java.util.LinkedList;

//A list of common food items with name and calories per serving
public class SavedFoods {

    private LinkedList<Food> knownFoods;


    public SavedFoods() {
        knownFoods = new LinkedList<Food>();
        knownFoods.add(new Food("burger", 400, 1));
        knownFoods.add(new Food("pizza slice", 285, 1));
        knownFoods.add(new Food("fries", 365, 1));
        knownFoods.add(new Food("alfredo pasta", 1200, 1));
        knownFoods.add(new Food("tomato pasta", 380, 1));
        knownFoods.add(new Food("wings", 309, 1));
        knownFoods.add(new Food("tacos", 160, 1));
        knownFoods.add(new Food("steak", 550, 1));
        knownFoods.add(new Food("cereal", 250, 1));
        knownFoods.add(new Food("eggs", 148, 1));
        knownFoods.add(new Food("rice", 200, 1));
    }

    public LinkedList<Food> getKnownFoods() {
        return knownFoods;
    }

    public void setKnownFoods(LinkedList<Food> f) {
        knownFoods = f;
    }

    //REQUIRES: Food is not already in knownFoods and size > 0
    //MODIFIES: This
    //EFFECTS: Adds food to saved foods
    public void addSavedFood(String meal, int cals) {
        Food food = new Food(meal, cals, 1);
        if (!(hasName(meal))) {
            knownFoods.add(food);
        }

    }

    public boolean hasName(String meal) {
        boolean hasName = false;
        for (Food f : knownFoods) {
            if (meal.equals(f.getName())) {
                hasName = true;
                return hasName;
            }
        }
        return hasName;
    }

    public JSONArray toJson() {
        JSONArray jsonArray = new JSONArray();

        for (Food f : knownFoods) {
            jsonArray.put(f.toJson());
        }

        return jsonArray;
    }

}

