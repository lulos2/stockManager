package com.example.stockmanager.DAO;
import com.example.stockmanager.model.Product;
import java.util.List;
public interface ProductDAO {
    void addProduct(Product product);
    void updateProduct(Product product);
    void deleteProduct(Product product);
    Product getProduct(Long code);
    List<Product> getAllProducts();

    List<Product> searchProduct(String text);
}
