package com.shophub.backend.dto;

import com.shophub.backend.entity.Order;
import jakarta.validation.constraints.NotNull;

public class OrderStatusRequest {

    @NotNull(message = "Status is required")
    private Order.OrderStatus status;

    public OrderStatusRequest() {
    }

    public OrderStatusRequest(Order.OrderStatus status) {
        this.status = status;
    }

    public Order.OrderStatus getStatus() {
        return status;
    }

    public void setStatus(Order.OrderStatus status) {
        this.status = status;
    }
}
