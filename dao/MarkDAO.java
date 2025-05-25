package dao;

import util.DBUtil;
import model.Mark;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MarkDAO {

    public static boolean insertMarks(Mark mark) {
        String query = "INSERT INTO marks (student_id, subject_id, marks, grade, feedback) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, mark.getStudentId());
            stmt.setInt(2, mark.getSubjectId());
            stmt.setInt(3, mark.getMarks());
            stmt.setString(4, mark.getGrade());
            stmt.setString(5, mark.getFeedback());

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            System.err.println("Error inserting marks: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    public static boolean insertOrUpdateMarks(Mark mark) {
        String checkQuery = "SELECT id FROM marks WHERE student_id = ? AND subject_id = ?";
        String updateQuery = "UPDATE marks SET marks = ?, grade = ?, feedback = ? WHERE student_id = ? AND subject_id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkQuery)) {

            checkStmt.setInt(1, mark.getStudentId());
            checkStmt.setInt(2, mark.getSubjectId());

            try (ResultSet rs = checkStmt.executeQuery()) {
                if (rs.next()) {
                    // Record exists, update it
                    try (PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) {
                        updateStmt.setInt(1, mark.getMarks());
                        updateStmt.setString(2, mark.getGrade());
                        updateStmt.setString(3, mark.getFeedback());
                        updateStmt.setInt(4, mark.getStudentId());
                        updateStmt.setInt(5, mark.getSubjectId());

                        int rowsUpdated = updateStmt.executeUpdate();
                        return rowsUpdated > 0;
                    }
                } else {
                    // Record does not exist, insert new
                    return insertMarks(mark);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error inserting or updating marks: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }
}
