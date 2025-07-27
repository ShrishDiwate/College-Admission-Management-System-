package com.college.admission.model;

import java.util.Date;

public class Application {
    private int applicationId;
    private int studentId;
    private int courseId;
    private Date applicationDate;
    private String status; // PENDING, APPROVED, REJECTED, WAITLISTED
    private String remarks;
    private double meritScore;
    private int preference; // 1st preference, 2nd preference, etc.
    private Date lastUpdated;

    // Default constructor
    public Application() {
        this.applicationDate = new Date();
        this.lastUpdated = new Date();
        this.status = "PENDING";
        this.preference = 1;
    }

    // Parameterized constructor
    public Application(int studentId, int courseId, int preference) {
        this();
        this.studentId = studentId;
        this.courseId = courseId;
        this.preference = preference;
    }

    // Constructor with all fields
    public Application(int studentId, int courseId, String status, 
                      String remarks, double meritScore, int preference) {
        this();
        this.studentId = studentId;
        this.courseId = courseId;
        this.status = status;
        this.remarks = remarks;
        this.meritScore = meritScore;
        this.preference = preference;
    }

    // Getters and Setters
    public int getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(int applicationId) {
        this.applicationId = applicationId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public Date getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(Date applicationDate) {
        this.applicationDate = applicationDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
        this.lastUpdated = new Date();
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public double getMeritScore() {
        return meritScore;
    }

    public void setMeritScore(double meritScore) {
        this.meritScore = meritScore;
    }

    public int getPreference() {
        return preference;
    }

    public void setPreference(int preference) {
        this.preference = preference;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public boolean isApproved() {
        return "APPROVED".equals(status);
    }

    public boolean isRejected() {
        return "REJECTED".equals(status);
    }

    public boolean isPending() {
        return "PENDING".equals(status);
    }

    public boolean isWaitlisted() {
        return "WAITLISTED".equals(status);
    }

    @Override
    public String toString() {
        return "Application{" +
                "applicationId=" + applicationId +
                ", studentId=" + studentId +
                ", courseId=" + courseId +
                ", applicationDate=" + applicationDate +
                ", status='" + status + '\'' +
                ", meritScore=" + meritScore +
                ", preference=" + preference +
                '}';
    }
}