package dao;

import util.DBUtil;
import model.Subject;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SubjectDAO {
    public static List<Subject> getAllSubjects() {
        List<Subject> subjects = new ArrayList<>();
        String query = "SELECT id, name, code FROM subjects";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                subjects.add(new Subject(rs.getInt("id"), rs.getString("name"), rs.getString("code")));
            }

            // If no subjects found, try to initialize them once
            if (subjects.isEmpty()) {
                try {
                    initializeSubjects();
                    // Try to get subjects one more time
                    try (PreparedStatement retryStmt = conn.prepareStatement(query);
                         ResultSet retryRs = retryStmt.executeQuery()) {
                        while (retryRs.next()) {
                            subjects.add(new Subject(retryRs.getInt("id"), retryRs.getString("name"), retryRs.getString("code")));
                        }
                    }
                } catch (SQLException initEx) {
                    System.err.println("Failed to initialize subjects: " + initEx.getMessage());
                }
            }

        } catch (SQLException e) {
            System.err.println("Error getting subjects: " + e.getMessage());
        }

        return subjects;
    }

    public static Subject getSubjectById(int id) {
        String query = "SELECT id, name, code FROM subjects WHERE id = ?";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Subject(rs.getInt("id"), rs.getString("name"), rs.getString("code"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error getting subject by ID: " + e.getMessage());
        }
        
        return null;
    }

    public static void initializeSubjects() {
        String[][] defaultSubjects = {
            {"Mathematics", "MATH"},
            {"Science", "SCI"},
            {"English", "ENG"}
        };

        String createTableQuery = "CREATE TABLE IF NOT EXISTS subjects (" +
                                "id INT PRIMARY KEY AUTO_INCREMENT, " +
                                "name VARCHAR(100) NOT NULL UNIQUE, " +
                                "code VARCHAR(100) NOT NULL UNIQUE)";

        String checkExistsQuery = "SELECT COUNT(*) as count FROM subjects";
        String insertQuery = "INSERT INTO subjects (name, code) VALUES (?, ?) ON DUPLICATE KEY UPDATE name = VALUES(name), code = VALUES(code)";

        try (Connection conn = DBUtil.getConnection()) {
            // First ensure table exists
            try (PreparedStatement createStmt = conn.prepareStatement(createTableQuery)) {
                createStmt.executeUpdate();
                System.out.println("Subjects table verified/created");
            }

            // Check if subjects exist
            boolean hasSubjects = false;
            try (PreparedStatement checkStmt = conn.prepareStatement(checkExistsQuery);
                 ResultSet rs = checkStmt.executeQuery()) {
                if (rs.next()) {
                    hasSubjects = rs.getInt("count") > 0;
                }
            }

            // Only insert if no subjects exist
            if (!hasSubjects) {
                try (PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {
                    for (String[] subject : defaultSubjects) {
                        insertStmt.setString(1, subject[0]);
                        insertStmt.setString(2, subject[1]);
                        insertStmt.executeUpdate();
                        System.out.println("Added subject: " + subject[0] + " (" + subject[1] + ")");
                    }
                }
                System.out.println("Successfully initialized subjects");
            } else {
                System.out.println("Subjects already exist, skipping initialization");
            }
        } catch (SQLException e) {
            System.err.println("Error initializing subjects: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
