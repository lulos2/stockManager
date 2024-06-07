package com.example.stockmanager;

import com.example.stockmanager.DAO.ProductDAO;
import com.example.stockmanager.DAO.ProductDAOImpl;
import com.example.stockmanager.model.Product;
import com.example.stockmanager.util.DatabaseUtil;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

import static javafx.geometry.Pos.CENTER;

public class StartAplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        DatabaseUtil.createTablesIfNotExist();
        primaryStage.setTitle("Stock Manager");
        primaryStage.setScene(new Scene(createAddProductPane(), 400, 400));
        primaryStage.show();
        Button showTableButton = new Button("Show Product Table");
        showTableButton.setOnAction(event -> {
            primaryStage.setScene(new Scene(createProductTablePane(), 400, 400));
        });
    }



    private Parent createAddProductPane() {
        GridPane grid = new GridPane();
        grid.setAlignment(CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Label nameLabel = new Label("Name:");
        grid.add(nameLabel, 0, 1);
        TextField nameTextField = new TextField();
        grid.add(nameTextField, 1, 1);

        Label typeLabel = new Label("Type:");
        grid.add(typeLabel, 0, 2);
        TextField typeTextField = new TextField();
        grid.add(typeTextField, 1, 2);

        Label brandLabel = new Label("Brand:");
        grid.add(brandLabel, 0, 3);
        TextField brandTextField = new TextField();
        grid.add(brandTextField, 1, 3);

        Label codeLabel = new Label("Code:");
        grid.add(codeLabel, 0, 4);
        TextField codeTextField = new TextField();
        grid.add(codeTextField, 1, 4);

        Label costLabel = new Label("Cost:");
        grid.add(costLabel, 0, 5);
        TextField costTextField = new TextField();
        grid.add(costTextField, 1, 5);

        Label priceLabel = new Label("Price:");
        grid.add(priceLabel, 0, 6);
        TextField priceTextField = new TextField();
        grid.add(priceTextField, 1, 6);

        Label quantityLabel = new Label("Quantity:");
        grid.add(quantityLabel, 0, 7);
        TextField quantityTextField = new TextField();
        grid.add(quantityTextField, 1, 7);

        Button addButton = new Button("Add");
        addButton.setOnAction(event -> {
            Product product = new Product(
                    nameTextField.getText(),
                    typeTextField.getText(),
                    brandTextField.getText(),
                    Long.parseLong(codeTextField.getText()),
                    Double.parseDouble(costTextField.getText()),
                    Double.parseDouble(priceTextField.getText()),
                    Integer.parseInt(quantityTextField.getText())
            );
            ProductDAO productDAO = new ProductDAOImpl();
            productDAO.addProduct(product);
        });
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(addButton);
        grid.add(hbBtn, 1, 8);

        HBox hbBtn2 = new HBox(10);
        hbBtn2.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn2.getChildren().add(createProductTablePane());
        grid.add(hbBtn2, 1, 9);


        return grid;
    }

    private Parent createProductTablePane() {
        // Crear la tabla
        TableView<Product> table = new TableView<>();

        // Crear las columnas
        TableColumn<Product, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Product, String> typeColumn = new TableColumn<>("Type");
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));

        TableColumn<Product, String> brandColumn = new TableColumn<>("Brand");
        brandColumn.setCellValueFactory(new PropertyValueFactory<>("brand"));

        TableColumn<Product, Integer> codeColumn = new TableColumn<>("Code");
        codeColumn.setCellValueFactory(new PropertyValueFactory<>("code"));

        TableColumn<Product, Double> costColumn = new TableColumn<>("Cost");
        costColumn.setCellValueFactory(new PropertyValueFactory<>("cost"));

        TableColumn<Product, Double> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn<Product, Integer> quantityColumn = new TableColumn<>("Quantity");
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        // Agregar las columnas a la tabla
        table.getColumns().add(nameColumn);
        table.getColumns().add(typeColumn);
        table.getColumns().add(brandColumn);
        table.getColumns().add(codeColumn);
        table.getColumns().add(costColumn);
        table.getColumns().add(priceColumn);
        table.getColumns().add(quantityColumn);

        // Obtener los productos de la base de datos
        ProductDAO productDAO = new ProductDAOImpl();
        List<Product> products = productDAO.getAllProducts();

        // Agregar los productos a la tabla
        table.getItems().addAll(products);

        // Crear un VBox para contener la tabla
        VBox vbox = new VBox();
        vbox.getChildren().addAll(table);

        return vbox;
    }
}