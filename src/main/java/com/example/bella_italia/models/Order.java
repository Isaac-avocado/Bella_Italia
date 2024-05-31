package com.example.bella_italia.models;

import java.time.LocalDateTime;

public class Order {
    private long id;
    private long clientId;
    private long dishId;
    private int quantity;
    private double total;
    private LocalDateTime orderDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Constructor, getters y setters
    public Order(long id, long clientId, long dishId, int quantity, double total, LocalDateTime orderDate, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.clientId = clientId;
        this.dishId = dishId;
        this.quantity = quantity;
        this.total = total;
        this.orderDate = orderDate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters y setters
    public long getId() { return id; }
    public long getClientId() { return clientId; }
    public long getDishId() { return dishId; }
    public int getQuantity() { return quantity; }
    public double getTotal() { return total; }
    public LocalDateTime getOrderDate() { return orderDate; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}
