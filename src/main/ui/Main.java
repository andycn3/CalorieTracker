//Main method

package ui;

import model.TrackerManager;
import model.WeightGoal;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        WeightGoal wg = new WeightGoal("lose", 180);
        TrackerManager tm = new TrackerManager("tm", wg);
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new StarterGui();
            }
        });
    }
}
