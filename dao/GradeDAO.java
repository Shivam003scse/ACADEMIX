package dao;

import model.Grade;
import util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GradeDAO {

    public static List<Grade> getGradesByStudentEmail(String email) {
        List<Grade> grades = new ArrayList<>();
        String query = "SELECT subject, marks, grade FROM grades WHERE student_email = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                grades.add(new Grade(
                        rs.getString("subject"),
                        rs.getDouble("marks"),
                        rs.getString("grade")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return grades;
    }

    public static void insertGrade(Grade grade) {
        String sql = "INSERT INTO grades (student_email, subject, marks, grade) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, grade.getStudentEmail());
            stmt.setString(2, grade.getSubject());
            stmt.setDouble(3, grade.getMarks());
            stmt.setString(4, grade.getGrade());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
