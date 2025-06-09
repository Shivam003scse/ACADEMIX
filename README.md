# 🎓 ACADEMIX – Academic Management System

Welcome to **ACADEMIX** — your all-in-one academic management platform built for seamless collaboration among students, teachers, and parents.

Designed with a **JavaFX** interface and powered by a **MySQL** backend, ACADEMIX ensures intuitive navigation, secure data handling, and smooth performance across all user roles.

---

## 🌟 Key Features

### 👥 Multi-Role Dashboards
- **Students** – View grades, feedback, and timetable.
- **Teachers** – Manage grades, provide feedback, update timetables.
- **Parents** – Track student performance and teacher remarks.

### 🔐 Authentication & Authorization
- Secure login and registration flow.
- Role-based access for data protection and personalization.

### 📊 Grade and Feedback Management
- Teachers can add/edit grades with subject-level details.
- Students and parents can view performance charts and textual feedback.

### 📆 Timetable Integration
- Role-specific timetable views.
- Teachers can update schedules directly via the dashboard.

### 💬 Communication Channel
- Feedback system between students and teachers.
- Parents can view this exchange for real-time updates.

### 🧭 Modern UI/UX
- Built using JavaFX with responsive FXML layouts and CSS styling.
- User-centric design for clarity and ease of use.

---

## 🔍 Evaluation Criteria (Review 2)

ACADEMIX has been developed in alignment with the following **Review 2 Guidelines** for project evaluation:

### ✅ Core Feature Implementation
- All essential modules like authentication, grade management, timetable view, and feedback system are **fully functional and integrated**.
- Role-based dashboards provide **distinct workflows** and UI elements.

### 🛡️ Error Handling & Robustness
- Invalid inputs trigger meaningful error messages using alert dialogs.
- No crash on null data or failed DB connection; fallback options and logging ensure continuity.

### 🔗 Integration of Components
- Smooth navigation between login, dashboards, and action panels.
- Unified design and communication between the **DAO**, **Model**, and **Controller** layers using MVC principles.

### ⚙️ Event Handling & Processing
- All buttons and input fields are managed by optimized **JavaFX event listeners**.
- Events are handled efficiently to avoid UI freeze or lag.

### 🧮 Data Validation
- **Client-side validation**: Prevent empty fields, incorrect data types, etc.
- **Server-side validation**: Checks in DAO layer before DB queries ensure consistent and secure operations.

### 🧹 Code Quality & Innovation
- Modular and reusable code with separation of concerns.
- Clear folder structure: `controller`, `model`, `dao`, `utils`, and `fxml`.
- Innovative additions include:
  - Graph-based performance analysis.
  - Role-specific UI.
  - Integrated timetable feature.

---

## 🗂️ Project Structure

ACADEMIX/
├── src/
│ ├── controller/ # Handles user actions and UI logic
│ ├── model/ # Java classes for Users, Grades, Subjects, etc.
│ ├── dao/ # Database access (CRUD) operations
│ ├── utils/ # Utility classes like DB config, OTP
│ └── Main.java # App entry point
├── resources/
│ ├── fxml/ # FXML layout files
│ ├── css/ # JavaFX UI styles
├── database/
│ └── academix_schema.sql # MySQL DB schema
├── pom.xml # Maven build file
└── README.md



🛠️ Set Up the Database
Use MySQL Workbench or any SQL client to import:

pgsql
Copy
Edit
database/academix_schema.sql


⚙️ Configure Your Database Credentials
Update DatabaseConfig.java:

String url = "jdbc:mysql://localhost:3306/academix";
String user = "root";
String password = "Shivam@8453";


▶️ Run the App
Open in IntelliJ or Eclipse.

Execute Main.java.

Or use terminal:


mvn clean install
mvn javafx:run


🛠️ Tech Stack

Java 8+

JavaFX – Modern GUI framework

MySQL – Relational database

JDBC – Database communication

Maven – Dependency management



📃 Database Overview
Normalized MySQL schema with key tables:

users – Stores login credentials and roles

students, teachers, parents – Profile data

subjects, grades, timetables – Academic records

feedback – Communication entries between teachers and students



👥 Developed By
Abhishek Nandan

Nitish Kumar Tiwari

Gaurav Bhati

Shivam Mishra

We’re passionate about building tools that empower educational systems with technology. 🌍

