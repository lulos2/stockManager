package app.service;

import app.DAO.ProductDAO;
import app.model.Product;
import app.DAO.ProductDAOImpl;

import java.sql.SQLException;
import java.util.List;

public class ProductService {

    ProductDAO productDAO = new ProductDAOImpl();

    public Product getProductBy(Long id) throws SQLException {
        return this.productDAO.getProductById(id, null);
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

    public List<Product> getProductsSold() {
        return productDAO.getProductsSold();
    }

    public List<Product> getActiveProducts() {
        return productDAO.getActiveProducts();
    }

    public Product getActiveProductByCode(Long code) {
        return productDAO.getActiveProductByCode(code);
    }
}
