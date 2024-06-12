package com.example.stockmanager.service;

import com.example.stockmanager.DAO.ProductDAO;
import com.example.stockmanager.DAO.ProductDAOImpl;
import com.example.stockmanager.model.Product;

import java.util.List;

public class ProductService {

    ProductDAO productDAO = new ProductDAOImpl();

    public Product getProductByCode(Long l) {
        return this.productDAO.getProduct(l);
    }

    public void addProduct(Product product) {
        productDAO.addProduct(product);
    }

    public List<Product> getAllProducts() {
        return productDAO.getAllProducts();
    }

    public void updateProduct(Product product) {
        productDAO.updateProduct(product);
    }

    public void deleteProduct(Product product) {
        productDAO.deleteProduct(product);
    }

    public List<Product> searchProduct(String text) {
        return productDAO.searchProduct(text);
    }
}
