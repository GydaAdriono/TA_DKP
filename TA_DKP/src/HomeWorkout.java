import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class HomeWorkout {
    private static JFrame frame;
    private static CardLayout cardLayout;
    private static JPanel mainPanel;

    private static JTextField usernameField;
    private static JPasswordField passwordField;

    private static Map<String, String> userDatabase = new HashMap<>();

    public static void main(String[] args) {
        userDatabase.put("user", "password");

        frame = new JFrame("Home Workout");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 500);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        mainPanel.add(createLoginPanel(), "login");
        mainPanel.add(createMainMenuPanel(), "mainMenu");

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    private static JPanel createLoginPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.BLACK);
        panel.setBorder(new EmptyBorder(50, 50, 50, 50));

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10, 10, 10);

        JLabel titleLabel = new JLabel("Login", SwingConstants.CENTER);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        panel.add(titleLabel, constraints);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setForeground(Color.WHITE);
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        panel.add(usernameLabel, constraints);

        usernameField = new JTextField(15);
        usernameField.setBorder(new MatteBorder(0, 0, 1, 0, Color.WHITE));
        constraints.gridx = 1;
        constraints.gridy = 1;
        panel.add(usernameField, constraints);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setForeground(Color.WHITE);
        constraints.gridx = 0;
        constraints.gridy = 2;
        panel.add(passwordLabel, constraints);

        passwordField = new JPasswordField(15);
        passwordField.setBorder(new MatteBorder(0, 0, 1, 0, Color.WHITE));
        constraints.gridx = 1;
        constraints.gridy = 2;
        panel.add(passwordField, constraints);

        JButton loginButton = new JButton("Login");
        loginButton.setBackground(Color.RED);
        loginButton.setForeground(Color.WHITE);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkLogin();
            }
        });

        constraints.gridx = 1;
        constraints.gridy = 3;
        constraints.gridwidth = 1;
        panel.add(loginButton, constraints);

        return panel;
    }

    private static void checkLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (userDatabase.containsKey(username) && userDatabase.get(username).equals(password)) {
            JOptionPane.showMessageDialog(frame, "Login Successful", "Success", JOptionPane.INFORMATION_MESSAGE);
            cardLayout.show(mainPanel, "mainMenu");
        } else {
            JOptionPane.showMessageDialog(frame, "Incorrect Username or Password", "Error", JOptionPane.ERROR_MESSAGE);
            passwordField.setText("");
        }
    }

    private static JPanel createMainMenuPanel() {
        JPanel panel = new JPanel(new GridLayout(4, 1));
        panel.setBackground(Color.BLACK);

        JLabel titleLabel = new JLabel("Choose Workout Type:", SwingConstants.CENTER);
        titleLabel.setForeground(Color.WHITE);

        JButton upperBodyButton = new JButton("Upper Body");
        upperBodyButton.setBackground(Color.RED);
        upperBodyButton.setForeground(Color.WHITE);
        upperBodyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "upperBodyMenu");
            }
        });

        JButton coreButton = new JButton("Core");
        coreButton.setBackground(Color.RED);
        coreButton.setForeground(Color.WHITE);
        coreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "coreMenu");
            }
        });

        JButton legsButton = new JButton("Legs");
        legsButton.setBackground(Color.RED);
        legsButton.setForeground(Color.WHITE);
        legsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "legsMenu");
            }
        });

        panel.add(titleLabel);
        panel.add(upperBodyButton);
        panel.add(coreButton);
        panel.add(legsButton);

        mainPanel.add(createWorkoutMenuPanel("Upper Body", new String[]{"Push Up", "Pull Up", "Shoulder Press", "Bench Press", "Bicep Curl"}), "upperBodyMenu");
        mainPanel.add(createWorkoutMenuPanel("Core", new String[]{"Plank", "Sit Up", "Leg Raise", "Russian Twist", "Bicycle Crunch"}), "coreMenu");
        mainPanel.add(createWorkoutMenuPanel("Legs", new String[]{"Squat", "Lunges", "Calf Raise", "Leg Press", "Deadlift"}), "legsMenu");

        return panel;
    }

    private static JPanel createWorkoutMenuPanel(String workoutType, String[] workoutNames) {
        JPanel panel = new JPanel(new GridLayout(workoutNames.length + 2, 1));
        panel.setBackground(Color.BLACK);

        JLabel titleLabel = new JLabel("Choose " + workoutType + " Workouts:", SwingConstants.CENTER);
        titleLabel.setForeground(Color.WHITE);
        panel.add(titleLabel);

        JCheckBox[] workoutCheckboxes = new JCheckBox[workoutNames.length];
        for (int i = 0; i < workoutNames.length; i++) {
            workoutCheckboxes[i] = new JCheckBox(workoutNames[i]);
            workoutCheckboxes[i].setBackground(Color.BLACK);
            workoutCheckboxes[i].setForeground(Color.WHITE);
            panel.add(workoutCheckboxes[i]);
        }

        JButton startButton = new JButton("Start Selected Workouts");
        startButton.setBackground(Color.RED);
        startButton.setForeground(Color.WHITE);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Queue<Integer> selectedWorkouts = new LinkedList<>();
                for (int i = 0; i < workoutCheckboxes.length; i++) {
                    if (workoutCheckboxes[i].isSelected()) {
                        selectedWorkouts.add(i + 1);
                    }
                }
                if (!selectedWorkouts.isEmpty()) {
                    startWorkoutSequence(workoutType, selectedWorkouts);
                }
            }
        });
        panel.add(startButton);

        JButton backButton = new JButton("Back");
        backButton.setBackground(Color.GRAY);
        backButton.setForeground(Color.WHITE);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "mainMenu");
            }
        });
        panel.add(backButton);

        return panel;
    }

    private static void startWorkoutSequence(String workoutType, Queue<Integer> selectedWorkouts) {
        if (selectedWorkouts.isEmpty()) {
            return;
        }

        int workoutNumber = selectedWorkouts.poll();
        JFrame workoutFrame = new JFrame("Workout: " + workoutType);
        workoutFrame.setSize(300, 300);
        workoutFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.BLACK);

        String workoutName = getWorkoutName(workoutType, workoutNumber);
        JLabel workoutLabel = new JLabel("Starting workout: " + workoutType + " - " + workoutName, SwingConstants.CENTER);
        workoutLabel.setForeground(Color.WHITE);
        panel.add(workoutLabel, BorderLayout.NORTH);

        JTextArea descriptionArea = new JTextArea(getWorkoutDescription(workoutType, workoutNumber));
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setLineWrap(true);
        descriptionArea.setOpaque(false);
        descriptionArea.setEditable(false);
        descriptionArea.setForeground(Color.WHITE);
        descriptionArea.setBackground(Color.BLACK);
        panel.add(descriptionArea, BorderLayout.CENTER);

        JButton startButton = new JButton("Start Timer");
        startButton.setBackground(Color.RED);
        startButton.setForeground(Color.WHITE);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Timer timer = new Timer(1000, new ActionListener() {
                    int timeRemaining = 30;

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (timeRemaining > 0) {
                            workoutLabel.setText("Time remaining: " + timeRemaining + " seconds");
                            timeRemaining--;
                        } else {
                            ((Timer) e.getSource()).stop();
                            workoutLabel.setText("Workout completed!");

                            if (!selectedWorkouts.isEmpty()) {
                                workoutFrame.dispose();
                                startWorkoutSequence(workoutType, selectedWorkouts);
                            } else {
                                JOptionPane.showMessageDialog(workoutFrame, "All workouts completed!", "Success", JOptionPane.INFORMATION_MESSAGE);
                                workoutFrame.dispose();
                            }
                        }
                    }
                });
                timer.start();
            }
        });

        panel.add(startButton, BorderLayout.SOUTH);

        workoutFrame.add(panel);
        workoutFrame.setVisible(true);
    }

    private static String getWorkoutName(String workoutType, int workoutNumber) {
        switch (workoutType) {
            case "Upper Body":
                switch (workoutNumber) {
                    case 1:
                        return "Push Up";
                    case 2:
                        return "Pull Up";
                    case 3:
                        return "Shoulder Press";
                    case 4:
                        return "Bench Press";
                    case 5:
                        return "Bicep Curl";
                    default:
                        return "Unknown workout.";
                }
            case "Core":
                switch (workoutNumber) {
                    case 1:
                        return "Plank";
                    case 2:
                        return "Sit Up";
                    case 3:
                        return "Leg Raise";
                    case 4:
                        return "Russian Twist";
                    case 5:
                        return "Bicycle Crunch";
                    default:
                        return "Unknown workout.";
                }
            case "Legs":
                switch (workoutNumber) {
                    case 1:
                        return "Squat";
                    case 2:
                        return "Lunges";
                    case 3:
                        return "Calf Raise";
                    case 4:
                        return "Leg Press";
                    case 5:
                        return "Deadlift";
                    default:
                        return "Unknown workout.";
                }
            default:
                return "Unknown workout.";
        }
    }

    private static String getWorkoutDescription(String workoutType, int workoutNumber) {
        switch (workoutType) {
            case "Upper Body":
                switch (workoutNumber) {
                    case 1:
                        return "Push Up: Keep your body straight, lower your body until your chest is just above the floor, then push back up.";
                    case 2:
                        return "Pull Up: Hang from a bar, pull yourself up until your chin is above the bar, then lower yourself back down.";
                    case 3:
                        return "Shoulder Press: Lift weights to shoulder height, then push them overhead until your arms are fully extended.";
                    case 4:
                        return "Bench Press: Lie on a bench, lower the bar to your chest, then push it back up until your arms are fully extended.";
                    case 5:
                        return "Bicep Curl: Hold weights with your arms extended, curl them up to your shoulders, then lower them back down.";
                    default:
                        return "Unknown workout.";
                }
            case "Core":
                switch (workoutNumber) {
                    case 1:
                        return "Plank: Hold your body in a straight line with your forearms on the ground.";
                    case 2:
                        return "Sit Up: Lie on your back, sit up and touch your toes, then lie back down.";
                    case 3:
                        return "Leg Raise: Lie on your back, lift your legs to a 90-degree angle, then lower them back down.";
                    case 4:
                        return "Russian Twist: Sit on the ground, twist your torso to each side while holding a weight.";
                    case 5:
                        return "Bicycle Crunch: Lie on your back, alternate touching your elbows to the opposite knee in a pedaling motion.";
                    default:
                        return "Unknown workout.";
                }
            case "Legs":
                switch (workoutNumber) {
                    case 1:
                        return "Squat: Stand with your feet shoulder-width apart, lower your hips until your thighs are parallel to the ground, then stand back up.";
                    case 2:
                        return "Lunges: Step forward with one leg, lower your hips until both knees are bent at a 90-degree angle, then return to standing.";
                    case 3:
                        return "Calf Raise: Stand on the edge of a step, raise your heels as high as possible, then lower them back down.";
                    case 4:
                        return "Leg Press: Sit on the leg press machine, push the platform away from you until your legs are fully extended, then return to starting position.";
                    case 5:
                        return "Deadlift: Stand with your feet hip-width apart, bend at your hips and knees to lower the weight, then stand back up.";
                    default:
                        return "Unknown workout.";
                }
            default:
                return "Unknown workout.";
        }
    }
}
