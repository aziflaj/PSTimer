package ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import org.json.*;

/**
 * This class is the Main class that creates the User Interface and makes it
 * visible to the user
 * @author Aldo Ziflaj
 * @version 1.0
 */
public class MainUI extends JFrame {
    protected static JSONObject json; //the file to hold data used by the app
    
    //the directory of the file where the data are saved
    protected final static String FILE_DIR = "src/data/datastored.data";
    //the icon directory
    private final static String ICON_DIR = "src/data/icon.png";
    
    /** the menu items for adding and removing PlayStations
     *  and changing the price
     */
    protected static JMenuItem addPS = new JMenuItem("Add PlayStation");
    protected static JMenuItem removePS = new JMenuItem("Remove PlayStation");
    protected static JMenuItem hourPrice = new JMenuItem("Set price");
    protected static JMenuItem about = new JMenuItem("About");
    
    //panel to hold PlayStations
    protected static JPanel view = new JPanel();
    
    public MainUI() throws Exception {
        
        //set main layout 
        this.setLayout(new BorderLayout());
        
        //add head panel of labels
        JPanel headPanel = new JPanel(new GridLayout(1,3,5,5));
        headPanel.add(new JLabel("PlayStation #"));
        headPanel.add(new JLabel("Status"));
        headPanel.add(new JLabel("Time"));
        headPanel.add(new JLabel("Start/End time"));
        this.add(headPanel,BorderLayout.NORTH);
        
        //add the view panel
        this.add(view,BorderLayout.CENTER);
        updateView();
        
        
        //add the menu bar
        JMenuBar jmb = new JMenuBar();
        this.setJMenuBar(jmb);
        
        JMenu settingsMenu = new JMenu("Settings");
        JMenu helpMenu = new JMenu("Help");
        jmb.add(settingsMenu);
        jmb.add(helpMenu);
        
        helpMenu.add(about);
        
        settingsMenu.add(addPS);
        settingsMenu.add(removePS);
        settingsMenu.add(hourPrice);
        
        
        addPS.setAccelerator(
                KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));
        removePS.setAccelerator(
                KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.CTRL_MASK));
        about.setAccelerator(
                KeyStroke.getKeyStroke(KeyEvent.VK_F1,0));
        
        addPS.addActionListener(new MenuListener());
        removePS.addActionListener(new MenuListener());
        hourPrice.addActionListener(new MenuListener());
        about.addActionListener(new MenuListener());
            
        
        //configure the frame
        this.setTitle("PSTimer v1.0");
        this.setIconImage(new ImageIcon(ICON_DIR).getImage());
        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
    }


    /**
     * This function is used to access a JSON-encoded file where the settings
     * are stored and makes it accessable as a JSONObject
     * @param file structured as JSON
     * @return JSONObject with access to the file
     * @throws FileNotFoundException
     * @throws JSONException
     * @throws IOException 
     */
    protected static JSONObject accessFile(String file) 
            throws FileNotFoundException, JSONException, IOException {
        
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line; 
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null)
            sb.append(line).append("\n");
        
        return new JSONObject(sb.toString());
    }
    

    
    /**
     * This function is used to write to the file where the settings are stored
     * @param file the file with the settings
     * @param json the new settings encoded as JSON
     * @throws FileNotFoundException
     * @throws JSONException
     * @throws IOException 
     */
    protected static void putIntoFile(String file,JSONObject json) 
            throws FileNotFoundException, JSONException, IOException {
        PrintWriter pw = new PrintWriter(new FileWriter(file),true);
        pw.println(json.toString());
    }

    
    /**
     * This function creates the view of all PlayStation controllers
     * @throws FileNotFoundException
     * @throws JSONException
     * @throws IOException 
     */
    protected static void updateView() 
            throws FileNotFoundException, JSONException, IOException {
        view.removeAll();
        
        //find the number of playstations
        json = accessFile(FILE_DIR);
        
        int psNum = json.getInt("amount"); //the total number of PlayStations
        
        view.setLayout(new BoxLayout(view, BoxLayout.Y_AXIS));
        
        for (int i=0; i<psNum; i++) {
            PlayStationPanel panel = new PlayStationPanel(i);
            view.add(panel);
            view.repaint();
        }
        
        view.validate();
    }
}
