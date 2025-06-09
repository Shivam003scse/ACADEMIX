package dao;

import model.Grade;
import util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GradeDAO {

    public static List<Grade> getGradesByStudentEmail(String email) {
        List<Grade> grades = new ArrayList<>();
        String query = "SELECT s.name AS subject, m.marks_obtained AS marks, m.grade, m.feedback FROM marks m JOIN students st ON m.student_id = st.id JOIN users u ON st.user_id = u.id JOIN subjects s ON m.subject_id = s.id WHERE u.email = ?";

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
}
