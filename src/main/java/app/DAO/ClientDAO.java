package app.DAO;

import app.model.Client;

import java.util.List;

public interface ClientDAO {
    void addClient(Client client);
    void updateClient(Client client);
    void deleteClient(Client client);
    List<Client> getAllClients();
    Client getClientById(Long id);
    Client getClientBy(String type, String value);
}
