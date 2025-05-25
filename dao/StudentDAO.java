package dao;

import util.DBUtil;
import model.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {
    public static List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();

        String query = "SELECT id, name FROM users WHERE role = 'student'";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                students.add(new Student(rs.getInt("id"), rs.getString("name")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return students;
    }
}
