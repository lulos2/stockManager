package app.controller;

import app.util.Paths;
import app.util.StageManager;
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
    public void goToSales(ActionEvent actionEvent) {
        StageManager.changeScene(Paths.SALES_FXML);
    }
}
