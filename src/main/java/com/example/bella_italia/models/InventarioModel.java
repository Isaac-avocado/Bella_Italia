package com.example.bella_italia.models;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InventarioModel {
    // Datos de conexión a la base de datos (cambia estos valores según tu configuración)
    public static final String DB_URL = "jdbc:mysql://sql.freedb.tech:3306/freedb_Bella Italia";
    public static final String DB_USER = "freedb_isaac";
    private static final String DB_PASSWORD = "tQv#3G2fdCgAuM?";

    public boolean createIngredient(String name) {
        String query = "INSERT INTO ingredients (name) VALUES (?)";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, name);

            int result = preparedStatement.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<String> obtenerIngredientes() {
        List<String> ingredientes = new ArrayList<>();
        String query = "SELECT name FROM ingredients";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            // Iterar sobre el resultado y agregar los nombres de los ingredientes a la lista
            while (resultSet.next()) {
                String nombreIngrediente = resultSet.getString("name");
                ingredientes.add(nombreIngrediente);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ingredientes;
    }
    public boolean deleteIngredient(String name) {
        String query = "DELETE FROM ingredients WHERE name = ?";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, name);

            int result = preparedStatement.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
