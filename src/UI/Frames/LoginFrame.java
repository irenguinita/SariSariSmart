package UI.Frames;

import Database.CustomException.DuplicateUserException;
import Database.CustomException.SignUpFailedException;
import Backend.DataService;
import Database.CustomException.*;
import Database.Users.User;
import Database.Users.UserManager;
import UI.Theme;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.io.IOException;

public class LoginFrame extends JFrame {
    public DataService dataService;
    public JPanel cardPanel;
    CardLayout cardLayout;
    private UserManager userManager;

    public LoginFrame(DataService dataService, UserManager userManager) {
        this.dataService = dataService;
        this.userManager = userManager;
        setTitle("Sari-Sari Smart");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.setBackground(Theme.BACKGROUND);

        cardPanel.add(createPanelWrapper(createLoginBox(), "LOGIN"), "LOGIN");
        cardPanel.add(createPanelWrapper(createSignupBox(), "SIGNUP"), "SIGNUP");
        cardPanel.add(createPanelWrapper(createForgotBox(), "FORGOT_PASSWORD"), "FORGOT_PASSWORD");

        add(cardPanel);
    }

    //PANELS
    //wrapper for the logo, text and the panel para maka adjust and space between the header and the panels
    JPanel createPanelWrapper(JPanel formBox, String type) {
        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setBackground(Theme.BACKGROUND);

        JPanel contentStack = new JPanel();
        contentStack.setLayout(new BoxLayout(contentStack, BoxLayout.Y_AXIS));
        contentStack.setBackground(Theme.BACKGROUND);

        JPanel header = createHeader();
        contentStack.add(header);

        contentStack.add(Box.createVerticalStrut(20));

        contentStack.add(formBox);

        wrapper.add(contentStack);
        return wrapper;
    }

    JPanel createHeader() {
        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setBackground(Theme.BACKGROUND);
        header.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel logoLabel = new JLabel();
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);


        try {
            ImageIcon originalIcon = new ImageIcon(getClass().getResource("/SariSariSmart_Logo.png"));
            Image scaled = originalIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            logoLabel.setIcon(new ImageIcon(scaled));
        } catch (Exception e) {
            logoLabel.setText("[LOGO]");
            logoLabel.setFont(Theme.FONT_HEADER);
        }

        JLabel titleLabel = new JLabel("Sari-Sari Smart");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        titleLabel.setForeground(Theme.PRIMARY);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        header.add(logoLabel);
        header.add(titleLabel);

        return header;
    }

    JPanel createLoginBox() {
        JPanel p = new JPanel(new GridLayout(0, 1, 10, 10));
        p.setBackground(Theme.CARD_BG);
        p.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(Theme.BORDER, 1, true),
                new EmptyBorder(30, 30, 30, 30)
        ));
        p.setPreferredSize(new Dimension(380, 350));
        p.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField userField = new JTextField();
        JPasswordField passField = new JPasswordField();

        JButton loginBtn = Theme.createButton("LOG IN", Theme.PRIMARY, Color.WHITE);
        JButton goToSignup = new JButton("No account? Sign Up");
        styleLinkButton(goToSignup);

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
                } catch (Database.CustomException.LoginFailedException l) {
                    statusLabel.setText("Login failed. Try again. (Update to specify if password is incorrect)");
                    // status label
                }
            }
        });

        goToSignup.addActionListener(e -> cardLayout.show(cardPanel, "SIGNUP"));
        forgotPasswordButton.addActionListener(e -> cardLayout.show(cardPanel, "FORGOT_PASSWORD"));

        return p;
    }

    private JPanel createSignupBox() {
        JPanel p = new JPanel(new GridLayout(0, 1, 10, 10));
        p.setBackground(Theme.CARD_BG);
        p.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(Theme.BORDER, 1, true),
                new EmptyBorder(30, 30, 30, 30)
        ));
        p.setPreferredSize(new Dimension(380, 520));
        p.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton signupBtn = Theme.createButton("CREATE ACCOUNT", Theme.SECONDARY, Color.WHITE);
        JButton goToLogin = new JButton("Back to Login");
        styleLinkButton(goToLogin);

        JTextField userField = new JTextField();
        JPasswordField passField = new JPasswordField();
        JPasswordField confirmPassField = new JPasswordField();

        p.add(new JLabel("Enter Username"));
        p.add(userField);

        p.add(new JLabel("Enter Email"));
        p.add(new JTextField());

        p.add(new JLabel("Enter Password"));
        p.add(passField);
        p.add(new JLabel("Confirm Password"));
        p.add(confirmPassField);

        JLabel statusLabel = new JLabel("");
        p.add(statusLabel);
        p.add(signupBtn);
        p.add(goToLogin);

        signupBtn.addActionListener(e -> {
            String user = userField.getText().trim();
            String pass = new String(passField.getPassword()).trim();
            String confirmPass = new String(confirmPassField.getPassword()).trim();

            if (!pass.equals(confirmPass)){
                // status label here
                statusLabel.setText("Passwords do not match.");
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
                    statusLabel.setText("User already exists.");
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
                    statusLabel.setText("Username must not include space.");
                    throw new SignUpFailedException("Must not include space.");
                } else if (user.length() < 6){
                    statusLabel.setText("Username must have at least 6 characters.");
                    throw new SignUpFailedException("User must have at least 6 characters");
                }
            }
        });

        goToLogin.addActionListener(e -> cardLayout.show(cardPanel, "LOGIN"));
        return p;
    }

    private JPanel createForgotBox() {
        JPanel p = new JPanel(new GridLayout(0, 1, 10, 10));
        p.setBackground(Theme.CARD_BG);
        p.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(Theme.BORDER, 1, true),
                new EmptyBorder(30, 30, 30, 30)
        ));
        p.setPreferredSize(new Dimension(380, 420));
        p.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton changePassBtn = Theme.createButton("CHANGE PASSWORD", Theme.PRIMARY, Color.WHITE);
        JButton backToLogin = new JButton("Back to Login");
        styleLinkButton(backToLogin);

        p.add(new JLabel("Enter Email"));
        p.add(new JTextField());
        p.add(new JLabel("New Password"));
        p.add(new JPasswordField());
        p.add(new JLabel("Confirm Password"));
        p.add(new JPasswordField());

        p.add(new JLabel(""));
        p.add(changePassBtn);
        p.add(backToLogin);

        changePassBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Password changed successfully!");
            cardLayout.show(cardPanel, "LOGIN");
        });

        backToLogin.addActionListener(e -> cardLayout.show(cardPanel, "LOGIN"));
        return p;
    }

    void styleLinkButton(JButton btn) {
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setContentAreaFilled(false);
        btn.setForeground(Theme.TEXT_MUTED);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
}
