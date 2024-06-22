package com.example.stockmanager.DAO;

import com.example.stockmanager.model.Service;
import com.example.stockmanager.util.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ServiceDAOImpl implements ServiceDAO{


    @Override
    public Service getServiceById(Long serviceId, Connection conn) {
        String sqlService = "SELECT * FROM service WHERE id = ?";
        Service service = null;
        try{
            if (conn == null) {
                conn = DatabaseUtil.getConnection();
            }
            try (PreparedStatement pstmtBill = conn.prepareStatement(sqlService)) {
                pstmtBill.setLong(1, serviceId);
                try (ResultSet rs = pstmtBill.executeQuery()) {
                    if (rs.next()) {
                        service = new Service(rs.getString("name"), rs.getDouble("price"), rs.getInt("duration"), rs.getString("description"));
                    }
                }
            }
        } catch (Exception e) {
            e.fillInStackTrace();
        }
        return service;
    }
}
