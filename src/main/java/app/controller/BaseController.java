package app.controller;

import app.util.Paths;
import app.util.StageManager;
import javafx.fxml.FXML;

public abstract class BaseController {

    @FXML
    protected void goToMain() {
        StageManager.changeScene(Paths.MAIN_FXML);
    }
    @FXML
    protected void goToBilling() {
        StageManager.changeScene(Paths.BILLING_FXML);
    }
    @FXML
    protected void goToProduct() {
        StageManager.changeScene(Paths.PRODUCT_FXML);
    }
    @FXML
    protected void goToSales() {
        StageManager.changeScene(Paths.SALES_FXML);
    }
    @FXML
    protected void goToSalesDetail() {
        StageManager.changeScene(Paths.SALES_DETAIL_FXML);
    }
}
