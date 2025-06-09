package model;

public class Grade {
    private String subject;
    private double marks;
    private String grade;
    private String feedback;

    public Grade(String subject, double marks, String grade, String feedback) {
        this.subject = subject;
        this.marks = marks;
        this.grade = grade;
        this.feedback = feedback;
    }

    public Grade(String subject, double marks, String grade) {
        this(subject, marks, grade, null);
    }

    public String getSubject() { return subject; }
    public double getMarks() { return marks; }
    public String getGrade() { return grade; }
    public String getFeedback() { return feedback; }

    public void setSubject(String subject) { this.subject = subject; }
    public void setMarks(double marks) { this.marks = marks; }
    public void setGrade(String grade) { this.grade = grade; }
    public void setFeedback(String feedback) { this.feedback = feedback; }
}
