package com.example.bella_italia.models;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderModel {

    // Datos de conexión a la base de datos (cambia estos valores según tu configuración)
    public static final String DB_URL = "jdbc:mysql://sql.freedb.tech:3306/freedb_Bella Italia";
    public static final String DB_USER = "freedb_isaac";
    private static final String DB_PASSWORD = "tQv#3G2fdCgAuM?";

    public long obtenerIdClientePorCorreo(String correo) {
        String sql = "SELECT id FROM clients WHERE email = ?";
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, correo);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getLong("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public boolean crearOrden(long clientId, List<Platillo> platillos) {
        String sqlOrder = "INSERT INTO orders (client_id, dish_id, quantity, total) VALUES (?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmtOrder = connection.prepareStatement(sqlOrder, Statement.RETURN_GENERATED_KEYS)) {
            double total = 0.0;
            for (Platillo platillo : platillos) {
                pstmtOrder.setLong(1, clientId);

                // Obtener el ID del platillo utilizando PlatillosModel
                long dishId = PlatillosModel.obtenerIdPlatillo(platillo);

                pstmtOrder.setLong(2, dishId);
                pstmtOrder.setInt(3, 1); // Aquí se puede ajustar la cantidad si es necesario
                pstmtOrder.setDouble(4, platillo.getPrice());
                total += platillo.getPrice();
                pstmtOrder.addBatch();
            }
            pstmtOrder.executeBatch();

            ResultSet rs = pstmtOrder.getGeneratedKeys();
            if (rs.next()) {
                long orderId = rs.getLong(1);
                crearOrderHistory(clientId, orderId);
            }

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean crearOrderHistory(long clientId, long orderId) {
        String sql = "INSERT INTO order_history (client_id, order_id) VALUES (?, ?)";
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setLong(1, clientId);
            pstmt.setLong(2, orderId);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Order> obtenerOrdenesPorCliente(long clientId) {
        List<Order> ordenes = new ArrayList<>();
        String sql = "SELECT * FROM orders WHERE client_id = ?";
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setLong(1, clientId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                long id = rs.getLong("id");
                long dishId = rs.getLong("dish_id");
                int quantity = rs.getInt("quantity");
                double total = rs.getDouble("total");
                Timestamp orderDate = rs.getTimestamp("order_date");
                Timestamp createdAt = rs.getTimestamp("created_at");
                Timestamp updatedAt = rs.getTimestamp("updated_at");

                // Obtener nombre y precio del platillo por su id
                String nombrePlatillo = obtenerNombrePlatilloPorId(dishId);
                double precioPlatillo = obtenerPrecioPlatilloPorId(dishId);

                // Crear el platillo con los datos obtenidos
                Platillo platillo = new Platillo(nombrePlatillo, (float) precioPlatillo, null);

                // Crear la orden con el platillo creado
                Order orden = new Order(id, clientId, List.of(platillo), total, orderDate.toLocalDateTime(), createdAt.toLocalDateTime(), updatedAt.toLocalDateTime());
                ordenes.add(orden);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ordenes;
    }

    public String obtenerNombrePlatilloPorId(long dishId) {
        String sql = "SELECT name FROM dishes WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setLong(1, dishId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString("name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public double obtenerPrecioPlatilloPorId(long dishId) {
        String sql = "SELECT price FROM dishes WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setLong(1, dishId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("price");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    public static Order getLastOrder(long clientId) {
        String query = "SELECT * FROM orders WHERE client_id = ? ORDER BY order_date DESC LIMIT 1";
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, clientId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    long orderId = resultSet.getLong("id");
                    double total = resultSet.getDouble("total");
                    LocalDateTime orderDate = resultSet.getTimestamp("order_date").toLocalDateTime();
                    LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
                    LocalDateTime updatedAt = resultSet.getTimestamp("updated_at").toLocalDateTime();

                    // Crear una instancia de OrderModel para llamar al método no estático obtenerPlatillosPorOrderId
                    OrderModel orderModel = new OrderModel();
                    List<Platillo> platillos = orderModel.obtenerPlatillosPorOrderId(orderId);

                    return new Order(orderId, clientId, platillos, total, orderDate, createdAt, updatedAt);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error al obtener la última orden: " + e.getMessage());
        }
        return null;
    }

    private List<Platillo> obtenerPlatillosPorOrderId(long orderId) {
        List<Platillo> platillos = new ArrayList<>();
        String query = "SELECT dish_id FROM orders WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, orderId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    long dishId = resultSet.getLong("dish_id");
                    String nombrePlatillo = obtenerNombrePlatilloPorId(dishId);
                    double precioPlatillo = obtenerPrecioPlatilloPorId(dishId);
                    platillos.add(new Platillo(nombrePlatillo, (float) precioPlatillo, null));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error al obtener los platillos de la última orden: " + e.getMessage());
        }
        return platillos;
    }

    public Platillo obtenerPlatilloPorNombre(String nombrePlatillo) {
        String sql = "SELECT * FROM dishes WHERE name = ?";
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, nombrePlatillo);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String nombre = rs.getString("name");
                float precio = rs.getFloat("price");
                // Aquí debes obtener los ingredientes del platillo si es necesario
                List<InventarioModel.Ingrediente> ingredientes = obtenerIngredientesDelPlatillo(nombrePlatillo);
                return new Platillo(nombre, precio, ingredientes);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<InventarioModel.Ingrediente> obtenerIngredientesDelPlatillo(String nombrePlatillo) {
        List<InventarioModel.Ingrediente> ingredientes = new ArrayList<>();
        String sql = "SELECT i.id, i.name, i.units FROM ingredients i INNER JOIN dish_ingredients pi ON i.id = pi.ingredient_id INNER JOIN dishes p ON pi.dish_id = p.id WHERE p.name = ?";
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, nombrePlatillo);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                long id = rs.getLong("id");
                String nombre = rs.getString("name");
                Float cantidad = rs.getFloat("units");
                InventarioModel.Ingrediente ingrediente = new InventarioModel.Ingrediente(nombre, cantidad);
                ingredientes.add(ingrediente);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ingredientes;
    }

    public List<Order> obtenerOrdenes() {
        List<Order> ordenes = new ArrayList<>();
        String sql = "SELECT * FROM orders";
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                long id = rs.getLong("id");
                long clientId = rs.getLong("client_id");
                double total = rs.getDouble("total");
                Timestamp orderDate = rs.getTimestamp("order_date");
                Timestamp createdAt = rs.getTimestamp("created_at");
                Timestamp updatedAt = rs.getTimestamp("updated_at");

                // Obtener la lista de platillos asociada a la orden
                List<Platillo> platillos = obtenerPlatillosPorOrderId(id);

                Order order = new Order(id, clientId, platillos, total, orderDate.toLocalDateTime(), createdAt.toLocalDateTime(), updatedAt.toLocalDateTime());
                ordenes.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ordenes;
    }

    public Cliente getClientByOrderId(long orderId) {
        String sql = "SELECT c.id, c.name, c.email, c.address, c.city, c.zip FROM clients c INNER JOIN orders o ON c.id = o.client_id WHERE o.id = ?";
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setLong(1, orderId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String address = rs.getString("address");
                String city = rs.getString("city");
                String zip = rs.getString("zip");
                return new Cliente(id, name, email, address, city, zip);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void descargarOrden(Order order) {
        try {
            // Crear un archivo con el ID de la orden y escribir los detalles de la orden en él
            String fileName = "Orden_" + order.getId() + ".txt";
            FileWriter writer = new FileWriter(fileName);
            writer.write("Detalles de la orden:\n");
            writer.write("ID: " + order.getId() + "\n");
            writer.write("Cliente: " + order.getClientId() + "\n");
            writer.write("Platillos:\n");
            for (Platillo platillo : order.getPlatillos()) {
                writer.write("- " + platillo.getName() + "\n");
            }
            writer.write("Total: $" + order.getTotal() + "\n");
            writer.close();
            System.out.println("Orden descargada como " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void eliminarOrden(Order order) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            // Eliminar la orden de la base de datos
            String sql = "DELETE FROM orders WHERE id = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setLong(1, order.getId());
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("La orden con ID " + order.getId() + " ha sido eliminada.");
            } else {
                System.out.println("No se encontró ninguna orden con ID " + order.getId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Platillo> obtenerTodosPlatillos() {
        List<Platillo> platillos = new ArrayList<>();

        String sql = "SELECT id, name, price FROM dishes";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nombre = resultSet.getString("name");
                float precio = resultSet.getFloat("price");

                Platillo platillo = new Platillo(nombre, precio, new ArrayList<>());
                platillo.actualizarId(id);

                platillos.add(platillo);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return platillos;
    }

    public void actualizarOrden(Order order) {
        String query = "UPDATE order_dishes SET quantity = ? WHERE order_id = ? AND dish_id = ?";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            for (Platillo platillo : order.getPlatillos()) {
                preparedStatement.setInt(1, platillo.getQuantity());
                preparedStatement.setLong(2, order.getId());
                preparedStatement.setLong(3, platillo.getId());
                preparedStatement.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
