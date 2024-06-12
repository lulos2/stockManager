package com.example.stockmanager.controller;
import com.example.stockmanager.model.Bill;
import com.example.stockmanager.model.Product;
import com.example.stockmanager.model.ProductList;
import com.example.stockmanager.model.Service;
import com.example.stockmanager.service.ProductService;
import com.example.stockmanager.util.DataStorage;
import com.example.stockmanager.util.Paths;
import com.example.stockmanager.util.StageManager;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

public class BillingController {

    ProductService productService = new ProductService();

    ProductList productListToBill;
    ObservableList<Service> serviceListToBill;

    @FXML
    private Label desc;

    @FXML
    private Label productsPriceCount;

    @FXML
    private Label servicePriceCount;

    @FXML
    private Label subTotalCount;

    @FXML
    private Label totalcount;

    @FXML
    private TableView<Product> tblProductsBill;

    @FXML
    private TableView<Service> tblServiceList;

    @FXML
    private TextField txtBillName;

    @FXML
    private TextField txtProductCode;

    @FXML
    private TextField txtQuantity;

    @FXML
    private TextField txtServiceDesc;

    @FXML
    private TextField txtServiceName;

    @FXML
    private TextField txtServicePrice;

    @FXML
    private TableColumn<Product, Integer> colProductCant;

    @FXML
    private TableColumn<Product, Long> colProductCode;

    @FXML
    private TableColumn<Product, Double> colProductTotalPrice;

    @FXML
    private TableColumn<Product, String> colProductTypeAndBrand;

    @FXML
    private TableColumn<Product, Double> colProductUnitPrice;

    @FXML
    private TableColumn<Product, String> colProductUnitType;

    @FXML
    private TableColumn<Service, String> colService;

    @FXML
    private TableColumn<Service, String> colServiceDescription;

    @FXML
    private TableColumn<Service, Double> colServicePrice;

    public BillingController() {
        this.productListToBill = DataStorage.getProductListToBill();
        this.serviceListToBill = DataStorage.getServiceListToBill();
    }

    @FXML
    void addProductToBillList(ActionEvent event) {
        addProductToBillList();
    }

    void addProductToBillList() {
        if(txtProductCode.getText().isEmpty() || txtQuantity.getText().isEmpty()) return;
        if(productListToBill.existCode(Long.parseLong(txtProductCode.getText()))) return;
        Product product = this.productService.getProductByCode(Long.parseLong(txtProductCode.getText()));
        if(product == null) return;
        if(product.getQuantity() < Integer.parseInt(txtQuantity.getText()) || Integer.parseInt(txtQuantity.getText()) <= 0) return;
        product.setQuantity(Integer.parseInt(txtQuantity.getText()));
        productListToBill.addProduct(product);
        tblProductsBill.getItems().add(product);
        updateProductTable();
        clearProductFields();
    }

    private void clearProductFields() {
        txtProductCode.clear();
        txtQuantity.clear();
    }

    private void updateProductTable() {
        tblProductsBill.getItems().clear();
        tblProductsBill.getItems().addAll(this.productListToBill.getProducts());
        tblProductsBill.refresh();
    }

    @FXML
    void addServiceToBillList(ActionEvent event) {
        addServiceToBillList();
    }

    void addServiceToBillList() {
        if(txtServiceName.getText().isEmpty() || txtServicePrice.getText().isEmpty()) return;
        Service service = new Service(txtServiceName.getText(), txtServiceDesc.getText(), Double.parseDouble(txtServicePrice.getText()));
        serviceListToBill.add(service);
        tblServiceList.getItems().add(service);
        updateServiceTable();
        clearServiceFields();
    }

    private void clearServiceFields() {
        txtServiceName.clear();
        txtServiceDesc.clear();
        txtServicePrice.clear();
    }

    private void updateServiceTable() {
        tblServiceList.getItems().clear();
        tblServiceList.getItems().addAll(serviceListToBill);
        tblServiceList.refresh();
    }

    @FXML
    void decreaseQuantity(ActionEvent event) {
        txtQuantity.setText(String.valueOf(Integer.parseInt(txtQuantity.getText()) - 1));
    }

    @FXML
    void increaseQuantity(ActionEvent event) {
        if(!txtQuantity.getText().isEmpty()) {
            txtQuantity.setText(String.valueOf(Integer.parseInt(txtQuantity.getText()) + 1));
        }else {
            txtQuantity.setText("1");
        }
    }

    @FXML
    void deleteItem(ActionEvent event) {
        Product product = tblProductsBill.getSelectionModel().getSelectedItem();
        productListToBill.removeProduct(product);
        updateProductTable();
        Service service = tblServiceList.getSelectionModel().getSelectedItem();
        serviceListToBill.remove(service);
        updateServiceTable();
    }

    @FXML
    void saveBill(ActionEvent event) {

    }

    @FXML
    void goToProduct(ActionEvent event) {
        StageManager.changeScene(Paths.PRODUCT_FXML);
    }

    private Callback<TableColumn.CellDataFeatures<Product, Double>, ObservableValue<Double>> importCalculator() {
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

    public void calculateTotal() {
        double productsPrice = productListToBill.getTotalPrice();
        double servicePrice = serviceListToBill.stream().mapToDouble(Service::getPrice).sum();
        double subTotal = productsPrice + servicePrice;
        double total = subTotal * 1.16;
        productsPriceCount.setText(String.valueOf(productsPrice));
        servicePriceCount.setText(String.valueOf(servicePrice));
        subTotalCount.setText(String.valueOf(subTotal));
        totalcount.setText(String.valueOf(total));
    }

    @FXML
    void initialize(){
        colProductCant.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colProductCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colProductTypeAndBrand.setCellValueFactory(new PropertyValueFactory<>("type"));
        colProductUnitPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colProductUnitType.setCellValueFactory(new PropertyValueFactory<>("unitType"));
        colProductTotalPrice.setCellValueFactory(importCalculator());

        colService.setCellValueFactory(new PropertyValueFactory<>("name"));
        colServiceDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colServicePrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        tblProductsBill.getItems().clear();
        tblProductsBill.getItems().addAll(this.productListToBill.getProducts());
        tblProductsBill.refresh();

        tblServiceList.getItems().clear();
        tblServiceList.getItems().addAll(serviceListToBill);
        tblServiceList.refresh();

        productListToBill.addListener(c -> calculateTotal());
        serviceListToBill.addListener((ListChangeListener<Service>) c -> calculateTotal());

        calculateTotal();

    }
}
