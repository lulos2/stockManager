module com.example.stockmanager {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;

    opens app to javafx.fxml;
    opens app.controller to javafx.fxml;
    opens app.model to javafx.base;
    exports app;
    exports app.controller to javafx.fxml;
}