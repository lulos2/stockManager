package app.controller;

import app.model.Bill;
import app.service.BillingService;
import app.util.DataStorage;
import app.util.Paths;
import app.util.StageManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

public class SaleController extends BaseController{

    private final ArrayList<Bill> billList = new ArrayList<>();
    private final BillingService billingService = new BillingService();

    @FXML
    private TableColumn<Bill, String> colSaleClient;

    @FXML
    private TableColumn<Bill, Timestamp> colSaleDate;

    @FXML
    private TableColumn<Bill, Void> colSaleDetail;

    @FXML
    private TableColumn<Bill, Long> colSaleId;

    @FXML
    private TableColumn<Bill, Double> colSaleTotal;

    @FXML
    private DatePicker endDatePicker;

    @FXML
    private TableView<Bill> salesTable;

    @FXML
    private DatePicker startDatePicker;

    public SaleController() {
    }

    public void updateTable() {
        salesTable.getItems().clear();
        salesTable.getItems().addAll(billingService.getAllBills());
        salesTable.refresh();
    }

    public void loadProductData() {
        this.billList.addAll(this.billingService.getAllBills());
        updateTable();
    }

    @FXML
    void filterByDate(ActionEvent event) {
        Date startDate = new Date(0);
        Date endDate = new Date();
        if(startDatePicker.getValue() != null) {
            startDate = java.sql.Date.valueOf(startDatePicker.getValue());
        }
        if (endDatePicker.getValue() != null) {
            endDate = java.sql.Date.valueOf(endDatePicker.getValue());
        }
        filterByDate(startDate, endDate);
    }

    void filterByDate(Date startDate, Date endDate) {
        ArrayList<Bill> billFilteredList = new ArrayList<>();

        for (Bill bill : billList) {
            if (bill.getDate().after(startDate) && bill.getDate().before(endDate)) {
                billFilteredList.add(bill);
            }
        }
        salesTable.getItems().clear();
        salesTable.getItems().addAll(billFilteredList);
        salesTable.refresh();
    }

    private void openBillDetail(Bill bill) {
        bill = billingService.getBillById(bill.getId());
        DataStorage.setBill(bill);
        StageManager.changeScene(Paths.SALES_DETAIL_FXML);
    }

    private void formatDateCellFactory(TableColumn<Bill, Timestamp> column) {
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

    @FXML
    void initialize() {
        formatDateCellFactory(colSaleDate);
        colSaleId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colSaleClient.setCellValueFactory(new PropertyValueFactory<>("client"));
        colSaleDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colSaleTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colSaleDetail.setCellFactory(_ -> new TableCell<>() {
            private final Button btn = new Button("Ver Detalle");
            {
                btn.setOnAction(_ -> {
                    Bill bill = getTableView().getItems().get(getIndex());
                    openBillDetail(bill);
                });
            }
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(btn);
                }
            }
        });

        currencyFormaterCellFactory(colSaleTotal);
        loadProductData();
    }
}
