package com.example.bella_italia.models;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlatillosModel {
    public static final String DB_URL = "jdbc:mysql://sql.freedb.tech:3306/freedb_Bella Italia";
    public static final String DB_USER = "freedb_isaac";
    private static final String DB_PASSWORD = "tQv#3G2fdCgAuM?";

    public boolean createDish(String name, Float price) {
        String query = "INSERT INTO dishes (name, price) VALUES (?,?)";
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, name);
            preparedStatement.setFloat(2, price);
            int result = preparedStatement.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Platillo> obtenerPlatillo() {
        List<Platillo> platillos = new ArrayList<>();
        String query = "SELECT name, price FROM dishes";
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                String nombrePlatillo = resultSet.getString("name");
                Float precioPlatillo = resultSet.getFloat("price");
                List<InventarioModel.Ingrediente> ingredientes = obtenerIngredientesPorPlatillo(nombrePlatillo); // Aquí obtienes los ingredientes del platillo
                platillos.add(new Platillo(nombrePlatillo, precioPlatillo, ingredientes)); // Pasas la lista de ingredientes al constructor de Platillo
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return platillos;
    }

    public List<InventarioModel.Ingrediente> obtenerIngredientesPorPlatillo(String nombrePlatillo) {
        List<InventarioModel.Ingrediente> ingredientes = new ArrayList<>();
        String query = "SELECT i.name, i.units " +
                "FROM ingredients i " +
                "INNER JOIN dish_ingredients di ON i.id = di.ingredient_id " +
                "INNER JOIN dishes d ON di.dish_id = d.id " +
                "WHERE d.name = ?";
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, nombrePlatillo);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String nombreIngrediente = resultSet.getString("name");
                    int cantidad = resultSet.getInt("units");
                    float cantidadFloat = (float) cantidad; // Convertir int a Float
                    ingredientes.add(new InventarioModel.Ingrediente(nombreIngrediente, cantidadFloat));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ingredientes;
    }





    public boolean deleteDish(String name) {
        String query = "DELETE FROM dishes WHERE name = ?";
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

    public boolean editDish(String oldName, String newName, Float price) {
        String query = "UPDATE dishes SET name = ?, price = ? WHERE name = ?";
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, newName);
            preparedStatement.setFloat(2, price);
            preparedStatement.setString(3, oldName);
            int result = preparedStatement.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Platillo getPlatillo(String nombrePlatillo) {
        Platillo platillo = null;
        String query = "SELECT name, price FROM dishes WHERE name = ?";
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, nombrePlatillo);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String nombre = resultSet.getString("name");
                    Float precio = resultSet.getFloat("price");
                    List<InventarioModel.Ingrediente> ingredientes = obtenerIngredientesPorPlatillo(nombrePlatillo);
                    platillo = new Platillo(nombre, precio, ingredientes);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return platillo;
    }

    public boolean verificarIngredienteExistente(String nombreIngrediente) {
        // Variable para almacenar el resultado de la verificación
        boolean ingredienteExiste = false;

        // Consulta SQL para verificar la existencia del ingrediente en la tabla 'ingredients'
        String query = "SELECT COUNT(*) AS count FROM ingredients WHERE name = ?";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            // Establecer el nombre del ingrediente en la consulta preparada
            preparedStatement.setString(1, nombreIngrediente);

            // Ejecutar la consulta y obtener el resultado
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                // Si hay al menos una fila en el resultado, significa que el ingrediente existe
                if (resultSet.next()) {
                    int count = resultSet.getInt("count");
                    ingredienteExiste = count > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Devolver true si el ingrediente existe, de lo contrario, false
        return ingredienteExiste;
    }

    public boolean agregarIngredienteAPlatillo(Platillo platillo, String nombreIngrediente) {
        // Verificar primero si el ingrediente existe en la base de datos
        if (!verificarIngredienteExistente(nombreIngrediente)) {
            return false; // El ingrediente no existe, no se puede agregar al platillo
        }

        // Obtener el ID del platillo recién creado
        int platilloId = obtenerIdPlatillo(platillo);

        // Verificar que se haya obtenido un ID válido
        if (platilloId == -1) {
            return false; // Error al obtener el ID del platillo
        }

        String query = "INSERT INTO dish_ingredients (dish_id, ingredient_id, created_at, updated_at) VALUES (?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)";
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, platilloId);
            preparedStatement.setInt(2, obtenerIdIngrediente(nombreIngrediente));
            int result = preparedStatement.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int obtenerIdIngrediente(String nombreIngrediente) {
        String query = "SELECT id FROM ingredients WHERE name = ?";
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, nombreIngrediente);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
    static int obtenerIdPlatillo(Platillo platillo) {
        String query = "SELECT id FROM dishes WHERE name = ?";
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, platillo.getName());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("id"); // Devuelve el ID del platillo encontrado
            } else {
                return -1; // No se encontró el platillo, devuelve -1
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1; // Devuelve -1 en caso de error
        }
    }

}
