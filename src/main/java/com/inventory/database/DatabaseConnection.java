package com.inventory.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    // Database URL
    private static final String URL = "jdbc:mysql://localhost:3306/inventory_db";

    // MySQL Username
    private static final String USER = "root";

    // MySQL Password
    private static final String PASSWORD = "9800fello";

    // Method to establish a connection
    public static Connection getConnection() {

        try {

            Connection connection = DriverManager.getConnection(
                    URL,
                    USER,
                    PASSWORD
            );

            System.out.println("✅ Database Connected Successfully!");

            return connection;

        } catch (SQLException e) {

            System.out.println("❌ Failed to connect to the database.");
            e.printStackTrace();

            return null;
        }
    }
}