package com.example.stockmanager.model;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Bill {

    private int id;
    private ProductList products;
    private ArrayList<Service> services;
    private String client;
    private LocalDateTime date;
    private double total;

    public Bill() {
    }

    public Bill(ProductList products, ArrayList<Service> services, String client, LocalDateTime date, Double total) {
        this.products = products;
        this.services = services;
        this.client = client;
        this.date = date;
        this.total = total;
    }

    public int getId() {
        return id;
    }


    public ProductList getProducts() {
        return products;
    }

    public void setProducts(ProductList products) {
        this.products = products;
    }

    public ArrayList<Service> getServices() {
        return services;
    }

    public void setServices(ArrayList<Service> services) {
        this.services = services;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
