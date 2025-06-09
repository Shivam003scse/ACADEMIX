package controller;

import dao.StudentDAO;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Student;
import javafx.scene.paint.Color;
import javafx.geometry.Pos;

public class ViewStudentsController {
    @FXML private TableView<Student> studentTable;
    @FXML private TableColumn<Student, String> studentNameCol;
    @FXML private TableColumn<Student, String> studentRollCol;
    @FXML private TableColumn<Student, String> studentContactCol;
    @FXML private Button backButton;

    @FXML
    public void initialize() {
        System.out.println("Initializing ViewStudentsController...");
        
        // Set up column cell factories
        studentNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        studentRollCol.setCellValueFactory(new PropertyValueFactory<>("rollNumber"));
        studentContactCol.setCellValueFactory(new PropertyValueFactory<>("parentContact"));

        // Style the table
        studentTable.setStyle("-fx-background-color: #232526; -fx-text-fill: #00c6ff;");
        
        // Make table non-editable
        studentTable.setEditable(false);
        
        // Style the cells with appropriate alignment
        styleColumn(studentNameCol, Pos.CENTER_LEFT);
        styleColumn(studentRollCol, Pos.CENTER);
        styleColumn(studentContactCol, Pos.CENTER);

        // Load data
        var students = StudentDAO.getAllStudents();
        if (students.isEmpty()) {
            System.out.println("No students found in database");
        } else {
            System.out.println("Found " + students.size() + " students");
            students.forEach(student -> {
                System.out.println("Student: " + student.getName() + 
                                 ", Roll: " + student.getRollNumber() + 
                                 ", Contact: " + student.getParentContact());
            });
        }
        studentTable.setItems(FXCollections.observableArrayList(students));

        // Add row factory for hover effect
        studentTable.setRowFactory(tv -> {
            TableRow<Student> row = new TableRow<>();
            row.setStyle("-fx-background-color: #232526;");
            row.setOnMouseEntered(event -> {
                if (!row.isEmpty()) {
                    row.setStyle("-fx-background-color: #2a2e3d;");
                }
            });
            row.setOnMouseExited(event -> {
                if (!row.isEmpty()) {
                    row.setStyle("-fx-background-color: #232526;");
                }
            });
            return row;
        });
    }

    private void styleColumn(TableColumn<Student, String> column, Pos alignment) {
        column.setCellFactory(col -> new TableCell<Student, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setStyle("-fx-background-color: #232526;");
                } else {
                    setText(item);
                    setStyle("-fx-background-color: #232526; -fx-text-fill: #00c6ff;");
                    setAlignment(alignment);
                }
            }
        });
    }

    @FXML
    private void handleBack() {
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.close();
    }
} 