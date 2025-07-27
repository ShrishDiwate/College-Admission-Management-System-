package com.college.admission;

import com.college.admission.service.AdmissionService;
import com.college.admission.model.Student;
import com.college.admission.model.Course;
import com.college.admission.model.Application;
import com.college.admission.util.DatabaseConfig;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class CollegeAdmissionSystem {
    private static AdmissionService admissionService;
    private static Scanner scanner;
    
    public static void main(String[] args) {
        admissionService = new AdmissionService();
        scanner = new Scanner(System.in);
        
        // Test database connection
        if (!DatabaseConfig.testConnection()) {
            System.err.println("Failed to connect to database. Exiting...");
            return;
        }
        
        System.out.println("=== College Admission Management System ===");
        
        while (true) {
            displayMainMenu();
            int choice = getIntInput("Enter your choice: ");
            
            try {
                switch (choice) {
                    case 1:
                        studentManagementMenu();
                        break;
                    case 2:
                        courseManagementMenu();
                        break;
                    case 3:
                        applicationManagementMenu();
                        break;
                    case 4:
                        admissionProcessMenu();
                        break;
                    case 5:
                        reportsMenu();
                        break;
                    case 6:
                        System.out.println("Thank you for using College Admission Management System!");
                        DatabaseConfig.closeConnection();
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
    }
    
    private static void displayMainMenu() {
        System.out.println("\n=== MAIN MENU ===");
        System.out.println("1. Student Management");
        System.out.println("2. Course Management");
        System.out.println("3. Application Management");
        System.out.println("4. Admission Process");
        System.out.println("5. Reports");
        System.out.println("6. Exit");
        System.out.print("Enter your choice: ");
    }
    
    private static void studentManagementMenu() throws SQLException {
        System.out.println("\n=== STUDENT MANAGEMENT ===");
        System.out.println("1. Register New Student");
        System.out.println("2. View All Students");
        System.out.println("3. Search Student by ID");
        System.out.println("4. Update Student");
        System.out.println("5. View Students by Category");
        System.out.println("6. Back to Main Menu");
        
        int choice = getIntInput("Enter your choice: ");
        
        switch (choice) {
            case 1:
                registerStudent();
                break;
            case 2:
                viewAllStudents();
                break;
            case 3:
                searchStudentById();
                break;
            case 4:
                updateStudent();
                break;
            case 5:
                viewStudentsByCategory();
                break;
            case 6:
                return;
            default:
                System.out.println("Invalid choice.");
        }
    }
    
    private static void courseManagementMenu() throws SQLException {
        System.out.println("\n=== COURSE MANAGEMENT ===");
        System.out.println("1. Add New Course");
        System.out.println("2. View All Courses");
        System.out.println("3. View Active Courses");
        System.out.println("4. Update Course");
        System.out.println("5. View Courses by Department");
        System.out.println("6. Back to Main Menu");
        
        int choice = getIntInput("Enter your choice: ");
        
        switch (choice) {
            case 1:
                addCourse();
                break;
            case 2:
                viewAllCourses();
                break;
            case 3:
                viewActiveCourses();
                break;
            case 4:
                updateCourse();
                break;
            case 5:
                viewCoursesByDepartment();
                break;
            case 6:
                return;
            default:
                System.out.println("Invalid choice.");
        }
    }
    
    private static void applicationManagementMenu() throws SQLException {
        System.out.println("\n=== APPLICATION MANAGEMENT ===");
        System.out.println("1. Submit New Application");
        System.out.println("2. View All Applications");
        System.out.println("3. View Applications by Student");
        System.out.println("4. View Applications by Course");
        System.out.println("5. Update Application Status");
        System.out.println("6. Back to Main Menu");
        
        int choice = getIntInput("Enter your choice: ");
        
        switch (choice) {
            case 1:
                submitApplication();
                break;
            case 2:
                viewAllApplications();
                break;
            case 3:
                viewApplicationsByStudent();
                break;
            case 4:
                viewApplicationsByCourse();
                break;
            case 5:
                updateApplicationStatus();
                break;
            case 6:
                return;
            default:
                System.out.println("Invalid choice.");
        }
    }
    
    private static void admissionProcessMenu() throws SQLException {
        System.out.println("\n=== ADMISSION PROCESS ===");
        System.out.println("1. Process All Admissions");
        System.out.println("2. Process Admission for Specific Course");
        System.out.println("3. Generate Merit List");
        System.out.println("4. Generate Admission List");
        System.out.println("5. Back to Main Menu");
        
        int choice = getIntInput("Enter your choice: ");
        
        switch (choice) {
            case 1:
                processAllAdmissions();
                break;
            case 2:
                processAdmissionForCourse();
                break;
            case 3:
                generateMeritList();
                break;
            case 4:
                generateAdmissionList();
                break;
            case 5:
                return;
            default:
                System.out.println("Invalid choice.");
        }
    }
    
    private static void reportsMenu() throws SQLException {
        System.out.println("\n=== REPORTS ===");
        System.out.println("1. Export Admission List to CSV");
        System.out.println("2. Export Merit List to CSV");
        System.out.println("3. View Statistics");
        System.out.println("4. Back to Main Menu");
        
        int choice = getIntInput("Enter your choice: ");
        
        switch (choice) {
            case 1:
                exportAdmissionList();
                break;
            case 2:
                exportMeritList();
                break;
            case 3:
                viewStatistics();
                break;
            case 4:
                return;
            default:
                System.out.println("Invalid choice.");
        }
    }
    
    // Student Management Methods
    private static void registerStudent() throws SQLException {
        System.out.println("\n=== REGISTER NEW STUDENT ===");
        
        String firstName = getStringInput("Enter First Name: ");
        String lastName = getStringInput("Enter Last Name: ");
        String email = getStringInput("Enter Email: ");
        String phone = getStringInput("Enter Phone: ");
        String address = getStringInput("Enter Address: ");
        double grade12Percentage = getDoubleInput("Enter Grade 12 Percentage: ");
        String category = getStringInput("Enter Category (GENERAL/OBC/SC/ST): ");
        
        Student student = new Student();
        student.setFirstName(firstName);
        student.setLastName(lastName);
        student.setEmail(email);
        student.setPhone(phone);
        student.setAddress(address);
        student.setGrade12Percentage(grade12Percentage);
        student.setCategory(category);
        student.setDateOfBirth(new Date()); // For simplicity, using current date
        student.setRegistrationDate(new Date());
        student.setStatus("ACTIVE");
        
        if (admissionService.validateStudentData(student)) {
            int studentId = admissionService.registerStudent(student);
            System.out.println("Student registered successfully with ID: " + studentId);
        } else {
            System.out.println("Invalid student data. Please check all fields.");
        }
    }
    
    private static void viewAllStudents() throws SQLException {
        System.out.println("\n=== ALL STUDENTS ===");
        List<Student> students = admissionService.getAllStudents();
        
        if (students.isEmpty()) {
            System.out.println("No students found.");
            return;
        }
        
        System.out.printf("%-5s %-15s %-15s %-25s %-10s %-10s%n", 
                         "ID", "First Name", "Last Name", "Email", "Grade12%", "Category");
        System.out.println("-------------------------------------------------------------------------");
        
        for (Student student : students) {
            System.out.printf("%-5d %-15s %-15s %-25s %-10.2f %-10s%n",
                             student.getStudentId(), student.getFirstName(), 
                             student.getLastName(), student.getEmail(),
                             student.getGrade12Percentage(), student.getCategory());
        }
    }
    
    private static void searchStudentById() throws SQLException {
        int studentId = getIntInput("Enter Student ID: ");
        Student student = admissionService.getStudentById(studentId);
        
        if (student != null) {
            System.out.println("\n=== STUDENT DETAILS ===");
            System.out.println("ID: " + student.getStudentId());
            System.out.println("Name: " + student.getFullName());
            System.out.println("Email: " + student.getEmail());
            System.out.println("Phone: " + student.getPhone());
            System.out.println("Address: " + student.getAddress());
            System.out.println("Grade 12 Percentage: " + student.getGrade12Percentage());
            System.out.println("Category: " + student.getCategory());
            System.out.println("Status: " + student.getStatus());
        } else {
            System.out.println("Student not found with ID: " + studentId);
        }
    }
    
    private static void updateStudent() throws SQLException {
        int studentId = getIntInput("Enter Student ID to update: ");
        Student student = admissionService.getStudentById(studentId);
        
        if (student == null) {
            System.out.println("Student not found with ID: " + studentId);
            return;
        }
        
        System.out.println("Current details:");
        System.out.println("Name: " + student.getFullName());
        System.out.println("Email: " + student.getEmail());
        System.out.println("Phone: " + student.getPhone());
        System.out.println("Grade 12 Percentage: " + student.getGrade12Percentage());
        
        System.out.println("\nEnter new details (press Enter to keep current value):");
        
        String newPhone = getStringInput("New Phone (" + student.getPhone() + "): ");
        if (!newPhone.isEmpty()) {
            student.setPhone(newPhone);
        }
        
        String newAddress = getStringInput("New Address (" + student.getAddress() + "): ");
        if (!newAddress.isEmpty()) {
            student.setAddress(newAddress);
        }
        
        if (admissionService.updateStudent(student)) {
            System.out.println("Student updated successfully.");
        } else {
            System.out.println("Failed to update student.");
        }
    }
    
    private static void viewStudentsByCategory() throws SQLException {
        String category = getStringInput("Enter Category (GENERAL/OBC/SC/ST): ");
        List<Student> students = admissionService.getStudentsByCategory(category);
        
        if (students.isEmpty()) {
            System.out.println("No students found for category: " + category);
            return;
        }
        
        System.out.println("\n=== STUDENTS - " + category + " ===");
        System.out.printf("%-5s %-20s %-25s %-10s%n", "ID", "Name", "Email", "Grade12%");
        System.out.println("------------------------------------------------------------");
        
        for (Student student : students) {
            System.out.printf("%-5d %-20s %-25s %-10.2f%n",
                             student.getStudentId(), student.getFullName(),
                             student.getEmail(), student.getGrade12Percentage());
        }
    }
    
    // Course Management Methods
    private static void addCourse() throws SQLException {
        System.out.println("\n=== ADD NEW COURSE ===");
        
        String courseName = getStringInput("Enter Course Name: ");
        String courseCode = getStringInput("Enter Course Code: ");
        String description = getStringInput("Enter Description: ");
        String department = getStringInput("Enter Department: ");
        int duration = getIntInput("Enter Duration (in years): ");
        int totalSeats = getIntInput("Enter Total Seats: ");
        double fees = getDoubleInput("Enter Fees: ");
        double generalCutoff = getDoubleInput("Enter General Cutoff: ");
        double obcCutoff = getDoubleInput("Enter OBC Cutoff: ");
        double scCutoff = getDoubleInput("Enter SC Cutoff: ");
        double stCutoff = getDoubleInput("Enter ST Cutoff: ");
        
        Course course = new Course();
        course.setCourseName(courseName);
        course.setCourseCode(courseCode);
        course.setDescription(description);
        course.setDepartment(department);
        course.setDuration(duration);
        course.setTotalSeats(totalSeats);
        course.setAvailableSeats(totalSeats);
        course.setFees(fees);
        course.setGeneralCutoff(generalCutoff);
        course.setObcCutoff(obcCutoff);
        course.setScCutoff(scCutoff);
        course.setStCutoff(stCutoff);
        course.setStatus("ACTIVE");
        
        if (admissionService.validateCourseData(course)) {
            int courseId = admissionService.addCourse(course);
            System.out.println("Course added successfully with ID: " + courseId);
        } else {
            System.out.println("Invalid course data. Please check all fields.");
        }
    }
    
    private static void viewAllCourses() throws SQLException {
        System.out.println("\n=== ALL COURSES ===");
        List<Course> courses = admissionService.getAllCourses();
        
        if (courses.isEmpty()) {
            System.out.println("No courses found.");
            return;
        }
        
        System.out.printf("%-5s %-20s %-10s %-15s %-8s %-8s %-10s%n", 
                         "ID", "Name", "Code", "Department", "Duration", "Seats", "Fees");
        System.out.println("--------------------------------------------------------------------------------");
        
        for (Course course : courses) {
            System.out.printf("%-5d %-20s %-10s %-15s %-8d %-8d %-10.2f%n",
                             course.getCourseId(), course.getCourseName(),
                             course.getCourseCode(), course.getDepartment(),
                             course.getDuration(), course.getAvailableSeats(),
                             course.getFees());
        }
    }
    
    private static void viewActiveCourses() throws SQLException {
        System.out.println("\n=== ACTIVE COURSES ===");
        List<Course> courses = admissionService.getActiveCourses();
        
        if (courses.isEmpty()) {
            System.out.println("No active courses found.");
            return;
        }
        
        System.out.printf("%-5s %-20s %-10s %-8s %-8s%n", 
                         "ID", "Name", "Code", "Total", "Available");
        System.out.println("--------------------------------------------------");
        
        for (Course course : courses) {
            System.out.printf("%-5d %-20s %-10s %-8d %-8d%n",
                             course.getCourseId(), course.getCourseName(),
                             course.getCourseCode(), course.getTotalSeats(),
                             course.getAvailableSeats());
        }
    }
    
    private static void updateCourse() throws SQLException {
        int courseId = getIntInput("Enter Course ID to update: ");
        Course course = admissionService.getCourseById(courseId);
        
        if (course == null) {
            System.out.println("Course not found with ID: " + courseId);
            return;
        }
        
        System.out.println("Current details:");
        System.out.println("Name: " + course.getCourseName());
        System.out.println("Total Seats: " + course.getTotalSeats());
        System.out.println("Available Seats: " + course.getAvailableSeats());
        System.out.println("Fees: " + course.getFees());
        
        System.out.println("\nEnter new details (press Enter to keep current value):");
        
        String newDescription = getStringInput("New Description: ");
        if (!newDescription.isEmpty()) {
            course.setDescription(newDescription);
        }
        
        String newFeesStr = getStringInput("New Fees (" + course.getFees() + "): ");
        if (!newFeesStr.isEmpty()) {
            course.setFees(Double.parseDouble(newFeesStr));
        }
        
        if (admissionService.updateCourse(course)) {
            System.out.println("Course updated successfully.");
        } else {
            System.out.println("Failed to update course.");
        }
    }
    
    private static void viewCoursesByDepartment() throws SQLException {
        String department = getStringInput("Enter Department: ");
        List<Course> courses = admissionService.getCoursesByDepartment(department);
        
        if (courses.isEmpty()) {
            System.out.println("No courses found for department: " + department);
            return;
        }
        
        System.out.println("\n=== COURSES - " + department + " ===");
        System.out.printf("%-5s %-20s %-10s %-8s %-10s%n", 
                         "ID", "Name", "Code", "Seats", "Fees");
        System.out.println("--------------------------------------------------");
        
        for (Course course : courses) {
            System.out.printf("%-5d %-20s %-10s %-8d %-10.2f%n",
                             course.getCourseId(), course.getCourseName(),
                             course.getCourseCode(), course.getAvailableSeats(),
                             course.getFees());
        }
    }
    
    // Application Management Methods
    private static void submitApplication() throws SQLException {
        System.out.println("\n=== SUBMIT APPLICATION ===");
        
        int studentId = getIntInput("Enter Student ID: ");
        int courseId = getIntInput("Enter Course ID: ");
        int preference = getIntInput("Enter Preference (1-5): ");
        
        int applicationId = admissionService.submitApplication(studentId, courseId, preference);
        System.out.println("Application submitted successfully with ID: " + applicationId);
    }
    
    private static void viewAllApplications() throws SQLException {
        System.out.println("\n=== ALL APPLICATIONS ===");
        List<Application> applications = admissionService.getAllApplications();
        
        if (applications.isEmpty()) {
            System.out.println("No applications found.");
            return;
        }
        
        System.out.printf("%-5s %-10s %-10s %-12s %-10s %-10s%n", 
                         "ID", "Student", "Course", "Merit Score", "Status", "Preference");
        System.out.println("---------------------------------------------------------------");
        
        for (Application app : applications) {
            System.out.printf("%-5d %-10d %-10d %-12.2f %-10s %-10d%n",
                             app.getApplicationId(), app.getStudentId(),
                             app.getCourseId(), app.getMeritScore(),
                             app.getStatus(), app.getPreference());
        }
    }
    
    private static void viewApplicationsByStudent() throws SQLException {
        int studentId = getIntInput("Enter Student ID: ");
        List<Application> applications = admissionService.getApplicationsByStudent(studentId);
        
        if (applications.isEmpty()) {
            System.out.println("No applications found for student ID: " + studentId);
            return;
        }
        
        System.out.printf("%-5s %-10s %-12s %-10s %-10s%n", 
                         "ID", "Course", "Merit Score", "Status", "Preference");
        System.out.println("-----------------------------------------------");
        
        for (Application app : applications) {
            System.out.printf("%-5d %-10d %-12.2f %-10s %-10d%n",
                             app.getApplicationId(), app.getCourseId(),
                             app.getMeritScore(), app.getStatus(), app.getPreference());
        }
    }
    
    private static void viewApplicationsByCourse() throws SQLException {
        int courseId = getIntInput("Enter Course ID: ");
        List<Application> applications = admissionService.getApplicationsByCourse(courseId);
        
        if (applications.isEmpty()) {
            System.out.println("No applications found for course ID: " + courseId);
            return;
        }
        
        System.out.printf("%-5s %-10s %-12s %-10s %-10s%n", 
                         "ID", "Student", "Merit Score", "Status", "Preference");
        System.out.println("-----------------------------------------------");
        
        for (Application app : applications) {
            System.out.printf("%-5d %-10d %-12.2f %-10s %-10d%n",
                             app.getApplicationId(), app.getStudentId(),
                             app.getMeritScore(), app.getStatus(), app.getPreference());
        }
    }
    
    private static void updateApplicationStatus() throws SQLException {
        int applicationId = getIntInput("Enter Application ID: ");
        String status = getStringInput("Enter New Status (PENDING/APPROVED/REJECTED/WAITLISTED): ");
        String remarks = getStringInput("Enter Remarks: ");
        
        if (admissionService.updateApplicationStatus(applicationId, status, remarks)) {
            System.out.println("Application status updated successfully.");
        } else {
            System.out.println("Failed to update application status.");
        }
    }
    
    // Admission Process Methods
    private static void processAllAdmissions() throws SQLException {
        System.out.println("\n=== PROCESSING ALL ADMISSIONS ===");
        admissionService.processAdmissions();
        System.out.println("All admissions processed successfully.");
    }
    
    private static void processAdmissionForCourse() throws SQLException {
        int courseId = getIntInput("Enter Course ID: ");
        Course course = admissionService.getCourseById(courseId);
        
        if (course == null) {
            System.out.println("Course not found with ID: " + courseId);
            return;
        }
        
        System.out.println("\n=== PROCESSING ADMISSION FOR: " + course.getCourseName() + " ===");
        admissionService.processAdmissionForCourse(course);
        System.out.println("Admission processed successfully for course: " + course.getCourseName());
    }
    
    private static void generateMeritList() throws SQLException {
        int courseId = getIntInput("Enter Course ID: ");
        List<Application> meritList = admissionService.generateMeritList(courseId);
        
        if (meritList.isEmpty()) {
            System.out.println("No applications found for course ID: " + courseId);
            return;
        }
        
        System.out.println("\n=== MERIT LIST ===");
        System.out.printf("%-5s %-10s %-12s %-10s%n", "Rank", "Student", "Merit Score", "Status");
        System.out.println("---------------------------------------");
        
        int rank = 1;
        for (Application app : meritList) {
            System.out.printf("%-5d %-10d %-12.2f %-10s%n",
                             rank++, app.getStudentId(), app.getMeritScore(), app.getStatus());
        }
    }
    
    private static void generateAdmissionList() throws SQLException {
        List<Application> admissionList = admissionService.generateAdmissionList();
        
        if (admissionList.isEmpty()) {
            System.out.println("No approved applications found.");
            return;
        }
        
        System.out.println("\n=== ADMISSION LIST ===");
        System.out.printf("%-5s %-10s %-10s %-12s%n", "App ID", "Student", "Course", "Merit Score");
        System.out.println("---------------------------------------");
        
        for (Application app : admissionList) {
            System.out.printf("%-5d %-10d %-10d %-12.2f%n",
                             app.getApplicationId(), app.getStudentId(),
                             app.getCourseId(), app.getMeritScore());
        }
    }
    
    // Reports Methods
    private static void exportAdmissionList() throws SQLException {
        String filename = getStringInput("Enter filename (e.g., admission_list.csv): ");
        try {
            admissionService.generateAdmissionListCSV(filename);
            System.out.println("Admission list exported successfully to: " + filename);
        } catch (Exception e) {
            System.err.println("Error exporting admission list: " + e.getMessage());
        }
    }
    
    private static void exportMeritList() throws SQLException {
        int courseId = getIntInput("Enter Course ID: ");
        String filename = getStringInput("Enter filename (e.g., merit_list.csv): ");
        try {
            admissionService.generateMeritListCSV(courseId, filename);
            System.out.println("Merit list exported successfully to: " + filename);
        } catch (Exception e) {
            System.err.println("Error exporting merit list: " + e.getMessage());
        }
    }
    
    private static void viewStatistics() throws SQLException {
        System.out.println("\n=== SYSTEM STATISTICS ===");
        System.out.println("Total Applications: " + admissionService.getTotalApplications());
        System.out.println("Approved Applications: " + admissionService.getApprovedApplicationsCount());
        System.out.println("Rejected Applications: " + admissionService.getRejectedApplicationsCount());
        System.out.println("Waitlisted Applications: " + admissionService.getWaitlistedApplicationsCount());
        System.out.println("Pending Applications: " + admissionService.getPendingApplicationsCount());
        System.out.println("Total Seats Available: " + admissionService.getTotalSeatsAvailable());
        System.out.println("Average Grade 12 Percentage: " + 
                          String.format("%.2f", admissionService.getAverageGrade12Percentage()));
    }
    
    // Utility Methods
    private static String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
    
    private static int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid integer.");
            }
        }
    }
    
    private static double getDoubleInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
}