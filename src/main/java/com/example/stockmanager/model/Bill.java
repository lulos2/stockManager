package com.example.stockmanager.model;

import javafx.collections.ObservableList;

import java.sql.Date;
import java.time.LocalDateTime;

public class Bill {

    private Long id;
    private ProductList products;
    private ObservableList<Service> services;
    private String client;
    private LocalDateTime date;
    private double total;

    public Bill() {
    }

    public Bill(Long id, ProductList products, ObservableList<Service> services, String client, Date date, double total) {
        this.id = id;
        this.products = products;
        this.services = services;
        this.client = client;
        this.date = LocalDateTime.of(date.toLocalDate(), LocalDateTime.now().toLocalTime());
        this.total = total;
    }

    public Bill(ProductList products, ObservableList<Service> services, String client, Double total) {
        this.products = products;
        this.services = services;
        this.client = client;
        this.date = LocalDateTime.now();
        this.total = total;
    }

    public Long getId() {
        return id;
    }


    public ProductList getProducts() {
        return products;
    }

    public void setProducts(ProductList products) {
        this.products = products;
    }

    public ObservableList<Service> getServices() {
        return services;
    }

    public void setServices(ObservableList<Service> services) {
        this.services = services;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public Date getDate() {
        return Date.valueOf(date.toLocalDate());
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

    public void setId(Long id) {
        this.id = id;
    }
}
