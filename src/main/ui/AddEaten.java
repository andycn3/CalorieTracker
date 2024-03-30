package ui;

import model.Food;
import model.TrackerManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

public class AddEaten extends JFrame implements ActionListener {

    private TrackerManager trackerManager;
    private JPanel left;
    private JPanel right;
    private JPanel save;
    private JPanel notSave;
    private JTextArea textArea;
    private JTextField instructions;
    private JLabel first;
    private JLabel second;
    private JLabel labelCals;
    private JLabel labelServing;
    private JLabel ateServing;
    private JTextField entry;
    private JTextField servings;
    private JTextField calories;
    private JTextField inServing;
    private JTextField servingsAte;
    private JButton enter = new JButton("Enter");
    private JLabel saved;
    private JLabel notSaved;
    private InitialGUI gui;

    //Constructor
    public AddEaten(TrackerManager tm, InitialGUI gui) {
        super("Add Eaten");
        trackerManager = tm;
        this.gui = gui;
        left = new JPanel();
        right = new JPanel();
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, left, right);
        splitPane.setOneTouchExpandable(true);
        add(splitPane);
        textArea = new JTextArea(1, 1);
        displayFoods();
        displayRight();

        enter.setBackground(Color.GREEN);
        enter.setOpaque(true);
        right.add(enter);
        enter.addActionListener(this);



        setMinimumSize(new Dimension(500, 500));
        setVisible(true);
        setLocationRelativeTo(null);
    }

    //MODIFIES: This
    //EFFECTS: Helper to assemble the right side of frame
    public void displayRight() {
        setRight();
        save = new JPanel();
        notSave = new JPanel();
        save.setLayout(new GridLayout(6, 1));
        save.add(saved);
        save.add(first);
        save.add(entry);
        save.add(second);
        save.add(servings);
        save.setVisible(true);
        notSave.setLayout(new GridLayout(7, 1));
        notSave.add(notSaved);
        notSave.add(labelCals);
        notSave.add(calories);
        notSave.add(labelServing);
        notSave.add(inServing);
        notSave.add(ateServing);
        notSave.add(servingsAte);
        notSave.setVisible(true);
        right.add(save);
        right.add(notSave);
    }

    //MODIFIES: This
    //EFFECTS: Sets the variables on right side
    public void setRight() {
        instructions = new JTextField("Enter the food information");
        saved = new JLabel("If the food is in the list, fill this out");
        notSaved = new JLabel("If the food is NOT in the list, fill this out");
        instructions.setEditable(false);
        right.add(instructions);
        entry = new JTextField();
        servings = new JTextField();
        first = new JLabel("Food:");
        second = new JLabel("Servings:");
        labelCals = new JLabel("Calories in 1 Serving:");
        calories = new JTextField();
        labelServing = new JLabel("Grams in 1 Serving");
        inServing = new JTextField();
        ateServing = new JLabel("Grams consumed");
        servingsAte = new JTextField();
        right.setLayout(new GridLayout(2, 2));
        right.setVisible(true);
    }

    //MODIFIES: This
    //EFFECTS: Creates list of foods for left side
    public void displayFoods() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        textArea.setEditable(false);
        textArea.setFont(new Font("Serif", Font.PLAIN, 10));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        LinkedList<Food> savedFoods = trackerManager.getSavedFoods().getKnownFoods();
        textArea.setVisible(true);
        String fullText = createText(savedFoods);
        textArea.setText(fullText);
        left.add(textArea);
    }

    //EFFECTS: Creates text for all saved foods
    public String createText(List<Food> savedFoods) {
        String fullText = "";
        for (Food f : savedFoods) {
            fullText += "\n" + f.getName();
        }
        return fullText;
    }

    //MODIFIES: Initial Gui
    //EFFECTS: Adds calories of food to daily tracker using either saved or not saved foods
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (!entry.getText().equals("") && !servings.getText().equals("")) {
                String food = entry.getText();
                int size = Integer.parseInt(servings.getText().trim());
                Food nextFood = null;
                for (Food i : trackerManager.getSavedFoods().getKnownFoods()) {
                    if (i.getName().equals(food.toLowerCase())) {
                        nextFood = i;
                    }
                }
                trackerManager.getDailyTracker().addFood(nextFood, size);
                gui.refreshTracker(trackerManager);
            } else {
                int cals = Integer.parseInt(calories.getText().trim());
                int g = Integer.parseInt(inServing.getText().trim());
                int ate = Integer.parseInt(servingsAte.getText().trim());
                trackerManager.getDailyTracker().addFoodNotListed(cals, ate, g);
                gui.refreshTracker(trackerManager);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "An error occurred:");
        }
        dispose();
    }

}