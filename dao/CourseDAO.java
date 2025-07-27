package com.college.admission.dao;

import com.college.admission.model.Course;
import com.college.admission.util.DatabaseConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseDAO {
    
    public int addCourse(Course course) throws SQLException {
        String query = "INSERT INTO courses (course_name, course_code, description, duration, " +
                      "total_seats, available_seats, general_cutoff, obc_cutoff, sc_cutoff, " +
                      "st_cutoff, fees, department, status) " +
                      "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DatabaseConfig.getConnection();
            pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            
            pstmt.setString(1, course.getCourseName());
            pstmt.setString(2, course.getCourseCode());
            pstmt.setString(3, course.getDescription());
            pstmt.setInt(4, course.getDuration());
            pstmt.setInt(5, course.getTotalSeats());
            pstmt.setInt(6, course.getAvailableSeats());
            pstmt.setDouble(7, course.getGeneralCutoff());
            pstmt.setDouble(8, course.getObcCutoff());
            pstmt.setDouble(9, course.getScCutoff());
            pstmt.setDouble(10, course.getStCutoff());
            pstmt.setDouble(11, course.getFees());
            pstmt.setString(12, course.getDepartment());
            pstmt.setString(13, course.getStatus());
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows > 0) {
                rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    int courseId = rs.getInt(1);
                    course.setCourseId(courseId);
                    return courseId;
                }
            }
            return -1;
        } finally {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
        }
    }
    
    public Course getCourseById(int courseId) throws SQLException {
        String query = "SELECT * FROM courses WHERE course_id = ?";
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DatabaseConfig.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, courseId);
            
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToCourse(rs);
            }
            return null;
        } finally {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
        }
    }
    
    public Course getCourseByCode(String courseCode) throws SQLException {
        String query = "SELECT * FROM courses WHERE course_code = ?";
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DatabaseConfig.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, courseCode);
            
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToCourse(rs);
            }
            return null;
        } finally {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
        }
    }
    
    public List<Course> getAllCourses() throws SQLException {
        String query = "SELECT * FROM courses ORDER BY course_name";
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Course> courses = new ArrayList<Course>();
        
        try {
            conn = DatabaseConfig.getConnection();
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                courses.add(mapResultSetToCourse(rs));
            }
            return courses;
        } finally {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
        }
    }
    
    public List<Course> getActiveCourses() throws SQLException {
        String query = "SELECT * FROM courses WHERE status = 'ACTIVE' ORDER BY course_name";
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Course> courses = new ArrayList<Course>();
        
        try {
            conn = DatabaseConfig.getConnection();
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                courses.add(mapResultSetToCourse(rs));
            }
            return courses;
        } finally {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
        }
    }
    
    public List<Course> getCoursesByDepartment(String department) throws SQLException {
        String query = "SELECT * FROM courses WHERE department = ? ORDER BY course_name";
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Course> courses = new ArrayList<Course>();
        
        try {
            conn = DatabaseConfig.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, department);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                courses.add(mapResultSetToCourse(rs));
            }
            return courses;
        } finally {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
        }
    }
    
    public boolean updateCourse(Course course) throws SQLException {
        String query = "UPDATE courses SET course_name = ?, course_code = ?, description = ?, " +
                      "duration = ?, total_seats = ?, available_seats = ?, general_cutoff = ?, " +
                      "obc_cutoff = ?, sc_cutoff = ?, st_cutoff = ?, fees = ?, department = ?, " +
                      "status = ? WHERE course_id = ?";
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        
        try {
            conn = DatabaseConfig.getConnection();
            pstmt = conn.prepareStatement(query);
            
            pstmt.setString(1, course.getCourseName());
            pstmt.setString(2, course.getCourseCode());
            pstmt.setString(3, course.getDescription());
            pstmt.setInt(4, course.getDuration());
            pstmt.setInt(5, course.getTotalSeats());
            pstmt.setInt(6, course.getAvailableSeats());
            pstmt.setDouble(7, course.getGeneralCutoff());
            pstmt.setDouble(8, course.getObcCutoff());
            pstmt.setDouble(9, course.getScCutoff());
            pstmt.setDouble(10, course.getStCutoff());
            pstmt.setDouble(11, course.getFees());
            pstmt.setString(12, course.getDepartment());
            pstmt.setString(13, course.getStatus());
            pstmt.setInt(14, course.getCourseId());
            
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } finally {
            if (pstmt != null) pstmt.close();
        }
    }
    
    public boolean updateAvailableSeats(int courseId, int newAvailableSeats) throws SQLException {
        String query = "UPDATE courses SET available_seats = ? WHERE course_id = ?";
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        
        try {
            conn = DatabaseConfig.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, newAvailableSeats);
            pstmt.setInt(2, courseId);
            
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } finally {
            if (pstmt != null) pstmt.close();
        }
    }
    
    public boolean deleteCourse(int courseId) throws SQLException {
        String query = "DELETE FROM courses WHERE course_id = ?";
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        
        try {
            conn = DatabaseConfig.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, courseId);
            
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } finally {
            if (pstmt != null) pstmt.close();
        }
    }
    
    public boolean courseCodeExists(String courseCode) throws SQLException {
        String query = "SELECT COUNT(*) FROM courses WHERE course_code = ?";
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DatabaseConfig.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, courseCode);
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
    
    private Course mapResultSetToCourse(ResultSet rs) throws SQLException {
        Course course = new Course();
        course.setCourseId(rs.getInt("course_id"));
        course.setCourseName(rs.getString("course_name"));
        course.setCourseCode(rs.getString("course_code"));
        course.setDescription(rs.getString("description"));
        course.setDuration(rs.getInt("duration"));
        course.setTotalSeats(rs.getInt("total_seats"));
        course.setAvailableSeats(rs.getInt("available_seats"));
        course.setGeneralCutoff(rs.getDouble("general_cutoff"));
        course.setObcCutoff(rs.getDouble("obc_cutoff"));
        course.setScCutoff(rs.getDouble("sc_cutoff"));
        course.setStCutoff(rs.getDouble("st_cutoff"));
        course.setFees(rs.getDouble("fees"));
        course.setDepartment(rs.getString("department"));
        course.setStatus(rs.getString("status"));
        return course;
    }
}