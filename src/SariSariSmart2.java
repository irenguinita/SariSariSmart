
import Backend.*;

import Database.Users.*;
import Database.Inventory.*;
import Database.CustomException.*;
import UI.Frames.LoginFrame;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.*;
import java.util.stream.*;

public class SariSariSmart2 {

    public static void main(String[] args) {

        File dataDirectory = new File("data");
        if (!dataDirectory.exists()){
            boolean created = dataDirectory.mkdirs();
            if (created){
                System.out.println("Created data directory: " + dataDirectory.getAbsolutePath());
            } else {
                System.err.println("ERROR: Could not create directory");
            }
        } else {
            System.err.println("Folder already existed.");
        }

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            try {
                UserManager userManager = new UserManager();
//                DataService dataService = new DataService();
//                new LoginFrame(dataService, userManager).setVisible(true);
                InventoryManager inventoryManager = new InventoryManager();

                userManager.userFolder();
                inventoryManager.inventoryFolder();

                DataService dataService = new DataService();
                new LoginFrame(dataService, userManager).setVisible(true);
            } catch (Exception e){
                System.err.println("Application failed to launch.");
                System.exit(1);
            }
        });
    }
}

