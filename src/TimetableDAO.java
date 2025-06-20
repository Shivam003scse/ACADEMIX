package dao;

import model.Timetable;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TimetableDAO {

    private Connection connection;

    // Constructor: Initialize DB connection
    public TimetableDAO(Connection connection) {
        this.connection = connection;
    }

    // Fetch all timetables with subject name and teacher name
    public List<Timetable> getAllTimetables() {
        List<Timetable> timetables = new ArrayList<>();

        String sql = "SELECT t.id, s.name AS subject, t.day_of_week, CONCAT(t.start_time, ' - ', t.end_time) AS time_slot, u.name AS teacher, t.room " +
                "FROM timetable t " +
                "JOIN subjects s ON t.subject_id = s.id " +
                "JOIN users u ON t.teacher_id = u.id";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String subject = rs.getString("subject");
                String day = rs.getString("day_of_week");
                String timeSlot = rs.getString("time_slot");
                String teacher = rs.getString("teacher");
                String room = rs.getString("room");

                Timetable timetable = new Timetable(id, 0, subject, day, timeSlot, 0, teacher, room);
                timetables.add(timetable);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return timetables;
    }

}
