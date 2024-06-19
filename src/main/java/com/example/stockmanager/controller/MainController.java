package com.example.stockmanager.controller;

import com.example.stockmanager.util.Paths;
import com.example.stockmanager.util.StageManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class MainController {




    @FXML
    void goToBilling(ActionEvent event) {
        StageManager.changeScene(Paths.BILLING_FXML);
    }

    @FXML
    void goToProduct(ActionEvent event) {
        StageManager.changeScene(Paths.PRODUCT_FXML);
    }

    @FXML
    void goToReports(ActionEvent event) {

    }

    @FXML
    void goToSelled(ActionEvent event) {

    }
}
