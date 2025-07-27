# College Admission Management System #
This project is a comprehensive, console-based College Admission Management System built using Java and MySQL. It provides a complete solution for managing students, courses, applications, and the end-to-end admission process. The system is designed with a layered architecture (Model-DAO-Service) to ensure separation of concerns and maintainability.

Features
Student Management: Register new students, view all student records, search for students by ID, and update student information.

Course Management: Add new courses, manage course details (seats, fees, cutoffs), view all available courses, and update course information.

Application Management: Submit new applications for students, view applications by student or by course, and update the status of applications (e.g., APPROVED, REJECTED).

Automated Admission Processing: Automatically process admissions based on student merit scores and category-based cutoffs for each course.

Merit List Generation: Generate and display merit lists for any course, sorted by student merit scores.

Reporting: Export generated admission lists and course-specific merit lists to CSV files for record-keeping and analysis.

System Statistics: View a dashboard with key statistics like total applications, number of approved/rejected applications, and total available seats.

Tech Stack
Language: Core Java

Database: MySQL

Connectivity: Java Database Connectivity (JDBC)

Build/Dependencies: Standard Java project (no external libraries beyond MySQL Connector/J)

Project Structure
The project follows a standard layered architecture to separate different logical concerns:


com.college.admission
├── dao/                  // Data Access Objects for DB operations
│   ├── ApplicationDAO.java
│   ├── CourseDAO.java
│   └── StudentDAO.java
├── model/                // POJO (Plain Old Java Objects) classes
│   ├── Application.java
│   ├── Course.java
│   └── Student.java
├── service/              // Business logic layer
│   └── AdmissionService.java
├── util/                 // Utility classes
│   └── DatabaseConfig.java
└── CollegeAdmissionSystem.java // Main entry point of the application
model: Contains the data models (Student, Course, Application).

dao: Handles all the SQL queries and direct database interactions.

service: Implements the core business logic and orchestrates calls between DAOs.

util: Provides database configuration and connection management.

CollegeAdmissionSystem.java: The main class that runs the console-based user interface.

Setup and Installation
Follow these steps to set up and run the project on your local machine.

1. Prerequisites
Java Development Kit (JDK): Version 8 or higher.

MySQL Server: Ensure you have MySQL installed and running.

MySQL Connector/J: The JDBC driver for MySQL. Download the JAR file and add it to your project's classpath.

IDE: An IDE like IntelliJ IDEA, Eclipse, or VS Code with Java support.

2. Database Setup
Open your MySQL client (e.g., MySQL Workbench, command line).

Create a new database for the project:

sql
CREATE DATABASE college_admission;
Use the created database:

sql
USE college_admission;
Execute the SQL script provided in database.txt to create the necessary tables (students, courses, applications), views, and sample data.

3. Configuration
Open the DatabaseConfig.java file located in src/com/college/admission/util/.

Update the database connection details if they differ from the default values:

java
private static final String DB_URL = "jdbc:mysql://localhost:3306/college_admission";
private static final String DB_USERNAME = "root";
private static final String DB_PASSWORD = "your_password"; // <-- CHANGE THIS
4. Running the Application
Clone the repository or import the project into your IDE.

Ensure the MySQL Connector/J JAR file is included in your project's build path.

Locate and run the main method in the CollegeAdmissionSystem.java file.

The application will start, and you can interact with it through the console menu.

How to Use
Once the application is running, a command-line menu will guide you through the available options. You can navigate through different sub-menus for managing students, courses, applications, and generating reports.


=== College Admission Management System ===

=== MAIN MENU ===
1. Student Management
2. Course Management
3. Application Management
4. Admission Process
5. Reports
6. Exit
Enter your choice:
Database Schema
The database consists of three primary tables: students, courses, and applications.

students: Stores personal and academic details of student applicants.

courses: Stores details about the courses offered by the college, including seats and admission cutoffs.

applications: A linking table that stores applications submitted by students for various courses. It includes the application status and calculated merit score.

Foreign key constraints are used to maintain referential integrity between the tables. For detailed schema information, including triggers and views, refer to the database.txt file.
