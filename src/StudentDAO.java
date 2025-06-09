package dao;

import util.DBUtil;
import model.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {
    public static List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        String query = "SELECT s.id, u.name, s.roll_number, s.phone AS student_phone, " +
                "'N/A' AS parent_contact " +
                "FROM students s " +
                "JOIN users u ON s.user_id = u.id " +
                "WHERE u.role = 'student'";
                
        System.out.println("Executing query: " + query);
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
             
            System.out.println("Query executed successfully");
            
            while (rs.next()) {
                String name = rs.getString("name");
                String rollNumber = rs.getString("roll_number");
                String studentPhone = rs.getString("student_phone");
                String parentContact = rs.getString("parent_contact");
                
                System.out.println("Retrieved student data:");
                System.out.println("  Name: " + name);
                System.out.println("  Roll Number: " + rollNumber);
                System.out.println("  Student Phone: " + studentPhone);
                System.out.println("  Parent Contact: " + parentContact);
                
                students.add(new Student(
                    rs.getInt("id"),
                    name,
                    rollNumber,
                    studentPhone != null ? studentPhone : "N/A",
                    parentContact
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error executing getAllStudents query:");
            System.err.println("Error message: " + e.getMessage());
            e.printStackTrace();
        }
        return students;
    }

    public static int getStudentIdByEmail(String email) {
        String query = "SELECT s.id FROM students s JOIN users u ON s.user_id = u.id WHERE u.email = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static String getTeacherNameById(int teacherId) {
        String query = "SELECT u.name FROM users u WHERE u.id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, teacherId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getStudentNameById(int studentId) {
        String query = "SELECT u.name FROM users u JOIN students s ON s.user_id = u.id WHERE s.id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, studentId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }
}
