package app.DAO;

import app.model.Product;
import app.util.DatabaseUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOImpl implements ProductDAO {

    @Override
    public void addProduct(Product product) {
        addOrUpdateProduct(product, true);
    }

    @Override
    public void updateProduct(Product product) {
        addOrUpdateProduct(product, false);
    }

    public void addOrUpdateProduct(Product product, boolean isNewProduct) {
        Connection conn = null;
        try {
            conn = DatabaseUtil.getConnection();
            conn.setAutoCommit(false);

            String checkSql = "SELECT id, version, active FROM product WHERE code = ? ORDER BY version DESC LIMIT 1";
            int lastVersion = 0;
            boolean productExists = false;
            boolean isActive = false;

            try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
                checkStmt.setLong(1, product.getCode());
                ResultSet rs = checkStmt.executeQuery();
                if (rs.next()) {
                    productExists = true;
                    lastVersion = rs.getInt("version");
                    isActive = rs.getBoolean("active");
                }
            }

            if (isNewProduct) {
                if (productExists && isActive) {
                    throw new SQLException("Ya existe un producto activo con este código");
                }

                String insertSql = "INSERT INTO product (description, type, brand, code, cost, price, quantity, unitType, active, version) VALUES (?, ?, ?, ?, ?, ?, ?, ?, TRUE, ?)";
                try (PreparedStatement pstmt = conn.prepareStatement(insertSql)) {
                    setStatementWithProductData(product, pstmt);
                    pstmt.setInt(9, lastVersion + 1);  // Incrementar la versión
                    pstmt.executeUpdate();
                }
            } else {
                if (!productExists || !isActive) {
                    throw new SQLException("No se encontró el producto activo para actualizar");
                }

                String deactivateSql = "UPDATE product SET active = FALSE WHERE code = ? AND active = TRUE";
                try (PreparedStatement deactivateStmt = conn.prepareStatement(deactivateSql)) {
                    deactivateStmt.setLong(1, product.getCode());
                    deactivateStmt.executeUpdate();
                }

                String insertSql = "INSERT INTO product (description, type, brand, code, cost, price, quantity, unitType, active, version) VALUES (?, ?, ?, ?, ?, ?, ?, ?, TRUE, ?)";
                try (PreparedStatement pstmt = conn.prepareStatement(insertSql)) {
                    setStatementWithProductData(product, pstmt);
                    pstmt.setInt(9, lastVersion + 1);  // Incrementar la versión
                    pstmt.executeUpdate();
                }
            }

            conn.commit();
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void deleteProduct(Product product) {
        String sql = "UPDATE product SET active = FALSE WHERE code = ? and active = TRUE";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, product.getCode());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Product getProductById(Long id, Connection connection) {
        String sql = "SELECT * FROM product WHERE id = ?";
        try {
            if (connection == null) {
                connection = DatabaseUtil.getConnection();
            }
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setLong(1, id);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        return productFactory(rs);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Product getActiveProductByCode(long code) {
        String sql = "SELECT * FROM product WHERE code = ? AND active = TRUE";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, code);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return productFactory(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM product";
        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                productsFactory(products, rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    public List<Product> getActiveProducts() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM Product WHERE active = TRUE";
        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            productsFactory(products, rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    @Override
    public List<Product> searchProduct(String text) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM product WHERE active = TRUE AND (type LIKE ? OR brand LIKE ? OR code LIKE ? OR cost LIKE ? OR price LIKE ? OR quantity LIKE ? OR description LIKE ?) ";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            for (int i = 1; i <= 7; i++) {
                pstmt.setString(i, STR."%\{text}%");
            }
            try (ResultSet rs = pstmt.executeQuery()) {
                productsFactory(products, rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    public void discountStock(Connection connection, int id, double quantityLess) throws SQLException {
        String sqlUpdateStock = "UPDATE product SET quantity = quantity - ? WHERE id = ?";

        try (PreparedStatement pstmtUpdateStock = connection.prepareStatement(sqlUpdateStock)) {
            pstmtUpdateStock.setDouble(1, quantityLess);
            pstmtUpdateStock.setLong(2, id);
            pstmtUpdateStock.executeUpdate();
        }
    }

    @Override
    public List<Product> getProductsSold() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT product_id, SUM(quantity) FROM Bill_Product GROUP BY product_id ORDER BY SUM(quantity) DESC LIMIT 9";
        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Product product = getProductById(rs.getLong("product_id"), conn);
                if(product == null) {
                    continue;
                }
                product.setQuantity(rs.getInt(2));
                products.add(product);
            }
        } catch (SQLException e) {
            e.fillInStackTrace();
        }
        return products;
    }

    private void productsFactory(List<Product> products, ResultSet rs) throws SQLException {
        while (rs.next()) {
            products.add(productFactory(rs));
        }
    }

    private Product productFactory(ResultSet rs) throws SQLException {
        return new Product(
                rs.getInt("id"),
                rs.getLong("code"),
                rs.getString("type"),
                rs.getString("brand"),
                rs.getDouble("cost"),
                rs.getDouble("price"),
                rs.getDouble("quantity"),
                rs.getString("description"),
                rs.getString("unitType")
        );
    }

    private void setStatementWithProductData(Product product, PreparedStatement stmt) throws SQLException {
        stmt.setString(1, product.getDescription());
        stmt.setString(2, product.getType());
        stmt.setString(3, product.getBrand());
        stmt.setLong(4, product.getCode());
        stmt.setDouble(5, product.getCost());
        stmt.setDouble(6, product.getPrice());
        stmt.setDouble(7, product.getQuantity());
        stmt.setString(8, product.getUnitType());
    }
}
