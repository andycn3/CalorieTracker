package model;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

public class FoodTest {

    Food food = new Food("Apple", 20, 40);

    @Test
    void constructorTest() {
        assertEquals("Apple", food.getName());
        assertEquals(20, food.getCals());
        assertEquals(40, food.getServingSize());
    }

}
