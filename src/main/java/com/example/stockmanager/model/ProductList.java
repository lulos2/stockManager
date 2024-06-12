package com.example.stockmanager.model;

import java.util.ArrayList;
import java.util.List;

public class ProductList extends ArrayList<Product>{

    List<Product> products;

    public ProductList() {
        this.products = new ArrayList<Product>();
    }

    public ProductList(List<Product> products) {
        this.products = products;
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public void removeProduct(Product product) {
        products.remove(product);
    }

    public ArrayList<Product> getProducts() {
        return new ArrayList<Product>(products);
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

    public boolean existCode(Long l) {
        for (Product p : this.products) {
            if (p.getCode().equals(l)) {
                System.out.println("Product with code " + l + " already exists.");
                return true;
            }
        }
        return false;
    }

}
