package ui;

import model.TrackerManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddExercise extends JFrame implements ActionListener {

    private TrackerManager tm;
    private InitialGUI gui;
    private JLabel calsLabel = new JLabel("How many calories did you burn?");
    private JTextArea cals;
    private JButton enter = new JButton("Enter");

    public AddExercise(TrackerManager tracker, InitialGUI graphic) {
        super("Exercise");
        tm = tracker;
        gui = graphic;

        setMinimumSize(new Dimension(500, 500));
        setLayout(new GridLayout(3, 1));
        cals = new JTextArea();
        add(calsLabel);
        add(cals);
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
            int x = Integer.parseInt(cals.getText());
            int i = tm.getDailyTracker().getDailyCount() - x;
            tm.getDailyTracker().setDailyCount(i);
            gui.refreshTracker(tm);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers");
        }
        dispose();
    }
}
