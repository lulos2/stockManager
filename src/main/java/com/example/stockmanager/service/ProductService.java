package com.example.stockmanager.service;

import com.example.stockmanager.DAO.ProductDAO;
import com.example.stockmanager.DAO.ProductDAOImpl;
import com.example.stockmanager.model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class ProductService {

    ProductDAO productDAO = new ProductDAOImpl();

    public void addProduct(Product product) {
    }

    public List<Product> getAllProducts() {
        return null;
    }

    public void updateProduct(Product product) {
    }

    public ObservableList<Product> getProducts() {
        List<Product> allProducts = productDAO.getAllProducts();
        return FXCollections.observableArrayList(allProducts);
    }

    public void deleteProduct(Product product) {
    }
}
