package com.example.bella_italia.models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientesModel {
    // Datos de conexión a la base de datos
    private static final String DB_URL = "jdbc:mysql://sql.freedb.tech:3306/freedb_Bella Italia";
    private static final String DB_USER = "freedb_isaac";
    private static final String DB_PASSWORD = "tQv#3G2fdCgAuM?";

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
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error al insertar el cliente en la base de datos: " + e.getMessage());
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
    public void actualizarCliente(Cliente cliente) throws Exception {
        String query = "UPDATE clientes SET name = ?, email = ?, address = ?, city = ?, zip = ? WHERE id = ?";

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
