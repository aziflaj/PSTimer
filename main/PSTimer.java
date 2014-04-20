package main;

/**
 * This is the main class of the PSTimer that calls the application
 * and makes it usable from the user
 * @author Aldo Ziflaj
 * @version 1.0
 */
public class PSTimer {
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run() {
                try {
                    ui.MainUI app = new ui.MainUI();
                    app.setVisible(true);
                } catch (Exception ex) {
                    javax.swing.JOptionPane.showMessageDialog(null,
                            ex, 
                            "Error", 
                            javax.swing.JOptionPane.ERROR_MESSAGE);
                    System.exit(1);   
                }
            }
        });
    }
    
}
