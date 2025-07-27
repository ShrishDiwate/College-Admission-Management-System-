package com.college.admission.dao;

import com.college.admission.model.Student;
import com.college.admission.util.DatabaseConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {
    
    public int addStudent(Student student) throws SQLException {
        String query = "INSERT INTO students (first_name, last_name, email, phone, date_of_birth, " +
                      "address, grade12_percentage, category, registration_date, status) " +
                      "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DatabaseConfig.getConnection();
            pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            
            pstmt.setString(1, student.getFirstName());
            pstmt.setString(2, student.getLastName());
            pstmt.setString(3, student.getEmail());
            pstmt.setString(4, student.getPhone());
            pstmt.setDate(5, new java.sql.Date(student.getDateOfBirth().getTime()));
            pstmt.setString(6, student.getAddress());
            pstmt.setDouble(7, student.getGrade12Percentage());
            pstmt.setString(8, student.getCategory());
            pstmt.setTimestamp(9, new Timestamp(student.getRegistrationDate().getTime()));
            pstmt.setString(10, student.getStatus());
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows > 0) {
                rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    int studentId = rs.getInt(1);
                    student.setStudentId(studentId);
                    return studentId;
                }
            }
            return -1;
        } finally {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
        }
    }
    
    public Student getStudentById(int studentId) throws SQLException {
        String query = "SELECT * FROM students WHERE student_id = ?";
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DatabaseConfig.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, studentId);
            
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToStudent(rs);
            }
            return null;
        } finally {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
        }
    }
    
    public Student getStudentByEmail(String email) throws SQLException {
        String query = "SELECT * FROM students WHERE email = ?";
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DatabaseConfig.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, email);
            
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToStudent(rs);
            }
            return null;
        } finally {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
        }
    }
    
    public List<Student> getAllStudents() throws SQLException {
        String query = "SELECT * FROM students ORDER BY registration_date DESC";
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Student> students = new ArrayList<Student>();
        
        try {
            conn = DatabaseConfig.getConnection();
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                students.add(mapResultSetToStudent(rs));
            }
            return students;
        } finally {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
        }
    }
    
    public List<Student> getStudentsByCategory(String category) throws SQLException {
        String query = "SELECT * FROM students WHERE category = ? ORDER BY grade12_percentage DESC";
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Student> students = new ArrayList<Student>();
        
        try {
            conn = DatabaseConfig.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, category);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                students.add(mapResultSetToStudent(rs));
            }
            return students;
        } finally {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
        }
    }
    
    public boolean updateStudent(Student student) throws SQLException {
        String query = "UPDATE students SET first_name = ?, last_name = ?, email = ?, phone = ?, " +
                      "date_of_birth = ?, address = ?, grade12_percentage = ?, category = ?, status = ? " +
                      "WHERE student_id = ?";
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        
        try {
            conn = DatabaseConfig.getConnection();
            pstmt = conn.prepareStatement(query);
            
            pstmt.setString(1, student.getFirstName());
            pstmt.setString(2, student.getLastName());
            pstmt.setString(3, student.getEmail());
            pstmt.setString(4, student.getPhone());
            pstmt.setDate(5, new java.sql.Date(student.getDateOfBirth().getTime()));
            pstmt.setString(6, student.getAddress());
            pstmt.setDouble(7, student.getGrade12Percentage());
            pstmt.setString(8, student.getCategory());
            pstmt.setString(9, student.getStatus());
            pstmt.setInt(10, student.getStudentId());
            
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } finally {
            if (pstmt != null) pstmt.close();
        }
    }
    
    public boolean deleteStudent(int studentId) throws SQLException {
        String query = "DELETE FROM students WHERE student_id = ?";
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        
        try {
            conn = DatabaseConfig.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, studentId);
            
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } finally {
            if (pstmt != null) pstmt.close();
        }
    }
    
    public boolean emailExists(String email) throws SQLException {
        String query = "SELECT COUNT(*) FROM students WHERE email = ?";
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DatabaseConfig.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, email);
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
    
    private Student mapResultSetToStudent(ResultSet rs) throws SQLException {
        Student student = new Student();
        student.setStudentId(rs.getInt("student_id"));
        student.setFirstName(rs.getString("first_name"));
        student.setLastName(rs.getString("last_name"));
        student.setEmail(rs.getString("email"));
        student.setPhone(rs.getString("phone"));
        student.setDateOfBirth(rs.getDate("date_of_birth"));
        student.setAddress(rs.getString("address"));
        student.setGrade12Percentage(rs.getDouble("grade12_percentage"));
        student.setCategory(rs.getString("category"));
        student.setRegistrationDate(rs.getTimestamp("registration_date"));
        student.setStatus(rs.getString("status"));
        return student;
    }
}