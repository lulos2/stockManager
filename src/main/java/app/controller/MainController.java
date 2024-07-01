package app.controller;

import app.model.Product;
import app.service.ProductService;
import app.util.CSVLoader;
import app.util.ShowAlert;
import javafx.stage.FileChooser;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import java.io.File;
import java.sql.SQLException;
import java.util.List;

public class MainController extends BaseController{

    ProductService productService = new ProductService();
    CSVLoader csvLoader = new CSVLoader();

    @FXML
    private BarChart<String, Double> barChartMostSold;

    public MainController() throws SQLException {
    }

    public void initialize() {
        setBarChartMostSold();
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
                e.fillInStackTrace();
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
                e.fillInStackTrace();
            }
        }
    }

    public void setBarChartMostSold() {
        List<Product> products = productService.getProductsSold();
        javafx.collections.ObservableList<XYChart.Series<String, Double>> bestSellers = FXCollections.observableArrayList();
        for (Product product : products) {
            XYChart.Series<String, Double> series = new XYChart.Series<>();
            String productDescription = STR."\{product.getType()} \{product.getBrand()} \{product.getDescription()}";
            series.setName(productDescription);
            Double quantitySold = (double) product.getQuantity();
            XYChart.Data<String, Double> data = new XYChart.Data<>(productDescription, quantitySold);
            series.getData().add(data);
            bestSellers.add(series);

            Label label = new Label(quantitySold.toString());
            StackPane stackPane = new StackPane();
            stackPane.getChildren().add(label);

            data.setNode(stackPane);
        }
        barChartMostSold.setData(bestSellers);
        barChartMostSold.getXAxis().setTickLabelsVisible(false);
        barChartMostSold.getXAxis().getTickMarks();
        barChartMostSold.setBarGap(-20.0);
    }

}
