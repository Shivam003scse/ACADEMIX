package controller;

import dao.GradeDAO;
import model.Grade;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import java.util.List;

public class StudentGradesController {

    @FXML private TableView<Grade> gradesTable;
    @FXML private TableColumn<Grade, String> subjectColumn;
    @FXML private TableColumn<Grade, Double> marksColumn;
    @FXML private TableColumn<Grade, String> gradeColumn;
    @FXML private Button viewFeedbackButton;

    private String studentEmail;

    public void setStudentEmail(String email) {
        this.studentEmail = email;
        loadGrades();
    }

    public void initialize() {
        subjectColumn.setCellValueFactory(new PropertyValueFactory<>("subject"));
        marksColumn.setCellValueFactory(new PropertyValueFactory<>("marks"));
        gradeColumn.setCellValueFactory(new PropertyValueFactory<>("grade"));
    }

    @FXML
    private void handleViewFeedback() {
        Grade selectedGrade = gradesTable.getSelectionModel().getSelectedItem();
        if (selectedGrade != null) {
            showFeedback(selectedGrade);
        } else {
            showAlert("Please select a subject to view feedback");
        }
    }

    private void showFeedback(Grade grade) {
        // Create feedback window
        Stage feedbackStage = new Stage();
        feedbackStage.initModality(Modality.APPLICATION_MODAL);
        feedbackStage.setTitle("Teacher Feedback");

        // Create content
        VBox content = new VBox(15);
        content.setStyle("-fx-background-color: #232526; -fx-padding: 20;");
        content.setAlignment(Pos.CENTER);

        // Subject header
        Label subjectLabel = new Label(grade.getSubject());
        subjectLabel.setStyle("-fx-font-size: 20px; -fx-text-fill: #00c6ff; -fx-font-weight: bold;");

        // Feedback text area
        TextArea feedbackText = new TextArea(grade.getFeedback());
        feedbackText.setEditable(false);
        feedbackText.setWrapText(true);
        feedbackText.setPrefRowCount(6);
        feedbackText.setPrefColumnCount(35);
        feedbackText.setStyle(
            "-fx-control-inner-background: #181c24; " +
            "-fx-text-fill: white; " +
            "-fx-background-radius: 8; " +
            "-fx-border-color: #00c6ff; " +
            "-fx-border-radius: 8; " +
            "-fx-font-size: 14px;"
        );

        // Close button
        Button closeBtn = new Button("Close");
        closeBtn.setStyle(
            "-fx-background-color: #00c6ff; " +
            "-fx-text-fill: white; " +
            "-fx-font-size: 14px; " +
            "-fx-background-radius: 8; " +
            "-fx-cursor: hand; " +
            "-fx-padding: 8 16;"
        );
        closeBtn.setOnAction(e -> feedbackStage.close());

        content.getChildren().addAll(subjectLabel, feedbackText, closeBtn);
        
        // Set scene
        javafx.scene.Scene scene = new javafx.scene.Scene(content);
        feedbackStage.setScene(scene);
        feedbackStage.setMinWidth(400);
        feedbackStage.setMinHeight(300);
        feedbackStage.show();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void loadGrades() {
        List<Grade> grades = GradeDAO.getGradesByStudentEmail(studentEmail);
        gradesTable.getItems().setAll(grades);
    }
}
