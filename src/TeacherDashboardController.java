package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Student;
import model.Timetable;
import dao.StudentDAO;
import dao.TimetableDAO;
import util.DBUtil;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import java.io.IOException;
import model.Mark;
import dao.MarkDAO;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import javafx.scene.control.TableCell;
import javafx.scene.layout.HBox;
import java.util.Map;
import java.util.stream.Collectors;
import util.SessionManager;
import model.Subject;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.Button;

public class TeacherDashboardController {

    // UI Elements
    @FXML private Label welcomeLabel;
    @FXML private Button manageClassesButton;
    @FXML private Button gradeSubmissionButton;
    @FXML private Button feedbackButton;
    @FXML private Button logoutButton;
    @FXML private Button viewStudentsButton;
    @FXML private Button viewTimetableButton;
    @FXML private TableColumn<Timetable, String> roomCol;
    @FXML private TextField studentIdField;
    @FXML private ComboBox<Subject> subjectComboBox;
    @FXML private TextField marksField;
    @FXML private Button submitButton;
    @FXML private Label alertLabel;

    // Initialization
    public void initialize() {
        welcomeLabel.setText("Welcome, Teacher!");
    }

    // ======================= BUTTON HANDLERS =======================

    @FXML
    private void handleManageClasses(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/timetable_view.fxml"));
            Parent root = loader.load();

            TimetableViewController controller = loader.getController();
            controller.loadTimetables();

            Stage stage = new Stage();
            stage.setTitle("Manage Classes - Timetable");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Failed to open Manage Classes window.");
        }
    }

    @FXML
    private void handleGradeSubmission(ActionEvent event) {
        openSubmitMarksWindow();
    }

    @FXML
    private void handleFeedback(ActionEvent event) {
        openFeedbackWindow();
    }

    @FXML
    private void handleLogout(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) logoutButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Login - Academix");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error loading login page.");
        }
    }

    @FXML
    private void handleViewTimetable(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/timetable_view.fxml"));
            Parent root = loader.load();
            TimetableViewController controller = loader.getController();
            controller.loadTimetables();
            Stage stage = new Stage();
            stage.setTitle("Class Timetable");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Failed to open Timetable window.");
        }
    }

    @FXML
    private void handleViewStudents(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/view_students.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Student List");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Failed to open Student List window.");
        }
    }

    // Open Submit Marks & Feedback window
    private void openSubmitMarksWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/submit_marks.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Submit Marks & Feedback");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Failed to open Submit Marks & Feedback window.");
        }
    }

    private void openFeedbackWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/submit_feedback.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Submit Feedback");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Failed to open Feedback window.");
        }
    }

    // ======================= HOVER STYLES =======================

    @FXML
    private void onHoverIn(MouseEvent event) {
        Button button = (Button) event.getSource();
        button.setStyle("-fx-background-color: #333333; -fx-text-fill: white; -fx-font-size: 16px; -fx-background-radius: 12;");
    }

    @FXML
    private void onHoverOut(MouseEvent event) {
        Button button = (Button) event.getSource();
        button.setStyle("-fx-background-color: #2980B9; -fx-text-fill: white; -fx-font-size: 16px; -fx-background-radius: 12;");
    }

    // ======================= UTIL =======================

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Academix Error");
        alert.setHeaderText("Oops! Something went wrong 😢");
        alert.setContentText(message);
        alert.getDialogPane().setStyle("-fx-font-size: 16px; -fx-font-family: 'Segoe UI', 'Arial';");
        alert.showAndWait();
    }

    @FXML
    public void submitMarks() {
        String studentIdText = studentIdField.getText().trim();
        Subject subject = subjectComboBox.getValue();
        String marksText = marksField.getText().trim();

        if (studentIdText.isEmpty() || subject == null || marksText.isEmpty()) {
            showError("Please fill all fields.");
            return;
        }

        int studentId, subjectId, marks;
        try {
            studentId = Integer.parseInt(studentIdText);
            subjectId = subject.getId();
            marks = Integer.parseInt(marksText);
        } catch (NumberFormatException e) {
            showError("Invalid input. IDs and marks must be numbers.");
            return;
        }

        // Validate marks range
        if (marks < 0 || marks > 100) {
            showError("Marks must be between 0 and 100.");
            return;
        }

        // Check if marks already exist for this student and subject
        if (MarkDAO.marksExist(studentId, subjectId)) {
            Alert confirmUpdate = new Alert(Alert.AlertType.CONFIRMATION);
            confirmUpdate.setTitle("Update Marks");
            confirmUpdate.setHeaderText("Marks already exist");
            confirmUpdate.setContentText("Marks already exist for this student in this subject. Would you like to update them?");
            
            if (confirmUpdate.showAndWait().get() == ButtonType.OK) {
                // User confirmed update
                updateMarks(studentId, subjectId, marks);
            }
        } else {
            // Insert new marks
            insertNewMarks(studentId, subjectId, marks);
        }
    }

    private void insertNewMarks(int studentId, int subjectId, int marks) {
        String grade = calculateGrade(marks);
        Mark mark = new Mark(studentId, subjectId, marks, grade, null);
        boolean success = MarkDAO.insertMarks(mark);

        if (success) {
            showSuccess("Marks submitted successfully!");
            clearFields();
        } else {
            showError("Failed to submit marks. Please try again.");
        }
    }

    private void updateMarks(int studentId, int subjectId, int marks) {
        String grade = calculateGrade(marks);
        Mark mark = new Mark(studentId, subjectId, marks, grade, null);
        boolean success = MarkDAO.updateMark(mark);

        if (success) {
            showSuccess("Marks updated successfully!");
            clearFields();
        } else {
            showError("Failed to update marks. Please try again.");
        }
    }

    private void showError(String message) {
        // Show error alert
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("❌ Operation Failed");
        alert.setContentText(message);
        
        // Style the alert dialog
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setStyle(
            "-fx-background-color: #232526;" +
            "-fx-border-color: #ff3333;" +
            "-fx-border-width: 2px;" +
            "-fx-border-radius: 5px;" +
            "-fx-background-radius: 5px;"
        );
        
        // Style the header
        Label headerLabel = (Label) dialogPane.lookup(".header-panel .label");
        if (headerLabel != null) {
            headerLabel.setStyle(
                "-fx-text-fill: #ff3333;" +
                "-fx-font-size: 18px;" +
                "-fx-font-weight: bold;" +
                "-fx-font-family: 'Segoe UI';"
            );
        }
        
        // Style the content
        Label contentLabel = (Label) dialogPane.lookup(".content.label");
        if (contentLabel != null) {
            contentLabel.setStyle(
                "-fx-text-fill: white;" +
                "-fx-font-size: 14px;" +
                "-fx-font-family: 'Segoe UI';"
            );
        }
        
        // Style the buttons
        dialogPane.lookupAll(".button").forEach(node -> {
            ((Button) node).setStyle(
                "-fx-background-color: #ff3333;" +
                "-fx-text-fill: white;" +
                "-fx-font-size: 14px;" +
                "-fx-background-radius: 5px;" +
                "-fx-cursor: hand;"
            );
        });

        alert.showAndWait();

        // Also update the label for persistent feedback
        alertLabel.setText(message);
        alertLabel.setStyle("-fx-text-fill: #ff3333; -fx-font-size: 15px; -fx-background-color: #232526; -fx-padding: 6 0; -fx-background-radius: 8;");
        alertLabel.setVisible(true);
    }

    private void showSuccess(String message) {
        // Show success alert
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("✅ Operation Successful!");
        alert.setContentText(message);
        
        // Style the alert dialog
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setStyle(
            "-fx-background-color: #232526;" +
            "-fx-border-color: #00c6ff;" +
            "-fx-border-width: 2px;" +
            "-fx-border-radius: 5px;" +
            "-fx-background-radius: 5px;"
        );
        
        // Style the header
        Label headerLabel = (Label) dialogPane.lookup(".header-panel .label");
        if (headerLabel != null) {
            headerLabel.setStyle(
                "-fx-text-fill: #00c6ff;" +
                "-fx-font-size: 18px;" +
                "-fx-font-weight: bold;" +
                "-fx-font-family: 'Segoe UI';"
            );
        }
        
        // Style the content
        Label contentLabel = (Label) dialogPane.lookup(".content.label");
        if (contentLabel != null) {
            contentLabel.setStyle(
                "-fx-text-fill: white;" +
                "-fx-font-size: 14px;" +
                "-fx-font-family: 'Segoe UI';"
            );
        }
        
        // Style the buttons
        dialogPane.lookupAll(".button").forEach(node -> {
            ((Button) node).setStyle(
                "-fx-background-color: #00c6ff;" +
                "-fx-text-fill: white;" +
                "-fx-font-size: 14px;" +
                "-fx-background-radius: 5px;" +
                "-fx-cursor: hand;"
            );
        });

        alert.showAndWait();

        // Also update the label for persistent feedback
        alertLabel.setText(message);
        alertLabel.setStyle("-fx-text-fill: #00c6ff; -fx-font-size: 15px; -fx-background-color: #232526; -fx-padding: 6 0; -fx-background-radius: 8;");
        alertLabel.setVisible(true);
    }

    private void clearFields() {
        studentIdField.clear();
        subjectComboBox.setValue(null);
        marksField.clear();
    }

    private String calculateGrade(int marks) {
        if (marks >= 90) return "A+";
        if (marks >= 80) return "A";
        if (marks >= 70) return "B";
        if (marks >= 60) return "C";
        if (marks >= 50) return "D";
        return "F";
    }
}
