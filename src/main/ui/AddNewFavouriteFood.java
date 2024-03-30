package ui;

import model.TrackerManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddNewFavouriteFood extends JFrame implements ActionListener {

    private TrackerManager tm;
    private InitialGUI gui;
    private JLabel nameLabel = new JLabel("Enter Food Name:");
    private JLabel calsLabel = new JLabel("Enter Calories Per Serving");
    private JTextArea name;
    private JTextArea cals;
    private JButton enter = new JButton("Enter");

    public AddNewFavouriteFood(TrackerManager tracker, InitialGUI graphic) {
        super("Add A Favourite");
        tm = tracker;
        gui = graphic;
        setMinimumSize(new Dimension(500, 500));
        setLayout(new GridLayout(5, 1));
        name = new JTextArea();
        cals = new JTextArea();
        add(nameLabel);
        add(name);
        add(calsLabel);
        add(cals);

        enter.setBackground(Color.GREEN);
        enter.setOpaque(true);
        add(enter);
        enter.addActionListener(this);

        setVisible(true);
        setLocationRelativeTo(null);
    }

    //MODIFIES: InitialGUi
    //EFFECTS: Adds food to saved foods with name and cals
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            String foodName = name.getText().toLowerCase();
            int calories = Integer.parseInt(cals.getText());
            tm.getSavedFoods().addSavedFood(foodName, calories);
            dispose();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers");
        }

    }
}
