import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;

public class MainGUI {

    JFrame frame;
    JPanel menuPanel, contentPanel;

    String loggedInUser = "";


    MainGUI() {

        frame = new JFrame("Expense Tracker");

        

        frame.setSize(1000, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JLabel title = new JLabel(
                "EXPENSE TRACKING AND MANAGEMENT SYSTEM",
                SwingConstants.CENTER
        );

        title.setBorder(BorderFactory.createEmptyBorder(25, 0, 25, 0));

        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setForeground(new Color(212, 175, 55));
        title.setBorder(
    BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(
            new Color(212, 175, 55), 2
        ),
        BorderFactory.createEmptyBorder(
            10, 20, 10, 20
        )
    )
);

        frame.add(title, BorderLayout.NORTH);

        menuPanel = new JPanel();
        menuPanel.setBorder(
    BorderFactory.createEmptyBorder(30, 20, 20, 20)
);
        menuPanel.setLayout(new GridLayout(7, 1, 10, 10));

        JButton addBtn = new JButton("Add Expense");
        addBtn.setBackground(new Color(52, 152, 219));
addBtn.setForeground(Color.WHITE);
addBtn.setFont(new Font("Arial", Font.BOLD, 14));
addBtn.setFocusPainted(false);
        JButton showBtn = new JButton("Show Expenses");
        JButton searchBtn = new JButton("Search Expense");
        JButton updateBtn = new JButton("Update Expense");
        JButton deleteBtn = new JButton("Delete Expense");
        JButton totalBtn = new JButton("Total Expense");
        JButton monthlyBtn = new JButton("Monthly Report");
        JButton exitBtn = new JButton("Exit");
        showBtn.setBackground(new Color(52, 152, 219));
showBtn.setForeground(Color.WHITE);
showBtn.setFont(new Font("Arial", Font.BOLD, 14));
showBtn.setFocusPainted(false);

searchBtn.setBackground(new Color(52, 152, 219));
searchBtn.setForeground(Color.WHITE);
searchBtn.setFont(new Font("Arial", Font.BOLD, 14));
searchBtn.setFocusPainted(false);

updateBtn.setBackground(new Color(52, 152, 219));
updateBtn.setForeground(Color.WHITE);
updateBtn.setFont(new Font("Arial", Font.BOLD, 14));
updateBtn.setFocusPainted(false);

deleteBtn.setBackground(new Color(52, 152, 219));
deleteBtn.setForeground(Color.WHITE);
deleteBtn.setFont(new Font("Arial", Font.BOLD, 14));
deleteBtn.setFocusPainted(false);

totalBtn.setBackground(new Color(52, 152, 219));
totalBtn.setForeground(Color.WHITE);
totalBtn.setFont(new Font("Arial", Font.BOLD, 14));
totalBtn.setFocusPainted(false);

monthlyBtn.setBackground(new Color(52, 152, 219));
monthlyBtn.setForeground(Color.WHITE);
monthlyBtn.setFont(new Font("Arial", Font.BOLD, 14));
monthlyBtn.setFocusPainted(false);

exitBtn.setBackground(new Color(231, 76, 60));
exitBtn.setForeground(Color.WHITE);
exitBtn.setFont(new Font("Arial", Font.BOLD, 14));
exitBtn.setFocusPainted(false);

        menuPanel.add(addBtn);
        menuPanel.add(showBtn);
        menuPanel.add(searchBtn);
        menuPanel.add(updateBtn);
        menuPanel.add(deleteBtn);
        menuPanel.add(totalBtn);
        menuPanel.add(monthlyBtn);
        menuPanel.add(exitBtn);

        frame.add(menuPanel, BorderLayout.WEST);

        contentPanel = new JPanel(new BorderLayout());

        JLabel welcome = new JLabel(
                "Welcome to Expense Tracker System",
                SwingConstants.CENTER
        );

        welcome.setFont(new Font("Arial", Font.BOLD, 22));
        welcome.setForeground(new Color(27, 94, 32));
        

        contentPanel.add(welcome, BorderLayout.CENTER);

        frame.add(contentPanel, BorderLayout.CENTER);

        addBtn.addActionListener(e -> showAddExpenseForm());

        showBtn.addActionListener(e -> showExpenses());

        searchBtn.addActionListener(e -> searchExpense());

        deleteBtn.addActionListener(e -> deleteExpense());

        totalBtn.addActionListener(e -> totalExpense());

        monthlyBtn.addActionListener(e -> monthlyReport());

        updateBtn.addActionListener(e -> updateExpense());

        exitBtn.addActionListener(e -> System.exit(0));

        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
    }

    public void showAddExpenseForm() {

        contentPanel.removeAll();

        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel titleLabel = new JLabel("Title:");
titleLabel.setBounds(300, 120, 100, 30);

JTextField titleField = new JTextField();
titleField.setBounds(450, 120, 220, 30);

JLabel amountLabel = new JLabel("Amount:");
amountLabel.setBounds(300, 170, 100, 30);

JTextField amountField = new JTextField();
amountField.setBounds(450, 170, 220, 30);

JLabel categoryLabel = new JLabel("Category:");
categoryLabel.setBounds(300, 220, 100, 30);

JTextField categoryField = new JTextField();
categoryField.setBounds(450, 220, 220, 30);

JLabel dateLabel = new JLabel("Date (YYYY-MM-DD):");
dateLabel.setBounds(300, 270, 140, 30);

JTextField dateField = new JTextField();
dateField.setBounds(450, 270, 220, 30);

JButton saveBtn = new JButton("Save");
saveBtn.setBounds(500, 340, 120, 40);

// Green Save Button
saveBtn.setBackground(new Color(40, 167, 69));
saveBtn.setForeground(Color.WHITE);
saveBtn.setFocusPainted(false);

        saveBtn.addActionListener(e -> {

                if(titleField.getText().trim().isEmpty()) {

    JOptionPane.showMessageDialog(
            frame,
            "Please Enter Title!"
    );
    return;
}

if(amountField.getText().trim().isEmpty()) {

    JOptionPane.showMessageDialog(
            frame,
            "Please Enter Amount!"
    );
    return;
}

if(categoryField.getText().trim().isEmpty()) {

    JOptionPane.showMessageDialog(
            frame,
            "Please Enter Category!"
    );
    return;
}

if(dateField.getText().trim().isEmpty()) {

    JOptionPane.showMessageDialog(
            frame,
            "Please Enter Date!"
    );
    return;
}

            try {

                Connection con =
                        DBConnection.createConnection();

                String query =
                        "INSERT INTO expenses(title, amount, category, expense_date) VALUES (?, ?, ?, ?)";

                PreparedStatement ps =
                        con.prepareStatement(query);

                ps.setString(1,
                        titleField.getText());

                ps.setDouble(2,
                        Double.parseDouble(
                                amountField.getText()
                        ));

                ps.setString(3,
                        categoryField.getText());

                ps.setDate(4,
                        java.sql.Date.valueOf(
                                dateField.getText()
                        ));

                ps.executeUpdate();

                JOptionPane.showMessageDialog(
                        frame,
                        "Expense Added Successfully!"
                );

                titleField.setText("");
                amountField.setText("");
                categoryField.setText("");
                dateField.setText("");

            } catch (Exception ex) {

                JOptionPane.showMessageDialog(
                        frame,
                        ex.toString()
                );
            }
        });

        panel.add(titleLabel);
        panel.add(titleField);

        panel.add(amountLabel);
        panel.add(amountField);

        panel.add(categoryLabel);
        panel.add(categoryField);

        panel.add(dateLabel);
        panel.add(dateField);

        panel.add(saveBtn);

        contentPanel.add(panel, BorderLayout.CENTER);

        contentPanel.revalidate();
        contentPanel.repaint();
    }

    public void showExpenses() {

    contentPanel.removeAll();

    String[] columns = {
            "ID",
            "Title",
            "Amount",
            "Category",
            "Date"
    };

    DefaultTableModel model =
            new DefaultTableModel(columns, 0);

    JTable table = new JTable(model);
    table.setFont(new Font("Arial", Font.PLAIN, 16));
table.setRowHeight(30);
table.getTableHeader().setFont(
    new Font("Arial", Font.BOLD, 18)
);

    JButton printBtn = new JButton("Print Report");

printBtn.setFont(
        new Font("Arial", Font.BOLD, 14)
);

printBtn.setPreferredSize(
        new Dimension(200, 40)
);

    printBtn.setPreferredSize(
        new Dimension(200, 40)
);

    printBtn.addActionListener(e -> {

    try {

        table.print();

    } catch (Exception ex) {

        JOptionPane.showMessageDialog(
                frame,
                ex.toString()
        );
    }
});

    try {

        Connection con =
                DBConnection.createConnection();

        Statement st =
                con.createStatement();

        ResultSet rs =
                st.executeQuery("SELECT * FROM expenses");

        while (rs.next()) {

            model.addRow(new Object[]{

                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getDouble("amount"),
                    rs.getString("category"),
                    rs.getDate("expense_date")
            });
        }

    } catch (Exception e) {

        JOptionPane.showMessageDialog(
                frame,
                e.toString()
        );
    }

    JScrollPane scrollPane =
            new JScrollPane(table);

           JPanel bottomPanel = new JPanel();

bottomPanel.add(printBtn);

contentPanel.add(
        bottomPanel,
        BorderLayout.SOUTH
);

    contentPanel.add(
            scrollPane,
            BorderLayout.CENTER
    );

    contentPanel.revalidate();
    contentPanel.repaint();
}

public void searchExpense() {

    contentPanel.removeAll();

    JPanel panel = new JPanel();
    panel.setLayout(null);

    JLabel categoryLabel =
            new JLabel("Category:");

    categoryLabel.setBounds(
            100, 50, 100, 30);

    JTextField categoryField =
            new JTextField();

    categoryField.setBounds(
            220, 50, 200, 30);

    JButton searchBtn =
            new JButton("Search");

    searchBtn.setBounds(
            450, 50, 120, 30);

    JTextArea resultArea =
            new JTextArea();
            resultArea.setFont(new Font("Arial", Font.PLAIN, 15));

    JScrollPane scrollPane =
            new JScrollPane(resultArea);

    scrollPane.setBounds(
            100, 100, 700, 350);

    searchBtn.addActionListener(e -> {

        try {

            Connection con =
                    DBConnection.createConnection();

            String query =
                    "SELECT * FROM expenses WHERE category=?";

            PreparedStatement ps =
                    con.prepareStatement(query);

            ps.setString(
                    1,
                    categoryField.getText()
            );

            ResultSet rs =
                    ps.executeQuery();

            resultArea.setText("");

            while (rs.next()) {

                resultArea.append(
                        "ID: " +
                        rs.getInt("id") +
                        "\nTitle: " +
                        rs.getString("title") +
                        "\nAmount: " +
                        rs.getDouble("amount") +
                        "\nCategory: " +
                        rs.getString("category") +
                        "\nDate: " +
                        rs.getDate("expense_date") +
                        "\n----------------------\n"
                );
            }

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    frame,
                    ex.toString()
            );
        }
    });

    panel.add(categoryLabel);
    panel.add(categoryField);
    panel.add(searchBtn);
    panel.add(scrollPane);

    contentPanel.add(
            panel,
            BorderLayout.CENTER
    );

    contentPanel.revalidate();
    contentPanel.repaint();
}

public void deleteExpense() {

    contentPanel.removeAll();

    JPanel panel = new JPanel();
    panel.setLayout(null);

   JLabel idLabel =
        new JLabel("Expense ID:");

idLabel.setBounds(
        300, 120, 180, 30);
        idLabel.setFont(new Font("Arial", Font.BOLD, 18));

JTextField idField =
        new JTextField();

idField.setBounds(
        450, 120, 220, 30);

JButton deleteBtn =
        new JButton("Delete");

deleteBtn.setBounds(
        500, 190, 120, 40);

deleteBtn.setBackground(new Color(220, 53, 69));
deleteBtn.setForeground(Color.WHITE);
deleteBtn.setFocusPainted(false);

    deleteBtn.addActionListener(e -> {

        int choice = JOptionPane.showConfirmDialog(
        frame,
        "Are you sure you want to delete this expense?",
        "Confirm Delete",
        JOptionPane.YES_NO_OPTION
);

if(choice != JOptionPane.YES_OPTION) {
    return;
}

        try {

            Connection con =
                    DBConnection.createConnection();

            String query =
                    "DELETE FROM expenses WHERE id=?";

            PreparedStatement ps =
                    con.prepareStatement(query);

            ps.setInt(
                    1,
                    Integer.parseInt(
                            idField.getText()
                    )
            );

            int rows =
                    ps.executeUpdate();

            if(rows > 0) {

                JOptionPane.showMessageDialog(
                        frame,
                        "Expense Deleted Successfully!"
                ); 

                idField.setText("");

            } else {

                JOptionPane.showMessageDialog(
                        frame,
                        "Expense ID Not Found!"
                );
            }

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    frame,
                    ex.toString()
            );
        }
    });

    panel.add(idLabel);
    panel.add(idField);
    panel.add(deleteBtn);

    contentPanel.add(
            panel,
            BorderLayout.CENTER
    );

    contentPanel.revalidate();
    contentPanel.repaint();
}

public void totalExpense() {

    contentPanel.removeAll();

    JLabel totalLabel = new JLabel(
            "Calculating Total...",
            SwingConstants.CENTER
    );

    totalLabel.setFont(
            new Font("Arial", Font.BOLD, 24)
    );

    try {

        Connection con =
                DBConnection.createConnection();

        Statement st =
                con.createStatement();

        ResultSet rs =
                st.executeQuery(
                        "SELECT SUM(amount) AS total FROM expenses"
                );

        if(rs.next()) {

            double total =
                    rs.getDouble("total");

            totalLabel.setText(
                    "Total Expense = ₹ " + total
            );
        }

    } catch(Exception e) {

        totalLabel.setText(
                "Error : " + e.toString()
        );
    }

    contentPanel.add(
            totalLabel,
            BorderLayout.CENTER
    );

    contentPanel.revalidate();
    contentPanel.repaint();
}

public void monthlyReport() {

    contentPanel.removeAll();

    JPanel panel = new JPanel();
    panel.setLayout(null);

    JLabel monthLabel = new JLabel("Month:");
    monthLabel.setBounds(100, 50, 100, 30);

    JTextField monthField = new JTextField();
    monthField.setBounds(220, 50, 150, 30);

    JLabel yearLabel = new JLabel("Year:");
    yearLabel.setBounds(100, 100, 100, 30);

    JTextField yearField = new JTextField();
    yearField.setBounds(220, 100, 150, 30);

    JButton reportBtn = new JButton("Generate Report");
    reportBtn.setBounds(220, 160, 180, 35);

    JTextArea reportArea = new JTextArea();
    reportArea.setFont(
        new Font("Monospaced", Font.PLAIN, 16)
);
    JScrollPane scrollPane =
            new JScrollPane(reportArea);

    scrollPane.setBounds(
            100, 220, 900, 400);

            reportBtn.addActionListener(e -> {

    try {

        Connection con =
                DBConnection.createConnection();

        String query =
                "SELECT category, SUM(amount) total FROM expenses " +
                "WHERE MONTH(expense_date)=? " +
                "AND YEAR(expense_date)=? " +
                "GROUP BY category";

        PreparedStatement ps =
                con.prepareStatement(query);

        ps.setInt(
                1,
                Integer.parseInt(
                        monthField.getText()
                )
        );

        ps.setInt(
                2,
                Integer.parseInt(
                        yearField.getText()
                )
        );

        ResultSet rs =
                ps.executeQuery();

        reportArea.setText("");

reportArea.append(
    "===== MONTHLY EXPENSE REPORT =====\n\n"
);

reportArea.append(
    "Month : " + monthField.getText() + "\n"
);

reportArea.append(
    "Year  : " + yearField.getText() + "\n\n"
);

reportArea.append(
    String.format(
        "%-15s %s\n",
        "Category",
        "Amount"
    )
);

reportArea.append(
    "---------------------------------------------\n"
);

        double grandTotal = 0;

        while (rs.next()) {

            String category =
                    rs.getString("category");

            double total =
                    rs.getDouble("total");

            grandTotal += total;

            reportArea.append(
        String.format(
                "%-15s ₹%.2f\n",
                category,
                total
        )
);
        }

        reportArea.append(
        "\n-----------------------------------------------------------\n"
);

reportArea.append(
    String.format(
        "TOTAL EXPENSE = ₹%.2f",
        grandTotal
    )
);

    } catch (Exception ex) {

        JOptionPane.showMessageDialog(
                frame,
                ex.toString()
        );
    }
});

    panel.add(monthLabel);
    panel.add(monthField);

    panel.add(yearLabel);
    panel.add(yearField);

    panel.add(reportBtn);
    panel.add(scrollPane);

    contentPanel.add(
            panel,
            BorderLayout.CENTER
    );

    contentPanel.revalidate();
    contentPanel.repaint();
}

public void updateExpense() {

    contentPanel.removeAll();

    JPanel panel = new JPanel();
    panel.setLayout(null);

    JLabel idLabel = new JLabel("Expense ID:");
    idLabel.setBounds(320, 120, 100, 30);

    JTextField idField = new JTextField();
    idField.setBounds(470, 120, 200, 30);

    JLabel titleLabel = new JLabel("New Title:");
    titleLabel.setBounds(320, 170, 100, 30);

    JTextField titleField = new JTextField();
    titleField.setBounds(470, 170, 200, 30);

    JLabel amountLabel = new JLabel("New Amount:");
    amountLabel.setBounds(320, 220, 100, 30);

    JTextField amountField = new JTextField();
    amountField.setBounds(470, 220, 200, 30);

    JLabel categoryLabel = new JLabel("New Category:");
    categoryLabel.setBounds(320, 270, 100, 30);

    JTextField categoryField = new JTextField();
    categoryField.setBounds(470, 270, 200, 30);

    JButton updateBtn = new JButton("Update");
    updateBtn.setBounds(470, 350, 120, 40);

    updateBtn.addActionListener(e -> {

        try {

            Connection con =
                    DBConnection.createConnection();

            String query =
                    "UPDATE expenses SET title=?, amount=?, category=? WHERE id=?";

            PreparedStatement ps =
                    con.prepareStatement(query);

            ps.setString(1, titleField.getText());

            ps.setDouble(
                    2,
                    Double.parseDouble(
                            amountField.getText()
                    )
            );

            ps.setString(
                    3,
                    categoryField.getText()
            );

            ps.setInt(
                    4,
                    Integer.parseInt(
                            idField.getText()
                    )
            );

            int rows =
                    ps.executeUpdate();

            if(rows > 0) {

                JOptionPane.showMessageDialog(
                        frame,
                        "Expense Updated Successfully!"
                );

            } else {

                JOptionPane.showMessageDialog(
                        frame,
                        "Expense ID Not Found!"
                );
            }

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    frame,
                    ex.toString()
            );
        }
    });

    panel.add(idLabel);
    panel.add(idField);

    panel.add(titleLabel);
    panel.add(titleField);

    panel.add(amountLabel);
    panel.add(amountField);

    panel.add(categoryLabel);
    panel.add(categoryField);

    panel.add(updateBtn);

    contentPanel.add(
            panel,
            BorderLayout.CENTER
    );

    contentPanel.revalidate();
    contentPanel.repaint();
}
    public static void main(String[] args) {

        new MainGUI();
    }
}