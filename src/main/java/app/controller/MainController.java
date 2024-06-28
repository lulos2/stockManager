package app.controller;

import app.model.Product;
import app.service.ProductService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import java.util.List;

public class MainController extends BaseController{

    ProductService productService = new ProductService();

    @FXML
    private BarChart<String, Double> barChartMostSold;

    public void initialize() {
        setBarChartMostSold();
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
