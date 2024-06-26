package app.controller;

import app.model.Bill;
import app.model.Product;
import app.model.Service;
import app.service.BillingService;
import app.util.DataStorage;
import app.util.Paths;
import app.util.StageManager;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

public class SaleViewController extends BaseController{

    private BillingService billingService;
    private Bill bill;


    @FXML
    private Label billClientLabel;

    @FXML
    private Label billDateLabel;

    @FXML
    private TableView<Product> billProductsTable;

    @FXML
    private TableView<Service> billServicesTable;

    @FXML
    private Label billIdLabel;

    @FXML
    private Label billTotalLabel;

    @FXML
    private TableColumn<Product, Long> colProductCode;

    @FXML
    private TableColumn<Product, Double> colProductPrice;

    @FXML
    private TableColumn<Product, Integer> colProductQuantity;

    @FXML
    private TableColumn<Product, Double> colProductTotal;

    @FXML
    private TableColumn<Product, String> colProductTypeBrand;

    @FXML
    private TableColumn<Service, String> colServiceDescription;

    @FXML
    private TableColumn<Service, Long> colServiceId;

    @FXML
    private TableColumn<Service, String> colServiceName;

    @FXML
    private TableColumn<Service, Double> colServiceTotal;

    @FXML
    private Label labelBillClientName;

    @FXML
    private Label labelBillDate;

    @FXML
    private Label labelBillNumber;

    @FXML
    private Label labelBillTotal;

    public SaleViewController() {
        this.bill = DataStorage.actualBill;
    }

    private Callback<TableColumn.CellDataFeatures<Product, Double>, ObservableValue<Double>> importCalculator() {
        return getCellDataFeaturesObservableValueCallback();
    }

    static Callback<TableColumn.CellDataFeatures<Product, Double>, ObservableValue<Double>> getCellDataFeaturesObservableValueCallback() {
        return param -> {
            Product product = param.getValue();
            return new ObservableValue<>() {
                @Override
                public void addListener(ChangeListener<? super Double> listener) {
                }

                @Override
                public void removeListener(ChangeListener<? super Double> listener) {
                }

                @Override
                public Double getValue() {
                    return product.getPrice() * product.getQuantity();
                }

                @Override
                public void addListener(InvalidationListener listener) {
                }

                @Override
                public void removeListener(InvalidationListener listener) {
                }
            };
        };
    }

    @FXML
    void initialize() {
        labelBillNumber.setText(String.valueOf(DataStorage.actualBill.getId()));
        labelBillClientName.setText(DataStorage.actualBill.getClient());
        labelBillDate.setText(DataStorage.actualBill.getDate().toString());
        labelBillTotal.setText(String.valueOf(DataStorage.actualBill.getTotal()));

        colProductCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colProductPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colProductQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colProductTypeBrand.setCellValueFactory(new PropertyValueFactory<>("type"));
        colProductTotal.setCellValueFactory(importCalculator());
        colServiceId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colServiceName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colServiceDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colServiceTotal.setCellValueFactory(new PropertyValueFactory<>("price"));

        billProductsTable.setItems(DataStorage.actualBill.getProducts().getProducts());
        billServicesTable.setItems(DataStorage.actualBill.getServices());
    }
}