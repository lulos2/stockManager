package com.example.stockmanager.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProductList {

    List<Product> products;

    public ProductList() {
        this.products = new ArrayList<Product>();
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public void removeProduct(Product product) {
        products.remove(product);
    }

    public List<Product> getProducts() {
        return products;
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

    public boolean existCode(long l) {
        for (Product p : products) {
            if (p.getCode().equals(l)) {
                return true;
            }
        }
        return false;
    }
}
