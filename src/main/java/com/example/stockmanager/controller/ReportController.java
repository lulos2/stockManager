package com.example.stockmanager.controller;

import com.example.stockmanager.model.Product;
import com.example.stockmanager.service.ProductService;
import com.example.stockmanager.service.ReportService;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

import java.util.List;

public class ReportController {

    @FXML
    private TextArea reportTextArea;

    private ReportService reportService = new ReportService();
    private ProductService productService = new ProductService();

    @FXML
    private void handleGenerateReport() {
        List<Product> products = productService.getAllProducts();
        reportService.generateReport(products);
        // Display the report in the TextArea
    }
}
