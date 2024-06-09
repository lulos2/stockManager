package com.example.stockmanager;

import com.example.stockmanager.controller.ProductController;
import com.example.stockmanager.model.ProductList;
import com.example.stockmanager.util.DatabaseUtil;
import com.example.stockmanager.util.Paths;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        DatabaseUtil.createTablesIfNotExist();
        FXMLLoader loader = new FXMLLoader(getClass().getResource(Paths.PRODUCT_FXML));
        AnchorPane pane = loader.load();

        ProductController controller = loader.getController();
        controller.setProductList(new ProductList());

        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }
}