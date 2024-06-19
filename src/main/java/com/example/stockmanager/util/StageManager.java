package com.example.stockmanager.util;

import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

import java.util.Objects;

public class StageManager {
    private static Stage primaryStage;

    public static void setPrimaryStage(Stage stage) {
        primaryStage = stage;
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void changeScene(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(StageManager.class.getResource(fxml)));
            primaryStage.getScene().setRoot(loader.load());
        }
        catch (Exception ignored){
            System.out.println("Error al cargar la escena"+ ignored.getMessage());
        }
    }
}
