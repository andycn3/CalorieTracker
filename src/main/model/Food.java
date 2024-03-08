package model;

import org.json.JSONObject;
import persistence.Writable;

//A Food / meal item that has a name, # of calories per serving and a serving size
public class Food implements Writable {

    private String name;
    private int cals;
    private int servingSize;

    //EFFECTS: Creates a new food with a name and how many calories per serving and serving size in grams
    // but serving size becomes 1 if it is added to saved foods
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

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("cals", cals);
        json.put("servingSize", servingSize);
        return json;
    }

}