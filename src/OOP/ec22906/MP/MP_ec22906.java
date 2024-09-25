package OOP.ec22906.MP;

import OOP.ec22906.MP.contributions.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;

public class MP_ec22906 implements Visitor {
    private final JTextArea outputArea;
    private final JTextField inputField;
    private final JButton submitButton;
    private final ArrayList<String> items;
    private static JProgressBar progressBar = null;
    private int gold;
    private int runNumber = 1;

    public MP_ec22906() {
        // Start Game Screen
        JFrame startFrame = new JFrame("Start Game");
        startFrame.setSize(400, 200);
        startFrame.setLocationRelativeTo(null);

        JLabel label = new JLabel("Would you like to start the game?");
        label.setBounds(80, 30, 250, 30);
        label.setForeground(Color.WHITE);

        JButton startButton = new JButton("Start");
        startButton.setBackground(Color.GREEN);

        JButton quitButton = new JButton("Quit");
        quitButton.setBackground(Color.RED);

        progressBar = new JProgressBar();
        progressBar.setBounds(5, 270, 670, 20);
        progressBar.setMinimum(0);
        progressBar.setMaximum(100);
        progressBar.setStringPainted(true);

        JPanel startPanel = new JPanel();
        startPanel.setLayout(null);
        startPanel.setBackground(Color.GRAY);
        startPanel.add(label);
        startPanel.add(startButton);
        startPanel.add(quitButton);
        startFrame.getContentPane().add(startPanel);
        startFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        startFrame.setVisible(true);
        startFrame.setResizable(false);

        // Room Screen
        JFrame roomScreen = new JFrame("Room Visitor");
        roomScreen.setSize(800, 420);
        roomScreen.setLocationRelativeTo(null);

        gold = 0;
        JButton goldButton = new JButton("Gold");
        goldButton.setBounds(5, 300, 100, 30);
        goldButton.setBackground(Color.YELLOW);
        goldButton.addActionListener(e -> JOptionPane.showMessageDialog(null,
                "Gold: " + gold, "Gold", JOptionPane.INFORMATION_MESSAGE));

        items = new ArrayList<>();
        JButton itemButton = new JButton("Items");
        itemButton.setBounds(5, 340, 100, 30);
        itemButton.setBackground(Color.ORANGE);
        itemButton.addActionListener(e -> {
            try {
                StringBuilder s = new StringBuilder();
                for (String item : items) {
                    s.append(item).append(", ");
                }
                s = new StringBuilder(s.substring(0, s.length() - 2));
                JOptionPane.showMessageDialog(null, "Items: " + s, "Items",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(null, "You currently have no Items");
            }
        });

        outputArea = new JTextArea();
        JScrollPane outputPane = new JScrollPane(outputArea);
        outputPane.setBounds(5, 10, 670, 250);
        outputPane.setVisible(true);
        outputArea.setEditable(false);

        inputField = new JTextField();
        inputField.setBounds(700, 10, 60, 30);

        submitButton = new JButton("Submit");
        submitButton.setBounds(680, 50, 100, 30);
        submitButton.setBackground(Color.BLUE);
        submitButton.setForeground(Color.WHITE);
        submitButton.addActionListener(e -> {
            synchronized (submitButton) {
                submitButton.notifyAll();
            }
        });

        JButton saveButton = new JButton("Save");
        saveButton.setBounds(680, 100, 100, 30);
        saveButton.setBackground(Color.MAGENTA);
        saveButton.setForeground(Color.WHITE);
        saveButton.addActionListener(e -> {
            try {
                FileWriter writer = new FileWriter("user_data.txt", true);
                writer.write("Run " + runNumber + ": ");
                writer.write("Gold: " + gold + "\n");
                Collections.sort(items);
                writer.write("Items: " + String.join(", ", items) + "\n");
                writer.write("\n");
                writer.close();
                JOptionPane.showMessageDialog(null, "Data saved successfully!",
                        "Save Data", JOptionPane.INFORMATION_MESSAGE);
                runNumber += 1;
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        JPanel roomPanel = new JPanel();
        roomPanel.setLayout(null);
        roomPanel.add(outputPane);
        roomPanel.add(inputField);
        roomPanel.add(submitButton);
        roomPanel.add(saveButton);
        roomPanel.add(goldButton);
        roomPanel.add(itemButton);
        roomPanel.add(progressBar);

        JButton leaveButton = new JButton("Leave");
        leaveButton.setBackground(Color.RED);
        leaveButton.setBounds(680, 300, 100, 30);
        leaveButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(roomPanel,"Once you confirm this option you will be sent back" +
                    "to the start menu however your progress will be saved unless you completely exit out.");
            int choice = JOptionPane.showConfirmDialog(roomPanel, "Are you sure you want to leave?",
                    "Confirmation", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                roomScreen.dispose();
                startFrame.setVisible(true);
            }
            // Do nothing and return to the game

        });
        roomPanel.add(leaveButton);

        roomScreen.getContentPane().add(roomPanel);
        roomScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        roomScreen.setVisible(false);
        roomScreen.setResizable(false);

        roomScreen.getContentPane().add(roomPanel);
        roomScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        roomScreen.setVisible(false);
        roomScreen.setResizable(false);

        startButton.setBounds(100, 80, 80, 30);
        startButton.addActionListener(e -> {
            startFrame.dispose();
            roomScreen.setVisible(true);
        });

        quitButton.setBounds(200, 80, 80, 30);
        quitButton.addActionListener(e ->{
            JOptionPane.showMessageDialog(null,"Thanks for playing!");
            System.exit(0);
        });
    }


    public void tell(String message) {
        outputArea.append(message + "\n");

    }

    public char getChoice(String prompt, char[] choices) {
        tell(prompt);
        inputField.setText("");
        submitButton.setEnabled(true);

        while (true) {
            synchronized (submitButton) {
                try {
                    submitButton.wait();
                    progressBar.setValue(progressBar.getValue() + 10);// Update the value as needed
                    if(progressBar.getValue() < 50){
                        progressBar.setForeground(Color.RED);

                    } else if (progressBar.getValue() < 100) {
                        progressBar.setForeground(Color.ORANGE);

                    } else if (progressBar.getValue() == 100) {
                        progressBar.setValue(100);
                        progressBar.setForeground(Color.GREEN);// Reset progress bar if it reaches 100%
                        JOptionPane.showMessageDialog(null,"You have reached 100% exploration for" +
                                " this run you may continue if you wish or leave completely.");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            String input = inputField.getText();
            if (input != null && input.length() > 0) {
                char selected = input.charAt(0);
                for (char c : choices) {
                    if (selected == c) {
                        return selected;
                    }
                }
            }
        }
    }

    public boolean giveItem(Item itemAvailable) {
        tell("You are being offered: " + itemAvailable.name);
        char choice = getChoice("Do you accept (y/n)?", new char[]{'y', 'n'});
        if(choice == 'y') {
            items.add(itemAvailable.name);
        }
        return choice == 'y';
    }

    public boolean hasIdenticalItem(Item item) {
        return false;
    }

    public boolean hasEqualItem(Item item) {
        return false;
    }

    public void giveGold(int amount) {
        tell("You are given " + amount + " gold pieces.");
        gold += amount;
    }

    public int takeGold(int amount) {
        tell(amount + " pieces of gold are taken from you.");
        gold -= amount;
        if(gold < 0){
            tell("Your lucky no more gold can be taken from you, broke bastard.");
            gold = 0;
        }
        return gold;
    }

    public static void running (Visitor visitor, Direction direction){
        progressBar.setValue(0);
        Room room = new Room_ec22579();
        while (true) {
            direction = room.visit(visitor, direction);
            visitor.tell("You are in the " + direction + " direction");

            char choice = visitor.getChoice("Do you want to go back and explore" +
                    " the room further (y/n)?", new char[]{'y', 'n'});
            if (choice == 'n') {
                JOptionPane.showMessageDialog(null,"Thanks for playing!");
                System.exit(0);

            }if (choice == 'y'){
                progressBar.setValue(0);
                JOptionPane.showMessageDialog(null,"Your progress has been reset however " +
                        "you will keep whatever items and gold you already have");
            }
        }
    }

    public static void main(String[] args) {
        JOptionPane.showMessageDialog(null,"This simple GUI was written thanks to " +
                "the use of code from ec22579");
        MP_ec22906 visitor = new MP_ec22906();
        Direction direction = Direction.FROM_SOUTH;
        running(visitor,direction);
    }
}