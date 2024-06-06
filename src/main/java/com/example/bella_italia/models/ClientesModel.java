package com.example.bella_italia.models;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientesModel {
    // Datos de conexión a la base de datos
    private static final String DB_URL = "jdbc:mysql://sql.freedb.tech:3306/freedb_Bella Italia";
    private static final String DB_USER = "freedb_isaac";
    public static final String DB_PASSWORD = "tQv#3G2fdCgAuM?";

    // Método para insertar un nuevo cliente en la base de datos
    public void insertCliente(String name, String email, String address, String city, String zip) {
        String query = "INSERT INTO clients (name, email, address, city, zip) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, name);
            statement.setString(2, email);
            statement.setString(3, address);
            statement.setString(4, city);
            statement.setString(5, zip);

            statement.executeUpdate();

            // Ejecutar los métodos adicionales
            long clientId = getLastInsertId(connection); // Obtener el ID del cliente insertado
            crearOrderHistory(connection, clientId);
            crearOrder(connection, clientId);

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error al insertar el cliente en la base de datos: " + e.getMessage());
        }
    }

    private long getLastInsertId(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT LAST_INSERT_ID()")) {
            if (resultSet.next()) {
                return resultSet.getLong(1);
            } else {
                throw new SQLException("No se pudo obtener el último ID insertado");
            }
        }
    }

    private void crearOrderHistory(Connection connection, long clientId) throws SQLException {
        String insertOrderHistorySQL = "INSERT INTO order_history (client_id, created_at, updated_at) VALUES (?, NOW(), NOW())";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertOrderHistorySQL)) {
            preparedStatement.setLong(1, clientId);
            preparedStatement.executeUpdate();
        }
    }

    private void crearOrder(Connection connection, long clientId) throws SQLException {
        String insertOrderSQL = "INSERT INTO orders (client_id, dish_id, cuantity, total, order_date, created_at, updated_at) VALUES (?, NULL, 0, 0.0, NOW(), NOW(), NOW())";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertOrderSQL)) {
            preparedStatement.setLong(1, clientId);
            preparedStatement.executeUpdate();
        }
    }

    // Método para obtener todos los clientes de la base de datos
    public List<Cliente> obtenerClientes() {
        List<Cliente> clientes = new ArrayList<>();
        String query = "SELECT * FROM clients";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nombre = resultSet.getString("name");
                String email = resultSet.getString("email");
                String address = resultSet.getString("address");
                String city = resultSet.getString("city");
                String zip = resultSet.getString("zip");

                Cliente cliente = new Cliente(id, nombre, email, address, zip, city);
                clientes.add(cliente);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error al obtener los clientes de la base de datos: " + e.getMessage());
        }

        return clientes;
    }
    // Método para eliminar un cliente de la base de datos por su ID
    public void eliminarCliente(int idCliente) {
        String query = "DELETE FROM clients WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idCliente);
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Cliente eliminado exitosamente");
            } else {
                System.out.println("No se encontró ningún cliente con el ID proporcionado");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error al eliminar el cliente de la base de datos: " + e.getMessage());
        }
    }


    public void editarCliente(int idCliente) {
        String query = "UPDATE clients SET name = ?, email = ?, address = ?, city = ?, zip = ? WHERE id = ?";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query)) {

            // Configurar los parámetros de la consulta SQL
            statement.setString(1, "name");
            statement.setString(2, "email");
            statement.setString(3, "address");
            statement.setString(4, "city");
            statement.setString(5, "zip");
            statement.setInt(6, idCliente);

            // Ejecutar la actualización del cliente
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error al editar el cliente en la base de datos: " + e.getMessage());
        }
    }

    public void actualizarCliente(Cliente cliente) throws Exception {
        String query = "UPDATE clients SET name = ?, email = ?, address = ?, city = ?, zip = ? WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, cliente.getName());
            pstmt.setString(2, cliente.getEmail());
            pstmt.setString(3, cliente.getAddress());
            pstmt.setString(4, cliente.getCity());
            pstmt.setString(5, cliente.getZip());
            pstmt.setInt(6, cliente.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new Exception("Error al actualizar el cliente", e);
        }
    }


}
