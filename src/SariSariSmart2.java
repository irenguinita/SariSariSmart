
import Database.Users.CustomException.DuplicateUserException;
import Database.Users.CustomException.LoginFailedException;
import Database.Users.CustomException.SignUpFailedException;
import Database.Users.User;
import Database.Users.UserManager;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Arc2D;
import java.awt.geom.Path2D;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class SariSariSmart2 {;

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
                MockDataService dataService = new MockDataService();
                new LoginFrame(dataService, userManager).setVisible(true);

                userManager.userFolder();
            } catch (Exception e){
                System.exit(1);
            }
        });
    }
}
//test change'-
// test 2
//test 3 push
// lololo
//testing testing
// stylings
class Theme {
    public static final Color BACKGROUND = new Color(245, 244, 235);
    public static final Color CARD_BG = Color.WHITE;
    public static final Color SURFACE_ALT = new Color(238, 236, 225);

    public static final Color PRIMARY = new Color(103, 114, 57);
    public static final Color PRIMARY_SOFT = new Color(138, 150, 85);
    public static final Color PRIMARY_FG = new Color(245, 244, 235);

    public static final Color SECONDARY = new Color(194, 98, 55);
    public static final Color SECONDARY_SOFT = new Color(215, 125, 85);

    public static final Color ACCENT = new Color(249, 168, 86);

    public static final Color TEXT_MAIN = new Color(60, 45, 40);
    public static final Color TEXT_MUTED = new Color(120, 110, 105);

    public static final Color SUCCESS = new Color(132, 153, 79);
    public static final Color WARNING = new Color(234, 179, 8);
    public static final Color DANGER = new Color(220, 38, 38);

    public static final Color BORDER = new Color(220, 220, 210);

    public static final Color TEAL = new Color(45, 110, 120);
    public static final Color CLAY = new Color(160, 82, 45);
    public static final Color GOLD = new Color(218, 165, 32);
    public static final Color SAGE = new Color(143, 188, 143);
    public static final Color SLATE = new Color(112, 128, 144);

    public static final Color[] CHART_COLORS = {
            PRIMARY, SECONDARY, ACCENT, TEAL, GOLD, CLAY, SAGE, SLATE
    };

    public static final Font FONT_HEADER = new Font("Segoe UI", Font.BOLD, 24);
    public static final Font FONT_TITLE = new Font("Segoe UI", Font.BOLD, 18);
    public static final Font FONT_REGULAR = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font FONT_BOLD = new Font("Segoe UI", Font.BOLD, 14);
    public static final Font FONT_MONO = new Font("Monospaced", Font.PLAIN, 13);

    public static void styleTable(JTable table) {
        table.setRowHeight(40);
        table.setShowVerticalLines(false);
        table.setGridColor(BORDER);
        table.setFont(FONT_REGULAR);
        table.setForeground(TEXT_MAIN);

        table.getTableHeader().setFont(FONT_BOLD);
        table.getTableHeader().setBackground(PRIMARY);
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setOpaque(true);

        table.setSelectionBackground(ACCENT);
        table.setSelectionForeground(TEXT_MAIN);

        // a custom renderer that handles background colors and removes the cell focus border
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? CARD_BG : SURFACE_ALT);
                }

                ((JLabel) c).setBorder(new EmptyBorder(0, 10, 0, 10));
                return c;
            }
        });
    }

    public static JButton createButton(String text, Color bg, Color fg) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                if (getModel().isPressed()) {
                    g2.setColor(bg.darker());
                } else {
                    g2.setColor(bg);
                }
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.dispose();
                super.paintComponent(g);
            }
        };

        btn.setFont(FONT_BOLD);
        btn.setForeground(fg);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setOpaque(false);
        btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        return btn;
    }
}

// product class
class Product {
    String id, sku, name, category, description;
    double price;
    int stock, reorderPoint;

    public Product(String id, String sku, String name, double price, int stock, String category, int reorderPoint, String description) {
        this.id = id; this.sku = sku; this.name = name; this.price = price;
        this.stock = stock; this.category = category; this.reorderPoint = reorderPoint; this.description = description;
    }

    public static String generateSKU() {
        return "PROD-" + (int)(Math.random() * 10000);
    }

    public boolean isLowStock(){
        return stock <= reorderPoint;
    }
    public void adjustStock(int qty){
        this.stock += qty;
    }
    public String toString(){
        return name;
    }
}

class Customer {
    String id, customId, name, phone;
    int loyaltyPoints;
    double totalPurchases;

    public Customer(String id, String customId, String name, String phone, int loyaltyPoints, double totalPurchases) {
        this.id = id; this.customId = customId; this.name = name;
        this.phone = phone; this.loyaltyPoints = loyaltyPoints; this.totalPurchases = totalPurchases;
    }

    public static String generateDisplayId() { return "CUST-" + (int)(Math.random() * 1000); }

    public void addPurchase(double amount) {
        this.totalPurchases += amount;
        this.loyaltyPoints += (int) (amount * 0.01);
    }

    @Override
    public String toString() { return customId + " | " + name; }
}

class CartItem {
    Product product;
    int quantity;
    double unitPrice;

    public CartItem(Product product, int quantity) {
        this.product = product; this.quantity = quantity; this.unitPrice = product.price;
    }
    public double getTotal() { return quantity * unitPrice; }
}

class Transaction {
    String id;
    Date date;
    List<CartItem> items;
    double total;
    String customerId, customerName;

    public Transaction(String id, Date date, List<CartItem> items, double total, String customerId, String customerName) {
        this.id = id; this.date = date; this.items = items;
        this.total = total; this.customerId = customerId; this.customerName = customerName;
    }
}

// mock data service class (this is a prototype ra so that those who are assigned for the backend get the idea)
class MockDataService {
    private List<Product> products = new ArrayList<>();
    private List<Customer> customers = new ArrayList<>();
    private List<Transaction> transactions = new ArrayList<>();

    public MockDataService() {
        populateDummyData();
    }

    private void populateDummyData() {
        products.add(new Product("1", "SKU-101", "Canned Tuna", 25.00, 50, "Canned Goods", 10, ""));
        products.add(new Product("2", "SKU-102", "Instant Noodles", 12.50, 100, "Canned Goods", 20, ""));
        products.add(new Product("3", "SKU-201", "Soda 1.5L", 65.00, 20, "Beverages", 5, ""));
        products.add(new Product("4", "SKU-202", "Energy Drink", 35.00, 5, "Beverages", 10, ""));
        products.add(new Product("5", "SKU-301", "Laundry Soap", 8.00, 200, "Household", 50, ""));

        customers.add(new Customer("1", "C-001", "Juan Dela Cruz", "09170000000", 120, 5000));
        customers.add(new Customer("2", "C-002", "Maria Clara", "09180000000", 50, 2500));
    }

    public List<Product> getProducts() { return products; }
    public List<Customer> getCustomers() { return customers; }
    public List<Transaction> getTransactions() { return transactions; }
}

// log-in frame
class LoginFrame extends JFrame {
    MockDataService dataService;
    JPanel cardPanel;
    CardLayout cardLayout;
    private UserManager userManager;

    public LoginFrame(MockDataService dataService, UserManager userManager) {
        this.dataService = dataService;
        this.userManager = userManager;
        setTitle("Sari-Sari Smart");
        setSize(450, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainContainer = new JPanel();
        mainContainer.setBackground(Theme.BACKGROUND);
        mainContainer.setLayout(new BorderLayout());
        setContentPane(mainContainer);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBackground(Theme.BACKGROUND);
        topPanel.setBorder(BorderFactory.createEmptyBorder(50, 0, 30, 0));

        JLabel logoLabel = new JLabel();
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        try {
            ImageIcon logoIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/SariSariSmart_Logo.png")));
            Image scaledIcon = logoIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            logoLabel.setIcon(new ImageIcon(scaledIcon));
        } catch (Exception e) {
            logoLabel.setText("[LOGO]");
        }
        logoLabel.setFont(Theme.FONT_HEADER);

        JLabel titleLabel = new JLabel("Sari-Sari Smart");
        titleLabel.setFont(Theme.FONT_HEADER);
        titleLabel.setForeground(Theme.PRIMARY);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        topPanel.add(logoLabel);
        topPanel.add(titleLabel);
        mainContainer.add(topPanel, BorderLayout.NORTH);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.setBackground(Theme.BACKGROUND);
        cardPanel.setBorder(BorderFactory.createEmptyBorder(0, 40, 40, 40));

        cardPanel.add(createLoginPanel(), "LOGIN");
        cardPanel.add(createSignupPanel(), "SIGNUP");

        mainContainer.add(cardPanel, BorderLayout.CENTER);
    }

    //log-in panel
    private JPanel createLoginPanel() {
        JPanel p = new JPanel(new GridLayout(0, 1, 10, 10));
        p.setBackground(Theme.CARD_BG);
        p.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(Theme.BORDER, 1, true),
                new EmptyBorder(20, 20, 20, 20)
        ));

        JTextField userField = new JTextField();
        JPasswordField passField = new JPasswordField();

        JButton loginBtn = Theme.createButton("LOG IN", Theme.PRIMARY, Color.WHITE);
        JButton goToSignup = new JButton("No account? Sign Up");
        styleLinkButton(goToSignup);

        //walay
        JButton forgotPasswordButton = new JButton("Forgot Password?");
        forgotPasswordButton.setHorizontalAlignment(SwingConstants.RIGHT);
        styleLinkButton(forgotPasswordButton);
        p.add(new JLabel("Username"));
        p.add(userField);
        p.add(new JLabel("Password"));
        p.add(passField);
        p.add(forgotPasswordButton);

        JLabel statusLabel = new JLabel("");
        p.add(statusLabel);
        p.add(loginBtn);
        p.add(goToSignup);

        loginBtn.addActionListener(e -> {
            String user = userField.getText().trim();
            String pass = new String(passField.getPassword()).trim();

            if (user.isEmpty()) {
                statusLabel.setText("Input username.");
            } else if (pass.isEmpty()) {
                statusLabel.setText("Input password.");
            } else if(user.contains(" ") || user.length() < 6) {
                statusLabel.setText("Invalid username.");
            } else {
                try {
                    User loggedInUser = userManager.login(user, pass);

                    if (loggedInUser != null) {
                        userField.setText("");
                        passField.setText("");

                        this.dispose();
                        new MainFrame(dataService).setVisible(true);
                    } else {
                        userField.setText("");
                        passField.setText("");
                        statusLabel.setText("User not found");
                    }
                } catch (LoginFailedException l) {
                    statusLabel.setText("Login failed. Try again. (Update to specify if password is incorrect)");
                    // status label
                }
            }
        });

        goToSignup.addActionListener(e -> cardLayout.show(cardPanel, "SIGNUP"));

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(Theme.BACKGROUND);
        wrapper.add(p, BorderLayout.NORTH);
        return wrapper;
    }

    //sign-up panel
    private JPanel createSignupPanel() {
        JPanel p = new JPanel(new GridLayout(0, 1, 10, 10));
        p.setBackground(Theme.CARD_BG);
        p.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(Theme.BORDER, 1, true),
                new EmptyBorder(20, 20, 20, 20)
        ));

        JButton signupBtn = Theme.createButton("CREATE ACCOUNT", Theme.SECONDARY, Color.WHITE);
        JButton goToLogin = new JButton("Back to Login");
        styleLinkButton(goToLogin);

        JTextField userField = new JTextField();
        JPasswordField passField = new JPasswordField();
        JPasswordField confirmPassField = new JPasswordField();

        p.add(new JLabel("New Username"));
        p.add(userField);
        p.add(new JLabel("Password"));
        p.add(passField);
        p.add(new JLabel("Confirm Password"));
        p.add(confirmPassField);
        p.add(new JLabel(""));
        p.add(signupBtn);
        p.add(goToLogin);

        signupBtn.addActionListener(e -> {
            String user = userField.getText().trim();
            String pass = new String(passField.getPassword()).trim();
            String confirmPass = new String(confirmPassField.getPassword()).trim();

            if (!pass.equals(confirmPass)){
                // status label here
                throw new SignUpFailedException("Passwords does not match.");
            }

            if (!user.contains(" ") && user.length() >= 6){
                try {
                    userManager.registerUser(user, pass);

                    userField.setText("");
                    passField.setText("");
                    confirmPassField.setText("");

                    JOptionPane.showMessageDialog(this, "Account Created (No DB)");
                    cardLayout.show(cardPanel, "LOGIN");

                } catch (DuplicateUserException ex) {
                    // status label here
                    System.err.println("ERROR: User already exist. " + ex.getMessage());
                } catch (SignUpFailedException ex) {
                    // status label here
                    System.err.println("ERROR: Passwords does not match. " + ex.getMessage());
                } catch (IOException ex) {
                    System.err.println("ERROR: Could not create an account. " + ex.getMessage());
                }
            } else {
                if (user.contains(" ")){
                    // status label here
                    throw new SignUpFailedException("Must not include space.");
                } else if (user.length() < 6){
                    throw new SignUpFailedException("User must have at least 6 characters");
                }
            }
        });

        goToLogin.addActionListener(e -> cardLayout.show(cardPanel, "LOGIN"));

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(Theme.BACKGROUND);
        wrapper.add(p, BorderLayout.NORTH);
        return wrapper;
    }

    private void styleLinkButton(JButton btn) {
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setContentAreaFilled(false);
        btn.setForeground(Theme.TEXT_MUTED);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
}

class MainFrame extends JFrame {
    MockDataService dataService;
    JTabbedPane tabbedPane;
    PosPanel posPanel;
    InventoryPanel inventoryPanel;
    CustomerPanel customerPanel;
    AnalyticsPanel analyticsPanel;

    public MainFrame(MockDataService dataService) {
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
            ImageIcon logoIcon = new ImageIcon(getClass().getResource("/SariSariSmart/SariSariSmart_Logo.png"));
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
            new LoginFrame(new MockDataService(), new UserManager()).setVisible(true);
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

class PosPanel extends JPanel {
    MainFrame frame;
    List<CartItem> cart = new ArrayList<>();
    JPanel productGrid;
    DefaultTableModel cartTableModel;
    JLabel totalLabel, pointsLabel;

    JComboBox<Customer> customerCombo;
    JTextField customerSearchField;
    JTextField searchField;
    JComboBox<String> categoryCombo;

    public PosPanel(MainFrame frame) {
        this.frame = frame;
        setLayout(new BorderLayout(10, 10));
        setBackground(Theme.BACKGROUND);
        setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel leftPanel = new JPanel(new BorderLayout(0, 10));
        leftPanel.setOpaque(false);

        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filterPanel.setOpaque(false);
        searchField = new JTextField(20);
        searchField.getDocument().addDocumentListener(new SimpleDocumentListener(e -> renderProducts()));

        categoryCombo = new JComboBox<>();
        categoryCombo.addActionListener(e -> renderProducts());

        filterPanel.add(new JLabel("Search Product: "));
        filterPanel.add(searchField);
        filterPanel.add(new JLabel(" Category: "));
        filterPanel.add(categoryCombo);
        leftPanel.add(filterPanel, BorderLayout.NORTH);

        productGrid = new JPanel(new GridLayout(0, 3, 10, 10));
        productGrid.setBackground(Theme.BACKGROUND);
        JScrollPane scrollPane = new JScrollPane(productGrid);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        leftPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel rightPanel = new JPanel(new BorderLayout(0, 10));
        rightPanel.setBackground(Theme.CARD_BG);
        rightPanel.setBorder(new CompoundBorder(new LineBorder(Theme.BORDER), new EmptyBorder(15, 15, 15, 15)));
        rightPanel.setPreferredSize(new Dimension(400, 0));

        JPanel customerPanel = new JPanel(new GridLayout(4, 1, 5, 5));
        customerPanel.setBackground(Theme.CARD_BG);
        customerPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Theme.PRIMARY), "Customer"));

        customerPanel.add(new JLabel("Search by ID:"));
        customerSearchField = new JTextField();
        customerPanel.add(customerSearchField);
        customerPanel.add(new JLabel("Select Customer:"));
        customerCombo = new JComboBox<>();
        customerPanel.add(customerCombo);
        customerSearchField.getDocument().addDocumentListener(new SimpleDocumentListener(e -> filterCustomers()));
        rightPanel.add(customerPanel, BorderLayout.NORTH);


        String[] cols = {"Item", "Qty", "Total", "X"};
        cartTableModel = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int row, int col){
                return col == 1 || col == 3;
            }
        };
        JTable cartTable = new JTable(cartTableModel);
        Theme.styleTable(cartTable);
        cartTable.getColumnModel().getColumn(1).setMaxWidth(50);
        cartTable.getColumnModel().getColumn(3).setMaxWidth(30);

        cartTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = cartTable.rowAtPoint(e.getPoint());
                int col = cartTable.columnAtPoint(e.getPoint());
                if (col == 3) removeFromCart(row);
            }
        });

        cartTableModel.addTableModelListener(e -> {
            if (e.getColumn() == 1 && e.getFirstRow() >= 0) {
                updateQty(e.getFirstRow(), (String) cartTableModel.getValueAt(e.getFirstRow(), 1));
            }
        });


        JScrollPane cartScrollPane = new JScrollPane(cartTable);
        cartScrollPane.setBorder(BorderFactory.createEmptyBorder());
        rightPanel.add(cartScrollPane, BorderLayout.CENTER);


        JPanel footer = new JPanel(new GridLayout(3, 1, 5, 5));
        footer.setOpaque(false);
        totalLabel = new JLabel("Total: ₱0.00", SwingConstants.RIGHT);
        totalLabel.setFont(Theme.FONT_HEADER);
        totalLabel.setForeground(Theme.PRIMARY);
        pointsLabel = new JLabel("Points to earn: 0", SwingConstants.RIGHT);
        pointsLabel.setFont(Theme.FONT_REGULAR);

        JButton checkoutBtn = Theme.createButton("COMPLETE SALE", Theme.SUCCESS, Color.WHITE);
        checkoutBtn.addActionListener(e -> checkout());

        footer.add(totalLabel);
        footer.add(pointsLabel);
        footer.add(checkoutBtn);
        rightPanel.add(footer, BorderLayout.SOUTH);

        add(leftPanel, BorderLayout.CENTER);
        add(rightPanel, BorderLayout.EAST);

        refresh();
    }

    public void refresh() {
        String currentCat = (String) categoryCombo.getSelectedItem();
        categoryCombo.removeAllItems();
        categoryCombo.addItem("All");
        Set<String> cats = frame.dataService.getProducts().stream().map(p -> p.category).collect(Collectors.toSet());
        cats.forEach(categoryCombo::addItem);
        if (currentCat != null) categoryCombo.setSelectedItem(currentCat);

        filterCustomers();
        renderProducts();
    }

    private void filterCustomers() {
        String query = customerSearchField.getText().toUpperCase().trim();
        customerCombo.removeAllItems();
        for (Customer c : frame.dataService.getCustomers()) {
            if (query.isEmpty() || c.customId.toUpperCase().contains(query) || c.name.toUpperCase().contains(query)) {
                customerCombo.addItem(c);
            }
        }
    }

    private void renderProducts() {
        productGrid.removeAll();
        String search = searchField.getText().toLowerCase();
        String cat = (String) categoryCombo.getSelectedItem();

        List<Product> filtered = frame.dataService.getProducts().stream()
                .filter(p -> (cat == null || cat.equals("All") || p.category.equals(cat)))
                .filter(p -> p.name.toLowerCase().contains(search) || p.sku.toLowerCase().contains(search))
                .collect(Collectors.toList());

        for (Product p : filtered) {
            JPanel card = new JPanel(new BorderLayout());
            card.setBackground(Theme.CARD_BG);
            card.setBorder(new LineBorder(Theme.PRIMARY_SOFT, 1));
            card.setPreferredSize(new Dimension(150, 150));

            JLabel name = new JLabel("<html><center>" + p.name + "</center></html>", SwingConstants.CENTER);
            name.setFont(Theme.FONT_BOLD);
            JLabel price = new JLabel("₱" + String.format("%.2f", p.price), SwingConstants.CENTER);
            price.setForeground(Theme.PRIMARY);
            price.setFont(Theme.FONT_TITLE);
            JLabel stock = new JLabel("Stock: " + p.stock, SwingConstants.CENTER);
            stock.setFont(Theme.FONT_MONO);
            if(p.isLowStock()) { stock.setForeground(Theme.WARNING); stock.setText("Stock: " + p.stock + " (Low)"); }

            JButton addBtn = Theme.createButton("Add", Theme.SECONDARY, Color.WHITE);
            addBtn.addActionListener(e -> addToCart(p));
            if (p.stock <= 0) { addBtn.setEnabled(false); addBtn.setText("Out of Stock"); addBtn.setBackground(Color.GRAY); }

            JPanel info = new JPanel(new GridLayout(3, 1));
            info.setOpaque(false);
            info.add(name); info.add(price); info.add(stock);
            card.add(info, BorderLayout.CENTER);
            card.add(addBtn, BorderLayout.SOUTH);
            productGrid.add(card);
        }
        productGrid.revalidate();
        productGrid.repaint();
    }

    private void addToCart(Product p) {
        int inCart = cart.stream().filter(i -> i.product.id.equals(p.id)).mapToInt(i -> i.quantity).sum();
        if (inCart >= p.stock){
            JOptionPane.showMessageDialog(this, "Not enough stock!");
            return;
        }

        Optional<CartItem> existing = cart.stream().filter(i -> i.product.id.equals(p.id)).findFirst();
        if (existing.isPresent()){
            existing.get().quantity++;
        }else{
            cart.add(new CartItem(p, 1));
        }

        updateCartTable();
    }

    private void removeFromCart(int row) {
        cart.remove(row);
        updateCartTable();
    }

    private void updateQty(int row, String val) {
        try {
            int qty = Integer.parseInt(val);
            if (qty <= 0) cart.remove(row);
            else {
                CartItem item = cart.get(row);
                if (qty > item.product.stock){
                    JOptionPane.showMessageDialog(this, "Exceeds stock level!");
                }else{
                    item.quantity = qty;
                }
            }
            updateCartTable();
        } catch (NumberFormatException e){

        }
    }

    private void updateCartTable() {
        cartTableModel.setRowCount(0);
        double total = 0;
        for (CartItem item : cart) {
            cartTableModel.addRow(new Object[]{item.product.name, String.valueOf(item.quantity), String.format("%.2f", item.getTotal()), "X"});
            total += item.getTotal();
        }
        totalLabel.setText("Total: ₱" + String.format("%.2f", total));
        pointsLabel.setText("Points to earn: " + (int)(total * 0.01));
    }

    private void checkout() {
        if (cart.isEmpty()) return;
        Customer cust = (Customer) customerCombo.getSelectedItem();
        if (cust == null){
            JOptionPane.showMessageDialog(this, "Please select a customer.");
            return;
        }

        double total = cart.stream().mapToDouble(CartItem::getTotal).sum();

        for (CartItem item : cart) {
            Product p = frame.dataService.getProducts().stream().filter(prod -> prod.id.equals(item.product.id)).findFirst().orElse(null);
            if (p != null) p.adjustStock(-item.quantity);
        }

        Customer c = frame.dataService.getCustomers().stream().filter(cus -> cus.id.equals(cust.id)).findFirst().orElse(null);
        if (c != null) c.addPurchase(total);

        Transaction t = new Transaction(UUID.randomUUID().toString(), new Date(), new ArrayList<>(cart), total, cust.id, cust.name);
        frame.dataService.getTransactions().add(t);

        JOptionPane.showMessageDialog(this, "Sale Completed! ₱" + String.format("%.2f", total));
        cart.clear();
        updateCartTable();
        frame.refreshAll();
    }
}

// Inventory Panel
class InventoryPanel extends JPanel {
    MainFrame frame;
    DefaultTableModel tableModel;

    public InventoryPanel(MainFrame frame) {
        this.frame = frame;
        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(20, 20, 20, 20));
        setBackground(Theme.BACKGROUND);

        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        JLabel title = new JLabel("Inventory Management");
        title.setFont(Theme.FONT_HEADER);
        JButton addBtn = Theme.createButton("+ Add Product", Theme.PRIMARY, Theme.PRIMARY_FG);
        addBtn.addActionListener(e -> showProductDialog(null));
        header.add(title, BorderLayout.WEST);
        header.add(addBtn, BorderLayout.EAST);
        add(header, BorderLayout.NORTH);


        String[] cols = {"SKU", "Name", "Category", "Price", "Stock", "Reorder Pt", "Edit"};
        tableModel = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };
        JTable table = new JTable(tableModel);
        Theme.styleTable(table);

        table.getColumnModel().getColumn(6).setMaxWidth(80);
        table.getColumnModel().getColumn(6).setCellRenderer(new ActionButtonRenderer());

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());
                int col = table.columnAtPoint(e.getPoint());

                if (row >= 0 && col == 6) {
                    String sku = (String) table.getValueAt(row, 0);
                    Product p = frame.dataService.getProducts().stream()
                            .filter(pr -> pr.sku.equals(sku))
                            .findFirst()
                            .orElse(null);
                    if (p != null) showProductDialog(p);
                }
            }
        });


        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        add(scrollPane, BorderLayout.CENTER);

        refresh();
    }

    public void refresh() {
        tableModel.setRowCount(0);
        for (Product p : frame.dataService.getProducts()) {
            tableModel.addRow(new Object[]{p.sku, p.name, p.category, p.price, p.stock, p.reorderPoint, ""});
        }
    }

    private void showProductDialog(Product p) {
        JDialog d = new JDialog(frame, (p == null ? "Add" : "Edit") + " Product", true);
        d.setLayout(new GridBagLayout());
        d.setSize(400, 500);
        d.setLocationRelativeTo(frame);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0; gbc.gridy = 0;

        JTextField txtSku = new JTextField();
        txtSku.setEditable(false);
        if (p == null) txtSku.setText(Product.generateSKU());
        else txtSku.setText(p.sku);

        JTextField txtName = new JTextField(p != null ? p.name : "");
        JTextField txtCat = new JTextField(p != null ? p.category : "");
        JTextField txtPrice = new JTextField(p != null ? String.valueOf(p.price) : "");
        JTextField txtStock = new JTextField(p != null ? String.valueOf(p.stock) : "");
        JTextField txtReorder = new JTextField(p != null ? String.valueOf(p.reorderPoint) : "10");

        addField(d, "SKU (Auto):", txtSku, gbc);
        addField(d, "Name:", txtName, gbc);
        addField(d, "Category:", txtCat, gbc);
        addField(d, "Price:", txtPrice, gbc);
        addField(d, "Stock:", txtStock, gbc);
        addField(d, "Reorder Point:", txtReorder, gbc);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton save = Theme.createButton("Save", Theme.PRIMARY, Color.WHITE);

        save.addActionListener(e -> {
            try {
                double price = Double.parseDouble(txtPrice.getText());
                int stock = Integer.parseInt(txtStock.getText());
                int reorder = Integer.parseInt(txtReorder.getText());

                if (p == null) {
                    Product newP = new Product(UUID.randomUUID().toString(), txtSku.getText(), txtName.getText(), price, stock, txtCat.getText(), reorder, "");
                    frame.dataService.getProducts().add(newP);
                } else {
                    p.sku = txtSku.getText(); p.name = txtName.getText(); p.category = txtCat.getText();
                    p.price = price; p.stock = stock; p.reorderPoint = reorder;
                }
                frame.refreshAll();
                d.dispose();
            } catch (Exception ex) { JOptionPane.showMessageDialog(d, "Invalid input"); }
        });
        btnPanel.add(save);

        if (p != null) {
            JButton delete = Theme.createButton("Delete", Theme.DANGER, Color.WHITE);
            delete.addActionListener(e -> {
                int confirm = JOptionPane.showConfirmDialog(d, "Are you sure you want to delete '" + p.name + "'?", "Confirm Delete", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if (confirm == JOptionPane.YES_OPTION) {
                    frame.dataService.getProducts().remove(p);
                    frame.refreshAll();
                    d.dispose();
                }
            });
            btnPanel.add(delete);
        }

        gbc.gridy++; gbc.gridwidth = 2;
        d.add(btnPanel, gbc);
        d.setVisible(true);
    }

    private void addField(JDialog d, String label, JTextField field, GridBagConstraints gbc) {
        gbc.gridwidth = 1; gbc.gridx = 0; d.add(new JLabel(label), gbc);
        gbc.gridx = 1; d.add(field, gbc); gbc.gridy++;
    }
}

class CustomerPanel extends JPanel {
    MainFrame frame;
    DefaultTableModel tableModel;

    public CustomerPanel(MainFrame frame) {
        this.frame = frame;
        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(20, 20, 20, 20));
        setBackground(Theme.BACKGROUND);

        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        JLabel title = new JLabel("Customer Management");
        title.setFont(Theme.FONT_HEADER);
        JButton addBtn = Theme.createButton("+ Add Customer", Theme.PRIMARY, Theme.PRIMARY_FG);
        addBtn.addActionListener(e -> showCustomerDialog());

        header.add(title, BorderLayout.WEST);
        header.add(addBtn, BorderLayout.EAST);
        add(header, BorderLayout.NORTH);

        String[] cols = {"Customer ID", "Name", "Phone", "Loyalty Pts", "Total Purchases"};
        tableModel = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };
        JTable table = new JTable(tableModel);
        Theme.styleTable(table);


        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        add(scrollPane, BorderLayout.CENTER);

        refresh();
    }

    public void refresh() {
        tableModel.setRowCount(0);
        for (Customer c : frame.dataService.getCustomers()) {
            tableModel.addRow(new Object[]{c.customId, c.name, c.phone, c.loyaltyPoints, String.format("₱%.2f", c.totalPurchases)});
        }
    }

    private void showCustomerDialog() {
        JDialog d = new JDialog(frame, "Add Customer", true);
        d.setLayout(new GridLayout(4, 2, 10, 10));
        d.setSize(300, 200);
        d.setLocationRelativeTo(frame);

        JTextField txtName = new JTextField();
        JTextField txtPhone = new JTextField();
        d.add(new JLabel(" Name:")); d.add(txtName);
        d.add(new JLabel(" Phone:")); d.add(txtPhone);
        d.add(new JLabel(" ID:")); d.add(new JLabel("ID will be generated"));

        JButton save = new JButton("Save");
        save.addActionListener(e -> {

            String newId = Customer.generateDisplayId();
            Customer c = new Customer(UUID.randomUUID().toString(), newId, txtName.getText(), txtPhone.getText(), 0, 0);
            frame.dataService.getCustomers().add(c);
            frame.refreshAll();
            JOptionPane.showMessageDialog(frame, "Customer Added! ID: " + newId);
            d.dispose();
        });
        d.add(new JLabel("")); d.add(save);
        d.setVisible(true);
    }
}

class AnalyticsPanel extends JPanel {
    MainFrame frame;
    JLabel lblSales, lblTrans, lblAvg, lblItems;
    SimpleBarChart barChart;
    SimplePieChart pieChart;

    public AnalyticsPanel(MainFrame frame) {
        this.frame = frame;
        setLayout(new BorderLayout(20, 20));
        setBorder(new EmptyBorder(20, 20, 20, 20));
        setBackground(Theme.BACKGROUND);

        JLabel title = new JLabel("Sales Analytics");
        title.setFont(Theme.FONT_HEADER);
        add(title, BorderLayout.NORTH);

        JPanel kpiPanel = new JPanel(new GridLayout(1, 4, 15, 0));
        kpiPanel.setOpaque(false);
        kpiPanel.setPreferredSize(new Dimension(0, 100));

        lblSales = createKpiCard("Total Sales", Theme.SUCCESS);
        lblTrans = createKpiCard("Transactions", Theme.PRIMARY);
        lblAvg = createKpiCard("Avg Transaction", Theme.ACCENT);
        lblItems = createKpiCard("Products Sold", Theme.SECONDARY);

        kpiPanel.add(formatKpiPanel(lblSales, "Total Revenue", Theme.SUCCESS));
        kpiPanel.add(formatKpiPanel(lblTrans, "Completed Sales", Theme.PRIMARY));
        kpiPanel.add(formatKpiPanel(lblAvg, "Per Sale Avg", Theme.ACCENT));
        kpiPanel.add(formatKpiPanel(lblItems, "Items Sold", Theme.SECONDARY));
        add(kpiPanel, BorderLayout.CENTER);

        JPanel chartsPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        chartsPanel.setOpaque(false);
        chartsPanel.setPreferredSize(new Dimension(0, 400));

        barChart = new SimpleBarChart();
        barChart.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Theme.PRIMARY_SOFT), "Sales by Date"));
        barChart.setBackground(Theme.CARD_BG);

        pieChart = new SimplePieChart();
        pieChart.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Theme.PRIMARY_SOFT), "Revenue by Category"));
        pieChart.setBackground(Theme.CARD_BG);

        chartsPanel.add(barChart);
        chartsPanel.add(pieChart);

        JPanel centerContainer = new JPanel(new BorderLayout(0, 20));
        centerContainer.setOpaque(false);
        centerContainer.add(kpiPanel, BorderLayout.NORTH);
        centerContainer.add(chartsPanel, BorderLayout.CENTER);
        add(centerContainer, BorderLayout.CENTER);
        refresh();
    }

    private JLabel createKpiCard(String title, Color color) {
        JLabel lbl = new JLabel("0");
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lbl.setForeground(color);
        return lbl;
    }

    private JPanel formatKpiPanel(JLabel valueLabel, String subText, Color sideColor) {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(Theme.CARD_BG);
        JPanel colorStrip = new JPanel();
        colorStrip.setBackground(sideColor);
        colorStrip.setPreferredSize(new Dimension(5, 0));
        p.add(colorStrip, BorderLayout.WEST);
        p.setBorder(new LineBorder(Theme.BORDER));

        JPanel content = new JPanel(new GridLayout(2, 1));
        content.setOpaque(false);
        content.setBorder(new EmptyBorder(15, 15, 15, 15));
        valueLabel.setHorizontalAlignment(SwingConstants.LEFT);
        content.add(valueLabel);
        JLabel sub = new JLabel(subText);
        sub.setForeground(Theme.TEXT_MUTED);
        content.add(sub);
        p.add(content, BorderLayout.CENTER);
        return p;
    }

    public void refresh() {
        List<Transaction> trans = frame.dataService.getTransactions();
        double totalSales = trans.stream().mapToDouble(t -> t.total).sum();
        int count = trans.size();
        double avg = count > 0 ? totalSales / count : 0;
        int items = trans.stream().flatMap(t -> t.items.stream()).mapToInt(i -> i.quantity).sum();

        lblSales.setText("₱" + String.format("%.2f", totalSales));
        lblTrans.setText(String.valueOf(count));
        lblAvg.setText("₱" + String.format("%.2f", avg));
        lblItems.setText(String.valueOf(items));

        Map<String, Double> salesByDate = new HashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd");
        for (Transaction t : trans) {
            String date = sdf.format(t.date);
            salesByDate.put(date, salesByDate.getOrDefault(date, 0.0) + t.total);
        }
        barChart.setData(salesByDate);

        Map<String, Double> salesByCat = new HashMap<>();
        for (Transaction t : trans) {
            for (CartItem item : t.items) {
                salesByCat.put(item.product.category, salesByCat.getOrDefault(item.product.category, 0.0) + item.getTotal());
            }
        }
        pieChart.setData(salesByCat);
        repaint();
    }
}

// charts and som helpers
class SimpleBarChart extends JPanel {
    private Map<String, Double> data = new HashMap<>();
    public void setData(Map<String, Double> data) { this.data = new TreeMap<>(data); repaint(); }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (data.isEmpty()) { g.drawString("No data available", getWidth()/2 - 40, getHeight()/2); return; }
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int pad = 40; int h = getHeight() - 2 * pad;
        double maxVal = data.values().stream().mapToDouble(v -> v).max().orElse(1);
        int barWidth = Math.max(10, (getWidth() - 2*pad) / data.size() - 10); int x = pad;
        for (Map.Entry<String, Double> entry : data.entrySet()) {
            int barHeight = (int) ((entry.getValue() / maxVal) * h);
            g2.setColor(Theme.PRIMARY);
            g2.fillRoundRect(x, getHeight() - pad - barHeight, barWidth, barHeight, 5, 5);
            g2.setColor(Color.BLACK);
            g2.setFont(new Font("Arial", Font.PLAIN, 10));
            g2.drawString(entry.getKey(), x, getHeight() - pad + 15);
            x += barWidth + 10;
        }
    }
}

class SimplePieChart extends JPanel {
    private Map<String, Double> data = new HashMap<>();
    public void setData(Map<String, Double> data) { this.data = data; repaint(); }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (data.isEmpty()) { g.drawString("No data available", getWidth()/2 - 40, getHeight()/2); return; }
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        double total = data.values().stream().mapToDouble(v -> v).sum();
        int diameter = Math.min(getWidth(), getHeight()) - 60;
        int startAngle = 0; int i = 0; int legendY = 20;
        for (Map.Entry<String, Double> entry : data.entrySet()) {
            double angle = (entry.getValue() / total) * 360;
            g2.setColor(Theme.CHART_COLORS[i % Theme.CHART_COLORS.length]);
            g2.fill(new Arc2D.Double(20, 20, diameter, diameter, startAngle, angle, Arc2D.PIE));
            g2.fillRect(getWidth() - 100, legendY, 10, 10);
            g2.setColor(Color.BLACK);
            g2.drawString(entry.getKey(), getWidth() - 85, legendY + 10);
            startAngle += angle; i++; legendY += 20;
        }
    }
}

interface DocumentUpdateListener {
    void update(DocumentEvent e);
}

class SimpleDocumentListener implements DocumentListener {
    private final DocumentUpdateListener listener;
    public SimpleDocumentListener(DocumentUpdateListener listener) { this.listener = listener; }
    public void insertUpdate(DocumentEvent e) { listener.update(e); }
    public void removeUpdate(DocumentEvent e) { listener.update(e); }
    public void changedUpdate(DocumentEvent e) { listener.update(e); }
}

//for edit icon
class ActionButtonRenderer extends DefaultTableCellRenderer {
    private final JLabel iconLabel;

    public ActionButtonRenderer() {
        iconLabel = new JLabel();
        iconLabel.setHorizontalAlignment(SwingConstants.CENTER);
        iconLabel.setIcon(new EditIcon(16, Theme.TEXT_MAIN));
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (!isSelected) {
            iconLabel.setBackground(row % 2 == 0 ? Theme.CARD_BG : Theme.SURFACE_ALT);
        } else {
            iconLabel.setBackground(table.getSelectionBackground());
        }
        iconLabel.setOpaque(true);
        iconLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        return iconLabel;
    }
}

class EditIcon implements Icon {
    private int size;
    private Color color;

    public EditIcon(int size, Color color) {
        this.size = size;
        this.color = color;
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(color);
        g2.setStroke(new BasicStroke(1.5f));

        // drawing the pencil
        g2.drawLine(x + 12, y + 2, x + 14, y + 4);
        g2.drawLine(x + 10, y + 4, x + 12, y + 6);

        Path2D p = new Path2D.Double();
        p.moveTo(x + 10, y + 4);
        p.lineTo(x + 4, y + 10);
        p.lineTo(x + 6, y + 12);
        p.lineTo(x + 12, y + 6);
        p.closePath();
        g2.draw(p);

        g2.drawLine(x + 4, y + 10, x + 2, y + 14);
        g2.drawLine(x + 2, y + 14, x + 6, y + 12);

        g2.dispose();
    }

    @Override
    public int getIconWidth() { return size; }

    @Override
    public int getIconHeight() { return size; }
}