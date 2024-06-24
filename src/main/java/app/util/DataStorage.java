package app.util;

import app.model.Bill;
import app.model.Service;
import app.model.ProductList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DataStorage {
    private static final ProductList productListToBill = new ProductList();
    private static final ObservableList<Service> serviceListToBill = FXCollections.observableArrayList();
    public static ProductList getProductListToBill() {
        return productListToBill;
    }

    public static ObservableList<Service> getServiceListToBill() {
        return serviceListToBill;
    }

    public static Bill actualBill = new Bill();

    public static void setBill(Bill bill) {
        actualBill = bill;
    }
}
