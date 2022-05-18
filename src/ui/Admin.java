package ui;

import app.Company;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static services.Helper.*;

public class Admin extends JFrame implements Window {
    private JPanel
            panelMain,
            panelHeader,
            panelBody,
            panelCompanies,
            panelType,
            panelSubscription,
            panelAge,
            panelRoles,
            panelPrices;

    private Label pageTitle;

    private JTextField
            companyNameField,
            companyDetailsField;

    private JButton
            addCompanyButton,
            deleteCompanyButton,
            updateCompanyButton,
            clearCompanyForm;

    private controllers.Admin controller;
    private int selectedCompanyId;

    public Admin() {
        super();
        initComponents();
    }

    @Override
    public void launch() {
        this.setVisible(true);
    }

    private void initComponents() {
        // logic controller
        controller = new controllers.Admin();

        final String mainFont = "Tahoma";
        final int spacing = 40;

        // general
        panelMain = new JPanel();
        panelHeader = new JPanel();
        panelBody = new JPanel();
        pageTitle = new Label("Registered Companies");

        panelMain.setBackground(Color.WHITE);
        panelMain.setFont(new Font(mainFont, Font.PLAIN, 14)); // NOI18N
        panelMain.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        panelMain.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);

        panelHeader.setBorder(new EmptyBorder(15, spacing, 15, spacing));
        panelHeader.setBackground(new Color(230, 0, 92));
        JLabel logo =  new JLabel("GoldOaK");
        logo.setForeground(Color.WHITE);
        logo.setFont(new Font(mainFont, Font.BOLD, 16));

        panelBody.setBorder(new EmptyBorder(spacing, spacing, spacing, spacing));
        panelBody.setBackground(Color.WHITE);

        pageTitle.setFont(new Font(mainFont, Font.BOLD, 16));
        pageTitle.setForeground(new Color(198, 23, 69));
        pageTitle.setBackground(new Color(250, 215, 215));

        // navigation buttons
        JPanel nav = new JPanel();
        nav.setBackground(Color.WHITE);
        JButton navBtnCompanies = new JButton("Companies");
        JButton navBtnType = new JButton("Insurance Type");
        JButton navBtnSubscription = new JButton("Subscription");
        JButton navBtnAges = new JButton("Age range");
        JButton navBtnRoles = new JButton("Roles");
        JButton navBtnPrices = new JButton("Prices");

        nav.setLayout(new BoxLayout(nav, BoxLayout.X_AXIS));
        nav.add(navBtnCompanies);
        nav.add(navBtnType);
        nav.add(navBtnSubscription);
        nav.add(navBtnAges);
        nav.add(navBtnRoles);
        nav.add(navBtnPrices);

        panelHeader.setLayout(new BorderLayout());
        panelHeader.add(logo, BorderLayout.WEST);
        panelHeader.add(nav, BorderLayout.EAST);


        // *** nav::companies ***
        panelCompanies =  new JPanel();
        panelCompanies.setLayout(new BorderLayout(20, 20));
        panelCompanies.setBackground(Color.WHITE);
        companyNameField = new JTextField();
        companyDetailsField = new JTextField();
        addCompanyButton = new JButton("ADD");
        deleteCompanyButton = new JButton("DELETE");
        updateCompanyButton = new JButton("UPDATE");
        clearCompanyForm = new JButton("CLEAR FORM");

        JPanel addCompanyPanel = new JPanel();
        addCompanyPanel.setPreferredSize(new Dimension(400, 0));
        addCompanyPanel.setBackground(Color.WHITE);
        addCompanyPanel.setLayout(new BoxLayout(addCompanyPanel, BoxLayout.Y_AXIS));
        addRow(addCompanyPanel, new JLabel("NEW COMPANY"), new JLabel(), "", 20, 0);
        addRow(addCompanyPanel, new JLabel("Name:"), companyNameField, "", 10, 10);
        addRow(addCompanyPanel, new JLabel("Details:"), companyDetailsField, "", 10, 10);
        addRow(addCompanyPanel, new JLabel(), addCompanyButton, "", 5, 10);
        addRow(addCompanyPanel, new JLabel(), updateCompanyButton, "", 5, 10);
        addRow(addCompanyPanel, new JLabel(), deleteCompanyButton, "", 5, 10);
        addRow(addCompanyPanel, new JLabel(), clearCompanyForm, "", 0, 10);
        panelCompanies.add(addCompanyPanel, BorderLayout.WEST);

        addCompanyButton.addActionListener(e -> {
            if(isFieldEmpty(companyNameField)) {
                // show message
                return;
            }
            if(isFieldEmpty(companyDetailsField)) {
                // show message
                return;
            }
            final String companyName = companyNameField.getText().trim();
            final String companyDetails = companyDetailsField.getText().trim();
            Boolean added = controller.createCompany(companyName, companyDetails);
            if(added) {
                // show message
                clearCompanyForm();
                refreshCompanies();
            }
        });

        deleteCompanyButton.addActionListener(e -> {
            Boolean deleted = controller.deleteCompany(selectedCompanyId);
            if(deleted) {
                // show message
                clearCompanyForm();
                refreshCompanies();
            }
        });

        updateCompanyButton.addActionListener(e -> {
            if(isFieldEmpty(companyNameField)) {
                // show message
                return;
            }
            if(isFieldEmpty(companyDetailsField)) {
                // show message
                return;
            }
            final String companyName = companyNameField.getText().trim();
            final String companyDetails = companyDetailsField.getText().trim();
            Boolean updated = controller.updateCompany(new Company(selectedCompanyId, companyName, companyDetails));
            if(updated) {
                // show message
                clearCompanyForm();
                refreshCompanies();
            }
        });

        clearCompanyForm.addActionListener(e -> {
            clearCompanyForm();
        });


        // *** nav::insuranceTypes ***
        panelType =  new JPanel();
        panelType.setLayout(new BoxLayout(panelType, BoxLayout.Y_AXIS));
        panelType.setBackground(Color.WHITE);


        // *** nav::subscriptions ***
        panelSubscription =  new JPanel();
        panelSubscription.setLayout(new BoxLayout(panelSubscription, BoxLayout.Y_AXIS));
        panelSubscription.setBackground(Color.WHITE);


        // *** nav::age ranges ***
        panelAge =  new JPanel();
        panelAge.setLayout(new BoxLayout(panelAge, BoxLayout.Y_AXIS));
        panelAge.setBackground(Color.WHITE);


        // *** nav::user roles ***
        panelRoles =  new JPanel();
        panelRoles.setLayout(new BoxLayout(panelRoles, BoxLayout.Y_AXIS));
        panelRoles.setBackground(Color.WHITE);


        // *** nav::prices ***
        panelPrices =  new JPanel();
        panelPrices.setLayout(new BoxLayout(panelPrices, BoxLayout.Y_AXIS));
        panelPrices.setBackground(Color.WHITE);


        // *** general layout ***
        panelBody.setLayout(new BorderLayout(20, 20));
        panelBody.add(pageTitle, BorderLayout.NORTH);
        panelBody.add(panelCompanies, BorderLayout.CENTER);

        panelMain.setLayout(new BorderLayout());
        panelMain.add(panelHeader, BorderLayout.NORTH);
        panelMain.add(panelBody, BorderLayout.CENTER);

        // Populate nav panels
        displayCompanies();
        listPrices();

        // Navigate
        navBtnCompanies.addActionListener(e -> setVisibleBodyPane(panelCompanies, "Registered Companies"));
        navBtnType.addActionListener(e -> setVisibleBodyPane(panelType, "Insurance Types"));
        navBtnSubscription.addActionListener(e -> setVisibleBodyPane(panelSubscription, "Insurance categories"));
        navBtnAges.addActionListener(e -> setVisibleBodyPane(panelAge, "Insurance age ranges"));
        navBtnRoles.addActionListener(e -> setVisibleBodyPane(panelRoles, "Customer roles"));
        navBtnPrices.addActionListener(e -> setVisibleBodyPane(panelPrices, "Company Prices"));

        // Window properties
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Gold OaK | ADMIN");
        this.setSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        this.setMinimumSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));

        this.setLocationRelativeTo(null); // center window
        this.getContentPane().add(panelMain);
        this.pack();
        this.setResizable(false);
    }

    private void clearCompanyForm() {
        companyNameField.setText("");
        companyDetailsField.setText("");
        companyNameField.requestFocusInWindow();
    }

    private void setVisibleBodyPane(JPanel panel, String title) {
        pageTitle.setText(title);
        panelBody.remove(1); // index::0-title, index::1-the panel we're updating
        panelBody.add(panel);
        refreshUI(panelBody);
    }

    private void refreshCompanies() {
        BorderLayout layout = (BorderLayout) panelCompanies.getLayout();
        panelCompanies.remove(layout.getLayoutComponent(BorderLayout.EAST));
        displayCompanies();
    }

    private void displayCompanies() {
        final String[] columnNames = {"ID", "Name", "Details"};
        ArrayList<Company> companyRecords = controller.queryCompanies();
        int cols = columnNames.length;
        int rows = companyRecords.size();
        String[][] tableData = new String[rows][cols];

        for(int i = 0; i < rows; i++)
            tableData[i] = companyRecords.get(i).toStringArray();

        JTable table = new JTable(tableData, columnNames);
        table.setDefaultEditor(Object.class, null);

        table.getSelectionModel().addListSelectionListener(e -> {
            int row = table.getSelectedRow();
            selectedCompanyId = Integer.parseInt(table.getValueAt(row, 0).toString());
            companyNameField.setText(table.getValueAt(row, 1).toString());
            companyDetailsField.setText(table.getValueAt(row, 2).toString());
        });

        // allow table to scroll
        JScrollPane sp = new JScrollPane(table);
        panelCompanies.add(sp, BorderLayout.EAST);
        refreshUI(panelCompanies);
    }

    private void listPrices() {
        List<Map<String, String>> priceRecords = controller.queryPrices();
        String[] columnNames = {"#", "Company", "Insurance Type", "Subscription", "Age Range", "Role", "Amount"};
        final int cols = columnNames.length;
        final int rows = priceRecords.size();

        // turn list into an array
        String[][] tableData = new String[rows][cols];
        int i = 0;

        for(Map<String, String> record : priceRecords) {
            tableData[i] = new String[] {
                    String.valueOf(++i),
                    record.get("company"),
                    record.get("insurance_type"),
                    record.get("subscription"),
                    record.get("age_range"),
                    record.get("role"),
                    record.get("amount"),
            };
        }

        JTable table = new JTable(tableData, columnNames);
        table.setDefaultEditor(Object.class, null); // prevent inline editing
        JScrollPane sp = new JScrollPane(table);
        panelPrices.add(sp);
    }


    public static void main(String[] args) {
        new Admin().launch();
    }

}
