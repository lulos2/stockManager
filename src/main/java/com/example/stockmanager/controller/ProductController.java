package com.example.stockmanager.controller;

import com.example.stockmanager.model.Product;
import com.example.stockmanager.service.ProductService;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class ProductController {

    @FXML
    private TextField productNameField;
    @FXML
    private TextField productQuantityField;

    private ProductService productService = new ProductService();

    @FXML
    private void handleAddProduct() {
        String name = productNameField.getText();
        int quantity = Integer.parseInt(productQuantityField.getText());
        Product product = new Product();
        product.setName(name);
        product.setQuantity(quantity);
        productService.addProduct(product);
        // Clear fields and update view
    }
}
