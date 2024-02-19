package model;

import java.util.LinkedList;

public class SavedFoods {

    private LinkedList<Food> knownFoods;


    public SavedFoods() {
        knownFoods = new LinkedList<Food>();
        knownFoods.add(new Food("Burger", 400, 1));
        knownFoods.add(new Food("Pizza Slice", 285, 1));
        knownFoods.add(new Food("Fries", 365, 1));
        knownFoods.add(new Food("Alfredo Pasta", 1200, 1));
        knownFoods.add(new Food("Tomato Pasta", 380, 1));
        knownFoods.add(new Food("Wings", 309, 1));
        knownFoods.add(new Food("Tacos", 160, 1));
        knownFoods.add(new Food("Steak", 550, 1));
        knownFoods.add(new Food("Cereal", 250, 1));
        knownFoods.add(new Food("Eggs", 148, 1));
        knownFoods.add(new Food("Rice", 200, 1));
    }

    public LinkedList<Food> getSavedFoods() {
        return knownFoods;
    }

    //REQUIRES: Food is not already in knownFoods
    //MODIFIES: This
    //EFFECTS: Adds food to
    public void addSavedFood(String meal, int cals, int size) {
        Food food = new Food(meal, cals, size);
        knownFoods.add(food);
    }

}

