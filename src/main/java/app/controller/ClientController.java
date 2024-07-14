package app.controller;
import app.DAO.ClientDAO;
import app.DAO.ClientDAOImpl;
import app.util.*;
import app.model.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

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
    private MenuButton documentTypeButton;

    @FXML
    public MenuItem menuItemDNI;

    @FXML
    public MenuItem menuItemCUIL;

    @FXML
    public MenuItem menuItemCUIT;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtBalance;

    @FXML
    private TextField txtDescription;

    @FXML
    private TextField txtDocumentNumber;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtLastname;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPhone;

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

        setDocumentType();
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


    @FXML
    void addClient(ActionEvent event) {
        Client client = createClient();
        if(client != null) {
            this.clientDAO.addClient(client);
            clientList.add(client);
            updateTable();
            clearFields();
        }
    }

    private void clearFields() {
        documentTypeButton.setText("Tipo");
        txtName.clear();
        txtLastname.clear();
        txtDocumentNumber.clear();
        txtBalance.clear();
        txtAddress.clear();
        txtEmail.clear();
        txtPhone.clear();
        txtDescription.clear();
    }

    private Client createClient() {

        if(txtDocumentNumber.getText().isEmpty()) {
            ShowAlert.showInformation("Falta completar campos", "Por favor complete el campo de número de documento");
            return null;
        }
        String documentType = documentTypeButton.getText().equals("Tipo") ? Enums.DNI : documentTypeButton.getText();
        if(this.clientDAO.existsClient(documentType, Long.parseLong(txtDocumentNumber.getText()))){
            ShowAlert.showInformation("El cliente ya existe", "El cliente ya se encuentra registrado en la base de datos");
            return null;
        }
        String name = txtName.getText().isEmpty() ? "nombre" : txtName.getText();
        String lastname = txtLastname.getText().isEmpty() ? "apellido" : txtLastname.getText();
        long documentNumber =  Long.parseLong(txtDocumentNumber.getText());
        double balance = txtBalance.getText().isEmpty() ? 0 : Double.parseDouble(txtBalance.getText());
        String address = txtAddress.getText().isEmpty() ? "direccion" : txtAddress.getText();
        String email = txtEmail.getText().isEmpty() ? "email" : txtEmail.getText();
        String phone = txtPhone.getText().isEmpty() ? "123456789" : txtPhone.getText();
        String description = txtDescription.getText().isEmpty() ? "descripcion" : txtDescription.getText();
        return new Client(documentNumber, balance, name, lastname, email, phone, address, documentType, description);
    }

    @FXML
    void deleteClient(ActionEvent event) {

    }

    void setDocumentType() {
        menuItemDNI.setOnAction(_ -> documentTypeButton.setText(Enums.DNI));
        menuItemCUIL.setOnAction(_ -> documentTypeButton.setText(Enums.CUIL));
        menuItemCUIT.setOnAction(_ -> documentTypeButton.setText(Enums.CUIT));
    }

}
