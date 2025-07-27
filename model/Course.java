package com.college.admission.model;

public class Course {
    private int courseId;
    private String courseName;
    private String courseCode;
    private String description;
    private int duration; // in years
    private int totalSeats;
    private int availableSeats;
    private double generalCutoff;
    private double obcCutoff;
    private double scCutoff;
    private double stCutoff;
    private double fees;
    private String department;
    private String status; // ACTIVE, INACTIVE

    // Default constructor
    public Course() {
        this.status = "ACTIVE";
    }

    // Parameterized constructor
    public Course(String courseName, String courseCode, String description, 
                 int duration, int totalSeats, double generalCutoff, 
                 double obcCutoff, double scCutoff, double stCutoff, 
                 double fees, String department) {
        this();
        this.courseName = courseName;
        this.courseCode = courseCode;
        this.description = description;
        this.duration = duration;
        this.totalSeats = totalSeats;
        this.availableSeats = totalSeats;
        this.generalCutoff = generalCutoff;
        this.obcCutoff = obcCutoff;
        this.scCutoff = scCutoff;
        this.stCutoff = stCutoff;
        this.fees = fees;
        this.department = department;
    }

    // Getters and Setters
    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(int totalSeats) {
        this.totalSeats = totalSeats;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    public double getGeneralCutoff() {
        return generalCutoff;
    }

    public void setGeneralCutoff(double generalCutoff) {
        this.generalCutoff = generalCutoff;
    }

    public double getObcCutoff() {
        return obcCutoff;
    }

    public void setObcCutoff(double obcCutoff) {
        this.obcCutoff = obcCutoff;
    }

    public double getScCutoff() {
        return scCutoff;
    }

    public void setScCutoff(double scCutoff) {
        this.scCutoff = scCutoff;
    }

    public double getStCutoff() {
        return stCutoff;
    }

    public void setStCutoff(double stCutoff) {
        this.stCutoff = stCutoff;
    }

    public double getFees() {
        return fees;
    }

    public void setFees(double fees) {
        this.fees = fees;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getCutoffByCategory(String category) {
        switch (category.toUpperCase()) {
            case "GENERAL":
                return generalCutoff;
            case "OBC":
                return obcCutoff;
            case "SC":
                return scCutoff;
            case "ST":
                return stCutoff;
            default:
                return generalCutoff;
        }
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseId=" + courseId +
                ", courseName='" + courseName + '\'' +
                ", courseCode='" + courseCode + '\'' +
                ", totalSeats=" + totalSeats +
                ", availableSeats=" + availableSeats +
                ", generalCutoff=" + generalCutoff +
                ", department='" + department + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}