package model;

public class Grade {
    private String subject;
    private double marks;
    private String grade;
    private String studentEmail;

    public Grade(String subject, double marks, String grade, String studentEmail) {
        this.subject = subject;
        this.marks = marks;
        this.grade = grade;
        this.studentEmail = studentEmail;
    }

    public Grade(String subject, double marks, String grade) {
        this.subject = subject;
        this.marks = marks;
        this.grade = grade;
    }

    public String getSubject() { return subject; }
    public double getMarks() { return marks; }
    public String getGrade() { return grade; }
    public String getStudentEmail() { return studentEmail; }

    public void setSubject(String subject) { this.subject = subject; }
    public void setMarks(double marks) { this.marks = marks; }
    public void setGrade(String grade) { this.grade = grade; }
    public void setStudentEmail(String studentEmail) { this.studentEmail = studentEmail; }
}
