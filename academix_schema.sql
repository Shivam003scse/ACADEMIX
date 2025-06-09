-- Create database
CREATE DATABASE academix;
USE academix;

-- Users table (stores all users, with role info)
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role ENUM('student', 'teacher', 'parent') NOT NULL
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
    marks_obtained INT,
    grade VARCHAR(3),
    feedback TEXT,
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

-- Sample Data Insertions

-- Insert 1 teacher user
INSERT INTO users (name, email, password, role) VALUES
('Mr. John Smith', 'john.smith@academix.com', 'password123', 'teacher');

-- Insert teacher in teachers table (assume user_id = 1)
INSERT INTO teachers (user_id, subject) VALUES (1, 'Mathematics');

-- Insert 10 student users
INSERT INTO users (name, email, password, role) VALUES
('Alice Johnson', 'alice.johnson@academix.com', 'password123', 'student'),
('Bob Lee', 'bob.lee@academix.com', 'password123', 'student'),
('Charlie Kim', 'charlie.kim@academix.com', 'password123', 'student'),
('Diana Patel', 'diana.patel@academix.com', 'password123', 'student'),
('Ethan Brown', 'ethan.brown@academix.com', 'password123', 'student'),
('Fiona Green', 'fiona.green@academix.com', 'password123', 'student'),
('George White', 'george.white@academix.com', 'password123', 'student'),
('Hannah Black', 'hannah.black@academix.com', 'password123', 'student'),
('Ivan Grey', 'ivan.grey@academix.com', 'password123', 'student'),
('Julia Blue', 'julia.blue@academix.com', 'password123', 'student');

-- Insert students in students table (user_id 2-11)
INSERT INTO students (user_id, roll_number, phone) VALUES
(2, 'S1001', '9000000001'),
(3, 'S1002', '9000000002'),
(4, 'S1003', '9000000003'),
(5, 'S1004', '9000000004'),
(6, 'S1005', '9000000005'),
(7, 'S1006', '9000000006'),
(8, 'S1007', '9000000007'),
(9, 'S1008', '9000000008'),
(10, 'S1009', '9000000009'),
(11, 'S1010', '9000000010');

-- Insert 1 parent user
INSERT INTO users (name, email, password, role) VALUES
('Parent Johnson', 'parent.johnson@academix.com', 'password123', 'parent');

-- Insert parent for Alice Johnson (students.id = 1, user_id = 12)
INSERT INTO parents (user_id, student_id) VALUES (12, 1);

-- Insert 3 subjects
INSERT INTO subjects (name, code) VALUES
('Mathematics', 'MATH101'),
('Science', 'SCI101'),
('English', 'ENG101');

-- Insert 2 more subjects
INSERT INTO subjects (name, code) VALUES
('History', 'HIST101'),
('Computer Science', 'CS101');

-- Insert marks for all students in all 5 subjects
INSERT INTO marks (student_id, subject_id, marks_obtained, grade) VALUES
(1, 1, 95, 'A+'), (1, 2, 88, 'A'), (1, 3, 92, 'A+'), (1, 4, 85, 'A'), (1, 5, 90, 'A+'),
(2, 1, 78, 'B'), (2, 2, 85, 'A'), (2, 3, 80, 'B'), (2, 4, 75, 'B'), (2, 5, 82, 'A'),
(3, 1, 88, 'A'), (3, 2, 90, 'A+'), (3, 3, 84, 'A'), (3, 4, 80, 'B'), (3, 5, 86, 'A'),
(4, 1, 70, 'B'), (4, 2, 75, 'B'), (4, 3, 72, 'B'), (4, 4, 68, 'C'), (4, 5, 74, 'B'),
(5, 1, 60, 'C'), (5, 2, 65, 'C'), (5, 3, 68, 'C'), (5, 4, 62, 'C'), (5, 5, 70, 'B'),
(6, 1, 82, 'A'), (6, 2, 79, 'B'), (6, 3, 85, 'A'), (6, 4, 80, 'B'), (6, 5, 88, 'A'),
(7, 1, 90, 'A+'), (7, 2, 92, 'A+'), (7, 3, 88, 'A'), (7, 4, 85, 'A'), (7, 5, 91, 'A+'),
(8, 1, 55, 'D'), (8, 2, 60, 'C'), (8, 3, 58, 'D'), (8, 4, 62, 'C'), (8, 5, 65, 'C'),
(9, 1, 77, 'B'), (9, 2, 80, 'B'), (9, 3, 75, 'B'), (9, 4, 78, 'B'), (9, 5, 82, 'A'),
(10, 1, 68, 'C'), (10, 2, 72, 'B'), (10, 3, 70, 'B'), (10, 4, 74, 'B'), (10, 5, 78, 'B');

-- Insert feedback for some students from the teacher (teachers.id = 1)
INSERT INTO feedback (teacher_id, student_id, feedback_text) VALUES
(1, 1, 'Excellent performance!'),
(1, 2, 'Good improvement.'),
(1, 3, 'Keep up the good work.'),
(1, 4, 'Needs to participate more.'),
(1, 5, 'Can do better with more practice.');

-- Insert timetable for all 5 subjects (all taught by teacher 1)
INSERT INTO timetable (subject_id, teacher_id, day_of_week, start_time, end_time, room) VALUES
(1, 1, 'Monday', '09:00:00', '10:00:00', 'A101'),
(2, 1, 'Tuesday', '10:00:00', '11:00:00', 'A102'),
(3, 1, 'Wednesday', '11:00:00', '12:00:00', 'A103'),
(4, 1, 'Thursday', '12:00:00', '13:00:00', 'A104'),
(5, 1, 'Friday', '13:00:00', '14:00:00', 'A105');