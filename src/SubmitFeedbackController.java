package controller;

import dao.StudentDAO;
import dao.FeedbackDAO;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Student;
import javafx.scene.paint.Color;
import javafx.util.StringConverter;

import java.util.List;

public class SubmitFeedbackController {
    @FXML private ComboBox<Student> studentComboBox;
    @FXML private TextArea feedbackArea;
    @FXML private Label alertLabel;
    @FXML private Button submitButton;
    @FXML private Button cancelButton;

    @FXML
    public void initialize() {
        // Set up student combo box
        List<Student> students = StudentDAO.getAllStudents();
        studentComboBox.setItems(FXCollections.observableArrayList(students));
        
        // Custom converter to display student names nicely
        studentComboBox.setConverter(new StringConverter<Student>() {
            @Override
            public String toString(Student student) {
                return student == null ? "" : student.getName() + " (" + student.getRollNumber() + ")";
            }

            @Override
            public Student fromString(String string) {
                return null;
            }
        });

        // Style the combo box list cells
        studentComboBox.setCellFactory(lv -> new ListCell<Student>() {
            @Override
            protected void updateItem(Student student, boolean empty) {
                super.updateItem(student, empty);
                if (empty || student == null) {
                    setText(null);
                } else {
                    setText(student.getName() + " (" + student.getRollNumber() + ")");
                    setStyle("-fx-text-fill: #00c6ff; -fx-background-color: #232526;");
                }
            }
        });

        // Initial setup
        alertLabel.setVisible(false);
        setupHoverEffects();
    }

    private void setupHoverEffects() {
        // Submit button hover effect
        submitButton.setOnMouseEntered(e -> 
            submitButton.setStyle("-fx-background-color: #00b3e6; -fx-text-fill: #181c24; -fx-background-radius: 12; -fx-font-size: 15px; -fx-font-family: 'Segoe UI Semibold'; -fx-padding: 12 32; -fx-cursor: hand;"));
        submitButton.setOnMouseExited(e -> 
            submitButton.setStyle("-fx-background-color: #00c6ff; -fx-text-fill: #181c24; -fx-background-radius: 12; -fx-font-size: 15px; -fx-font-family: 'Segoe UI Semibold'; -fx-padding: 12 32; -fx-cursor: hand;"));

        // Cancel button hover effect
        cancelButton.setOnMouseEntered(e -> 
            cancelButton.setStyle("-fx-background-color: #232526; -fx-text-fill: #00c6ff; -fx-background-radius: 12; -fx-font-size: 15px; -fx-font-family: 'Segoe UI Semibold'; -fx-padding: 12 32; -fx-cursor: hand; -fx-border-color: #00c6ff44; -fx-border-width: 1; -fx-border-radius: 12;"));
        cancelButton.setOnMouseExited(e -> 
            cancelButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #00c6ff; -fx-background-radius: 12; -fx-font-size: 15px; -fx-font-family: 'Segoe UI Semibold'; -fx-padding: 12 32; -fx-cursor: hand; -fx-border-color: #00c6ff44; -fx-border-width: 1; -fx-border-radius: 12;"));
    }

    @FXML
    private void handleSubmit() {
        Student student = studentComboBox.getValue();
        String feedback = feedbackArea.getText().trim();
        
        if (student == null) {
            showAlert("Please select a student.");
            return;
        }
        if (feedback.isEmpty()) {
            showAlert("Feedback cannot be empty.");
            return;
        }
        
        boolean success = FeedbackDAO.submitFeedback(student.getId(), feedback);
        if (success) {
            showAlert("✅ Feedback submitted successfully!", true);
        } else {
            showAlert("❌ Failed to submit feedback.");
        }
    }

    @FXML
    private void handleCancel() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String msg) {
        showAlert(msg, false);
    }
    
    private void showAlert(String msg, boolean closeOnSuccess) {
        alertLabel.setText(msg);
        alertLabel.setVisible(true);
        
        // Style the alert based on success/failure
        if (closeOnSuccess) {
            alertLabel.setStyle("-fx-font-size: 14px; -fx-font-family: 'Segoe UI'; -fx-text-fill: #00ff88; -fx-background-color: #232526; -fx-padding: 8 16; -fx-background-radius: 8;");
        } else {
            alertLabel.setStyle("-fx-font-size: 14px; -fx-font-family: 'Segoe UI'; -fx-text-fill: #ff5252; -fx-background-color: #232526; -fx-padding: 8 16; -fx-background-radius: 8;");
        }
        
        if (closeOnSuccess) {
            new Thread(() -> {
                try { Thread.sleep(1200); } catch (InterruptedException ignored) {}
                javafx.application.Platform.runLater(() -> {
                    Stage stage = (Stage) submitButton.getScene().getWindow();
                    stage.close();
                });
            }).start();
        }
    }
} 