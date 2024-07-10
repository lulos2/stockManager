package app.DAO;

import app.model.Client;

public interface ClientDAO {
    void addClient(Client client);
    void updateClient(Client client);
    void deleteClient(Client client);
    Client getClientById(Long id);
    Client getClientBy(String type, String value);
}
