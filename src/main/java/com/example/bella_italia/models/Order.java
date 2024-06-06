package com.example.bella_italia.models;

import java.time.LocalDateTime;
import java.util.List;

public class Order {
    private long id;
    private long clientId;
    private List<Platillo> platillos;
    private double total;
    private LocalDateTime orderDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Order(long id, long clientId, List<Platillo> platillos, double total, LocalDateTime orderDate, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.clientId = clientId;
        this.platillos = platillos;
        this.total = total;
        this.orderDate = orderDate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters y setters
    public long getId() {
        return id;
    }

    public long getClientId() {
        return clientId;
    }

    public List<Platillo> getPlatillos() {
        return platillos;
    }

    public double getTotal() {
        return total;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
