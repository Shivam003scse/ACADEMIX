package com.feedback.gui;

import com.feedback.model.Feedback;
import javax.swing.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class FeedbackViewPanel extends JPanel {
    private static final Color DARK_BACKGROUND = new Color(33, 33, 33);
    private static final Color DARKER_BACKGROUND = new Color(23, 23, 23);
    private static final Color TEXT_COLOR = new Color(240, 240, 240);
    private static final Color ACCENT_COLOR = new Color(75, 110, 175);

    private JTextField searchField;
    private JPanel feedbackListPanel;
    private List<Feedback> feedbackList;
    private DateTimeFormatter dateFormatter;

    public FeedbackViewPanel(List<Feedback> feedbackList) {
        this.feedbackList = feedbackList;
        this.dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        setupUI();
    }

    private void setupUI() {
        setLayout(new BorderLayout(10, 10));
        setBackground(DARK_BACKGROUND);

        // Search Panel
        JPanel searchPanel = new JPanel(new BorderLayout(10, 10));
        searchPanel.setBackground(DARK_BACKGROUND);
        searchPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel searchLabel = createLabel("Search by Student Name:");
        searchField = createTextField();
        searchField.addActionListener(e -> updateFeedbackList());

        searchPanel.add(searchLabel, BorderLayout.WEST);
        searchPanel.add(searchField, BorderLayout.CENTER);

        // Feedback List Panel
        feedbackListPanel = new JPanel();
        feedbackListPanel.setLayout(new BoxLayout(feedbackListPanel, BoxLayout.Y_AXIS));
        feedbackListPanel.setBackground(DARK_BACKGROUND);

        JScrollPane scrollPane = new JScrollPane(feedbackListPanel);
        scrollPane.setBackground(DARKER_BACKGROUND);
        scrollPane.setBorder(BorderFactory.createLineBorder(ACCENT_COLOR));
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        // Add components to main panel
        add(searchPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        updateFeedbackList();
    }

    private void updateFeedbackList() {
        feedbackListPanel.removeAll();
        String searchTerm = searchField.getText().toLowerCase().trim();

        for (Feedback feedback : feedbackList) {
            if (searchTerm.isEmpty() || 
                feedback.getStudentName().toLowerCase().contains(searchTerm)) {
                feedbackListPanel.add(createFeedbackCard(feedback));
                feedbackListPanel.add(Box.createRigidArea(new Dimension(0, 10)));
            }
        }

        feedbackListPanel.revalidate();
        feedbackListPanel.repaint();
    }

    private JPanel createFeedbackCard(Feedback feedback) {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout(5, 5));
        card.setBackground(DARKER_BACKGROUND);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(ACCENT_COLOR),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        // Header Panel
        JPanel headerPanel = new JPanel(new GridLayout(2, 1));
        headerPanel.setBackground(DARKER_BACKGROUND);

        JLabel studentLabel = createLabel("Student: " + feedback.getStudentName());
        JLabel teacherLabel = createLabel("Teacher: " + feedback.getTeacherName());
        headerPanel.add(studentLabel);
        headerPanel.add(teacherLabel);

        // Content Panel
        JTextArea contentArea = new JTextArea(feedback.getFeedbackContent());
        contentArea.setBackground(DARKER_BACKGROUND);
        contentArea.setForeground(TEXT_COLOR);
        contentArea.setLineWrap(true);
        contentArea.setWrapStyleWord(true);
        contentArea.setEditable(false);
        contentArea.setFont(new Font("Arial", Font.PLAIN, 14));

        // Footer Panel
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        footerPanel.setBackground(DARKER_BACKGROUND);
        JLabel dateLabel = createLabel("Date: " + 
            feedback.getTimestamp().format(dateFormatter));
        footerPanel.add(dateLabel);

        // Add components to card
        card.add(headerPanel, BorderLayout.NORTH);
        card.add(contentArea, BorderLayout.CENTER);
        card.add(footerPanel, BorderLayout.SOUTH);

        return card;
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
} 