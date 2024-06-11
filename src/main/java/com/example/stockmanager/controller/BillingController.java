package com.example.stockmanager.controller;
import com.example.stockmanager.util.Paths;
import com.example.stockmanager.util.StageManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class BillingController {

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
    private TableView<?> tblProductsBill;

    @FXML
    private TableView<?> tblServiceList;

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
    void addProductToBillList(ActionEvent event) {

    }

    @FXML
    void addServiceToBillList(ActionEvent event) {

    }

    @FXML
    void decreaseQuantity(ActionEvent event) {

    }

    @FXML
    void deleteItem(ActionEvent event) {

    }

    @FXML
    void increaseQuantity(ActionEvent event) {

    }

    @FXML
    void saveBill(ActionEvent event) {

    }

    @FXML
    void goToProduct(ActionEvent event) {
        StageManager.changeScene(Paths.PRODUCT_FXML);
    }

}
