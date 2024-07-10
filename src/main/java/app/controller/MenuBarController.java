package app.controller;

import app.util.CSVLoader;
import app.util.ShowAlert;
import javafx.fxml.FXML;
import javafx.stage.FileChooser;
import java.io.File;
import java.sql.SQLException;

public class MenuBarController extends BaseController {

    CSVLoader csvLoader = new CSVLoader();

    public MenuBarController() throws SQLException {
    }

    @FXML
    void loadProductCSV () {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        File selectedFile = fileChooser.showOpenDialog(null);
        if(selectedFile != null) {
            try {
                csvLoader.loadCSVs(selectedFile.getAbsolutePath());
                ShowAlert.showInformation("Archivo CSV cargado", "El archivo se cargo correctamente.");

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void exportProductCSV () {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save CSV File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        File selectedFile = fileChooser.showSaveDialog(null);
        if(selectedFile != null) {
            try {
                csvLoader.exportToCSV(selectedFile.getAbsolutePath());
                ShowAlert.showInformation("Datos CSV exportados", "Los productos de la base de datos se exporto correctamente.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
