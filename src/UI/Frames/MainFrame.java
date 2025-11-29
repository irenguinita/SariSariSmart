package UI.Frames;

import Backend.DataService;
import Database.Users.UserManager;
import UI.Panels.*;
import UI.Theme;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class MainFrame extends JFrame {
    public DataService dataService;
    public JTabbedPane tabbedPane;
    public PosPanel posPanel;
    public InventoryPanel inventoryPanel;
    public CustomerPanel customerPanel;
    public AnalyticsPanel analyticsPanel;

    public MainFrame(DataService dataService) {
        this.dataService = dataService;
        setTitle("SariSariSmart - Retail Management");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Theme.BACKGROUND);

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Theme.CARD_BG);
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Theme.BORDER));
        header.setPreferredSize(new Dimension(getWidth(), 60));

        JLabel logo = new JLabel("Sari-Sari Smart");
        logo.setFont(Theme.FONT_HEADER);
        logo.setForeground(Theme.PRIMARY);
        try {
            ImageIcon logoIcon = new ImageIcon(getClass().getResource("/SariSariSmart_Logo.png"));
            Image scaledIcon = logoIcon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
            logo.setIcon(new ImageIcon(scaledIcon));
        } catch (Exception e) {}
        header.add(logo, BorderLayout.WEST);

        JButton logoutBtn = new JButton("Log Out");
        logoutBtn.setFont(Theme.FONT_BOLD);
        logoutBtn.setForeground(Theme.DANGER);
        logoutBtn.setContentAreaFilled(false);
        logoutBtn.setFocusable(false);
        logoutBtn.setBorderPainted(false);
        logoutBtn.addActionListener(e -> {
            this.dispose();
            try {
                new LoginFrame(new DataService(), new UserManager()).setVisible(true);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        header.add(logoutBtn, BorderLayout.EAST);

        add(header, BorderLayout.NORTH);

        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(Theme.FONT_BOLD);
        tabbedPane.setBackground(Theme.BACKGROUND);

        posPanel = new PosPanel(this);
        inventoryPanel = new InventoryPanel(this);
        customerPanel = new CustomerPanel(this);
        analyticsPanel = new AnalyticsPanel(this);

        tabbedPane.addTab("POS", posPanel);
        tabbedPane.addTab("Inventory", inventoryPanel);
        tabbedPane.addTab("Customers", customerPanel);
        tabbedPane.addTab("Analytics", analyticsPanel);

        tabbedPane.addChangeListener(e -> refreshAll());

        add(tabbedPane, BorderLayout.CENTER);
    }

    public void refreshAll() {
        posPanel.refresh();
        inventoryPanel.refresh();
        customerPanel.refresh();
        analyticsPanel.refresh();
    }
}
