package app.DAO;

import app.model.Service;
import app.util.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ServiceDAOImpl implements ServiceDAO{


    @Override
    public List<Service> getServicesByBill(Long billId, Connection conn) {
        String sqlService = "SELECT * FROM service WHERE bill_id = ?";
        List<Service> services = new ArrayList<>();
        try{
            if (conn == null) {
                conn = DatabaseUtil.getConnection();
            }
            try (PreparedStatement pstmtBill = conn.prepareStatement(sqlService)) {
                pstmtBill.setLong(1, billId);
                try (ResultSet rs = pstmtBill.executeQuery()) {
                    while (rs.next()) {
                        services.add(new Service(rs.getInt("id"),
                                            rs.getString("name"),
                                            rs.getDouble("price"),
                                            rs.getInt("duration"),
                                            rs.getString("description"),
                                            rs.getLong("bill_id"))
                        );
                    }
                }
            }
        } catch (Exception e) {
            e.fillInStackTrace();
        }
        return services;
    }

    @Override
    public void addService(Service service, Connection conn) {
        String sql = "INSERT INTO service (name, price, duration, description, bill_id) VALUES (?, ?, ?, ?,?)";
        try {
            if (conn == null) {
                conn = DatabaseUtil.getConnection();
            }
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, service.getName());
                pstmt.setDouble(2, service.getPrice());
                pstmt.setInt(3, service.getDuration());
                pstmt.setString(4, service.getDescription());
                pstmt.setLong(5, service.getBillId());
                pstmt.executeUpdate();
            } catch (Exception e) {
                e.fillInStackTrace();
            }
        } catch (Exception e) {
            e.fillInStackTrace();
        }
    }
}
