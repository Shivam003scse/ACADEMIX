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
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Grade;
import model.Feedback;
import model.Timetable;
import dao.GradeDAO;
import dao.FeedbackDAO;
import dao.StudentDAO;
import dao.TimetableDAO;
import util.DBUtil;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import java.io.IOException;
import javafx.scene.layout.VBox;
import javafx.scene.control.cell.PropertyValueFactory;
import util.SessionManager;
import dao.MarkDAO;
import model.Mark;
import dao.SubjectDAO;
import model.Subject;

public class StudentDashboardController {

    @FXML private Label welcomeLabel;
    @FXML private Button viewGradesButton;
    @FXML private Button feedbackButton;
    @FXML private Button logoutButton;
    @FXML private Button viewTimetableButton;
    private String studentEmail = "guvi.24scse@gmail.com"; // TODO: Replace with session logic

    public void initialize() {
        welcomeLabel.setText("Welcome, Student!");
        int studentId = SessionManager.getCurrentUserId();
        String studentName = StudentDAO.getStudentNameById(studentId);
        if (studentName != null && !studentName.isEmpty()) {
            welcomeLabel.setText("Welcome, " + studentName + "!");
        }
    }
    
    private String getSubjectName(int subjectId) {
        try {
            Subject subject = SubjectDAO.getSubjectById(subjectId);
            return subject != null ? subject.getName() : "Unknown Subject";
        } catch (Exception e) {
            e.printStackTrace();
            return "Subject " + subjectId;
        }
    }

    @FXML
    private void handleViewGrades(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/grades_view.fxml"));
            Parent root = loader.load();

            GradesViewController controller = loader.getController();
            int studentId = SessionManager.getCurrentUserId();
            controller.loadGrades(studentId, "Your Academic Performance");

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
    private void handleViewTimetable(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/timetable_view.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Class Timetable");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Failed to open Timetable window.");
        }
    }

    @FXML
    private void handleFeedback(ActionEvent event) {
        try {
            Stage stage = new Stage();
            stage.setTitle("Your Feedback");
            TableView<model.Feedback> table = new TableView<>();
            TableColumn<model.Feedback, String> dateCol = new TableColumn<>("Date");
            dateCol.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(
                cell.getValue().getDateSubmitted() != null ? cell.getValue().getDateSubmitted().toString() : ""));
            dateCol.setPrefWidth(120);
            TableColumn<model.Feedback, String> teacherCol = new TableColumn<>("Teacher");
            teacherCol.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(
                dao.StudentDAO.getTeacherNameById(cell.getValue().getTeacherId())));
            teacherCol.setPrefWidth(120);
            TableColumn<model.Feedback, String> feedbackCol = new TableColumn<>("Feedback");
            feedbackCol.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getFeedbackText()));
            feedbackCol.setPrefWidth(260);
            table.getColumns().addAll(dateCol, teacherCol, feedbackCol);
            table.setStyle("-fx-background-radius: 16; -fx-border-radius: 16; -fx-font-size: 15; -fx-font-family: 'Segoe UI';");
            java.util.List<model.Feedback> feedbacks = dao.FeedbackDAO.getFeedbackByStudentId(dao.StudentDAO.getStudentIdByEmail(studentEmail));
            table.getItems().setAll(feedbacks);
            VBox vbox = new VBox(table);
            vbox.setStyle("-fx-background-color: #232526; -fx-background-radius: 18; -fx-padding: 18;");
            stage.setScene(new Scene(vbox, 540, 340));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Failed to open Feedback window.");
        }
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
        btn.setStyle("-fx-background-color: #333333; -fx-text-fill: white; -fx-font-size: 16px; -fx-background-radius: 12;");
    }

    @FXML
    private void onHoverOut(MouseEvent event) {
        Button btn = (Button) event.getSource();
        btn.setStyle("-fx-background-color: #2980B9; -fx-text-fill: white; -fx-font-size: 16px; -fx-background-radius: 12;");
    }

    private void showAlert(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Academix");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
