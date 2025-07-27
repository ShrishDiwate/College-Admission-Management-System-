package com.college.admission.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConfig {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/college_admission";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "root";
    private static final String DB_DRIVER = "com.mysql.jdbc.Driver";
    
    private static Connection connection = null;
    
    static {
        try {
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                Properties props = new Properties();
                props.setProperty("user", DB_USERNAME);
                props.setProperty("password", DB_PASSWORD);
                props.setProperty("useSSL", "false");
                props.setProperty("serverTimezone", "UTC");
                props.setProperty("allowPublicKeyRetrieval", "true");
                
                connection = DriverManager.getConnection(DB_URL, props);
                System.out.println("Database connection established successfully.");
            } catch (SQLException e) {
                System.err.println("Failed to create database connection: " + e.getMessage());
                throw e;
            }
        }
        return connection;
    }
    
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Database connection closed.");
            } catch (SQLException e) {
                System.err.println("Error closing database connection: " + e.getMessage());
            }
        }
    }
    
    public static boolean testConnection() {
        try {
            Connection conn = getConnection();
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            System.err.println("Database connection test failed: " + e.getMessage());
            return false;
        }
    }
    
    // Method to get connection with custom parameters
    public static Connection getConnection(String url, String username, String password) throws SQLException {
        try {
            Properties props = new Properties();
            props.setProperty("user", username);
            props.setProperty("password", password);
            props.setProperty("useSSL", "false");
            props.setProperty("serverTimezone", "UTC");
            props.setProperty("allowPublicKeyRetrieval", "true");
            
            return DriverManager.getConnection(url, props);
        } catch (SQLException e) {
            System.err.println("Failed to create custom database connection: " + e.getMessage());
            throw e;
        }
    }
}