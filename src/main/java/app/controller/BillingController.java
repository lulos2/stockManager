package app.controller;
import app.model.Bill;
import app.model.Product;
import app.model.ProductList;
import app.model.Service;
import app.service.BillingService;
import app.service.ProductService;
import app.util.DataStorage;
import app.util.ShowAlert;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.util.Callback;

import java.sql.SQLException;
import java.text.NumberFormat;

public class BillingController extends BaseController {

    ProductService productService = new ProductService();
    BillingService billingService = new BillingService();

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
    private TextField txtDescTotal;

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
    private TableColumn<Product, Void> colProductActions;

    @FXML
    private TableColumn<Service, String> colService;

    @FXML
    private TableColumn<Service, String> colServiceDescription;

    @FXML
    private TableColumn<Service, Double> colServicePrice;

    @FXML
    private TableColumn<Service, Void> colServiceActions;


    public BillingController() {
        this.productListToBill = DataStorage.getProductListToBill();
        this.serviceListToBill = DataStorage.getServiceListToBill();
    }

    @FXML
    void addProductToBillList(ActionEvent event) throws SQLException {
        addProductToBillList();
    }

    void addProductToBillList() throws SQLException {
        if(txtProductCode.getText().isEmpty() || txtQuantity.getText().isEmpty()) return;
        if(productListToBill.existCode(Long.parseLong(txtProductCode.getText()))) return;
        Product product = this.productService.getProductByCode(Long.parseLong(txtProductCode.getText()));
        if(product == null){
            ShowAlert.productNotFound();
            return;
        }
        if(product.getQuantity() < Integer.parseInt(txtQuantity.getText()) || Integer.parseInt(txtQuantity.getText()) <= 0) return;
        product.setQuantity(Integer.parseInt(txtQuantity.getText()));
        productListToBill.addProduct(product);
        tblProductsBill.getItems().add(product);
        updateProductTable();
        clearProductFields();
        ShowAlert.productAdded();
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

    private void clearBillFields() {
        txtBillName.clear();
        txtDescTotal.clear();
        clearProductFields();
        clearServiceFields();
    }

    private void updateServiceTable() {
        tblServiceList.getItems().clear();
        tblServiceList.getItems().addAll(serviceListToBill);
        tblServiceList.refresh();
    }

    @FXML
    void decreaseQuantity(ActionEvent event) {
        if(txtQuantity.getText().isEmpty()) return;
        if(Integer.parseInt(txtQuantity.getText()) <= 1) return;
        txtQuantity.setText(String.valueOf(Integer.parseInt(txtQuantity.getText()) - 1));
    }

    @FXML
    void increaseQuantity(ActionEvent event) throws SQLException {
        if(txtProductCode.getText().isEmpty()) return;
        if(!txtQuantity.getText().isEmpty()) {
            if(productService.getProductByCode(Long.parseLong(txtProductCode.getText()))==null) return;
            if(productService.getProductByCode(Long.parseLong(txtProductCode.getText())).getQuantity() <= Integer.parseInt(txtQuantity.getText())) return;
            txtQuantity.setText(String.valueOf(Integer.parseInt(txtQuantity.getText()) + 1));
        }else {
            txtQuantity.setText("0");
        }
    }

    @FXML
    void saveBill(ActionEvent event) {
        Bill bill = createBill();
        if(bill != null){
            this.billingService.addBill(bill);
            ShowAlert.billAdded();
            this.serviceListToBill.clear();
            this.productListToBill.getProducts().clear();
            updateProductTable();
            updateServiceTable();
            clearBillFields();
        }
    }

    @FXML
    public void loadQuantity(){
        try {
            if(txtProductCode.getText().isEmpty()) {
                ShowAlert.productNotFound();
                txtQuantity.setText("");
                return;
            }
            if(txtQuantity.getText().isEmpty()) {
                txtQuantity.setText("1");
            }
            int maxQuantity = productService.getProductByCode(Long.parseLong(txtProductCode.getText())).getQuantity();
            if(Integer.parseInt(txtQuantity.getText()) > maxQuantity) {
                txtQuantity.setText(String.valueOf(maxQuantity));
            }
        }
        catch (Exception e){
            ShowAlert.productNotFound();
            txtQuantity.setText("");
        }
    }

    private Bill createBill() {
        if (productListToBill.getProducts().isEmpty() && serviceListToBill.isEmpty()) return null;
        if(txtBillName.getText().isEmpty()) this.txtBillName.setText("Cliente");
        return new Bill(
                productListToBill,
                serviceListToBill,
                txtBillName.getText(),
                Double.parseDouble(totalcount.getText().replace(Character.toString(','), "").replace('$','0'))
        );
    }

    private Callback<TableColumn.CellDataFeatures<Product, Double>, ObservableValue<Double>> importCalculator() {
        return SaleViewController.getCellDataFeaturesObservableValueCallback();
    }

    @FXML
    void loadDescTotal(KeyEvent event) {
        loadDescTotal();
        calculateTotal();
    }

    void loadDescTotal(){
        try {
            if(txtDescTotal.getText().isEmpty()) {
                txtDescTotal.setText("0");
            }
            Double.parseDouble(txtDescTotal.getText());
            if(Double.parseDouble(txtDescTotal.getText()) < 0) {
                txtDescTotal.setText("0");
            }
            if(Double.parseDouble(txtDescTotal.getText()) > 100) {
                txtDescTotal.setText("100");
            }
        } catch (Exception e) {
            txtDescTotal.setText("0");
        }
    }

    public void calculateTotal() {
        loadDescTotal();
        double productsPrice = productListToBill.getTotalPrice();
        double servicePrice = serviceListToBill.stream().mapToDouble(Service::getPrice).sum();
        double subTotal = productsPrice + servicePrice;
        double descent = subTotal * (Double.parseDouble(txtDescTotal.getText())/100);
        double total = subTotal - descent;

        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();

        productsPriceCount.setText(currencyFormat.format(productsPrice));
        servicePriceCount.setText(currencyFormat.format(servicePrice));
        subTotalCount.setText(currencyFormat.format(subTotal));
        desc.setText(currencyFormat.format(descent));
        totalcount.setText(currencyFormat.format(total));
    }

    private void setupNumericValidation(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                textField.setText(newValue.replaceAll("[^\\d]", ""));
                ShowAlert.onlyNumbers();
            }
        });
    }

    private <T> Callback<TableColumn<T, Void>, TableCell<T, Void>> createButtonCellFactory(ObservableList<T> list, TableView<?> tableView, Runnable updateMethod) {
        return new Callback<>() {
            @Override
            public TableCell<T, Void> call(final TableColumn<T, Void> param) {
                return new TableCell<>() {
                    private final Button btn = new Button("Eliminar");
                    {
                        btn.setOnAction((ActionEvent event) -> {
                            T item = getTableView().getItems().get(getIndex());
                            list.remove(item);
                            updateMethod.run();
                            tableView.refresh();
                        });
                    }
                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                            setAlignment(Pos.CENTER);
                        }
                    }
                };
            }
        };
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

        currencyFormaterCellFactory(colProductUnitPrice);
        currencyFormaterCellFactory(colProductTotalPrice);
        currencyFormaterCellFactory(colServicePrice);

        tblProductsBill.getItems().clear();
        tblProductsBill.getItems().addAll(this.productListToBill.getProducts());
        tblProductsBill.refresh();

        tblServiceList.getItems().clear();
        tblServiceList.getItems().addAll(serviceListToBill);
        tblServiceList.refresh();

        productListToBill.addListener(c -> calculateTotal());
        serviceListToBill.addListener((ListChangeListener<Service>) c -> calculateTotal());

        colProductActions.setCellFactory(createButtonCellFactory(productListToBill.getProducts(), tblProductsBill, this::updateProductTable));
        colServiceActions.setCellFactory(createButtonCellFactory(serviceListToBill, tblServiceList, this::updateServiceTable));

        setupNumericValidation(txtServicePrice);
        setupNumericValidation(txtQuantity);
        setupNumericValidation(txtProductCode);

        calculateTotal();
    }
}
