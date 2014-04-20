package ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import org.json.*;


/**
 * This class is the holder of the panel which represents a PlayStation
 * This class holds the controller to start and end the time of a PlayStation
 * @author Aldo Ziflaj
 * @version 1.0
 */
public class PlayStationPanel extends JPanel {
    private JLabel status;
    private JLabel timeLabel;
    private JButton timeButton;
    private long startTime;
    private Timer timer;
    
    public PlayStationPanel(int index) {
        status = new JLabel("Off"); //initial status of PS
        timeLabel = new JLabel("0 minutes");
        timeButton = new JButton("Start time");
        timeButton.setBackground(Color.PINK); //status off
        timer = new Timer(1000, (ActionEvent ae) ->{
            String str = String.format("%d minutes",this.calculateTime());
            this.timeLabel.setText(str);
        });
        
        this.setLayout(new GridLayout(1,3,5,5));
        this.add(new JLabel("PlayStation "+(index+1)));
        this.add(status);
        this.add(timeLabel);
        this.add(timeButton);
        
        //add action listener for time button
        timeButton.addActionListener((ActionEvent ae) -> {
            BufferedReader fromFile;
            int price;
            try {
                //get the data from the settings file
                JSONObject json = MainUI.accessFile(MainUI.FILE_DIR);
                
                switch (status.getText()) {
                    case "Off":
                        //change the status to ON and start measuring time
                        status.setText("On");
                        timer.start(); //start the timer
                        timeButton.setBackground(Color.GREEN);
                        startTime = System.currentTimeMillis();
                        timeButton.setText("End time");
                        break;
                        
                    case "On":
                        //change the status to OFF and end time
                        status.setText("Off");
                        //stop the timer and set the time to 0
                        timer.stop(); 
                        
                        timeButton.setBackground(Color.PINK);
                        int minutes = this.calculateTime();
                        
                        
                        /* if time is less than 10 minutes
                        * read the initial price from ps_amount.json
                        */
                        if (minutes < 10) {
                            price = json.getInt("initial-price");
                        }
                        
                        // time is more than 10 minutes
                        else {
                            price = json.getInt("price-per-hour")*minutes/60 ;
                        }
                        
                        
                        timeButton.setText("Start time");
                        
                        //print the time and the price
                        JOptionPane.showMessageDialog(null,
                                "Time: " + minutes + " minutes\n" +
                                        "Price: " + price + " lek",
                                "Time and price",
                                JOptionPane.INFORMATION_MESSAGE);
                        break;
                }
            } catch (HeadlessException | IOException | JSONException ex) {
                JOptionPane.showMessageDialog(null,
                        ex,
                        "Error",
                        JOptionPane.ERROR);
            }
        });
        
        
    }
    
    private int calculateTime() {
            long now = System.currentTimeMillis();
            //get the difference between start time and now;
            int time = (int) (now - startTime) / 1000 / 60; //time in minutes
            return time;
    }
}
