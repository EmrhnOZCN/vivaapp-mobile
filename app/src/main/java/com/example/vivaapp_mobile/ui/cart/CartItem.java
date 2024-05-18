package com.example.vivaapp_mobile.ui.cart;

public class CartItem {
    private String name;
    private double price;
    private String imageUrl;
    private int quantity;

    public CartItem(String name, double price, int imageUrl, int quantity) {
        this.name = name;
        this.price = price;
        this.imageUrl = String.valueOf(imageUrl);
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
