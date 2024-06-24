package app.service;

import app.DAO.ProductDAO;
import app.model.Product;
import app.DAO.ProductDAOImpl;

import java.sql.SQLException;
import java.util.List;

public class ProductService {

    ProductDAO productDAO = new ProductDAOImpl();

    public Product getProductByCode(Long l) throws SQLException {
        return this.productDAO.getProduct(l, null);
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
