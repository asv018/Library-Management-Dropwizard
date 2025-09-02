package com.librarymanagement.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    // Read database configuration from environment variables
    private static final String URL = System.getenv("DB_URL");
    private static final String USER = System.getenv("DB_USER");
    private static final String PASSWORD = System.getenv("DB_PASSWORD");

    static {
        if (URL == null || USER == null || PASSWORD == null) {
            throw new IllegalStateException("Database configuration environment variables (DB_URL, DB_USER, DB_PASSWORD) are not set.");
        }
    }

    public static Connection getConnection() throws SQLException {
        // The getConnection method now uses the values from the environment variables
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}