package com.example.vivaapp_mobile.ui.notifications;

public class CartItem {
    private String name;
    private double price;
    private String imageUrl;

    public CartItem(String name, double price, String imageUrl) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
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
}

