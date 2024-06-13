package com.example.stockmanager;

import com.example.stockmanager.controller.ProductController;
import com.example.stockmanager.model.ProductList;
import com.example.stockmanager.util.DatabaseUtil;
import com.example.stockmanager.util.Paths;
import com.example.stockmanager.util.StageManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.Objects;

public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        DatabaseUtil.createTablesIfNotExist();
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResource(Paths.ICONAPP)).toExternalForm()));

        StageManager.setPrimaryStage(stage);

        FXMLLoader loader = new FXMLLoader(getClass().getResource(Paths.PRODUCT_FXML));
        AnchorPane pane = loader.load();

        Scene scene = new Scene(pane);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource(Paths.STYLES_CSS)).toExternalForm());
        StageManager.getPrimaryStage().setScene(scene);
        stage.show();
    }
}