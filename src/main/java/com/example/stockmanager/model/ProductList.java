package com.example.stockmanager.model;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class ProductList {

    private final ObservableList<Product> products;
    public ProductList() {
        this.products = FXCollections.observableArrayList();
    }
    public ProductList(List<Product> products) {
        this.products = FXCollections.observableArrayList(products);
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public void removeProduct(Product product) {
        products.remove(product);
    }

    public List<Product> getProducts() {
        return this.products;
    }

    public void updateProduct(Product product) {
        for (Product p : products) {
            if (p.getCode().equals(product.getCode())) {
                p.setDescription(product.getDescription());
                p.setType(product.getType());
                p.setBrand(product.getBrand());
                p.setCost(product.getCost());
                p.setPrice(product.getPrice());
                p.setQuantity(product.getQuantity());
            }
        }
    }

    public boolean existCode(Long code) {
        return this.products.stream().anyMatch(product -> product.getCode().equals(code));
    }

    public double getTotalPrice() {
        double total = 0;
        for (Product product : products) {
            total += product.getPrice() * product.getQuantity();
        }
        return total;
    }

    public void addListener(ListChangeListener<Product> productListChangeListener) {
        this.products.addListener(productListChangeListener);
    }
}
