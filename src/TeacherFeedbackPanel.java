package com.feedback.gui;

import com.feedback.model.Feedback;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class TeacherFeedbackPanel extends JPanel {
    private static final Color DARK_BACKGROUND = new Color(33, 33, 33);
    private static final Color DARKER_BACKGROUND = new Color(23, 23, 23);
    private static final Color TEXT_COLOR = new Color(240, 240, 240);
    private static final Color ACCENT_COLOR = new Color(75, 110, 175);

    private JTextField studentNameField;
    private JTextArea feedbackArea;
    private JTextField teacherNameField;
    private List<Feedback> feedbackList;

    public TeacherFeedbackPanel() {
        feedbackList = new ArrayList<>();
        setupUI();
    }

    private void setupUI() {
        setLayout(new BorderLayout(10, 10));
        setBackground(DARK_BACKGROUND);

        // Header Panel
        JPanel headerPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        headerPanel.setBackground(DARK_BACKGROUND);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel studentLabel = createLabel("Student Name:");
        studentNameField = createTextField();
        JLabel teacherLabel = createLabel("Teacher Name:");
        teacherNameField = createTextField();

        headerPanel.add(studentLabel);
        headerPanel.add(studentNameField);
        headerPanel.add(teacherLabel);
        headerPanel.add(teacherNameField);

        // Feedback Area
        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.setBackground(DARK_BACKGROUND);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel feedbackLabel = createLabel("Feedback:");
        feedbackArea = createTextArea();
        JScrollPane scrollPane = new JScrollPane(feedbackArea);
        scrollPane.setBackground(DARKER_BACKGROUND);
        scrollPane.setBorder(BorderFactory.createLineBorder(ACCENT_COLOR));

        centerPanel.add(feedbackLabel, BorderLayout.NORTH);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(DARK_BACKGROUND);
        
        JButton submitButton = createButton("Submit Feedback");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                submitFeedback();
            }
        });
        
        JButton clearButton = createButton("Clear");
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearFields();
            }
        });

        buttonPanel.add(clearButton);
        buttonPanel.add(submitButton);

        // Add all panels to main panel
        add(headerPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(TEXT_COLOR);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        return label;
    }

    private JTextField createTextField() {
        JTextField field = new JTextField();
        field.setBackground(DARKER_BACKGROUND);
        field.setForeground(TEXT_COLOR);
        field.setCaretColor(TEXT_COLOR);
        field.setBorder(BorderFactory.createLineBorder(ACCENT_COLOR));
        field.setFont(new Font("Arial", Font.PLAIN, 14));
        return field;
    }

    private JTextArea createTextArea() {
        JTextArea area = new JTextArea();
        area.setBackground(DARKER_BACKGROUND);
        area.setForeground(TEXT_COLOR);
        area.setCaretColor(TEXT_COLOR);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setFont(new Font("Arial", Font.PLAIN, 14));
        return area;
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(ACCENT_COLOR);
        button.setForeground(TEXT_COLOR);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        return button;
    }

    private void submitFeedback() {
        String studentName = studentNameField.getText().trim();
        String teacherName = teacherNameField.getText().trim();
        String feedbackContent = feedbackArea.getText().trim();

        if (studentName.isEmpty() || teacherName.isEmpty() || feedbackContent.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Please fill in all fields",
                "Incomplete Information",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        Feedback feedback = new Feedback(studentName, teacherName, feedbackContent);
        feedbackList.add(feedback);
        
        JOptionPane.showMessageDialog(this,
            "Feedback submitted successfully!",
            "Success",
            JOptionPane.INFORMATION_MESSAGE);
            
        clearFields();
    }

    private void clearFields() {
        studentNameField.setText("");
        teacherNameField.setText("");
        feedbackArea.setText("");
    }

    public List<Feedback> getFeedbackList() {
        return feedbackList;
    }
} 