package com.example.stockmanager.model;

import javafx.collections.ObservableList;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Bill {

    private Long id;
    private ProductList products;
    private ObservableList<Service> services;
    private String client;
    private LocalDateTime date;
    private Double total;

    public Bill() {
    }

    public Bill(Long id, ProductList products, ObservableList<Service> services, String client, Timestamp date, Double total) {
        this.id = id;
        this.products = products;
        this.services = services;
        this.client = client;
        this.date = date.toLocalDateTime();
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

    public Timestamp getDate() {
        return Timestamp.valueOf(date);
    }

    public void setDate(Timestamp date) {
        this.date = date.toLocalDateTime();
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
