package app.controller;

import app.util.Paths;
import app.util.StageManager;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;

import java.text.NumberFormat;

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

    protected <T> void currencyFormaterCellFactory(TableColumn<T, Double> column) {
        column.setCellFactory(_ -> new TableCell<>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);

                if (item == null || empty) {
                    setText(null);
                } else {
                    NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
                    setText(currencyFormat.format(item));
                }
            }
        });
    }

}
