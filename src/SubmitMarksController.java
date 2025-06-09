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
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.util.List;

public class SubmitMarksController {

    @FXML private ComboBox<Student> studentComboBox;
    @FXML private ComboBox<Subject> subjectComboBox;
    @FXML private TextField marksField;
    @FXML private Button submitButton;
    @FXML private Label alertLabel;

    private final StudentDAO studentDAO = new StudentDAO();
    private final SubjectDAO subjectDAO = new SubjectDAO();

    @FXML
    public void initialize() {
        // Initialize subjects if needed
        SubjectDAO.initializeSubjects();
        loadStudents();
        loadSubjects();
        
        // Style the student names in the combo box
        studentComboBox.setButtonCell(new ListCell<Student>() {
            @Override
            protected void updateItem(Student item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText("");
                } else {
                    setText(item.getName());
                    setStyle("-fx-text-fill: #00c6ff; -fx-font-weight: bold;");
                }
            }
        });
        
        studentComboBox.setCellFactory(lv -> new ListCell<Student>() {
            @Override
            protected void updateItem(Student item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText("");
                } else {
                    setText(item.getName());
                    setStyle("-fx-text-fill: #00c6ff; -fx-font-weight: bold;");
                }
            }
        });

        // Style the subject names in the combo box
        subjectComboBox.setButtonCell(new ListCell<Subject>() {
            @Override
            protected void updateItem(Subject item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText("");
                } else {
                    setText(item.getName());
                    setStyle("-fx-text-fill: #00c6ff; -fx-font-weight: bold;");
                }
            }
        });
        
        subjectComboBox.setCellFactory(lv -> new ListCell<Subject>() {
            @Override
            protected void updateItem(Subject item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText("");
                } else {
                    setText(item.getName());
                    setStyle("-fx-text-fill: #00c6ff; -fx-font-weight: bold;");
                }
            }
        });
    }

    private void loadStudents() {
        ObservableList<Student> students = FXCollections.observableArrayList(studentDAO.getAllStudents());
        System.out.println("Loaded students: " + students);
        studentComboBox.setItems(students);
    }

    private void loadSubjects() {
        // Initialize subjects first
        SubjectDAO.initializeSubjects();
        
        // Then load them
        List<Subject> subjectList = SubjectDAO.getAllSubjects();
        System.out.println("Loading subjects: " + subjectList);
        
        if (subjectList.isEmpty()) {
            System.out.println("Warning: No subjects found in database!");
            showAlert("No subjects found in the database. Please contact the administrator.", true);
            return;
        }
        
        ObservableList<Subject> subjects = FXCollections.observableArrayList(subjectList);
        subjectComboBox.setItems(subjects);
        
        // Set cell factory for better display
        subjectComboBox.setCellFactory(lv -> new ListCell<Subject>() {
            @Override
            protected void updateItem(Subject item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText("");
                } else {
                    setText(item.getName());
                    setStyle("-fx-text-fill: #00c6ff; -fx-font-weight: bold;");
                }
            }
        });
        
        // Set button cell for selected item display
        subjectComboBox.setButtonCell(new ListCell<Subject>() {
            @Override
            protected void updateItem(Subject item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText("");
                } else {
                    setText(item.getName());
                    setStyle("-fx-text-fill: #00c6ff; -fx-font-weight: bold;");
                }
            }
        });
    }

    @FXML
    private void handleSubmit() {
        Student student = studentComboBox.getValue();
        Subject subject = subjectComboBox.getValue();
        String marksText = marksField.getText().trim();

        if (student == null) {
            showAlert("Please select a student.", true);
            return;
        }
        if (subject == null) {
            showAlert("Please select a subject.", true);
            return;
        }
        if (marksText.isEmpty()) {
            showAlert("Please enter marks.", true);
            return;
        }

        try {
            int marksObtained = Integer.parseInt(marksText);
            
            if (marksObtained < 0 || marksObtained > 100) {
                showAlert("Marks must be between 0 and 100.", true);
                return;
            }
            
            String grade = calculateGrade(marksObtained);
            Mark mark = new Mark(student.getId(), subject.getId(), marksObtained, grade, "");

            // Check if marks already exist
            if (MarkDAO.marksExist(student.getId(), subject.getId())) {
                Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
                confirmAlert.setTitle("Update Marks");
                confirmAlert.setHeaderText("Marks already exist");
                confirmAlert.setContentText("Do you want to update the marks for " + student.getName() + " in " + subject.getName() + "?");
                
                DialogPane dialogPane = confirmAlert.getDialogPane();
                dialogPane.setStyle(
                    "-fx-background-color: #232526;" +
                    "-fx-border-color: #00c6ff;" +
                    "-fx-border-width: 2px;" +
                    "-fx-border-radius: 5px;" +
                    "-fx-background-radius: 5px;"
                );
                
                // Style the header
                Label headerLabel = (Label) dialogPane.lookup(".header-panel .label");
                if (headerLabel != null) {
                    headerLabel.setStyle(
                        "-fx-text-fill: #00c6ff;" +
                        "-fx-font-size: 18px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-family: 'Segoe UI';"
                    );
                }
                
                // Style the content
                Label contentLabel = (Label) dialogPane.lookup(".content.label");
                if (contentLabel != null) {
                    contentLabel.setStyle(
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 14px;" +
                        "-fx-font-family: 'Segoe UI';"
                    );
                }
                
                // Style the buttons
                dialogPane.lookupAll(".button").forEach(node -> {
                    ((Button) node).setStyle(
                        "-fx-background-color: #00c6ff;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 14px;" +
                        "-fx-background-radius: 5px;" +
                        "-fx-cursor: hand;"
                    );
                });

                confirmAlert.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.OK) {
                        boolean success = MarkDAO.insertOrUpdateMarks(mark);
                        if (success) {
                            showAlert("✅ Marks updated successfully for " + student.getName(), false);
                            clearFields();
                        } else {
                            showAlert("Failed to update marks.", true);
                        }
                    }
                });
            } else {
                boolean success = MarkDAO.insertOrUpdateMarks(mark);
                if (success) {
                    showAlert("✅ Marks submitted successfully for " + student.getName(), false);
                    clearFields();
                } else {
                    showAlert("Failed to submit marks.", true);
                }
            }
        } catch (NumberFormatException e) {
            showAlert("Please enter a valid number for marks.", true);
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
        alertLabel.setVisible(false);
    }

    private void showAlert(String message, boolean isError) {
        // Update the label
        alertLabel.setText(message);
        alertLabel.setVisible(true);
        alertLabel.setStyle(isError
                ? "-fx-font-size: 15px; -fx-font-family: 'Segoe UI'; -fx-text-fill: #ff5252; -fx-background-color: #232526; -fx-padding: 6 0; -fx-background-radius: 8;"
                : "-fx-font-size: 15px; -fx-font-family: 'Segoe UI'; -fx-text-fill: #00c6ff; -fx-background-color: #232526; -fx-padding: 6 0; -fx-background-radius: 8;");

        // Show alert dialog
        Alert alert = new Alert(isError ? Alert.AlertType.ERROR : Alert.AlertType.INFORMATION);
        alert.setTitle(isError ? "Error" : "Success");
        alert.setHeaderText(isError ? "❌ Error" : "✅ Success");
        alert.setContentText(message);

        // Style the alert dialog
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setStyle(
            "-fx-background-color: #232526;" +
            "-fx-border-color: " + (isError ? "#ff5252" : "#00c6ff") + ";" +
            "-fx-border-width: 2px;" +
            "-fx-border-radius: 5px;" +
            "-fx-background-radius: 5px;"
        );
        
        // Style the header
        Label headerLabel = (Label) dialogPane.lookup(".header-panel .label");
        if (headerLabel != null) {
            headerLabel.setStyle(
                "-fx-text-fill: " + (isError ? "#ff5252" : "#00c6ff") + ";" +
                "-fx-font-size: 18px;" +
                "-fx-font-weight: bold;" +
                "-fx-font-family: 'Segoe UI';"
            );
        }
        
        // Style the content
        Label contentLabel = (Label) dialogPane.lookup(".content.label");
        if (contentLabel != null) {
            contentLabel.setStyle(
                "-fx-text-fill: white;" +
                "-fx-font-size: 14px;" +
                "-fx-font-family: 'Segoe UI';"
            );
        }
        
        // Style the buttons
        dialogPane.lookupAll(".button").forEach(node -> {
            ((Button) node).setStyle(
                "-fx-background-color: " + (isError ? "#ff5252" : "#00c6ff") + ";" +
                "-fx-text-fill: white;" +
                "-fx-font-size: 14px;" +
                "-fx-background-radius: 5px;" +
                "-fx-cursor: hand;"
            );
        });

        alert.showAndWait();
    }
}
