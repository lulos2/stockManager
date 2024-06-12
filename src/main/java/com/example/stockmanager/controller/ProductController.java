package com.example.stockmanager.controller;
import com.example.stockmanager.model.Product;
import com.example.stockmanager.model.ProductList;
import com.example.stockmanager.service.ProductService;
import com.example.stockmanager.util.Enums;
import com.example.stockmanager.util.Paths;
import com.example.stockmanager.util.StageManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class ProductController {

    private final ProductService productService = new ProductService();
    private ProductList productList;
    @FXML
    private TableView <Product> tblProduct;
    @FXML
    private TableColumn <Product,String> colType;
    @FXML
    private TableColumn <Product,String> colBrand;
    @FXML
    private TableColumn <Product,Long> colCode;
    @FXML
    private TableColumn <Product, Double> colCost;
    @FXML
    private TableColumn <Product, Double> colPrice;
    @FXML
    private TableColumn <Product, Integer> colQuantity;
    @FXML
    private TableColumn<Product, String> colUnidad;
    @FXML
    private TableColumn <Product, String> colDescription;
    @FXML
    private TextField txtType;
    @FXML
    private TextField txtBrand;
    @FXML
    private TextField txtCode;
    @FXML
    private TextField txtCost;
    @FXML
    private TextField txtPrice;
    @FXML
    private TextField txtQuantity;
    @FXML
    private TextField txtDescription;
    @FXML
    private TextField txtSearch;
    @FXML
    private CheckBox chkLts;

    @FXML
    private CheckBox chkUnity;

    public ProductController() {
        this.productList = new ProductList();
    }

    @FXML
    void addProduct(ActionEvent event) {
        addProduct();
    }

    void addProduct() {
        if(txtType.getText().isEmpty() || txtBrand.getText().isEmpty() || txtCode.getText().isEmpty() || txtCost.getText().isEmpty() || txtPrice.getText().isEmpty() || txtQuantity.getText().isEmpty()) return;
        if(productList.existCode(Long.parseLong(txtCode.getText()))) return;
        if(Integer.parseInt(txtQuantity.getText()) < 0) return;
        Product product = new Product(
                txtType.getText(),
                txtBrand.getText(),
                Long.parseLong(txtCode.getText()),
                Double.parseDouble(txtCost.getText()),
                Double.parseDouble(txtPrice.getText()),
                Integer.parseInt(txtQuantity.getText()),
                txtDescription.getText(),
                unitType()
        );
        productService.addProduct(product);
        productList.addProduct(product);
        updateTable();
        clearFields();
    }

    private String unitType() {
        System.out.println(chkLts.isSelected());
        if(chkLts.isSelected()) return Enums.LTS;
        chkUnity.isSelected();
        return Enums.UNITS;
    }
    @FXML
    void deleteProduct(ActionEvent event) {
        deleteProduct();
    }

    void deleteProduct(){
        if(tblProduct.getSelectionModel().getSelectedItem() == null) return;
        Product product = tblProduct.getSelectionModel().getSelectedItem();
        productService.deleteProduct(product);
        productList.removeProduct(product);
        updateTable();
        clearFields();
    }

    @FXML
    void updateProduct(ActionEvent event) {
        updateProduct();
    }

    void updateProduct(){
        if(tblProduct.getSelectionModel().getSelectedItem() == null) return;
        Product product = new Product(
                txtType.getText(),
                txtBrand.getText(),
                Long.parseLong(txtCode.getText()),
                Double.parseDouble(txtCost.getText()),
                Double.parseDouble(txtPrice.getText()),
                Integer.parseInt(txtQuantity.getText()),
                txtDescription.getText(),
                unitType()
        );
        productList.updateProduct(product);
        productService.updateProduct(product);
        updateTable();
        clearFields();
    }

    public void updateTable() {
        tblProduct.getItems().clear();
        tblProduct.getItems().addAll(productService.getAllProducts());
        tblProduct.refresh();
    }

    public void loadProductData() {
        for (Product product : productService.getAllProducts()) {
            productList.addProduct(product);
        }
        updateTable();
    }

    public void setProductList(ProductList productList) {
        this.productList = productList;
    }

    private void clearFields() {
        txtType.clear();
        txtBrand.clear();
        txtCode.clear();
        txtCost.clear();
        txtPrice.clear();
        txtQuantity.clear();
        txtDescription.clear();
        txtCode.setDisable(false);
        chkLts.setSelected(false);
        chkUnity.setSelected(false);
    }

    private void updateFields() {
        Product product = tblProduct.getSelectionModel().getSelectedItem();
        txtType.setText(product.getType());
        txtBrand.setText(product.getBrand());
        txtCode.setText(String.valueOf(product.getCode()));
        txtCost.setText(String.valueOf(product.getCost()));
        txtPrice.setText(String.valueOf(product.getPrice()));
        txtQuantity.setText(String.valueOf(product.getQuantity()));
        txtDescription.setText(product.getDescription());

        if(product.getUnitType().equals(Enums.LTS)) chkLts.setSelected(true);
        else chkUnity.setSelected(true);

        txtCode.setDisable(true);
    }

    @FXML
    void searchProduct(ActionEvent event) {
        searchProduct();
    }

    public void searchProduct() {
        tblProduct.getItems().clear();
        tblProduct.getItems().addAll(productService.searchProduct(txtSearch.getText()));
        tblProduct.refresh();
    }

    @FXML
    void onlyOneSelected(MouseEvent event) {
        onlyOneSelected();
    }

    void onlyOneSelected() {
        chkLts.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                chkUnity.setSelected(false);
            }
        });
        chkUnity.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                chkLts.setSelected(false);
            }
        });
    }

    @FXML
    void goToBilling(ActionEvent event) {
        StageManager.changeScene(Paths.BILLING_FXML);
    }

    @FXML
    void initialize() {
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colBrand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colCost.setCellValueFactory(new PropertyValueFactory<>("cost"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colUnidad.setCellValueFactory(new PropertyValueFactory<>("unitType"));

        tblProduct.setOnMouseClicked(event -> {
            if(tblProduct.getSelectionModel().getSelectedItem() != null) updateFields();
        });
        loadProductData();
    }
}
