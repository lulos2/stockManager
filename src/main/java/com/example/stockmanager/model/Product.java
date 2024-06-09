package com.example.stockmanager.model;

public class Product {
    private int id;
    private String description;
    private String type;
    private String brand;
    private Long code;
    private double cost;
    private double price;
    private int quantity;

    public Product() {
    }

    public Product(String type, String brand, Long code, double cost, double price, int quantity, String description) {
        this.description = description;
        this.type = type;
        this.brand = brand;
        this.code = code;
        this.cost = cost;
        this.price = price;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
