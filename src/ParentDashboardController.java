package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import java.io.IOException;
import util.SessionManager;

public class ParentDashboardController {
    @FXML private Label welcomeLabel;
    @FXML private Button viewGradesButton;
    @FXML private Button viewFeedbackButton;
    @FXML private Button logoutButton;

    public void initialize() {
        welcomeLabel.setText("Welcome, Parent!");
    }

    @FXML
    private void handleViewGrades(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/grades_view.fxml"));
            Parent root = loader.load();

            GradesViewController controller = loader.getController();
            int childId = SessionManager.getChildIdForParent();
            controller.loadGrades(childId, "Your Child's Academic Performance");

            Stage stage = new Stage();
            stage.setTitle("Child's Grades - Academix");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Unable to load grades page.");
        }
    }

    @FXML
    private void handleViewFeedback(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/submit_feedback.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Teacher Feedback");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Unable to load feedback page.");
        }
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
            showAlert("Unable to load login page.");
        }
    }

    private void showAlert(String message) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
        alert.setTitle("Academix Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
} 