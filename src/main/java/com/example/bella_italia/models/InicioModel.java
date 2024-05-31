package com.example.bella_italia.models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InicioModel {
    // Datos de conexión a la base de datos (cambia estos valores según tu configuración)
    public static final String DB_URL = "jdbc:mysql://sql.freedb.tech:3306/freedb_Bella Italia";
    public static final String DB_USER = "freedb_isaac";
    private static final String DB_PASSWORD = "tQv#3G2fdCgAuM?";

    // Método para validar el inicio de sesión
    public boolean login(String email, String password) {
        // Query para verificar las credenciales en la base de datos
        String query = "SELECT * FROM users WHERE email = ? AND password = ?";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, email);
            statement.setString(2, password);

            try (ResultSet resultSet = statement.executeQuery()) {
                // Si se encuentra una coincidencia en la base de datos, el inicio de sesión es exitoso
                return resultSet.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Manejo de excepciones
            System.err.println("Error al conectar con la base de datos: " + e.getMessage());
            return false;
        }
    }
}
