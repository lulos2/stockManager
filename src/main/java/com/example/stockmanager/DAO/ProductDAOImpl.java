package com.example.stockmanager.DAO;

import com.example.stockmanager.model.Product;
import com.example.stockmanager.util.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOImpl implements ProductDAO {

    @Override
    public void addProduct(Product product) {
        String sql = "INSERT INTO product (name, type, brand, code, cost, price, quantity) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, product.getName());
            pstmt.setString(2, product.getType());
            pstmt.setString(3, product.getBrand());
            pstmt.setLong(4, product.getCode());
            pstmt.setDouble(5, product.getCost());
            pstmt.setDouble(6, product.getPrice());
            pstmt.setInt(7, product.getQuantity());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateProduct(Product product) {

    }

    @Override
    public void deleteProduct(int productId) {

    }

    @Override
    public Product getProduct(int productId) {
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
                Product product = new Product(rs.getString("name"), rs.getString("type"), rs.getString("brand"),
                        rs.getLong("code"), rs.getDouble("cost"), rs.getDouble("price"), rs.getInt("quantity"));
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

}