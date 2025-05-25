package controller;

import dao.UserDAO;
import model.User;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class RegisterController {

    @FXML
    private TextField nameField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private ComboBox<String> roleComboBox;

    private UserDAO userDAO = new UserDAO();

    @FXML
    public void initialize() {
        // Set user roles in ComboBox
        roleComboBox.setItems(FXCollections.observableArrayList("Student", "Teacher", "Parent", "Admin"));
        roleComboBox.getSelectionModel().selectFirst(); // default selection
    }

    @FXML
    private void handleRegister(ActionEvent event) {
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();
        String role = roleComboBox.getValue();

        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Form Error!", "Please fill in all fields");
            return;
        }

        if (!password.equals(confirmPassword)) {
            showAlert(Alert.AlertType.ERROR, "Password Mismatch", "Passwords do not match");
            return;
        }

        // Check if user already exists
        if (userDAO.getUserByEmail(email) != null) {
            showAlert(Alert.AlertType.ERROR, "Registration Error", "User with this email already exists");
            return;
        }

        User newUser = new User(name, email, password, role);
        boolean success = userDAO.registerUser(newUser);
        if (success) {
            showAlert(Alert.AlertType.INFORMATION, "Registration Successful", "User registered successfully");
            clearFields();

            // Navigate to Login.fxml
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
                Parent root = loader.load();

                Stage stage = (Stage) nameField.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.setTitle("Login - Academix");
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to load Login page");
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "Registration Failed", "Something went wrong");
        }
    }

    @FXML
    private void goToLogin(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Login.fxml")); // Adjust path if needed
            Parent root = loader.load();

            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Login - Academix");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Navigation Error", "Could not load the login page.");
        }
    }

    private void clearFields() {
        nameField.clear();
        emailField.clear();
        passwordField.clear();
        confirmPasswordField.clear();
        roleComboBox.getSelectionModel().selectFirst();
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

