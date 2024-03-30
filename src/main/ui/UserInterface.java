package ui;

import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

public class UserInterface {

    private Scanner input = new Scanner(System.in);
    private SavedFoods foods = new SavedFoods();
    private DailyTracker dt = new DailyTracker();
    private WeeklyTracker wt = new WeeklyTracker();
    private WeightGoal wg;
    private TrackerManager tm;
    private LinkedList<String> savedFoods = new LinkedList<>();
    private static final String JSON_STORE = "./data/trackermanager.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;


    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    public UserInterface() {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        System.out.println("Hello!");
        System.out.println("If you'd like to start a new file type \"new\"");
        System.out.println("If you'd like to load your previous file enter anything");

        String start = input.nextLine();
        start = start.toLowerCase();

        if (start.equals("new")) {
            starter();
        } else {
            loadTrackerManager();
            dt = new DailyTracker();
            dt.setDailyCount(tm.getDailyTracker().getDailyCount());
            dt.setPreviousCals(tm.getDailyTracker().getPreviousCals());
            wg = new WeightGoal("lose", 1);
            wg.setGoal(tm.getWeightGoal().getGoal());
            wg.setCurrentWeight(tm.getWeightGoal().getCurrentWeight());
            foods = new SavedFoods();
            foods.setKnownFoods(tm.getSavedFoods().getKnownFoods());
            wt = new WeeklyTracker();
            wt.setWeekCals(tm.getWeeklyTracker().getWeekCals());
        }

        String skipLine = input.nextLine();
        runProgram();
    }

    private void printCommands() {
        System.out.println("You can add more food by typing \"Add\"");
        System.out.println("To quickly add what your just ate, type \"Previous\"");
        System.out.println("If you'd like to add a food to the database type \"Favourite\"");
        System.out.println("You can remove calories by typing \"Exercise\"");
        System.out.println("You can end the day and add today's progress to your week by typing \"End\"");
        System.out.println("You can view your week progress by typing \"Week\"");
        System.out.println("To change your weight / goal type \"Change\"");
        System.out.println("To close app type \"Quit\"");
    }

    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    private void runProgram() {
        printCommands();
        boolean keepGoing = true;

        while (keepGoing == true) {

            String function = input.nextLine();
            function = function.toLowerCase();

            if (function.equals("add")) {
                addFood();
                String skipLine = input.nextLine();
            } else if (function.equals("previous")) {
                previous();
            } else if (function.equals("favourite")) {
                favourite();
                String skipLine = input.nextLine();
            } else if (function.equals("exercise")) {
                exercise();
                String skipLine = input.nextLine();
            } else if (function.equals("end")) {
                end();
            } else if (function.equals("week")) {
                week();
            } else if (function.equals("change")) {
                change();
            } else if (function.equals("quit")) {
                quit();
                keepGoing = false;
            } else {
                System.out.println("Please type one of the commands");
                printCommands();
            }

        }
    }

    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    private void addFood() {
        System.out.println("Enter what you ate");
        System.out.println("If you ate something in the list below, please type that in");
        for (Food i : foods.getKnownFoods()) {
            System.out.println(i.getName());
            savedFoods.add(i.getName());
        }
        String ate = input.nextLine();


        if (savedFoods.contains(ate.toLowerCase())) {
            Food nextFood = null;
            for (Food i : foods.getKnownFoods()) {
                if (i.getName().equals(ate.toLowerCase())) {
                    nextFood = i;
                }
            }
            System.out.println("How many servings of " + ate + " did you have?");
            int firstServing = input.nextInt();
            dt.addFood(nextFood, firstServing);
            System.out.println("Great, you've had " + dt.getDailyCount() + " calories");
            int farFromGoal = wg.calorieGoal(wg) - dt.getDailyCount();
            if (farFromGoal >= 0) {
                System.out.println("And you're " + farFromGoal + " calories from your goal");
            } else {
                System.out.println("And you're " + (farFromGoal * -1) + " calories over your goal");
            }
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
            if (farFromGoal >= 0) {
                System.out.println("And you're " + farFromGoal + " calories from your goal");
            } else {
                System.out.println("And you're " + (farFromGoal * -1) + " calories over your goal");
            }

        }
        tm.setDailyTracker(dt);
    }

    private void previous() {
        dt.addPrevious();
        tm.setDailyTracker(dt);
        System.out.println("Great, you've had " + dt.getDailyCount() + " calories");

        int farFromGoal = wg.calorieGoal(wg) - dt.getDailyCount();
        if (farFromGoal >= 0) {
            System.out.println("And you're " + farFromGoal + " calories from your goal");
        } else {
            System.out.println("And you're " + (farFromGoal * -1) + " calories over your goal");
        }
    }

    private void favourite() {
        System.out.println("What is this food called?");
        String favName = input.nextLine();
        if (foods.hasName(favName)) {
            System.out.println("That's already in our system!");
        } else {
            System.out.println("How many calories are in one serving of " + favName);
            int favCals = input.nextInt();
            foods.addSavedFood(favName, favCals);
            tm.setSavedFoods(foods);
            System.out.println("Your food has been added");
        }
    }

    private void exercise() {
        System.out.println("How many calories did you burn?");
        int burnt = input.nextInt();
        dt.removeCals(burnt);

        System.out.println("Great, you've had " + dt.getDailyCount() + " calories");

        int farFromGoal = wg.calorieGoal(wg) - dt.getDailyCount();
        if (farFromGoal >= 0) {
            System.out.println("And you're " + farFromGoal + " calories from your goal");
        } else {
            System.out.println("And you're " + (farFromGoal * -1) + " calories over your goal");
        }
        tm.setDailyTracker(dt);
    }

    private void week() {
        System.out.println(wt.getWeekCals());
    }

    private void change() {
        System.out.println("Enter your new weight");
        int newWeight = input.nextInt();
        wg.setCurrentWeight(newWeight);
        String skipLine = input.nextLine();
        System.out.println("Enter your new goal");
        String newGoal = input.nextLine();
        newGoal = newGoal.toLowerCase();
        wg.setGoal(newGoal);
        System.out.println("Your new daily calorie goal is " + wg.calorieGoal(wg));
        tm.setWeightGoal(wg);
    }

    private void end() {
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
            if (farFromGoal >= 0) {
                System.out.println("And you were " + farFromGoal + " calories from your goal");
            } else {
                System.out.println("And you were " + (farFromGoal * -1) + " calories over your goal");
            }

            wt.addDay(dt);
            tm.setWeeklyTracker(wt);
            dt.setDailyCount(0);

        }
    }

    private void quit() {
        System.out.println("Would you like to save your tracker? (yes, no)");
        String save = input.nextLine();
        save = save.toLowerCase();
        if (save.equals("yes")) {
            try {
                jsonWriter.open();
                jsonWriter.write(tm);
                jsonWriter.close();
                System.out.println("Saved data to " + JSON_STORE);
            } catch (FileNotFoundException e) {
                System.out.println("Unable to write to file: " + JSON_STORE);
            }
        }
        System.out.println("Thanks for using the program");
    }

    private void loadTrackerManager() {
        try {
            tm = jsonReader.read();
            System.out.println(tm.getName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    private void starter() {
        System.out.println("Hello! Welcome to this Calorie Tracker. Please Begin by entering your weight in lbs");
        int currentWeight = input.nextInt();

        System.out.println("Great! Now what's your weight goal? (Gain, Maintain, Lose)");

        String skipLine = input.nextLine();
        String goal = input.nextLine();
        goal = goal.toLowerCase();

        wg = new WeightGoal(goal, currentWeight);
        tm = new TrackerManager("Loaded data", wg);
        System.out.println("From our calculations you should consume " + wg.calorieGoal(wg) + " calories a day.");

        System.out.println("Start entering foods that you've eaten by typing \"New\"");
        String next = input.nextLine();
        next = next.toLowerCase();

        if (next.equals("new")) {
            addFood();
        }
    }

}
