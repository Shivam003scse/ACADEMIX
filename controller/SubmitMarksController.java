package controller;

import dao.MarkDAO;
import dao.StudentDAO;
import dao.SubjectDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Mark;
import model.Student;
import model.Subject;

public class SubmitMarksController {

    @FXML private ComboBox<Student> studentComboBox;
    @FXML private ComboBox<Subject> subjectComboBox;
    @FXML private TextField marksField;
    @FXML private TextArea feedbackArea;
    @FXML private Button submitButton;

    private final StudentDAO studentDAO = new StudentDAO();
    private final SubjectDAO subjectDAO = new SubjectDAO();

    @FXML
    public void initialize() {
        loadStudents();
        loadSubjects();
    }

    private void loadStudents() {
        ObservableList<Student> students = FXCollections.observableArrayList(studentDAO.getAllStudents());
        studentComboBox.setItems(students);
    }

    private void loadSubjects() {
        ObservableList<Subject> subjects = FXCollections.observableArrayList(subjectDAO.getAllSubjects());
        subjectComboBox.setItems(subjects);
    }

    @FXML
    private void handleSubmit() {
        Student student = studentComboBox.getValue();
        Subject subject = subjectComboBox.getValue();
        String marksText = marksField.getText().trim();
        String feedback = feedbackArea.getText().trim();

        if (student == null || subject == null || marksText.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "All fields are required.");
            return;
        }

        try {
            int marks = Integer.parseInt(marksText);
            String grade = calculateGrade(marks);

            // Create Mark object using model class
            Mark mark = new Mark(student.getId(), subject.getId(), marks, grade, feedback);

            // Use DAO method that accepts Mark object
            boolean success = MarkDAO.insertOrUpdateMarks(mark);

            if (success) {
                showAlert(Alert.AlertType.INFORMATION, "Marks and feedback submitted successfully!");
                clearFields();
            } else {
                showAlert(Alert.AlertType.ERROR, "Failed to submit data.");
            }

        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Please enter a valid number for marks.");
        }
    }

    private String calculateGrade(int marks) {
        if (marks >= 90) return "A+";
        if (marks >= 80) return "A";
        if (marks >= 70) return "B";
        if (marks >= 60) return "C";
        if (marks >= 50) return "D";
        return "F";
    }

    private void clearFields() {
        studentComboBox.setValue(null);
        subjectComboBox.setValue(null);
        marksField.clear();
        feedbackArea.clear();
    }

    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setTitle("Academix");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
