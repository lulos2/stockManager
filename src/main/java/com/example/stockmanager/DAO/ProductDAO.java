package com.example.stockmanager.DAO;
import com.example.stockmanager.model.Product;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
public interface ProductDAO {
    void addProduct(Product product);
    void updateProduct(Product product);
    void deleteProduct(Product product);
    Product getProduct(Long code);
    List<Product> getAllProducts();
    List<Product> searchProduct(String text);
    void discountStock(Connection connection, Long productCode, int quantityLess) throws SQLException;
}
