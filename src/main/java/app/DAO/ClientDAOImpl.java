package app.DAO;

import app.model.Client;
import app.util.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientDAOImpl implements ClientDAO{
    @Override
    public void addClient(Client client) {
        String sql = "INSERT INTO Client (document_number, document_type, name, last_name, email, phone, address, balance,description) VALUES (?,?,?,?,?,?,?,?,?)";
        try (Connection conn = DatabaseUtil.getConnection()){
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setLong(1, client.getDocumentNumber());
            stmt.setString(2, client.getDocumentType());
            stmt.setString(3, client.getName());
            stmt.setString(4, client.getLastName());
            stmt.setString(5, client.getEmail());
            stmt.setString(6, client.getPhone());
            stmt.setString(7, client.getAddress());
            stmt.setDouble(8, client.getBalance());
            stmt.setString(9, client.getDescription());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateClient(Client client) {

    }

    @Override
    public void deleteClient(Client client) {
        String sql = "DELETE FROM Client WHERE id = ?";
        try (Connection conn = DatabaseUtil.getConnection()){
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setLong(1, client.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Client> getAllClients() {
        List<Client> clients = new ArrayList<>();
        String sql = "SELECT * FROM Client";
        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
             clientsFactory(clients, rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clients;
    }

    @Override
    public Client getClientById(Long id) {
        String sql = "SELECT * FROM Client WHERE id = ?";
        try (Connection conn = DatabaseUtil.getConnection()){
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setLong(1, id);
            return clientFactory(stmt.executeQuery());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Client getClientBy(String type, String value) {
        String sql = "SELECT * FROM Client WHERE document_type = ? AND document_number = ?";
        try (Connection conn = DatabaseUtil.getConnection()){
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, type);
            stmt.setString(2, value);
            Client rs = clientFactory(stmt.executeQuery());
            if (rs != null) return rs;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    private void clientsFactory(List<Client> clients, ResultSet rs) throws SQLException {
        while (rs.next()) {
            clients.add(clientFactory(rs));
        }
    }

    private Client clientFactory(ResultSet rs) throws SQLException {
        return new Client(rs.getLong("id"),
                rs.getLong("document_number"),
                rs.getDouble("balance"),
                rs.getString("name"),
                rs.getString("last_name"),
                rs.getString("email"),
                rs.getString("phone"),
                rs.getString("address"),
                rs.getString("document_type"),
                rs.getString("description")
        );
    }
}
