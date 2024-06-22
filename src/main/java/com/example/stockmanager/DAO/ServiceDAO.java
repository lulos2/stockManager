package com.example.stockmanager.DAO;

import com.example.stockmanager.model.Service;

import java.sql.Connection;

public interface ServiceDAO {
    Service getServiceById(Long serviceId, Connection conn);
}
