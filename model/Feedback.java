package model;

import java.time.LocalDateTime;

public class Feedback {
    private int id;
    private int teacherId;
    private int studentId;
    private String feedbackText;
    private LocalDateTime dateSubmitted;

    public Feedback(int id, int teacherId, int studentId, String feedbackText, LocalDateTime dateSubmitted) {
        this.id = id;
        this.teacherId = teacherId;
        this.studentId = studentId;
        this.feedbackText = feedbackText;
        this.dateSubmitted = dateSubmitted;
    }

    public Feedback(int teacherId, int studentId, String feedbackText) {
        this.teacherId = teacherId;
        this.studentId = studentId;
        this.feedbackText = feedbackText;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public int getStudentId() {
        return studentId;
    }

    public String getFeedbackText() {
        return feedbackText;
    }

    public LocalDateTime getDateSubmitted() {
        return dateSubmitted;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public void setFeedbackText(String feedbackText) {
        this.feedbackText = feedbackText;
    }

    public void setDateSubmitted(LocalDateTime dateSubmitted) {
        this.dateSubmitted = dateSubmitted;
    }
}
