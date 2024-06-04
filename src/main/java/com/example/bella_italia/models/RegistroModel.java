package com.example.bella_italia.models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegistroModel {
    // Datos de conexión a la base de datos (cambia estos valores según tu configuración)
    public static final String DB_URL = "jdbc:mysql://sql.freedb.tech:3306/freedb_Bella Italia";
    public static final String DB_USER = "freedb_isaac";
    private static final String DB_PASSWORD = "tQv#3G2fdCgAuM?";

    public boolean register(String email, String password) {
        String query = "INSERT INTO users (email, password) VALUES (?, ?)";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);

            int result = preparedStatement.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
