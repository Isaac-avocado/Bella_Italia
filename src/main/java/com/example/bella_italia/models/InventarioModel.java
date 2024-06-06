package com.example.bella_italia.models;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InventarioModel {
    // Datos de conexión a la base de datos (cambia estos valores según tu configuración)
    public static final String DB_URL = "jdbc:mysql://sql.freedb.tech:3306/freedb_Bella Italia";
    public static final String DB_USER = "freedb_isaac";
    private static final String DB_PASSWORD = "tQv#3G2fdCgAuM?";

    public boolean createIngredient(String name, Float units) {
        String query = "INSERT INTO ingredients (name, units) VALUES (?, ?)";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, name);
            preparedStatement.setFloat(2, units);

            int result = preparedStatement.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Ingrediente> obtenerIngredientes() {
        List<Ingrediente> ingredientes = new ArrayList<>();
        String query = "SELECT name, units FROM ingredients";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            // Iterar sobre el resultado y agregar los nombres de los ingredientes a la lista
            while (resultSet.next()) {
                String nombreIngrediente = resultSet.getString("name");
                Float unidadesIngrediente = resultSet.getFloat("units");
                ingredientes.add(new Ingrediente(nombreIngrediente, unidadesIngrediente));
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

    public boolean editIngredient(String oldName, String newName, Float units) {
        String query = "UPDATE ingredients SET name = ?, units = ? WHERE name = ?";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, newName);
            preparedStatement.setFloat(2, units);
            preparedStatement.setString(3, oldName);

            int result = preparedStatement.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static class Ingrediente {
        private String name;
        private Float units;

        public Ingrediente(String name, Float units) {
            this.name = name;
            this.units = units;
        }

        public String getName() {
            return name;
        }

        public Float getUnits() {
            return units;
        }
        @Override
        public String toString() {
            return name; // Devuelve solo el nombre del ingrediente
        }
    }
}
