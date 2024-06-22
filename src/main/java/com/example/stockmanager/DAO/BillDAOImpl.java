package com.example.stockmanager.DAO;

import com.example.stockmanager.model.Bill;
import com.example.stockmanager.model.Product;
import com.example.stockmanager.model.ProductList;
import com.example.stockmanager.model.Service;
import com.example.stockmanager.util.DatabaseUtil;
import javafx.collections.FXCollections;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BillDAOImpl implements BillDAO {

    private final ProductDAO productDAO = new ProductDAOImpl();
    private final ServiceDAO serviceDAO = new ServiceDAOImpl();

    public void addBill(Bill bill) {
        String sqlBill = "INSERT INTO bill (client, date, total) VALUES (?, ?, ?)";
        String sqlBillProduct = "INSERT INTO bill_product (bill_id, product_code, quantity) VALUES (?, ?, ?)";
        String sqlBillService = "INSERT INTO bill_service (bill_id, service_id) VALUES (?, ?)";

        try (Connection conn = DatabaseUtil.getConnection()) {
            conn.setAutoCommit(false);
            long billId;
            try (PreparedStatement pstmtBill = conn.prepareStatement(sqlBill, PreparedStatement.RETURN_GENERATED_KEYS)) {
                pstmtBill.setString(1, bill.getClient());
                pstmtBill.setLong(2, bill.getDate().getTime());
                pstmtBill.setDouble(3, bill.getTotal());
                pstmtBill.executeUpdate();

                try (ResultSet generatedKeys = pstmtBill.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        billId = generatedKeys.getLong(1);
                    } else {
                        throw new SQLException("Error al obtener el ID de la factura.");
                    }
                }
            }

            try (PreparedStatement pstmtBillProduct = conn.prepareStatement(sqlBillProduct)) {
                for (Product product : bill.getProducts().getProducts()) {
                    pstmtBillProduct.setLong(1, billId);
                    pstmtBillProduct.setLong(2, product.getCode());
                    pstmtBillProduct.setInt(3, product.getQuantity());
                    pstmtBillProduct.addBatch();

                    productDAO.discountStock(conn,product.getCode(), product.getQuantity());
                }
                pstmtBillProduct.executeBatch();
            }

            try (PreparedStatement pstmtBillService = conn.prepareStatement(sqlBillService)) {
                for (Service service : bill.getServices()) {
                    pstmtBillService.setLong(1, billId);
                    pstmtBillService.setInt(2, service.getId());
                    pstmtBillService.addBatch();
                }
                pstmtBillService.executeBatch();
            }

            conn.commit();
        } catch (SQLException e) {
            e.fillInStackTrace();
            try (Connection conn = DatabaseUtil.getConnection()) {
                // Realizar rollback en caso de error
                conn.rollback();
            } catch (SQLException rollbackEx) {
                rollbackEx.fillInStackTrace();
            }
        }
    }

    @Override
    public List<Bill> getAllBills() {
        List<Bill> bills = new ArrayList<>();
        String sql = "SELECT * FROM bill";
        try (Connection conn = DatabaseUtil.getConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        long timestamp = rs.getLong("date");
                        Timestamp date = new Timestamp(timestamp);
                        Bill bill = new Bill(rs.getLong("id"), null, null, rs.getString("client"), date, rs.getDouble("total"));
                        bills.add(bill);
                    }
                }
            }
        } catch (SQLException e) {
            e.fillInStackTrace();
        }
        return bills;
    }

    @Override
    public Bill getBillById(Long id) {
        Bill bill = null;
        String sqlBill = "SELECT * FROM bill WHERE id = ?";
        String sqlBillProduct = "SELECT * FROM bill_product WHERE bill_id = ?";
        String sqlBillService = "SELECT * FROM bill_service WHERE bill_id = ?";

        try (Connection conn = DatabaseUtil.getConnection()) {
            try (PreparedStatement pstmtBill = conn.prepareStatement(sqlBill)) {
                pstmtBill.setLong(1, id);
                try (ResultSet rs = pstmtBill.executeQuery()) {
                    if (rs.next()) {
                        long timestamp = rs.getLong("date");
                        Timestamp date = new Timestamp(timestamp);
                        bill = new Bill(rs.getLong("id"), new ProductList(), FXCollections.observableArrayList(), rs.getString("client"), date, rs.getDouble("total"));
                    }
                }
            }

            if (bill != null) {
                try (PreparedStatement pstmtBillProduct = conn.prepareStatement(sqlBillProduct)) {
                    pstmtBillProduct.setLong(1, id);
                    try (ResultSet rs = pstmtBillProduct.executeQuery()) {
                        while (rs.next()) {
                            Product product = productDAO.getProduct(rs.getLong("product_code"), conn);
                            product.setQuantity(rs.getInt("quantity"));
                            bill.getProducts().addProduct(product);
                        }
                    }
                }

                try (PreparedStatement pstmtBillService = conn.prepareStatement(sqlBillService)) {
                    pstmtBillService.setLong(1, id);
                    try (ResultSet rs = pstmtBillService.executeQuery()) {
                        while (rs.next()) {
                            Service service = serviceDAO.getServiceById(rs.getLong("service_id"), conn);
                            bill.addService(service);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.fillInStackTrace();
        }
        return bill;
    }

    @Override
    public void updateBill(Bill bill) {

    }

    @Override
    public void deleteBill(Bill bill) {

    }

    @Override
    public List<Bill> searchBill(String text) {
        return null;
    }
}
