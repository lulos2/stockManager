module com.example.stockmanager {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens com.example.stockmanager to javafx.fxml;
    opens com.example.stockmanager.controller to javafx.fxml;
    opens com.example.stockmanager.model to javafx.base;
    exports com.example.stockmanager;
    exports com.example.stockmanager.controller to javafx.fxml;
}