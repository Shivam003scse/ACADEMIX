package com.feedback;

import com.feedback.gui.TeacherFeedbackPanel;
import com.feedback.gui.FeedbackViewPanel;
import javax.swing.*;
import java.awt.*;

public class FeedbackSystem {
    private JFrame mainFrame;
    private TeacherFeedbackPanel teacherPanel;
    private FeedbackViewPanel viewPanel;
    private JTabbedPane tabbedPane;

    public FeedbackSystem() {
        setupUI();
    }

    private void setupUI() {
        try {
            // Set system look and feel
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Create main components
        mainFrame = new JFrame("School Feedback System");
        teacherPanel = new TeacherFeedbackPanel();
        viewPanel = new FeedbackViewPanel(teacherPanel.getFeedbackList());

        // Create tabbed pane with dark theme
        tabbedPane = new JTabbedPane();
        tabbedPane.setBackground(new Color(33, 33, 33));
        tabbedPane.setForeground(new Color(240, 240, 240));
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 14));

        // Add tabs
        tabbedPane.addTab("Teacher Feedback", new ImageIcon(), teacherPanel, "Write feedback for students");
        tabbedPane.addTab("View Feedback", new ImageIcon(), viewPanel, "View student feedback");

        // Setup main frame
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.getContentPane().add(tabbedPane);
        mainFrame.setSize(800, 600);
        mainFrame.setLocationRelativeTo(null);
    }

    public void show() {
        mainFrame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FeedbackSystem system = new FeedbackSystem();
            system.show();
        });
    }
} 