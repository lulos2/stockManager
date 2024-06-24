package app.DAO;

import app.model.Service;

import java.sql.Connection;
import java.util.List;

public interface ServiceDAO {
    List<Service> getServicesByBill(Long serviceId, Connection conn);
    void addService(Service service, Connection conn);
}
