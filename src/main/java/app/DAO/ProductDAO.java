package app.DAO;
import app.model.Product;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
public interface ProductDAO {
    void addProduct(Product product);
    void updateProduct(Product product);
    void deleteProduct(Product product);
    Product getProduct(Long code, Connection connection) throws SQLException;
    List<Product> getAllProducts();
    List<Product> searchProduct(String text);
    void discountStock(Connection connection, Long productCode, int quantityLess) throws SQLException;
    List<Product> getProductsSold();
}
