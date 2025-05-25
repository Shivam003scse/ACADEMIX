# ACADEMIX
 Academix is a JavaFX-based Student  Management System that allows teachers, students, and parents to access and manage academic performance data. The system features secure login, role-based dashboards, grade tracking, feedback, and progress visualizations using interactive charts. Built with Java, JavaFX, and MySQL.


## 📌 Features

### ✅ Common Features
- User login and registration
- OTP-based email verification
- Role-based dashboards (Student, Teacher, Parent)

### 🎓 Student Dashboard
- View personal marks and grades
- Graphical analysis of performance
- View feedback from teachers
- View timetable

### 👨‍🏫 Teacher Dashboard
- Add / Update / Delete student marks
- View student details (Roll No, Email, Phone)
- Provide feedback to students
- View class timetable

### 👨‍👩‍👧 Parent Dashboard *(Coming Soon)*
- View child’s academic performance
- View feedback from teachers
- View class timetable

---

## 🛠️ Tech Stack

- **Frontend:** JavaFX, FXML, CSS
- **Backend:** Java
- **Database:** MySQL
- **Build Tool:** Maven

---

## 🗂️ Project Structure

com.academix/
│
├── controller/ # JavaFX Controllers
├── model/ # Data Models (User, Student, Grades, etc.)
├── dao/ # Database Access Objects
├── util/ # Utility classes (e.g., DB connection, email OTP)
├── fxml/ # FXML layouts

DATABASE
database_schema.sql
CREATE DATABASE academix;
USE academix;

-- Users table (stores all users, with role info)
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role ENUM('student', 'teacher', 'parent', 'admin') NOT NULL
);

-- Students table
CREATE TABLE students (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    roll_number VARCHAR(20) UNIQUE NOT NULL,
    phone VARCHAR(15),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Teachers table
CREATE TABLE teachers (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    subject VARCHAR(100),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Parents table
CREATE TABLE parents (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    student_id INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (student_id) REFERENCES students(id) ON DELETE CASCADE
);

-- Subjects table
CREATE TABLE subjects (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    code VARCHAR(20) UNIQUE NOT NULL
);

-- Marks table
CREATE TABLE marks (
    id INT AUTO_INCREMENT PRIMARY KEY,
    student_id INT NOT NULL,
    subject_id INT NOT NULL,
    marks_obtained DECIMAL(5,2),
    grade VARCHAR(2),
    FOREIGN KEY (student_id) REFERENCES students(id) ON DELETE CASCADE,
    FOREIGN KEY (subject_id) REFERENCES subjects(id) ON DELETE CASCADE
);

-- Feedback table
CREATE TABLE feedback (
    id INT AUTO_INCREMENT PRIMARY KEY,
    teacher_id INT NOT NULL,
    student_id INT NOT NULL,
    feedback_text TEXT,
    date_submitted TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (teacher_id) REFERENCES teachers(id) ON DELETE CASCADE,
    FOREIGN KEY (student_id) REFERENCES students(id) ON DELETE CASCADE
);

-- Timetable table
CREATE TABLE timetable (
    id INT AUTO_INCREMENT PRIMARY KEY,
    subject_id INT NOT NULL,
    teacher_id INT NOT NULL,
    day_of_week ENUM('Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'),
    start_time TIME,
    end_time TIME,
    room VARCHAR(20),
    FOREIGN KEY (subject_id) REFERENCES subjects(id) ON DELETE CASCADE,
    FOREIGN KEY (teacher_id) REFERENCES teachers(id) ON DELETE CASCADE
);


CREATE TABLE grades (
    id INT AUTO_INCREMENT PRIMARY KEY,
    student_email VARCHAR(100),
    subject VARCHAR(100),
    marks DOUBLE,
    grade VARCHAR(5)
);



PREVIEW.....
LOGIN PAGE....

![image](https://github.com/user-attachments/assets/60f006b0-0a3e-4632-ae62-50d1837d3afb)

