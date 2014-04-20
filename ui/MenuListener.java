package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

/**
 * This class is used to create ActionListeners for the Menu
 * items in the MainUI. It defines actions for every Menu item
 * @author Aldo Ziflaj
 * @version 1.0
 */

public class MenuListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent ae) {
            try {
                //action listener for adding another PlayStation
                if (ae.getSource() == MainUI.addPS) {
                    //new value;
                    int newAmount = MainUI.json.getInt("amount") + 1;
                    MainUI.json.put("amount",newAmount);
                    MainUI.putIntoFile(MainUI.FILE_DIR,MainUI.json);

                    JOptionPane.showMessageDialog(null, 
                            "Changes will take place after restart");
                }
                
                //action listener for removing a PlayStation
                else if(ae.getSource() == MainUI.removePS) {
                    int currentAmount = MainUI.json.getInt("amount");
                    
                    //this block of code prevents from removing every PlayStation
                    if (currentAmount == 1) {
                        JOptionPane.showMessageDialog(null, 
                                "Can't have less than 1 PlayStation");
                    }
                    
                    else {
                        //new value;
                        int newAmount = MainUI.json.getInt("amount") - 1;
                        MainUI.json.put("amount",newAmount);
                        MainUI.putIntoFile(MainUI.FILE_DIR,MainUI.json);
                        JOptionPane.showMessageDialog(null, 
                            "Changes will take place after restart");
                    }
                }
                
                //action listener for changing the price 
                else if (ae.getSource() == MainUI.hourPrice) {
                    //read the new price
                    int newPrice = Integer.parseInt(
                            JOptionPane.showInputDialog(null, "The new Price")
                    );
                    
                    MainUI.json.put("price-per-hour",newPrice);
                    MainUI.putIntoFile(MainUI.FILE_DIR,MainUI.json);
                }
                
                
                //action listener for showing information about PSTimer
                else if (ae.getSource() == MainUI.about) {
                    StringBuilder about = new StringBuilder();
                    about.append("PSTimer v1.0\n")
                            .append("Developed by: Aldo Ziflaj\n")
                            .append("Language used: JavaSE 8\n");
                    JOptionPane.showMessageDialog(null, 
                            about.toString(),
                            "About",
                            JOptionPane.INFORMATION_MESSAGE);
                }
                
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null,ex);
            }
            
        }
}
