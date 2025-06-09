package dao;

import util.DBUtil;
import model.Mark;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MarkDAO {

    public static boolean insertMarks(Mark mark) {
        String query = "INSERT INTO marks (student_id, subject_id, marks_obtained, grade, feedback) VALUES (?, ?, ?, ?, ?)";

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
        String insertQuery = "INSERT INTO marks (student_id, subject_id, marks_obtained, grade, feedback) VALUES (?, ?, ?, ?, ?)";
        String updateQuery = "UPDATE marks SET marks_obtained = ?, grade = ?, feedback = ? WHERE student_id = ? AND subject_id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkQuery)) {

            System.out.println("Checking for existing marks: student_id=" + mark.getStudentId() + ", subject_id=" + mark.getSubjectId());
            checkStmt.setInt(1, mark.getStudentId());
            checkStmt.setInt(2, mark.getSubjectId());

            try (ResultSet rs = checkStmt.executeQuery()) {
                if (rs.next()) {
                    System.out.println("Duplicate found for student_id=" + mark.getStudentId() + ", subject_id=" + mark.getSubjectId());
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
                    System.out.println("Inserting new marks: student_id=" + mark.getStudentId() + ", subject_id=" + mark.getSubjectId());
                    // Record does not exist, insert new
                    try (PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {
                        insertStmt.setInt(1, mark.getStudentId());
                        insertStmt.setInt(2, mark.getSubjectId());
                        insertStmt.setInt(3, mark.getMarks());
                        insertStmt.setString(4, mark.getGrade());
                        insertStmt.setString(5, mark.getFeedback());
                        int rowsInserted = insertStmt.executeUpdate();
                        return rowsInserted > 0;
                    }
                }
            }

        } catch (SQLException e) {
            System.err.println("Error inserting/updating marks: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    // Get all marks (with student and subject info if needed)
    public static List<Mark> getAllMarks() {
        List<Mark> marks = new ArrayList<>();
        String query = "SELECT * FROM marks";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Mark mark = new Mark(
                    rs.getInt("student_id"),
                    rs.getInt("subject_id"),
                    rs.getInt("marks_obtained"),
                    rs.getString("grade"),
                    rs.getString("feedback")
                );
                mark.setId(rs.getInt("id"));
                marks.add(mark);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return marks;
    }

    // Update mark by ID
    public static boolean updateMark(Mark mark) {
        String query = "UPDATE marks SET marks_obtained = ?, grade = ?, feedback = ? WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, mark.getMarks());
            stmt.setString(2, mark.getGrade());
            stmt.setString(3, mark.getFeedback());
            stmt.setInt(4, mark.getId());
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Delete mark by ID
    public static boolean deleteMark(int id) {
        String query = "DELETE FROM marks WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean marksExist(int studentId, int subjectId) {
        String query = "SELECT id FROM marks WHERE student_id = ? AND subject_id = ?";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, studentId);
            stmt.setInt(2, subjectId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next(); // Returns true if a record exists
            }
        } catch (SQLException e) {
            System.err.println("Error checking marks existence: " + e.getMessage());
            e.printStackTrace();
        }
        
        return false;
    }
}
