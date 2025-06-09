package model;

public class Student {
    private int id;
    private String name;
    private String rollNumber;
    private String studentPhone;
    private String parentContact;

    public Student(int id, String name, String rollNumber, String studentPhone, String parentContact) {
        this.id = id;
        this.name = name;
        this.rollNumber = rollNumber;
        this.studentPhone = studentPhone;
        this.parentContact = parentContact;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getRollNumber() { return rollNumber; }
    public String getStudentPhone() { return studentPhone; }
    public String getParentContact() { return parentContact; }

    @Override
    public String toString() {
        return name + " (" + rollNumber + ")";
    }
}
