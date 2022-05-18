package ui;

import app.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static services.Helper.*;

public class Search extends JFrame implements Window {
    private JPanel
            mainPanel,
            headerPanel,
            principalPanel,
            additionsPanel,
            resultsPanel;

    private JLabel
            titleLabel,
            companyLabel,
            insuranceLabel,
            principalAgeLabel,
            addRoleLabel,
            addAgeLabel;

    private JComboBox<String>
            companyComboBox,
            insuranceComboBox;

    private JTextField principalAgeField;

    private JButton
            searchButton,
            clearFormButton,
            loginButton,
            addPersonButton,
            msgButton;

    private controllers.Search controller;

    private ArrayList<Company> companyList;
    private ArrayList<InsuranceType> insuranceTypeList;
    private ArrayList<Role> roleList;

    public Search() {
        super();
        initComponents();
    }

    @Override
    public void launch() {
        this.setVisible(true);
    }

    private void initComponents() {
        controller = new controllers.Search();
        companyList = controller.queryCompanies();
        insuranceTypeList = controller.queryInsuranceTypes();
        roleList = controller.queryRoles();

        // arrays to be used in creating ComboBoxes
        String[] companies = new String[companyList.size()];
        String[] insuranceTypes = new String[insuranceTypeList.size()];
        for (int i = 0; i < companyList.size(); i++)
            companies[i] = companyList.get(i).getName();
        for (int i = 0; i < insuranceTypeList.size(); i++)
            insuranceTypes[i] = insuranceTypeList.get(i).getDescription();

        // Initialize components
        mainPanel = new JPanel();
        headerPanel = new JPanel();
        principalPanel = new JPanel();
        additionsPanel = new JPanel();
        resultsPanel = new JPanel();

        titleLabel = new JLabel("SEARCH INSURANCE PREMIUMS");
        companyLabel = new JLabel("Company:");
        insuranceLabel = new JLabel("Insurance Type:");
        principalAgeLabel = new JLabel("Principal age:");
        addRoleLabel = new JLabel("ROLE:");
        addAgeLabel = new JLabel("AGE:");

        companyComboBox = new JComboBox<>(companies);
        insuranceComboBox = new JComboBox<>(insuranceTypes);
        principalAgeField = new JTextField();

        addPersonButton = new JButton("Add child/spouse");
        searchButton = new JButton("SEARCH");
        clearFormButton = new JButton("CLEAR FORM");
        loginButton = new JButton("Admin Login");
        msgButton = new JButton("");

        // style title
        titleLabel.setForeground(new Color(118, 101, 88));
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 16));

        // msg button
        msgButton.setBorder(BorderFactory.createEmptyBorder());
        msgButton.setBackground(Color.RED);
        msgButton.setForeground(Color.WHITE);
        msgButton.setPreferredSize(new Dimension(200, 30));
        msgButton.setVisible(false);

        // header
        headerPanel.setLayout(new BorderLayout(50, 15));
        headerPanel.setBackground(new Color(255, 255, 255));
        headerPanel.setBorder(new EmptyBorder(0, 0, 20, 0));
        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(loginButton, BorderLayout.EAST);
        headerPanel.add(msgButton, BorderLayout.SOUTH);

        // principal
        principalPanel.setPreferredSize(new Dimension(450, 300));
        principalPanel.setBackground(new Color(255, 255, 255));
        principalPanel.setLayout(new BoxLayout(principalPanel, BoxLayout.Y_AXIS));
        // addPrincipalRow(companyLabel, companyComboBox);
        addRow(principalPanel, companyLabel, companyComboBox, "", 10, 10);
        // addPrincipalRow(insuranceLabel, insuranceComboBox);
        addRow(principalPanel, insuranceLabel, insuranceComboBox, "", 10, 10);
        // addPrincipalRow(principalAgeLabel, principalAgeField);
        addRow(principalPanel, principalAgeLabel, principalAgeField, "", 10, 10);
        // addPrincipalRow(new Label(""), searchButton);
        addRow(principalPanel, new JLabel(), searchButton, "", 5, 10);
        // addPrincipalRow(new Label(""), clearFormButton);
        addRow(principalPanel, new JLabel(), clearFormButton, "", 0, 10);

        // additions
        additionsPanel.setPreferredSize(new Dimension(350, 300));
        additionsPanel.setBackground(new Color(255, 255, 255));
        additionsPanel.setLayout(new BoxLayout(additionsPanel, BoxLayout.Y_AXIS));
        addPersonButton.setPreferredSize(new Dimension(0, 30));

        JPanel additionsHeader = new JPanel();
        additionsHeader.setBackground(new Color(255, 255, 255));
        additionsHeader.setLayout(new BorderLayout(5, 10));
        additionsHeader.setPreferredSize(new Dimension(350, 70));

        addRoleLabel.setPreferredSize(new Dimension(
                ((int) (additionsHeader.getPreferredSize().getWidth() / 3) - 2),
                30
        ));
        addAgeLabel.setPreferredSize(new Dimension(
                ((int) (additionsHeader.getPreferredSize().getWidth() * 2 / 3) - 2),
                30
        ));
        addRoleLabel.setMaximumSize(addAgeLabel.getPreferredSize());
        addAgeLabel.setMaximumSize(addAgeLabel.getPreferredSize());
        addRoleLabel.setForeground(new Color(85, 85, 85));
        addAgeLabel.setForeground(new Color(85, 85, 85));

        additionsHeader.add(addPersonButton, BorderLayout.NORTH);
        additionsHeader.add(addRoleLabel, BorderLayout.WEST);
        additionsHeader.add(addAgeLabel, BorderLayout.EAST);

        JPanel additionsContainer = new JPanel();
        additionsContainer.setPreferredSize(new Dimension(350, 370));
        additionsContainer.setBackground(new Color(255, 255, 255));
        additionsContainer.setLayout(new BorderLayout());

        additionsContainer.add(additionsHeader, BorderLayout.NORTH);
        additionsContainer.add(additionsPanel, BorderLayout.CENTER);
        hideAddLabels();

        // results
        resultsPanel.setPreferredSize(new Dimension(400, 200));
        resultsPanel.setBackground(new Color(255, 255, 255));
        resultsPanel.setLayout(new BorderLayout(10, 10));

        // body panel
        mainPanel.setLayout(new BorderLayout(100, 15));
        mainPanel.setBorder(new EmptyBorder(40, 40, 40, 40));
        mainPanel.setBackground(new Color(255, 255, 255));
        mainPanel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        mainPanel.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        mainPanel.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);

        mainPanel.add(headerPanel, BorderLayout.PAGE_START);
        mainPanel.add(principalPanel, BorderLayout.LINE_START);
        mainPanel.add(additionsContainer, BorderLayout.LINE_END);
        mainPanel.add(resultsPanel, BorderLayout.PAGE_END);

        // Action events
        clearFormButton.addActionListener(e -> clearForm());
        addPersonButton.addActionListener(e -> addPerson());

        searchButton.addActionListener(e -> {
            final int companyIdx = companyComboBox.getSelectedIndex();
            final int insuranceIdx = insuranceComboBox.getSelectedIndex();
            final int companyId = companyList.get(companyIdx).getId();
            final InsuranceType insuranceType = insuranceTypeList.get(insuranceIdx);

            if(isFieldEmpty(principalAgeField)) {
                showMsg("Principal age can't be empty!");
                return;
            }

            final int principalAge = Integer.parseInt(principalAgeField.getText().trim());
            ArrayList<Person> people = new ArrayList<>();
            people.add(new Person(principalAge, roleList.get(0))); // role=principal

            int numberOfSpouses = 0; // check if there are more than 1 spouse

            for(Component row : additionsPanel.getComponents()) {
                if(!(row instanceof JPanel && row.getName().equals("row")))
                    continue;

                int age = -1;
                Role role = null;

                for(Component child : ((JPanel) row).getComponents()) {
                    if(child instanceof JComboBox && child.getName().equals("role")) {
                        final int roleIdx = ((JComboBox<?>) child).getSelectedIndex();
                        role = roleList.get(roleIdx + 1);
                    }
                    if(child instanceof JTextField && child.getName().equals("age")) {
                        if(isFieldEmpty((JTextField) child)) {
                            showMsg("Spouse/child age can't be empty!");
                            return;
                        }
                        age = Integer.parseInt(((JTextField) child).getText().trim());
                    }
                }

                final String roleName = (role != null) ? role.getName() : "";
                if (roleName.equalsIgnoreCase("spouse"))
                    numberOfSpouses++;

                if(numberOfSpouses > 1) {
                    showMsg("You can't have more than one spouse!");
                    return;
                }
                if (roleName.equalsIgnoreCase("child") && age > 17) {
                    showMsg("Results won't include children above 18. They must have their own premium account!");
                    continue;
                }
                if(age > -1)
                    people.add(new Person(age, role));
            }

            List<Map<String, String>> searchResult;
            searchResult = controller.calculateInsurance(companyId, insuranceType, people);
            printResult(searchResult);
        });

        loginButton.addActionListener(e -> {
            this.dispose();
            Login loginWindow = new Login();
            loginWindow.launch();
        });

        // window properties
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("MEDCALC");
        this.setSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        this.setMinimumSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));

        this.setLocationRelativeTo(null); // center window
        this.getContentPane().add(mainPanel);
        this.pack();
        this.setResizable(false);
    }

    private void addPerson() {
        JComboBox<String> roleComboBox;
        JTextField ageField;
        String[] roles = new String[roleList.size() - 1]; // no 'principal' choice

        int j = 0;
        for (Role role : roleList) {
            String roleName = role.getName();
            if (!roleName.equalsIgnoreCase("principal")) {
                roles[j] = roleName;
                j++;
            }
        }

        roleComboBox = new JComboBox<>(roles);
        ageField = new JTextField();
        roleComboBox.setName("role");
        ageField.setName("age");
        showAddLabels();
        addRow(additionsPanel, roleComboBox, ageField, "row", 5, 5);
    }

    private void showMsg(String msg) {
        msgButton.setText(msg);
        msgButton.setVisible(true);
    }

    private void hideMsg() {
        msgButton.setText("");
        msgButton.setVisible(false);
    }

    private void showAddLabels() {
        addRoleLabel.setVisible(true);
        addAgeLabel.setVisible(true);
    }

    private void hideAddLabels() {
        addRoleLabel.setVisible(false);
        addAgeLabel.setVisible(false);
    }

    private void clearForm() {
        companyComboBox.setSelectedIndex(0);
        insuranceComboBox.setSelectedIndex(0);
        principalAgeField.setText("");
        principalAgeField.requestFocusInWindow();

        resultsPanel.removeAll();
        additionsPanel.removeAll();
        refreshUI(resultsPanel);
        refreshUI(additionsPanel);
        hideAddLabels();
        hideMsg();
    }

    private void printResult(List<Map<String, String>> searchResult) {
        if(searchResult.size() == 0) {
            JLabel msgLabel = new JLabel("NO RECORDS FOUND IN THE DATABASE!");
            msgLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
            msgLabel.setForeground(new Color(217, 14, 14));
            resultsPanel.removeAll(); // remove old data
            resultsPanel.add(msgLabel, BorderLayout.NORTH);
            refreshUI(resultsPanel);
            return;
        }

        String[] columnNames = {"#", "Role", "Age range", "Classic", "Premier", "Advanced"};
        final int cols = columnNames.length;
        final int rows = searchResult.size() + 1;

        String[][] tableData = new String[rows][cols];
        double totalClassic = 0;
        double totalPremier = 0;
        double totalAdvanced = 0;
        int i = 0;

        for(Map<String, String> record : searchResult) {
            tableData[i] = new String[]{
                    String.valueOf(i + 1),
                    record.get("role"),
                    record.get("age_range"),
                    record.get("classic"),
                    record.get("premier"),
                    record.get("advanced"),
            };
            totalClassic += Double.parseDouble(record.get("classic"));
            totalPremier += Double.parseDouble(record.get("premier"));
            totalAdvanced += Double.parseDouble(record.get("advanced"));
            i++;
        }
        tableData[rows - 1] = new String[] {
                "Total",
                "---",
                "---",
                String.valueOf(totalClassic),
                String.valueOf(totalPremier),
                String.valueOf(totalAdvanced),
        };

        JTable table = new JTable(tableData, columnNames);
        table.setDefaultEditor(Object.class, null);
        JScrollPane sp = new JScrollPane(table);

        resultsPanel.removeAll(); // remove old data
        resultsPanel.add(new Label("SEARCH RESULTS:"), BorderLayout.NORTH);
        resultsPanel.add(sp, BorderLayout.CENTER);
        refreshUI(resultsPanel);
    }

}
