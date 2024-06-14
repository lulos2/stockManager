package com.example.stockmanager.util;

public class ShowAlert {
    public static void showAlertProductAdded() {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
        alert.setTitle("Producto añadido");
        alert.setHeaderText(null);
        alert.setContentText("El producto se añadido correctamente.");
        alert.showAndWait();
    }

    public static void showAlertProductExists() {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.WARNING);
        alert.setTitle("El producto ya existe");
        alert.setHeaderText("El producto ya existe");
        alert.setContentText("El producto ya existe.");
        alert.showAndWait();
    }

    public static void showAlertBillAdded() {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
        alert.setTitle("Factura Confirmada");
        alert.setHeaderText(null);
        alert.setContentText("La venta se confirmo con exito.");
        alert.showAndWait();
    }

    public static void showAlertProductNotFound() {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.WARNING);
        alert.setTitle("Producto no encontrado");
        alert.setHeaderText("Producto no encontrado");
        alert.setContentText("El producto no se encuentra en la base de datos.");
        alert.showAndWait();
    }
}
