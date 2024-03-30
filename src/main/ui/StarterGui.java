package ui;

import model.TrackerManager;
import persistence.JsonReader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class StarterGui extends JFrame {

    private TrackerManager tm;
    private JLabel welcomeLabel;
    private JLabel goalLabel = new JLabel("Enter New Goal (Lose, Gain, Maintain):");
    private JLabel weightLabel = new JLabel("Enter Weight (in lbs):");
    private JTextArea goal;
    private JTextArea weight;
    private JButton load = new JButton("Saved File");
    private JButton fresh = new JButton("New File");
    private static final String JSON_STORE = "./data/trackermanager.json";
    private JsonReader jsonReader;

    public StarterGui() {
        super("Change Weight Goal");
        setMinimumSize(new Dimension(1000, 1000));
        welcomeLabel = new JLabel("Welcome To This Calorie Tracker" + "\n"
                + "Would you like to load a previous file or start a new file?");
        setLayout(new GridLayout(4, 1));
        setButtonFunctions();
        add(welcomeLabel);
        add(load);
        add(fresh);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void setButtonFunctions() {
        StarterGui x = this;
        jsonReader = new JsonReader(JSON_STORE);
        load.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    tm = jsonReader.read();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(x, "An error occurred");
                }
                setVisible(false);
                new InitialGUI(tm).setVisible(true);
            }
        });

        fresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new SetWG().setVisible(true);
            }
        });
    }


}
