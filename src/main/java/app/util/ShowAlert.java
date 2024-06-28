package app.util;

public class ShowAlert {
    public static void productAdded() {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
        alert.setTitle("Producto añadido");
        alert.setHeaderText(null);
        alert.setContentText("El producto se añadido correctamente.");
        alert.showAndWait();
    }

    public static void productExists() {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.WARNING);
        alert.setTitle("El producto ya existe");
        alert.setHeaderText("El producto ya existe");
        alert.setContentText("El producto ya existe.");
        alert.showAndWait();
    }

    public static void billAdded() {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
        alert.setTitle("Factura Confirmada");
        alert.setHeaderText(null);
        alert.setContentText("La venta se confirmo con exito.");
        alert.showAndWait();
    }

    public static void productNotFound() {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.WARNING);
        alert.setTitle("Producto no encontrado");
        alert.setHeaderText("Producto no encontrado");
        alert.setContentText("El producto no se encuentra en la base de datos.");
        alert.showAndWait();
    }

    public static void onlyNumbers() {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.WARNING);
        alert.setTitle("Solo números");
        alert.setHeaderText("Solo números");
        alert.setContentText("Solo se permiten números en este campo.");
        alert.showAndWait();
    }

    public static void invalidData(NumberFormatException e) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
        alert.setTitle("Datos inválidos");
        alert.setHeaderText("Datos inválidos");
        alert.setContentText("Los datos ingresados no son válidos.");
        alert.showAndWait();
    }
}
