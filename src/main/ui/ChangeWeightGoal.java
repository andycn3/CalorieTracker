package ui;

import model.TrackerManager;
import model.WeightGoal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChangeWeightGoal extends JFrame implements ActionListener {

    private TrackerManager tm;
    private InitialGUI gui;
    private JLabel goalLabel = new JLabel("Enter New Goal (Lose, Gain, Maintain):");
    private JLabel weightLabel = new JLabel("Enter Weight (in lbs):");
    private JTextArea goal;
    private JTextArea weight;
    private JButton enter = new JButton("Enter");

    public ChangeWeightGoal(TrackerManager tm, InitialGUI gui) {
        super("Change Weight Goal");
        this.tm = tm;
        this.gui = gui;
        setMinimumSize(new Dimension(500, 500));
        setLayout(new GridLayout(5, 1));
        goal = new JTextArea();
        weight = new JTextArea();
        add(goalLabel);
        add(goal);
        add(weightLabel);
        add(weight);

        enter.setBackground(Color.GREEN);
        enter.setOpaque(true);
        add(enter);
        enter.addActionListener(this);

        setVisible(true);
        setLocationRelativeTo(null);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            String newGoal = goal.getText().toLowerCase();
            int currentWeight = Integer.parseInt(weight.getText());
            tm.setWeightGoal(new WeightGoal(newGoal, currentWeight));
            gui.refreshTracker(tm);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers");
        }
        dispose();
    }
}
