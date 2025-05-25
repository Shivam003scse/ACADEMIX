package dao;

import util.DBUtil;
import model.Subject;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SubjectDAO {
    public static List<Subject> getAllSubjects() {
        List<Subject> subjects = new ArrayList<>();

        String query = "SELECT id, name FROM subjects";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                subjects.add(new Subject(rs.getInt("id"), rs.getString("name")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return subjects;
    }
}
