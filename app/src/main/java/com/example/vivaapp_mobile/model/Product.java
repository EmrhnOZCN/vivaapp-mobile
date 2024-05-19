package com.example.vivaapp_mobile.model;

public class Product {
    private int id;
    private int imageResource;
    private String name;
    private double price;
    private String categoryName;

    public Product(int imageResource, String name, double price, String categoryName) {
        this.imageResource = imageResource;
        this.name = name;
        this.price = price;
        this.categoryName = categoryName;
    }

    public int getImageResource() {
        return imageResource;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getCategoryName() {
        return categoryName;
    }
}
