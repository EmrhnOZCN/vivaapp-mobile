package com.example.vivaapp_mobile.model;

public class OrderItem {
    private int orderId;
    private int userId;
    private String[] products;
    private int quantity;
    private String orderDate;

    public OrderItem(int orderId, int userId, String[] products, int quantity, String orderDate) {
        this.orderId = orderId;
        this.userId = userId;
        this.products = products;
        this.quantity = quantity;
        this.orderDate = orderDate;
    }

    public int getOrderId() {
        return orderId;
    }

    public int getUserId() {
        return userId;
    }

    public String[] getProducts() {
        return products;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getOrderDate() {
        return orderDate;
    }
}
