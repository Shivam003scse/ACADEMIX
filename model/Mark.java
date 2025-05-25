package model;

public class Mark {
    private int studentId;
    private int subjectId;
    private int marks;
    private String grade;
    private String feedback;

    // No-argument constructor
    public Mark() {}

    // Parameterized constructor
    public Mark(int studentId, int subjectId, int marks, String grade, String feedback) {
        this.studentId = studentId;
        this.subjectId = subjectId;
        this.marks = marks;
        this.grade = grade;
        this.feedback = feedback;
    }

    // Getters
    public int getStudentId() { return studentId; }
    public int getSubjectId() { return subjectId; }
    public int getMarks() { return marks; }
    public String getGrade() { return grade; }
    public String getFeedback() { return feedback; }

    // Setters
    public void setStudentId(int studentId) { this.studentId = studentId; }
    public void setSubjectId(int subjectId) { this.subjectId = subjectId; }
    public void setMarks(int marks) { this.marks = marks; }
    public void setGrade(String grade) { this.grade = grade; }
    public void setFeedback(String feedback) { this.feedback = feedback; }
}
