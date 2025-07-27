package com.college.admission.dao;

import com.college.admission.model.Application;
import com.college.admission.util.DatabaseConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ApplicationDAO {
    
    public int addApplication(Application application) throws SQLException {
        String query = "INSERT INTO applications (student_id, course_id, application_date, " +
                      "status, remarks, merit_score, preference, last_updated) " +
                      "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DatabaseConfig.getConnection();
            pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            
            pstmt.setInt(1, application.getStudentId());
            pstmt.setInt(2, application.getCourseId());
            pstmt.setTimestamp(3, new Timestamp(application.getApplicationDate().getTime()));
            pstmt.setString(4, application.getStatus());
            pstmt.setString(5, application.getRemarks());
            pstmt.setDouble(6, application.getMeritScore());
            pstmt.setInt(7, application.getPreference());
            pstmt.setTimestamp(8, new Timestamp(application.getLastUpdated().getTime()));
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows > 0) {
                rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    int applicationId = rs.getInt(1);
                    application.setApplicationId(applicationId);
                    return applicationId;
                }
            }
            return -1;
        } finally {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
        }
    }
    
    public Application getApplicationById(int applicationId) throws SQLException {
        String query = "SELECT * FROM applications WHERE application_id = ?";
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DatabaseConfig.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, applicationId);
            
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToApplication(rs);
            }
            return null;
        } finally {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
        }
    }
    
    public List<Application> getApplicationsByStudentId(int studentId) throws SQLException {
        String query = "SELECT * FROM applications WHERE student_id = ? ORDER BY preference";
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Application> applications = new ArrayList<Application>();
        
        try {
            conn = DatabaseConfig.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, studentId);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                applications.add(mapResultSetToApplication(rs));
            }
            return applications;
        } finally {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
        }
    }
    
    public List<Application> getApplicationsByCourseId(int courseId) throws SQLException {
        String query = "SELECT * FROM applications WHERE course_id = ? ORDER BY merit_score DESC";
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Application> applications = new ArrayList<Application>();
        
        try {
            conn = DatabaseConfig.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, courseId);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                applications.add(mapResultSetToApplication(rs));
            }
            return applications;
        } finally {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
        }
    }
    
    public List<Application> getApplicationsByStatus(String status) throws SQLException {
        String query = "SELECT * FROM applications WHERE status = ? ORDER BY application_date DESC";
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Application> applications = new ArrayList<Application>();
        
        try {
            conn = DatabaseConfig.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, status);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                applications.add(mapResultSetToApplication(rs));
            }
            return applications;
        } finally {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
        }
    }
    
    public List<Application> getAllApplications() throws SQLException {
        String query = "SELECT * FROM applications ORDER BY application_date DESC";
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Application> applications = new ArrayList<Application>();
        
        try {
            conn = DatabaseConfig.getConnection();
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                applications.add(mapResultSetToApplication(rs));
            }
            return applications;
        } finally {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
        }
    }
    
    public List<Application> getApplicationsWithDetails() throws SQLException {
        String query = "SELECT a.*, s.first_name, s.last_name, s.email, s.category, s.grade12_percentage, " +
                      "c.course_name, c.course_code, c.department " +
                      "FROM applications a " +
                      "JOIN students s ON a.student_id = s.student_id " +
                      "JOIN courses c ON a.course_id = c.course_id " +
                      "ORDER BY a.application_date DESC";
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Application> applications = new ArrayList<Application>();
        
        try {
            conn = DatabaseConfig.getConnection();
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                applications.add(mapResultSetToApplication(rs));
            }
            return applications;
        } finally {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
        }
    }
    
    public boolean updateApplication(Application application) throws SQLException {
        String query = "UPDATE applications SET status = ?, remarks = ?, merit_score = ?, " +
                      "preference = ?, last_updated = ? WHERE application_id = ?";
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        
        try {
            conn = DatabaseConfig.getConnection();
            pstmt = conn.prepareStatement(query);
            
            pstmt.setString(1, application.getStatus());
            pstmt.setString(2, application.getRemarks());
            pstmt.setDouble(3, application.getMeritScore());
            pstmt.setInt(4, application.getPreference());
            pstmt.setTimestamp(5, new Timestamp(application.getLastUpdated().getTime()));
            pstmt.setInt(6, application.getApplicationId());
            
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } finally {
            if (pstmt != null) pstmt.close();
        }
    }
    
    public boolean updateApplicationStatus(int applicationId, String status, String remarks) throws SQLException {
        String query = "UPDATE applications SET status = ?, remarks = ?, last_updated = ? WHERE application_id = ?";
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        
        try {
            conn = DatabaseConfig.getConnection();
            pstmt = conn.prepareStatement(query);
            
            pstmt.setString(1, status);
            pstmt.setString(2, remarks);
            pstmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
            pstmt.setInt(4, applicationId);
            
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } finally {
            if (pstmt != null) pstmt.close();
        }
    }
    
    public boolean deleteApplication(int applicationId) throws SQLException {
        String query = "DELETE FROM applications WHERE application_id = ?";
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        
        try {
            conn = DatabaseConfig.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, applicationId);
            
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } finally {
            if (pstmt != null) pstmt.close();
        }
    }
    
    public boolean hasStudentAppliedToCourse(int studentId, int courseId) throws SQLException {
        String query = "SELECT COUNT(*) FROM applications WHERE student_id = ? AND course_id = ?";
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DatabaseConfig.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, studentId);
            pstmt.setInt(2, courseId);
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            return false;
        } finally {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
        }
    }
    
    public int getApplicationCountByCourse(int courseId) throws SQLException {
        String query = "SELECT COUNT(*) FROM applications WHERE course_id = ?";
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DatabaseConfig.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, courseId);
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        } finally {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
        }
    }
    
    private Application mapResultSetToApplication(ResultSet rs) throws SQLException {
        Application application = new Application();
        application.setApplicationId(rs.getInt("application_id"));
        application.setStudentId(rs.getInt("student_id"));
        application.setCourseId(rs.getInt("course_id"));
        application.setApplicationDate(rs.getTimestamp("application_date"));
        application.setStatus(rs.getString("status"));
        application.setRemarks(rs.getString("remarks"));
        application.setMeritScore(rs.getDouble("merit_score"));
        application.setPreference(rs.getInt("preference"));
        application.setLastUpdated(rs.getTimestamp("last_updated"));
        return application;
    }
}