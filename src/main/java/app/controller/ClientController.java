package app.controller;
import app.DAO.ClientDAO;
import app.DAO.ClientDAOImpl;
import app.util.DataStorage;
import app.model.Client;
import app.util.Paths;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import app.util.StageManager;
import java.util.ArrayList;

public class ClientController extends BaseController {

    ArrayList<Client> clientList = new ArrayList<>();
    ClientDAO clientDAO = new ClientDAOImpl();

    @FXML
    private TableView<Client> clientTable;

    @FXML
    private TableColumn<Client, Double> colBalance;

    @FXML
    private TableColumn<Client, Void> colDetail;

    @FXML
    private TableColumn<Client, Long> colDocumentNumber;

    @FXML
    private TableColumn<Client, String> colDocumentType;

    @FXML
    private TableColumn<Client, String> colLastname;

    @FXML
    private TableColumn<Client, String> colName;

    @FXML
    void initialize() {
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colLastname.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        colDocumentType.setCellValueFactory(new PropertyValueFactory<>("documentType"));
        colDocumentNumber.setCellValueFactory(new PropertyValueFactory<>("documentNumber"));
        colBalance.setCellValueFactory(new PropertyValueFactory<>("balance"));
        colDetail.setCellFactory(_ -> new TableCell<>() {
            private final Button btn = new Button("Ver Detalle");
            {
                btn.setOnAction(_ -> {
                    Client client = getTableView().getItems().get(getIndex());
                    openClientDetail(client);
                });
            }
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(btn);
                }
            }
        });

        loadClientData();
    }

    private void openClientDetail(Client client) {
        DataStorage.setClient(client);
        StageManager.changeScene(Paths.CLIENT_DETAIL_FXML);
    }

    public void loadClientData() {
        this.clientList.addAll(this.clientDAO.getAllClients());
        updateTable();
    }

    private void updateTable() {
        clientTable.getItems().clear();
        clientTable.getItems().addAll(clientList);
        clientTable.refresh();
    }

}
