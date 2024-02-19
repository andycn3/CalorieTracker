package model;

public class Food {

    private String name;
    private int cals;
    private int servingSize;

    //EFFECTS: Creates a new food with a name and how many calories per serving and serving size in grams
    public Food(String name, int cals, int servingSize) {
        this.name = name;
        this.cals = cals;
        this.servingSize = servingSize;
    }

    public int getServingSize() {
        return servingSize;
    }

    public String getName() {
        return name;
    }

    public int getCals() {
        return cals;
    }


}