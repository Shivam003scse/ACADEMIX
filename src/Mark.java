package model;

public class Mark {
    private int id;
    private int studentId;
    private int subjectId;
    private int marksObtained;
    private String grade;
    private String feedback;

    // No-argument constructor
    public Mark() {}

    // Parameterized constructor
    public Mark(int studentId, int subjectId, int marksObtained, String grade, String feedback) {
        this.studentId = studentId;
        this.subjectId = subjectId;
        this.marksObtained = marksObtained;
        this.grade = grade;
        this.feedback = feedback;
    }

    // Getters
    public int getId() { return id; }
    public int getStudentId() { return studentId; }
    public int getSubjectId() { return subjectId; }
    public int getMarks() { return marksObtained; }
    public String getGrade() { return grade; }
    public String getFeedback() { return feedback; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setStudentId(int studentId) { this.studentId = studentId; }
    public void setSubjectId(int subjectId) { this.subjectId = subjectId; }
    public void setMarks(int marksObtained) { this.marksObtained = marksObtained; }
    public void setGrade(String grade) { this.grade = grade; }
    public void setFeedback(String feedback) { this.feedback = feedback; }
}
