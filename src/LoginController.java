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
import javafx.application.Platform;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private ProgressIndicator loadingIndicator;

    private final UserDAO userDAO = new UserDAO();

    @FXML
    private void handleLogin(ActionEvent event) {
        String email = emailField.getText().trim();
        String password = passwordField.getText();

        if (email.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Form Error!", "Please enter both email and password.");
            return;
        }

        loadingIndicator.setVisible(true);
        emailField.setDisable(true);
        passwordField.setDisable(true);

        new Thread(() -> {
            try {
                Thread.sleep(900); // Simulate loading
            } catch (InterruptedException ignored) {}
            Platform.runLater(() -> {
        User user = userDAO.getUserByEmail(email);
        if (user == null) {
                    loadingIndicator.setVisible(false);
                    emailField.setDisable(false);
                    passwordField.setDisable(false);
            showAlert(Alert.AlertType.ERROR, "Login Failed", "User not found.");
            return;
        }
        if (!password.equals(user.getPassword())) {
                    loadingIndicator.setVisible(false);
                    emailField.setDisable(false);
                    passwordField.setDisable(false);
            showAlert(Alert.AlertType.ERROR, "Login Failed", "Incorrect password.");
            return;
        }
                showWelcomeScreen(user.getName(), user.getRole());
            });
        }).start();
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

    private void showWelcomeScreen(String name, String role) {
        if (role.equalsIgnoreCase("teacher")) {
            Stage popup = new Stage();
            popup.initModality(Modality.APPLICATION_MODAL);
            popup.initOwner(emailField.getScene().getWindow());
            StackPane root = new StackPane();
            root.setStyle("-fx-background-color: linear-gradient(to bottom right, #00c6ff, #181c24); -fx-background-radius: 40; -fx-padding: 40 40 40 40;");
            VBox vbox = new VBox(16);
            vbox.setAlignment(javafx.geometry.Pos.CENTER);
            Text helloText = new Text("Hello Teacher \uD83D\uDC4B");
            helloText.setFont(Font.font("Poppins Semibold", 34));
            helloText.setFill(Color.web("#fff"));
            helloText.setStyle("-fx-effect: dropshadow(gaussian, #00c6ff99, 8, 0, 0, 2);");
            Text nameText = new Text(name);
            nameText.setFont(Font.font("Poppins", 26));
            nameText.setFill(Color.web("#fff"));
            nameText.setStyle("-fx-effect: dropshadow(gaussian, #00c6ff99, 4, 0, 0, 1);");
            vbox.getChildren().addAll(helloText, nameText);
            root.getChildren().add(vbox);
            Scene scene = new Scene(root, 420, 210);
            root.setMaxWidth(420);
            root.setMaxHeight(210);
            popup.setScene(scene);
            popup.setTitle("Welcome");
            popup.setResizable(false);
            popup.show();
            new Thread(() -> {
                try { Thread.sleep(1500); } catch (InterruptedException ignored) {}
                Platform.runLater(() -> {
                    popup.close();
                    try {
                        loadScene("fxml/teacher_dashboard.fxml", "Teacher Dashboard");
                    } catch (IOException e) {
                        e.printStackTrace();
                        showAlert(Alert.AlertType.ERROR, "Navigation Error", "Unable to load the dashboard.");
                    }
                });
            }).start();
        } else {
            StackPane root = new StackPane();
            root.setStyle("-fx-background-color: linear-gradient(to bottom right, #181c24, #00c6ff); -fx-background-radius: 20; -fx-padding: 40;");
            Text welcomeText = new Text("Welcome, " + name + "!\n(" + capitalize(role) + ")");
            welcomeText.setFont(Font.font("Poppins Semibold", 32));
            welcomeText.setFill(Color.web("#fff"));
            welcomeText.setStyle("-fx-effect: dropshadow(gaussian, #00c6ff99, 8, 0, 0, 2);");
            root.getChildren().add(welcomeText);
            Stage popup = new Stage();
            popup.initModality(Modality.APPLICATION_MODAL);
            popup.initOwner(emailField.getScene().getWindow());
            Scene scene = new Scene(root, 400, 200);
            popup.setScene(scene);
            popup.setTitle("Welcome");
            popup.setResizable(false);
            popup.show();
            new Thread(() -> {
                try { Thread.sleep(1500); } catch (InterruptedException ignored) {}
                Platform.runLater(() -> {
                    popup.close();
                    try {
                        switch (role.toLowerCase()) {
                            case "student":
                                loadScene("fxml/student_dashboard.fxml", "Student Dashboard");
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
                });
            }).start();
        }
    }

    private String capitalize(String str) {
        if (str == null || str.isEmpty()) return str;
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }
}
