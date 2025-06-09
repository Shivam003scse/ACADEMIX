package dao;

import model.Feedback;
import util.DBUtil;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FeedbackDAO {

    public static boolean addFeedback(Feedback feedback) {
        String query = "INSERT INTO feedback (teacher_id, student_id, feedback_text) VALUES (?, ?, ?)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, feedback.getTeacherId());
            stmt.setInt(2, feedback.getStudentId());
            stmt.setString(3, feedback.getFeedbackText());

            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static List<Feedback> getFeedbackByStudentId(int studentId) {
        List<Feedback> feedbackList = new ArrayList<>();
        String query = "SELECT * FROM feedback WHERE student_id = ? ORDER BY date_submitted DESC";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, studentId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Feedback feedback = new Feedback(
                        rs.getInt("id"),
                        rs.getInt("teacher_id"),
                        rs.getInt("student_id"),
                        rs.getString("feedback_text"),
                        rs.getTimestamp("date_submitted").toLocalDateTime()
                );
                feedbackList.add(feedback);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return feedbackList;
    }

    public static boolean submitFeedback(int studentId, String feedbackText) {
        // TODO: Replace teacher_id=1 with session logic
        int teacherId = 1;
        String query = "INSERT INTO feedback (teacher_id, student_id, feedback_text) VALUES (?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, teacherId);
            stmt.setInt(2, studentId);
            stmt.setString(3, feedbackText);
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
