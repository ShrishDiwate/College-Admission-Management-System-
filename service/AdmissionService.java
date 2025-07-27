package com.college.admission.service;

import com.college.admission.dao.ApplicationDAO;
import com.college.admission.dao.CourseDAO;
import com.college.admission.dao.StudentDAO;
import com.college.admission.model.Application;
import com.college.admission.model.Course;
import com.college.admission.model.Student;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AdmissionService {
    
    private StudentDAO studentDAO;
    private CourseDAO courseDAO;
    private ApplicationDAO applicationDAO;
    
    public AdmissionService() {
        this.studentDAO = new StudentDAO();
        this.courseDAO = new CourseDAO();
        this.applicationDAO = new ApplicationDAO();
    }
    
    // Student Management
    public int registerStudent(Student student) throws SQLException {
        // Check if email already exists
        if (studentDAO.emailExists(student.getEmail())) {
            throw new SQLException("Email already exists: " + student.getEmail());
        }
        
        // Validate grade12 percentage
        if (student.getGrade12Percentage() < 0 || student.getGrade12Percentage() > 100) {
            throw new IllegalArgumentException("Grade 12 percentage must be between 0 and 100");
        }
        
        return studentDAO.addStudent(student);
    }
    
    public Student getStudentById(int studentId) throws SQLException {
        return studentDAO.getStudentById(studentId);
    }
    
    public List<Student> getAllStudents() throws SQLException {
        return studentDAO.getAllStudents();
    }
    
    public boolean updateStudent(Student student) throws SQLException {
        return studentDAO.updateStudent(student);
    }
    
    // Course Management
    public int addCourse(Course course) throws SQLException {
        // Check if course code already exists
        if (courseDAO.courseCodeExists(course.getCourseCode())) {
            throw new SQLException("Course code already exists: " + course.getCourseCode());
        }
        
        // Validate course data
        if (course.getTotalSeats() <= 0) {
            throw new IllegalArgumentException("Total seats must be greater than 0");
        }
        
        return courseDAO.addCourse(course);
    }
    
    public Course getCourseById(int courseId) throws SQLException {
        return courseDAO.getCourseById(courseId);
    }
    
    public List<Course> getAllCourses() throws SQLException {
        return courseDAO.getAllCourses();
    }
    
    public List<Course> getActiveCourses() throws SQLException {
        return courseDAO.getActiveCourses();
    }
    
    public boolean updateCourse(Course course) throws SQLException {
        return courseDAO.updateCourse(course);
    }
    
    // Application Management
    public int submitApplication(int studentId, int courseId, int preference) throws SQLException {
        // Check if student exists
        Student student = studentDAO.getStudentById(studentId);
        if (student == null) {
            throw new SQLException("Student not found with ID: " + studentId);
        }
        
        // Check if course exists
        Course course = courseDAO.getCourseById(courseId);
        if (course == null) {
            throw new SQLException("Course not found with ID: " + courseId);
        }
        
        // Check if student has already applied for this course
        if (applicationDAO.hasStudentAppliedToCourse(studentId, courseId)) {
            throw new SQLException("Student has already applied for this course");
        }
        
        // Create application
        Application application = new Application(studentId, courseId, preference);
        application.setMeritScore(calculateMeritScore(student, course));
        
        return applicationDAO.addApplication(application);
    }
    
    public List<Application> getApplicationsByStudent(int studentId) throws SQLException {
        return applicationDAO.getApplicationsByStudentId(studentId);
    }
    
    public List<Application> getApplicationsByCourse(int courseId) throws SQLException {
        return applicationDAO.getApplicationsByCourseId(courseId);
    }
    
    public List<Application> getAllApplications() throws SQLException {
        return applicationDAO.getAllApplications();
    }
    
    // Merit Calculation
    public double calculateMeritScore(Student student, Course course) {
        double baseScore = student.getGrade12Percentage();
        
        // Add category-based bonus (example logic)
        switch (student.getCategory().toUpperCase()) {
            case "SC":
            case "ST":
                baseScore += 5.0; // 5% bonus for SC/ST
                break;
            case "OBC":
                baseScore += 2.5; // 2.5% bonus for OBC
                break;
            default:
                // No bonus for General category
                break;
        }
        
        // Ensure merit score doesn't exceed 100
        return Math.min(baseScore, 100.0);
    }
    
    // Admission Process
    public void processAdmissions() throws SQLException {
        List<Course> courses = courseDAO.getActiveCourses();
        
        for (Course course : courses) {
            processAdmissionForCourse(course);
        }
    }
    
    public void processAdmissionForCourse(Course course) throws SQLException {
        List<Application> applications = applicationDAO.getApplicationsByCourseId(course.getCourseId());
        
        // Separate applications by category
        List<Application> generalApps = new ArrayList<Application>();
        List<Application> obcApps = new ArrayList<Application>();
        List<Application> scApps = new ArrayList<Application>();
        List<Application> stApps = new ArrayList<Application>();
        
        for (Application app : applications) {
            Student student = studentDAO.getStudentById(app.getStudentId());
            if (student != null) {
                switch (student.getCategory().toUpperCase()) {
                    case "GENERAL":
                        generalApps.add(app);
                        break;
                    case "OBC":
                        obcApps.add(app);
                        break;
                    case "SC":
                        scApps.add(app);
                        break;
                    case "ST":
                        stApps.add(app);
                        break;
                }
            }
        }
        
        // Sort applications by merit score (descending)
        Comparator<Application> meritComparator = new Comparator<Application>() {
            public int compare(Application a1, Application a2) {
                return Double.compare(a2.getMeritScore(), a1.getMeritScore());
            }
        };
        
        Collections.sort(generalApps, meritComparator);
        Collections.sort(obcApps, meritComparator);
        Collections.sort(scApps, meritComparator);
        Collections.sort(stApps, meritComparator);
        
        // Process admissions based on cutoffs
        processApplicationsByCategory(generalApps, course.getGeneralCutoff(), "GENERAL");
        processApplicationsByCategory(obcApps, course.getObcCutoff(), "OBC");
        processApplicationsByCategory(scApps, course.getScCutoff(), "SC");
        processApplicationsByCategory(stApps, course.getStCutoff(), "ST");
    }
    
    private void processApplicationsByCategory(List<Application> applications, 
                                             double cutoff, String category) throws SQLException {
        for (Application app : applications) {
            if (app.getMeritScore() >= cutoff) {
                // Check if seats are available
                Course course = courseDAO.getCourseById(app.getCourseId());
                if (course.getAvailableSeats() > 0) {
                    // Approve application
                    applicationDAO.updateApplicationStatus(app.getApplicationId(), 
                                                         "APPROVED", 
                                                         "Approved based on merit and cutoff");
                    
                    // Update available seats
                    course.setAvailableSeats(course.getAvailableSeats() - 1);
                    courseDAO.updateCourse(course);
                } else {
                    // Waitlist application
                    applicationDAO.updateApplicationStatus(app.getApplicationId(), 
                                                         "WAITLISTED", 
                                                         "Waitlisted - no seats available");
                }
            } else {
                // Reject application
                applicationDAO.updateApplicationStatus(app.getApplicationId(), 
                                                     "REJECTED", 
                                                     "Rejected - below cutoff (" + cutoff + "%)");
            }
        }
    }
    
    // Merit List Generation
    public List<Application> generateMeritList(int courseId) throws SQLException {
        List<Application> applications = applicationDAO.getApplicationsByCourseId(courseId);
        
        // Sort by merit score (descending)
        Collections.sort(applications, new Comparator<Application>() {
            public int compare(Application a1, Application a2) {
                return Double.compare(a2.getMeritScore(), a1.getMeritScore());
            }
        });
        
        return applications;
    }
    
    public List<Application> generateAdmissionList() throws SQLException {
        List<Application> approvedApplications = applicationDAO.getApplicationsByStatus("APPROVED");
        
        // Sort by merit score (descending)
        Collections.sort(approvedApplications, new Comparator<Application>() {
            public int compare(Application a1, Application a2) {
                return Double.compare(a2.getMeritScore(), a1.getMeritScore());
            }
        });
        
        return approvedApplications;
    }
    
    // File Generation
    public void generateAdmissionListCSV(String filename) throws SQLException, IOException {
        List<Application> admissionList = generateAdmissionList();
        
        FileWriter writer = new FileWriter(filename);
        
        try {
            // Write header
            writer.append("Application ID,Student ID,Student Name,Course ID,Course Name,Merit Score,Status,Application Date\n");
            
            // Write data
            for (Application app : admissionList) {
                Student student = studentDAO.getStudentById(app.getStudentId());
                Course course = courseDAO.getCourseById(app.getCourseId());
                
                writer.append(String.valueOf(app.getApplicationId())).append(",");
                writer.append(String.valueOf(app.getStudentId())).append(",");
                writer.append(student.getFullName()).append(",");
                writer.append(String.valueOf(app.getCourseId())).append(",");
                writer.append(course.getCourseName()).append(",");
                writer.append(String.valueOf(app.getMeritScore())).append(",");
                writer.append(app.getStatus()).append(",");
                writer.append(app.getApplicationDate().toString()).append("\n");
            }
            
            writer.flush();
            System.out.println("Admission list exported to: " + filename);
        } finally {
            writer.close();
        }
    }
    
    public void generateMeritListCSV(int courseId, String filename) throws SQLException, IOException {
        List<Application> meritList = generateMeritList(courseId);
        Course course = courseDAO.getCourseById(courseId);
        
        FileWriter writer = new FileWriter(filename);
        
        try {
            // Write header
            writer.append("Merit List for Course: " + course.getCourseName() + " (" + course.getCourseCode() + ")\n");
            writer.append("Rank,Student ID,Student Name,Category,Merit Score,Status\n");
            
            // Write data
            int rank = 1;
            for (Application app : meritList) {
                Student student = studentDAO.getStudentById(app.getStudentId());
                
                writer.append(String.valueOf(rank)).append(",");
                writer.append(String.valueOf(app.getStudentId())).append(",");
                writer.append(student.getFullName()).append(",");
                writer.append(student.getCategory()).append(",");
                writer.append(String.valueOf(app.getMeritScore())).append(",");
                writer.append(app.getStatus()).append("\n");
                
                rank++;
            }
            
            writer.flush();
            System.out.println("Merit list exported to: " + filename);
        } finally {
            writer.close();
        }
    }
    
    // Additional utility methods
    public List<Student> getStudentsByCategory(String category) throws SQLException {
        return studentDAO.getStudentsByCategory(category);
    }
    
    public List<Course> getCoursesByDepartment(String department) throws SQLException {
        return courseDAO.getCoursesByDepartment(department);
    }
    
    public int getApplicationCountByCourse(int courseId) throws SQLException {
        return applicationDAO.getApplicationCountByCourse(courseId);
    }
    
    public List<Application> getApplicationsWithDetails() throws SQLException {
        return applicationDAO.getApplicationsWithDetails();
    }
    
    public boolean updateApplicationStatus(int applicationId, String status, String remarks) throws SQLException {
        return applicationDAO.updateApplicationStatus(applicationId, status, remarks);
    }
    
    public Application getApplicationById(int applicationId) throws SQLException {
        return applicationDAO.getApplicationById(applicationId);
    }
    
    public Student getStudentByEmail(String email) throws SQLException {
        return studentDAO.getStudentByEmail(email);
    }
    
    public Course getCourseByCode(String courseCode) throws SQLException {
        return courseDAO.getCourseByCode(courseCode);
    }
    
    public boolean deleteApplication(int applicationId) throws SQLException {
        return applicationDAO.deleteApplication(applicationId);
    }
    
    public boolean deleteStudent(int studentId) throws SQLException {
        return studentDAO.deleteStudent(studentId);
    }
    
    public boolean deleteCourse(int courseId) throws SQLException {
        return courseDAO.deleteCourse(courseId);
    }
    
    public boolean updateAvailableSeats(int courseId, int newAvailableSeats) throws SQLException {
        return courseDAO.updateAvailableSeats(courseId, newAvailableSeats);
    }
    
    // Validation methods
    public boolean validateStudentData(Student student) {
        if (student.getFirstName() == null || student.getFirstName().trim().isEmpty()) {
            return false;
        }
        if (student.getLastName() == null || student.getLastName().trim().isEmpty()) {
            return false;
        }
        if (student.getEmail() == null || student.getEmail().trim().isEmpty()) {
            return false;
        }
        if (student.getGrade12Percentage() < 0 || student.getGrade12Percentage() > 100) {
            return false;
        }
        if (student.getCategory() == null || student.getCategory().trim().isEmpty()) {
            return false;
        }
        return true;
    }
    
    public boolean validateCourseData(Course course) {
        if (course.getCourseName() == null || course.getCourseName().trim().isEmpty()) {
            return false;
        }
        if (course.getCourseCode() == null || course.getCourseCode().trim().isEmpty()) {
            return false;
        }
        if (course.getTotalSeats() <= 0) {
            return false;
        }
        if (course.getDuration() <= 0) {
            return false;
        }
        if (course.getFees() < 0) {
            return false;
        }
        return true;
    }
    
    // Statistical methods
    public double getAverageGrade12Percentage() throws SQLException {
        List<Student> students = studentDAO.getAllStudents();
        if (students.isEmpty()) {
            return 0.0;
        }
        
        double sum = 0.0;
        for (Student student : students) {
            sum += student.getGrade12Percentage();
        }
        return sum / students.size();
    }
    
    public int getTotalSeatsAvailable() throws SQLException {
        List<Course> courses = courseDAO.getActiveCourses();
        int totalSeats = 0;
        for (Course course : courses) {
            totalSeats += course.getAvailableSeats();
        }
        return totalSeats;
    }
    
    public int getTotalApplications() throws SQLException {
        return applicationDAO.getAllApplications().size();
    }
    
    public int getApprovedApplicationsCount() throws SQLException {
        return applicationDAO.getApplicationsByStatus("APPROVED").size();
    }
    
    public int getRejectedApplicationsCount() throws SQLException {
        return applicationDAO.getApplicationsByStatus("REJECTED").size();
    }
    
    public int getWaitlistedApplicationsCount() throws SQLException {
        return applicationDAO.getApplicationsByStatus("WAITLISTED").size();
    }
    
    public int getPendingApplicationsCount() throws SQLException {
        return applicationDAO.getApplicationsByStatus("PENDING").size();
    }
}