package ui;

import model.*;

import java.util.LinkedList;
import java.util.Scanner;

public class Main {
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        SavedFoods foods = new SavedFoods();

        DailyTracker dt = new DailyTracker();
        WeeklyTracker wt = new WeeklyTracker();
        WeightGoal wg;


        System.out.println("Hello! Welcome to this Calorie Tracker. Please Begin by entering your weight in lbs");
        int currentWeight = input.nextInt();

        System.out.println("Great! Now what's your weight goal? (Gain, Maintain, Lose)");

        String skipLine = input.nextLine();
        String goal = input.nextLine();

        wg = new WeightGoal(goal, currentWeight);
        System.out.println("From our calculations you should consume " + wg.calorieGoal(wg) + " calories a day.");

        System.out.println("Start entering foods that you've eaten by typing \"New\"");
        String next = input.nextLine();

        LinkedList<String> savedFoods = new LinkedList<>();

        if (next.equals("New")) {
            System.out.println("If you ate something in the list below, please type that in");
            for (Food i : foods.getSavedFoods()) {
                System.out.println(i.getName());
                savedFoods.add(i.getName());
            }
        }

        String firstFoodName = input.nextLine();

        if (savedFoods.contains(firstFoodName)) {
            Food firstFood = null;
            for (Food i : foods.getSavedFoods()) {
                if (i.getName().equals(firstFoodName)) {
                    firstFood = i;
                }
            }
            System.out.println("How many servings of " + firstFoodName + " did you have?");
            int firstServing = input.nextInt();
            dt.addFood(firstFood, firstServing);
            System.out.println("Great, you've had " + dt.getDailyCount() + " calories");


            int farFromGoal = wg.calorieGoal(wg) - dt.getDailyCount();
            System.out.println("And you're " + farFromGoal + " your daily goal (negative means over)");
        } else {
            System.out.println("That doesn't seem to be in our system");
            System.out.println("How many calories are in each serving of " + firstFoodName);
            int cals = input.nextInt();
            System.out.println("How many grams is one serving size of " + firstFoodName);
            int serveSize = input.nextInt();
            System.out.println("How many grams of " + firstFoodName + " did you eat");
            int consumed = input.nextInt();
            dt.addFoodNotListed(cals, consumed, serveSize);

            System.out.println("Great, you've had " + dt.getDailyCount() + " calories");

            int farFromGoal = wg.calorieGoal(wg) - dt.getDailyCount();
            System.out.println("And you're " + farFromGoal + " your daily goal (negative means over)");
        }

        System.out.println("You can add more food by typing \"Add\"");
        System.out.println("To quickly add what your just ate, type \"Previous\"");
        System.out.println("If you'd like to add a food to the database type \"Favourite\"");
        System.out.println("You can remove calories by typing \"Exercise\"");
        System.out.println("You can end the day and add today's progress to your week by typing \"End\"");
        System.out.println("You can view your week progress by typing \"Week\"");
        System.out.println("To change your weight / goal type \"Change\"");

        while (true) {

            String function = input.nextLine();

            if (function.equals("Add")) {
                System.out.println("Enter what you ate");
                String ate = input.nextLine();

                if (savedFoods.contains(ate)) {
                    Food nextFood = null;
                    for (Food i : foods.getSavedFoods()) {
                        if (i.getName().equals(ate)) {
                            nextFood = i;
                        }
                    }
                    System.out.println("How many servings of " + ate + " did you have?");
                    int firstServing = input.nextInt();
                    dt.addFood(nextFood, firstServing);
                    System.out.println("Great, you've had " + dt.getDailyCount() + " calories");


                    int farFromGoal = wg.calorieGoal(wg) - dt.getDailyCount();
                    System.out.println("And you're " + farFromGoal + " your daily goal (negative means over)");
                } else {
                    System.out.println("That doesn't seem to be in our system");
                    System.out.println("How many calories are in each serving of " + ate);
                    int cals = input.nextInt();
                    System.out.println("How many grams is one serving size of " + ate);
                    int serveSize = input.nextInt();
                    System.out.println("How many grams of " + ate + " did you eat");
                    int consumed = input.nextInt();
                    dt.addFoodNotListed(cals, consumed, serveSize);

                    System.out.println("Great, you've had " + dt.getDailyCount() + " calories");

                    int farFromGoal = wg.calorieGoal(wg) - dt.getDailyCount();
                    System.out.println("And you're " + farFromGoal + " your daily goal (negative means over)");
                }
            }

            if (function.equals("Previous")) {
                dt.addPrevious();
                System.out.println("Great, you've had " + dt.getDailyCount() + " calories");

                int farFromGoal = wg.calorieGoal(wg) - dt.getDailyCount();
                System.out.println("And you're " + farFromGoal + " your daily goal (negative means over)");
            }

            if (function.equals("Favourite")) {
                System.out.println("What is this food called?");
                String favName = input.nextLine();
                System.out.println("How many calories are in one serving of " + favName);
                int favCals = input.nextInt();
                System.out.println("How many grams is one serving of " + favName);
                int favSize = input.nextInt();
                foods.addSavedFood(favName, favCals, favSize);
                System.out.println("Your food has been added");
            }

            if (function.equals("Exercise")) {
                System.out.println("How many calories did you burn?");
                int burnt = input.nextInt();
                dt.removeCals(burnt);

                System.out.println("Great, you've had " + dt.getDailyCount() + " calories");

                int farFromGoal = wg.calorieGoal(wg) - dt.getDailyCount();
                System.out.println("And you're " + farFromGoal + " your daily goal (negative means over)");
            }

            if (function.equals("End")) {
                if (dt.getDailyCount() == 0) {
                    System.out.println("Did you eat anything today? (Yes or No)");
                    String noCals = input.nextLine();
                    if (noCals.equals("No")) {
                        wt.addDay(dt);
                    }
                    if (noCals.equals("Yes")) {
                        System.out.println("Please type \"Add\" instead and enter what you ate");
                    }
                } else {
                    System.out.println("You had " + dt.getDailyCount() + " calories today");
                    int farFromGoal = wg.calorieGoal(wg) - dt.getDailyCount();
                    System.out.println("And you're " + farFromGoal + " your daily goal (negative means over)");

                    wt.addDay(dt);
                    dt = new DailyTracker();

                }
            }

            if (function.equals("Week")) {
                System.out.println(wt.getWeekCals());
            }

            if (function.equals("Change")) {
                System.out.println("Enter your new weight");
                int newWeight = input.nextInt();
                wg.setCurrentWeight(newWeight);
                System.out.println("Enter your new goal");
                String newGoal = input.nextLine();
                wg.setGoal(newGoal);
                System.out.println("Your new daily calorie goal is " + wg.calorieGoal(wg));
            }
        }

    }
}
