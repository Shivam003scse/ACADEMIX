package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Grade;
import model.Mark;
import dao.MarkDAO;
import dao.SubjectDAO;
import model.Subject;
import java.util.List;
import java.util.stream.Collectors;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.geometry.Pos;
import javafx.geometry.Insets;

public class GradesViewController {
    @FXML private Label titleLabel;
    @FXML private TableView<Grade> gradesTable;
    @FXML private TableColumn<Grade, String> subjectColumn;
    @FXML private TableColumn<Grade, Integer> marksColumn;
    @FXML private TableColumn<Grade, String> gradeColumn;
    @FXML private Button closeButton;
    @FXML private Button viewFeedbackButton;

    private int studentId;

    public void initialize() {
        setupTableColumns();
        
        // Make the table read-only
        gradesTable.setEditable(false);
        
        // Style the table
        gradesTable.setStyle("-fx-background-color: #232526; -fx-text-fill: #00c6ff;");
    }

    private void setupTableColumns() {
        subjectColumn.setCellValueFactory(new PropertyValueFactory<>("subject"));
        marksColumn.setCellValueFactory(new PropertyValueFactory<>("marks"));
        gradeColumn.setCellValueFactory(new PropertyValueFactory<>("grade"));
        
        // Style the cells with cyan text
        styleColumn(subjectColumn);
        styleColumn(marksColumn);
        styleColumn(gradeColumn);
    }

    private <T> void styleColumn(TableColumn<Grade, T> column) {
        column.setCellFactory(col -> new TableCell<Grade, T>() {
            @Override
            protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setStyle("-fx-background-color: #232526;");
                } else {
                    setText(item.toString());
                    setStyle("-fx-background-color: #232526; -fx-text-fill: #00c6ff; -fx-alignment: CENTER;");
                }
            }
        });
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
        content.setStyle("-fx-background-color: #181c24; -fx-padding: 20;");
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
            "-fx-control-inner-background: #232526; " +
            "-fx-text-fill: #00c6ff; " +
            "-fx-background-radius: 8; " +
            "-fx-border-color: #00c6ff44; " +
            "-fx-border-radius: 8; " +
            "-fx-font-size: 14px;"
        );

        // Close button
        Button closeBtn = new Button("Close");
        closeBtn.setStyle(
            "-fx-background-color: #00c6ff; " +
            "-fx-text-fill: #181c24; " +
            "-fx-font-size: 14px; " +
            "-fx-background-radius: 8; " +
            "-fx-cursor: hand; " +
            "-fx-padding: 10 24;"
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
        
        // Style the alert
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setStyle(
            "-fx-background-color: #181c24; " +
            "-fx-text-fill: #00c6ff;"
        );
        dialogPane.lookup(".content.label").setStyle("-fx-text-fill: #00c6ff;");
        
        alert.showAndWait();
    }

    public void loadGrades(int studentId, String title) {
        this.studentId = studentId;
        titleLabel.setText(title);

        // Get all marks for the student
        List<Mark> marks = MarkDAO.getAllMarks().stream()
            .filter(mark -> mark.getStudentId() == studentId)
            .collect(Collectors.toList());

        // Convert marks to grades for display
        ObservableList<Grade> grades = FXCollections.observableArrayList();
        for (Mark mark : marks) {
            Subject subject = SubjectDAO.getSubjectById(mark.getSubjectId());
            String subjectName = subject != null ? subject.getName() : "Unknown Subject";
            
            grades.add(new Grade(
                subjectName,
                mark.getMarks(),
                mark.getGrade(),
                mark.getFeedback()  // Keep feedback in model but don't show in table
            ));
        }

        gradesTable.setItems(grades);
    }

    @FXML
    private void handleClose() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
} 