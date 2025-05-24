package ui;

import dao.StudentDAO;
import model.Student;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class StudentForm extends JFrame {

    private JTextField nameField, emailField, phoneField;
    private JButton addButton, showAllButton;
    private JTable studentTable;
    private DefaultTableModel tableModel;
    private StudentDAO studentDAO;

    public StudentForm() {
        studentDAO = new StudentDAO();
        initUI();
    }

    private void initUI() {
        setTitle("Student Registration");
        setSize(700, 450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(15, 15));
        getContentPane().setBackground(Color.WHITE);

        Font labelFont = new Font("Segoe UI", Font.PLAIN, 14);

        // Form Panel
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));
        formPanel.setBackground(Color.WHITE);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setFont(labelFont);
        formPanel.add(nameLabel);
        nameField = new JTextField();
        formPanel.add(nameField);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(labelFont);
        formPanel.add(emailLabel);
        emailField = new JTextField();
        formPanel.add(emailField);

        JLabel phoneLabel = new JLabel("Phone:");
        phoneLabel.setFont(labelFont);
        formPanel.add(phoneLabel);
        phoneField = new JTextField();
        formPanel.add(phoneField);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setBackground(Color.WHITE);

        addButton = new JButton("Add Student");
        styleButton(addButton, new Color(70, 130, 180)); // Steel Blue
        buttonPanel.add(addButton);

        showAllButton = new JButton("Show All Students");
        styleButton(showAllButton, new Color(60, 179, 113)); // Medium Sea Green
        buttonPanel.add(showAllButton);

        formPanel.add(buttonPanel);
        formPanel.add(new JLabel()); // Empty cell

        add(formPanel, BorderLayout.NORTH);

        // Table for students
        String[] columns = {"ID", "Name", "Email", "Phone"};
        tableModel = new DefaultTableModel(columns, 0);
        studentTable = new JTable(tableModel);
        studentTable.setRowHeight(22);
        studentTable.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        studentTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        JScrollPane scrollPane = new JScrollPane(studentTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Registered Students"));
        add(scrollPane, BorderLayout.CENTER);

        // Event Handlers
        addButton.addActionListener(this::handleAddStudent);
        showAllButton.addActionListener(e -> loadAllStudents());
    }

    private void styleButton(JButton button, Color bgColor) {
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setContentAreaFilled(true);
        button.setOpaque(true);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    }

    private void handleAddStudent(ActionEvent e) {
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String phone = phoneField.getText().trim();

        if (name.isEmpty() || email.isEmpty() || phone.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Student student = new Student(name, email, phone);
        boolean success = studentDAO.addStudent(student);

        if (success) {
            JOptionPane.showMessageDialog(this, "Student added successfully!");
            nameField.setText("");
            emailField.setText("");
            phoneField.setText("");
            loadAllStudents();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to add student", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadAllStudents() {
        List<Student> students = studentDAO.getAllStudents();
        tableModel.setRowCount(0);

        for (Student s : students) {
            Object[] row = {s.getId(), s.getName(), s.getEmail(), s.getPhone()};
            tableModel.addRow(row);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            StudentForm sf = new StudentForm();
            sf.setVisible(true);
        });
    }
}
