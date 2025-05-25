package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class StudentDashboardController {

    @FXML private Label welcomeLabel;
    @FXML private Button viewGradesButton;
    @FXML private Button feedbackButton;
    @FXML private Button logoutButton;

    public void initialize() {
        welcomeLabel.setText("Welcome, Student!");
    }

    @FXML
    private void handleViewGrades(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/StudentGrades.fxml"));
            Parent root = loader.load();

            // Pass student email (replace with actual session logic)
            StudentGradesController controller = loader.getController();
            controller.setStudentEmail("guvi.24scse@gmail.com");

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Your Grades - Academix");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Unable to load grades page.");
        }
    }

    @FXML
    private void handleFeedback(ActionEvent event) {
        showAlert("Feedback feature is under development.");
    }

    @FXML
    private void handleLogout(ActionEvent event) {
        try {
            // Load the Login.fxml from resources/fxml/Login.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
            Parent root = loader.load();

            // Get current stage from the logout button (any node works)
            Stage stage = (Stage) logoutButton.getScene().getWindow();

            // Set new scene to the stage
            stage.setScene(new Scene(root));
            stage.setTitle("Login - Academix");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Failed to load Login page.");
        }
    }

    @FXML
    private void onHoverIn(MouseEvent event) {
        Button btn = (Button) event.getSource();
        if (btn == viewGradesButton) {
            btn.setStyle("-fx-background-color: #2980B9; -fx-text-fill: white; -fx-font-size: 16px; -fx-background-radius: 12;");
        } else if (btn == feedbackButton) {
            btn.setStyle("-fx-background-color: #16A085; -fx-text-fill: white; -fx-font-size: 16px; -fx-background-radius: 12;");
        } else if (btn == logoutButton) {
            btn.setStyle("-fx-background-color: #C0392B; -fx-text-fill: white; -fx-font-size: 16px; -fx-background-radius: 12;");
        }
    }

    @FXML
    private void onHoverOut(MouseEvent event) {
        Button btn = (Button) event.getSource();
        if (btn == viewGradesButton) {
            btn.setStyle("-fx-background-color: #3498DB; -fx-text-fill: white; -fx-font-size: 16px; -fx-background-radius: 12;");
        } else if (btn == feedbackButton) {
            btn.setStyle("-fx-background-color: #1ABC9C; -fx-text-fill: white; -fx-font-size: 16px; -fx-background-radius: 12;");
        } else if (btn == logoutButton) {
            btn.setStyle("-fx-background-color: #E74C3C; -fx-text-fill: white; -fx-font-size: 16px; -fx-background-radius: 12;");
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Academix");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
