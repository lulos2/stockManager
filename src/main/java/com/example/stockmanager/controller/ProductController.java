package com.example.stockmanager.controller;

import com.example.stockmanager.DAO.ProductDAO;
import com.example.stockmanager.DAO.ProductDAOImpl;
import com.example.stockmanager.model.Product;
import com.example.stockmanager.model.ProductList;
import com.example.stockmanager.service.ProductService;
import javafx.beans.InvalidationListener;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

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

    public ProductController() {
        this.productList = new ProductList();
    }

    @FXML
    void addProduct(ActionEvent event) {
        addProduct();
    }
    void addProduct() {
        Product product = new Product(
                txtType.getText(),
                txtBrand.getText(),
                Long.parseLong(txtCode.getText()),
                Double.parseDouble(txtCost.getText()),
                Double.parseDouble(txtPrice.getText()),
                Integer.parseInt(txtQuantity.getText()),
                txtDescription.getText()
        );
        productService.addProduct(product);
        productList.addProduct(product);
        updateTable();
        clearFields();
    }

    @FXML
    void deleteProduct(ActionEvent event) {
        deleteProduct();
    }
    void deleteProduct(){
        Product product = tblProduct.getSelectionModel().getSelectedItem();
        productService.deleteProduct(product);
        productList.removeProduct(product);
        updateTable();
    }

    @FXML
    void updateProduct(ActionEvent event) {
        Product product = new Product(
                txtType.getText(),
                txtBrand.getText(),
                Long.parseLong(txtCode.getText()),
                Double.parseDouble(txtCost.getText()),
                Double.parseDouble(txtPrice.getText()),
                Integer.parseInt(txtQuantity.getText()),
                txtDescription.getText()
        );
        productList.updateProduct(product);
        productService.updateProduct(product);
        updateTable();
        clearFields();
    }

    public void updateTable() {
        tblProduct.getItems().clear();
        tblProduct.getItems().addAll(productList.getProducts());
        tblProduct.refresh();
    }

    public void loadProductData() {
        //tblProduct.setItems(productService.getProducts());
        //tblProduct.refresh();
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

        tblProduct.setOnMouseClicked(event -> {
            if(tblProduct.getSelectionModel().getSelectedItem() != null) updateFields();
        });
        loadProductData();
    }

    public ProductList getProductList() {
        return productList;
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
        txtCost.setEditable(true);
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
        txtCode.setDisable(true);
    }
}
