package com.example.bella_italia.models;

import java.time.LocalDateTime;

public class OrderHistory {
    private long clientId;
    private long orderId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public OrderHistory(long clientId, long orderId, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.clientId = clientId;
        this.orderId = orderId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public long getClientId() {
        return clientId;
    }

    public long getOrderId() {
        return orderId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
