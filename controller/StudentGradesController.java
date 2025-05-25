package controller;

import dao.GradeDAO;
import model.Grade;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class StudentGradesController {

    @FXML private TableView<Grade> gradesTable;
    @FXML private TableColumn<Grade, String> subjectColumn;
    @FXML private TableColumn<Grade, Double> marksColumn;
    @FXML private TableColumn<Grade, String> gradeColumn;

    private String studentEmail; // To be passed from login/session

    public void setStudentEmail(String email) {
        this.studentEmail = email;
        loadGrades();
    }

    public void initialize() {
        subjectColumn.setCellValueFactory(new PropertyValueFactory<>("subject"));
        marksColumn.setCellValueFactory(new PropertyValueFactory<>("marks"));
        gradeColumn.setCellValueFactory(new PropertyValueFactory<>("grade"));
    }

    private void loadGrades() {
        List<Grade> grades = GradeDAO.getGradesByStudentEmail(studentEmail);
        gradesTable.getItems().setAll(grades);
    }
}
