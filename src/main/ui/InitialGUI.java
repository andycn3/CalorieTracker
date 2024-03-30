package ui;

import model.TrackerManager;
import model.WeeklyTracker;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;


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
        JLabel image = new JLabel(new ImageIcon("healthyImage.png"));
        left.add(image);
        image.setVisible(true);
        left.setVisible(true);
        desktop.setVisible(true);
        desktop.setMinimumSize(new Dimension(1000, 1000));
        desktop.add(splitPane);
        desktop.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }


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

    public JPanel buttonsDisplay(TrackerManager tm) {
        JPanel container = new JPanel();
        return (addFunctionsToPane(container, tm));
    }

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
        JButton quit = new JButton("Quit Application");
        quit.setAlignmentX(Component.CENTER_ALIGNMENT);
        setButtonFunctions(quit,"quit", color1, pane);
        quit.addActionListener(new ActionListener() {
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

    public void setButtonFunctions(JButton button, String string, Color color, JPanel pane) {
        button.setActionCommand(string);
        button.setOpaque(true);
        button.setBackground(color);
        pane.add(button);
    }

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

    public void close() {
        System.exit(0);
    }

}