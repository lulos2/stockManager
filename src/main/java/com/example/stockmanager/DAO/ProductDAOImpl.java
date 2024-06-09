package com.example.stockmanager.DAO;

import com.example.stockmanager.model.Product;
import com.example.stockmanager.util.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOImpl implements ProductDAO {

    @Override
    public void addProduct(Product product) {
        String sql = "INSERT INTO product (description, type, brand, code, cost, price, quantity) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, product.getDescription());
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
        String sql = "UPDATE product SET description = ?, type = ?, brand = ?, cost = ?, price = ?, quantity = ? WHERE code = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, product.getDescription());
            pstmt.setString(2, product.getType());
            pstmt.setString(3, product.getBrand());
            pstmt.setDouble(4, product.getCost());
            pstmt.setDouble(5, product.getPrice());
            pstmt.setInt(6, product.getQuantity());
            pstmt.setLong(7, product.getCode());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteProduct(Product product) {
        String sql = "DELETE FROM product WHERE code = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, product.getCode());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Product getProduct(Long code) {
        String sql = "SELECT * FROM product WHERE code = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, code);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Product(rs.getString("type"), rs.getString("brand"),
                            rs.getLong("code"), rs.getDouble("cost"), rs.getDouble("price"), rs.getInt("quantity"),rs.getString("name"));
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
                Product product = new Product(rs.getString("type"), rs.getString("brand"),
                        rs.getLong("code"), rs.getDouble("cost"), rs.getDouble("price"), rs.getInt("quantity"),rs.getString("description"));
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    @Override
    public List<Product> searchProduct(String text) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM product WHERE type LIKE ? OR brand LIKE ? OR code LIKE ? OR cost LIKE ? OR price LIKE ? OR quantity LIKE ? OR description LIKE ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "%" + text + "%");
            pstmt.setString(2, "%" + text + "%");
            pstmt.setString(3, "%" + text + "%");
            pstmt.setString(4, "%" + text + "%");
            pstmt.setString(5, "%" + text + "%");
            pstmt.setString(6, "%" + text + "%");
            pstmt.setString(7, "%" + text + "%");
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Product product = new Product(rs.getString("type"), rs.getString("brand"),
                            rs.getLong("code"), rs.getDouble("cost"), rs.getDouble("price"), rs.getInt("quantity"), rs.getString("description"));
                    products.add(product);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }
}