module app {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;
    requires org.xerial.sqlitejdbc;
    requires org.slf4j;
    requires org.slf4j.simple;
    requires org.apache.commons.csv;

    exports app;
    exports app.model;
    exports app.service;
    exports app.util;
    exports app.controller to javafx.fxml;

    opens app to javafx.fxml;
    opens app.controller to javafx.fxml;
    opens app.model to javafx.base;
}