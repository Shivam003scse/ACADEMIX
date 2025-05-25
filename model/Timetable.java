package model;

public class Timetable {

    private int id;
    private int subjectId;
    private String subject;
    private String day;
    private String timeSlot;
    private int teacherId;
    private String teacher;

    // Full Constructor (with all fields)
    public Timetable(int id, int subjectId, String subject, String day, String timeSlot, int teacherId, String teacher) {
        this.id = id;
        this.subjectId = subjectId;
        this.subject = subject;
        this.day = day;
        this.timeSlot = timeSlot;
        this.teacherId = teacherId;
        this.teacher = teacher;
    }

    // Constructor without IDs (for display only)
    public Timetable(String subject, String day, String timeSlot, String teacher) {
        this.subject = subject;
        this.day = day;
        this.timeSlot = timeSlot;
        this.teacher = teacher;
    }

    // Additional constructor matching DAO with id, subject, day, timeSlot, teacher (no IDs)
    public Timetable(int id, String subject, String day, String timeSlot, String teacher) {
        this.id = id;
        this.subject = subject;
        this.day = day;
        this.timeSlot = timeSlot;
        this.teacher = teacher;
    }

    // =============== Getters ===============

    public int getId() {
        return id;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public String getSubject() {
        return subject;
    }

    public String getDay() {
        return day;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public String getTeacher() {
        return teacher;
    }

    // =============== Setters ===============

    public void setId(int id) {
        this.id = id;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public void setTimeSlot(String timeSlot) {
        this.timeSlot = timeSlot;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }
}
