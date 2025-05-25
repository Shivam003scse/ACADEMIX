package controller;

import dao.TimetableDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Timetable;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class TimetableViewController {

    @FXML
    private TableView<Timetable> timetableTable;

    @FXML
    private TableColumn<Timetable, String> subjectCol;

    @FXML
    private TableColumn<Timetable, String> dayCol;

    @FXML
    private TableColumn<Timetable, String> timeCol;

    @FXML
    private TableColumn<Timetable, String> teacherCol;

    @FXML
    private Button backButton;

    private final ObservableList<Timetable> timetableList = FXCollections.observableArrayList();

    // Update credentials as per your setup
    private final String DB_URL = "jdbc:mysql://localhost:3306/academix";
    private final String DB_USER = "root";
    private final String DB_PASS = "Shivam@8453";

    // Called automatically by FXMLLoader
    public void initialize() {
        subjectCol.setCellValueFactory(new PropertyValueFactory<>("subject"));
        dayCol.setCellValueFactory(new PropertyValueFactory<>("day"));
        timeCol.setCellValueFactory(new PropertyValueFactory<>("timeSlot"));
        teacherCol.setCellValueFactory(new PropertyValueFactory<>("teacher"));

        // Optional: call only if loaded directly
        // loadTimetables();
    }

    // Made public so it can be called externally when loading via FXMLLoader
    public void loadTimetables() {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
            TimetableDAO dao = new TimetableDAO(connection);
            List<Timetable> timetables = dao.getAllTimetables();

            timetableList.clear();
            if (timetables != null && !timetables.isEmpty()) {
                timetableList.addAll(timetables);
                timetableTable.setItems(timetableList);
            } else {
                System.out.println("No timetable data found.");
            }
        } catch (SQLException e) {
            System.out.println("Error fetching timetable:");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleBack(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/teacher_dashboard.fxml"));
            Parent root = loader.load();

            // Optional: if teacher dashboard needs init logic
            // TeacherDashboardController controller = loader.getController();
            // controller.initData(...);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Teacher Dashboard - Academix");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onHoverIn(MouseEvent event) {
        Button btn = (Button) event.getSource();
        btn.setStyle("-fx-background-color: #2980b9; -fx-text-fill: white; -fx-font-size: 14px; -fx-background-radius: 8; -fx-padding: 8 16;");
    }

    @FXML
    private void onHoverOut(MouseEvent event) {
        Button btn = (Button) event.getSource();
        btn.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-size: 14px; -fx-background-radius: 8; -fx-padding: 8 16;");
    }
}
