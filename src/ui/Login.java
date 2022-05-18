package ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

import static services.Helper.*;

public class Login extends JFrame implements Window {
    private JPanel mainPanel;

    private JLabel
            titleLabel,
            usernameLabel,
            passwordLabel;

    private JTextField usernameField;
    private JPasswordField passwordField;

    private JButton
            loginButton,
            clearButton,
            msgButton;

    private controllers.Login controller;

    // constructor
    public Login() {
        super();
        initComponents();
    }

    @Override
    public void launch() {
        this.setVisible(true);
    }

    private void initComponents() {
        // logic controller
        controller = new controllers.Login();

        // initialize components
        mainPanel = new JPanel();

        titleLabel = new JLabel("ADMIN LOGIN");
        usernameLabel = new JLabel("Email:");
        passwordLabel = new JLabel("Password:");

        loginButton = new JButton("SIGN IN");
        clearButton = new JButton("RESET");
        msgButton = new JButton();

        usernameField = new JTextField();
        passwordField = new JPasswordField();

        // main panel
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(40, 40, 40, 40));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        mainPanel.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        mainPanel.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);

        // style title
        titleLabel.setForeground(new Color(118, 101, 88));
        titleLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));

        // error messages
        msgButton.setBorder(BorderFactory.createEmptyBorder());
        msgButton.setBackground(Color.RED);
        msgButton.setForeground(Color.WHITE);
        msgButton.setPreferredSize(new Dimension(200, 30));
        msgButton.setVisible(false);

        JPanel bodyPanel = new JPanel();
        bodyPanel.setBackground(Color.WHITE);
        bodyPanel.setPreferredSize(new Dimension(300, 300));
        bodyPanel.setLayout(new BoxLayout(bodyPanel, BoxLayout.Y_AXIS));

        JPanel imgPanel = new JPanel();
        imgPanel.setLayout(new FlowLayout());
        imgPanel.setBackground(Color.WHITE);
        JLabel loginImg = new JLabel(new ImageIcon("C:\\Users\\arnau\\IdeaProjects\\gold_oak_v1\\src\\resources\\login.png"));
        imgPanel.add(loginImg);

        bodyPanel.add(titleLabel);
        addVGap(bodyPanel, 40);
        addRow(bodyPanel, usernameLabel, usernameField, "", 15, 15);
        addRow(bodyPanel, passwordLabel, passwordField, "", 15, 15);
        addRow(bodyPanel, new JLabel(), loginButton, "", 10, 15);
        addRow(bodyPanel, new JLabel(), clearButton, "", 0, 15);

        mainPanel.add(imgPanel, BorderLayout.WEST);
        mainPanel.add(bodyPanel, BorderLayout.CENTER);
        mainPanel.add(msgButton, BorderLayout.SOUTH);

        //events
        clearButton.addActionListener(e -> {
            usernameField.setText("");
            passwordField.setText("");
            hideMsg();
            usernameField.requestFocusInWindow();
        });

        loginButton.addActionListener(e -> {
            // Check if any of the fields is empty
            if(isFieldEmpty(usernameField)) {
                showMsg("Username field CANNOT be empty!");
                return;
            }
            if(isFieldEmpty(passwordField)) {
                showMsg("Password field CANNOT be empty!");
                return;
            }

            // get data
            final String email = usernameField.getText().trim();
            final String password = String.valueOf(passwordField.getPassword());

            // check if data is saved in the database
            if(controller.userExists(email, password)) {
                this.dispose();
                Admin adminDashboard = new Admin();
                adminDashboard.launch();
            }
            else {
                showMsg("Wrong username or password!");
            }
        });

        // window properties
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("MEDCALC | LOGIN");
        this.setSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        this.setMinimumSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));

        this.setLocationRelativeTo(null); // center window
        this.getContentPane().add(mainPanel);
        this.pack();
        this.setResizable(false);
    }

    private void showMsg(String msg) {
        msgButton.setText(msg);
        msgButton.setVisible(true);
    }

    private void hideMsg() {
        msgButton.setText("");
        msgButton.setVisible(false);
    }

}
