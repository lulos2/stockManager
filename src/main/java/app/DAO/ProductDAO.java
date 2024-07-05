package app.DAO;
import app.model.Product;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
public interface ProductDAO {
    void addProduct(Product product);
    void updateProduct(Product product);
    void deleteProduct(Product product);
    Product getProductById(Long id, Connection connection) throws SQLException;
    Product getActiveProductByCode(long code);
    List<Product> getAllProducts();
    List<Product> searchProduct(String text);
    void discountStock(Connection connection, int productId, int quantityLess) throws SQLException;
    List<Product> getProductsSold();
    List<Product> getActiveProducts();
}
