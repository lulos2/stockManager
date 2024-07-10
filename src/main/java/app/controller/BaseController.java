package app.controller;

import app.model.Bill;
import app.util.Paths;
import app.util.ShowAlert;
import app.util.StageManager;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
    @FXML
    protected void goToClient() {
        StageManager.changeScene(Paths.CLIENT_FXML);
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

    protected void setupIntegerValidation(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                textField.setText(newValue.replaceAll("[^\\d]", ""));
                ShowAlert.onlyNumbers();
            }
        });
    }

    protected void setupDoubleValidation(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*(\\.\\d*)?")) {
                textField.setText(newValue.replaceAll("[^\\d.]", ""));
                ShowAlert.showInformation("numeros y puntos", "Solo se permiten numeros y puntos en este campo.");
            }
        });
    }

    protected void formatDateCellFactory(TableColumn<Bill, Timestamp> column) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yy");
        column.setCellFactory(col -> new TableCell<Bill, Timestamp>() {
            @Override
            protected void updateItem(Timestamp item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                } else {
                    LocalDateTime dateTime = item.toLocalDateTime();
                    setText(dateTime.format(formatter));
                }
            }
        });
    }
}
