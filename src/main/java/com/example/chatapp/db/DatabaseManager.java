package com.example.chatapp.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseManager {
    private Connection connection;

    public DatabaseManager() {
        try {
            // Remplacez par vos informations de connexion
            String url = "jdbc:mysql://localhost:3306/chat?allowPublicKeyRetrieval=true";
            String user = "root";
            String password = "rootmysql";
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertMessage(String sender, String message) {
        String sql = "INSERT INTO conversations (sender, message) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, sender);
            statement.setString(2, message);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}