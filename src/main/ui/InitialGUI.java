package ui;

import model.TrackerManager;
import model.WeeklyTracker;
import persistence.JsonWriter;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;

//Represents main operation window
public class InitialGUI extends JFrame {
    private TrackerManager tm;
    private JPanel left;
    private JPanel right;
    private JTextArea textArea;
    private JLabel wgLabel;
    private JLabel dtLabel;
    private JLabel wtLabel;
    private JLabel wgText;
    private JLabel dtText;
    private JLabel wtText;
    private JLabel awayFromGoal;
    private JFrame desktop;
    private static final String JSON_STORE = "./data/trackermanager.json";
    private JsonWriter jsonWriter;

    //EFFECTS: Creates the main homescreen with buttons, image, and trackers
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    public InitialGUI(TrackerManager tm) {
        this.tm = tm;
        desktop = new JFrame("Calorie Tracker");
        left = new JPanel();
        right = new JPanel();
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, left, right);
        splitPane.setOneTouchExpandable(true);
        splitPane.setDividerLocation(300);
        Dimension minimumSizeLeft = new Dimension(300, 1000);
        Dimension minimumSizeRight = new Dimension(700, 1000);
        left.setMinimumSize(minimumSizeLeft);
        right.setMinimumSize(minimumSizeRight);
        displayTrackers(tm);
        left.setLayout(new GridLayout(2,1));
        left.add(buttonsDisplay(tm));
        try {
            BufferedImage photo = ImageIO.read(new File("healthyImage.jpg"));
            JLabel picLabel = new JLabel(new ImageIcon(photo));
            left.add(picLabel);
        } catch (IOException e) {
            System.err.println("Error displaying photo");
            e.printStackTrace();
        }
        left.setVisible(true);
        desktop.setVisible(true);
        desktop.setMinimumSize(new Dimension(1000, 1000));
        desktop.add(splitPane);
        desktop.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }


    //EFFECTS: Puts the trackers together on the right side of the main frame
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    public void displayTrackers(TrackerManager trackerManager) {
        wgLabel = new JLabel("Calorie Goal");
        dtLabel = new JLabel("Calories Today");
        wtLabel = new JLabel("Calories: Last 7 Days");
        String calGoal = String.valueOf(tm.getWeightGoal().calorieGoal(tm.getWeightGoal()));
        String calToday = String.valueOf(tm.getDailyTracker().getDailyCount());
        String weekCals = makeText(tm.getWeeklyTracker());
        int x = tm.getWeightGoal().calorieGoal(tm.getWeightGoal())
                - tm.getDailyTracker().getDailyCount();
        String away;
        if (x < 0) {
            x *= -1;
            away = String.valueOf(x) + " over goal";
        } else {
            away = String.valueOf(x) + " away from goal";
        }
        wgText = new JLabel();
        wgText.setText(calGoal);
        dtText = new JLabel();
        dtText.setText(calToday);
        wtText = new JLabel();
        wtText.setText(weekCals);
        awayFromGoal = new JLabel();
        awayFromGoal.setText(away);
        right.setLayout(new GridLayout(7, 1));
        right.add(wgLabel);
        right.add(wgText);
        right.add(dtLabel);
        right.add(dtText);
        right.add(awayFromGoal);
        right.add(wtLabel);
        right.add(wtText);

    }

    //EFFECTS: Turns the weekly tracker value into strings to be displayed
    public String makeText(WeeklyTracker wt) {
        String text = "";
        if (wt.getWeekCals().isEmpty()) {
            return "No entries yet";
        } else {
            for (int cals : wt.getWeekCals()) {
                text = text + String.valueOf(cals) + ", ";
            }
            return text;
        }
    }

    //Creates a new panel for the buttons and call method to add functions to them
    public JPanel buttonsDisplay(TrackerManager tm) {
        JPanel container = new JPanel();
        return (addFunctionsToPane(container, tm));
    }

    //EFFECTS: Adds functions to the buttons
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    public JPanel addFunctionsToPane(JPanel pane, TrackerManager tm) {
        Color color = Color.DARK_GRAY;
        Color color1 = Color.RED;
        pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
        InitialGUI x = this;
        JButton add = new JButton("Add Eaten");
        add.setAlignmentX(Component.CENTER_ALIGNMENT);
        setButtonFunctions(add,"add", color, pane);
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddEaten(tm, x);
            }
        });
        JButton previous = new JButton("Add Previous");
        previous.setAlignmentX(Component.CENTER_ALIGNMENT);
        setButtonFunctions(previous,"previous", color, pane);
        previous.addActionListener(new ActionListener() {
            //MODIFIES: This
            //EFFECTS: Adds previous cals to tracker and changes the display
            @Override
            public void actionPerformed(ActionEvent e) {
                int x = tm.getDailyTracker().getDailyCount()
                        + tm.getDailyTracker().getPreviousCals();
                tm.getDailyTracker().setDailyCount(x);
                refreshTracker(tm);
            }
        });
        JButton addFavourite = new JButton("Add New Favourite Food");
        addFavourite.setAlignmentX(Component.CENTER_ALIGNMENT);
        setButtonFunctions(addFavourite,"addFavourite", color, pane);
        addFavourite.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddNewFavouriteFood(tm, x);
            }
        });
        JButton exercise = new JButton("Add Exercise");
        exercise.setAlignmentX(Component.CENTER_ALIGNMENT);
        setButtonFunctions(exercise,"exercise", color, pane);
        exercise.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddExercise(tm, x);
            }
        });
        JButton end = new JButton("End Day");
        end.setAlignmentX(Component.CENTER_ALIGNMENT);
        setButtonFunctions(end,"end", color, pane);
        end.addActionListener(new ActionListener() {
            //MODIFIES: This
            //EFFECTS: Creates popup asking if the user ate today if there are no entries
            //              Adds entry of 0 if not and just closes if yes
            //          Otherwise resents the tracker and adds the day to the week
            @Override
            public void actionPerformed(ActionEvent e) {
                if (tm.getDailyTracker().getDailyCount() == 0) {
                    int response = JOptionPane.showConfirmDialog(
                            x,
                            "You didn't enter any meals today"
                                    + "\n" + "If you ate something, please press Cancel instead."
                                    + "\n" + "Otherwise press 'OK'.",
                            "ALERT",
                            JOptionPane.OK_CANCEL_OPTION,
                            JOptionPane.INFORMATION_MESSAGE
                    );

                    if (response == JOptionPane.OK_OPTION) {
                        tm.getWeeklyTracker().addDay(tm.getDailyTracker());
                        refreshTracker(tm);
                        System.out.println("User pressed OK.");
                    } else if (response == JOptionPane.CANCEL_OPTION) {
                        dispose();
                    }
                } else {
                    tm.getWeeklyTracker().addDay(tm.getDailyTracker());
                    refreshTracker(tm);
                }

            }
        });
        JButton changeGoal = new JButton("Change Weight Goal");
        changeGoal.setAlignmentX(Component.CENTER_ALIGNMENT);
        setButtonFunctions(changeGoal,"change", color, pane);
        changeGoal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ChangeWeightGoal(tm, x);
            }
        });
        JButton clearWeek = new JButton("Clear Week");
        clearWeek.setAlignmentX(Component.CENTER_ALIGNMENT);
        setButtonFunctions(clearWeek,"clear", color, pane);
        clearWeek.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LinkedList<Integer> blank = new LinkedList<>();
                tm.getWeeklyTracker().setWeekCals(blank);
                refreshTracker(tm);
            }
        });
        JButton quit = new JButton("Quit Application");
        quit.setAlignmentX(Component.CENTER_ALIGNMENT);
        setButtonFunctions(quit,"quit", color1, pane);
        quit.addActionListener(new ActionListener() {
            //EFFECTS: Displays popup asking if the user wants to save or not
            //          Saves and closes if yes, just closes if no
            @Override
            public void actionPerformed(ActionEvent e) {
                int response = JOptionPane.showConfirmDialog(
                        x,
                        "Do you want to save your tracker?",
                        "CLOSE",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.INFORMATION_MESSAGE
                );
                if (response == JOptionPane.YES_OPTION) {
                    try {
                        jsonWriter = new JsonWriter(JSON_STORE);
                        jsonWriter.open();
                        jsonWriter.write(tm);
                        jsonWriter.close();
                    } catch (FileNotFoundException ex) {
                        System.out.println("Unable to write to file: " + JSON_STORE);
                    }
                    x.close();
                } else {
                    x.close();
                }
            }
        });
        return pane;
    }

    //MODIFIES: button, This
    //EFFECTS: Sets button colour and adds it to panel
    public void setButtonFunctions(JButton button, String string, Color color, JPanel pane) {
        button.setActionCommand(string);
        button.setOpaque(true);
        button.setBackground(color);
        pane.add(button);
    }

    //MODIFIES: This
    //EFFECTS: Updates the main frame with updated info
    public void refreshTracker(TrackerManager trackerManager) {
        right.revalidate();
        right.repaint();
        String calGoal = String.valueOf(trackerManager.getWeightGoal().calorieGoal(trackerManager.getWeightGoal()));
        String calToday = String.valueOf(trackerManager.getDailyTracker().getDailyCount());
        String weekCals = makeText(trackerManager.getWeeklyTracker());
        int x = trackerManager.getWeightGoal().calorieGoal(trackerManager.getWeightGoal())
                - trackerManager.getDailyTracker().getDailyCount();
        String away;
        if (x < 0) {
            x *= -1;
            away = String.valueOf(x) + " over goal";
        } else {
            away = String.valueOf(x) + " away from goal";
        }
        awayFromGoal.setText(away);
        wgText.setText(calGoal);
        dtText.setText(calToday);
        wtText.setText(weekCals);
        right.revalidate();
        right.repaint();
    }

    //EFFECTS: Exits the frame
    public void close() {
        System.exit(0);
    }

}