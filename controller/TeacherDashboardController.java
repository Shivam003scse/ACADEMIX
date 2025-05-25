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

import java.io.IOException;

public class TeacherDashboardController {

    // UI Elements
    @FXML private Label welcomeLabel;
    @FXML private Button manageClassesButton;
    @FXML private Button gradeSubmissionButton;
    @FXML private Button feedbackButton;
    @FXML private Button logoutButton;

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
        openSubmitMarksWindow();
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
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Academix");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
