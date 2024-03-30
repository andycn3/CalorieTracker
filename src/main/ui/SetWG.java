package ui;

import model.TrackerManager;
import model.WeightGoal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SetWG extends JFrame {

    private TrackerManager tm;
    private JLabel goalLabel = new JLabel("Enter New Goal (Lose, Gain, Maintain):");
    private JLabel weightLabel = new JLabel("Enter Weight (in lbs):");
    private JTextArea goal;
    private JTextArea weight;
    private JFrame newFrame;
    private JButton enter = new JButton("Enter");

    //EFFECTS: Creates new InitialGUI with TrackerManager with Weight goal with weight and goal
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    public SetWG() {
        newFrame = new JFrame();
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
        enter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String newGoal = goal.getText().toLowerCase();
                    int currentWeight = Integer.parseInt(weight.getText());
                    tm = new TrackerManager("tracker", new WeightGoal(newGoal, currentWeight));
                    setVisible(false);
                    new InitialGUI(tm).setVisible(true);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(newFrame, "Please enter valid numbers");
                }
            }
        });

        setVisible(true);
        setLocationRelativeTo(null);
    }
}
