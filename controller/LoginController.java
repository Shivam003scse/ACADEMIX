package controller;

import model.User; 
import dao.UserDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    private final UserDAO userDAO = new UserDAO();

    @FXML
    private void handleLogin(ActionEvent event) {
        String email = emailField.getText().trim();
        String password = passwordField.getText();

        if (email.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Form Error!", "Please enter both email and password.");
            return;
        }

        User user = userDAO.getUserByEmail(email);
        if (user == null) {
            showAlert(Alert.AlertType.ERROR, "Login Failed", "User not found.");
            return;
        }

        if (!password.equals(user.getPassword())) {
            showAlert(Alert.AlertType.ERROR, "Login Failed", "Incorrect password.");
            return;
        }

        showAlert(Alert.AlertType.INFORMATION, "Login Successful", "Welcome, " + user.getName());

        try {
            switch (user.getRole().toLowerCase()) {
                case "student":
                    loadScene("fxml/student_dashboard.fxml", "Student Dashboard");
                    break;
                case "teacher":
                    loadScene("fxml/teacher_dashboard.fxml", "Teacher Dashboard");
                    break;
                case "parent":
                    loadScene("fxml/parent_dashboard.fxml", "Parent Dashboard");
                    break;
                default:
                    showAlert(Alert.AlertType.ERROR, "Unknown Role", "User role is not recognized.");
            }
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Navigation Error", "Unable to load the dashboard.");
        }
    }

    @FXML
    private void goToRegister(ActionEvent event) {
        try {
            loadScene("fxml/register.fxml", "Register");
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Navigation Error", "Unable to load the registration screen.");
        }
    }

    private void loadScene(String fxmlPath, String title) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(fxmlPath));
        if (loader.getLocation() == null) {
            throw new IOException("FXML file not found: " + fxmlPath);
        }

        Parent root = loader.load();
        Stage stage = (Stage) emailField.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle(title);
        stage.show();
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
