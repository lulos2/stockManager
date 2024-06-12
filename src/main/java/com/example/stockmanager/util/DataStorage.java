package com.example.stockmanager.util;

import com.example.stockmanager.model.ProductList;
import com.example.stockmanager.model.Service;
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
}
