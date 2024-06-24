package app.controller;
import app.model.Product;
import app.model.ProductList;
import app.service.ProductService;
import app.util.Enums;
import app.util.Paths;
import app.util.ShowAlert;
import app.util.StageManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseEvent;

import java.text.NumberFormat;


public class ProductController {

    private final ProductService productService = new ProductService();
    private final ProductList productList;
    @FXML
    private TableView <Product> tblProduct;
    @FXML
    private TableColumn <Product,String> colType;
    @FXML
    private TableColumn <Product,String> colBrand;
    @FXML
    private TableColumn <Product,Long> colCode;
    @FXML
    private TableColumn <Product, Double> colCost;
    @FXML
    private TableColumn <Product, Double> colPrice;
    @FXML
    private TableColumn <Product, Integer> colQuantity;
    @FXML
    private TableColumn<Product, String> colUnidad;
    @FXML
    private TableColumn <Product, String> colDescription;
    @FXML
    private TextField txtType;
    @FXML
    private TextField txtBrand;
    @FXML
    private TextField txtCode;
    @FXML
    private TextField txtCost;
    @FXML
    private TextField txtPrice;
    @FXML
    private TextField txtQuantity;
    @FXML
    private TextField txtDescription;
    @FXML
    private TextField txtSearch;
    @FXML
    private CheckBox chkLts;

    @FXML
    private CheckBox chkUnity;

    public ProductController() {
        this.productList = new ProductList();
    }

    @FXML
    void addProduct(ActionEvent event) {
        addProduct();
    }

    void addProduct() {
        Product product = createProduct();
        if (product != null) {
            productService.addProduct(product);
            productList.addProduct(product);
            updateTable();
            clearFields();
            ShowAlert.productAdded();
        }
    }

    Product createProduct(){
        if(txtType.getText().isEmpty() || txtBrand.getText().isEmpty() || txtCode.getText().isEmpty() || txtCost.getText().isEmpty() || txtPrice.getText().isEmpty() || txtQuantity.getText().isEmpty()) return null;
        try {
            if (Integer.parseInt(txtQuantity.getText()) < 0) return null;
            if(productList.existCode(Long.parseLong(txtCode.getText()))) {
                ShowAlert.productExists();
                return null;
            }
            return new Product(
                    txtType.getText(),
                    txtBrand.getText(),
                    Long.parseLong(txtCode.getText()),
                    Double.parseDouble(txtCost.getText()),
                    Double.parseDouble(txtPrice.getText()),
                    Integer.parseInt(txtQuantity.getText()),
                    txtDescription.getText(),
                    unitType()
            );
        }
        catch (NumberFormatException e){
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    private String unitType() {
        System.out.println(chkLts.isSelected());
        if(chkLts.isSelected()) return Enums.LTS;
        chkUnity.isSelected();
        return Enums.UNITS;
    }
    @FXML
    void deleteProduct(ActionEvent event) {
        deleteProduct();
    }

    void deleteProduct(){
        if(tblProduct.getSelectionModel().getSelectedItem() == null) return;
        Product product = tblProduct.getSelectionModel().getSelectedItem();
        productService.deleteProduct(product);
        productList.removeProduct(product);
        updateTable();
        clearFields();
    }

    @FXML
    void updateProduct(ActionEvent event) {
        updateProduct();
    }

    void updateProduct(){
        if(tblProduct.getSelectionModel().getSelectedItem() == null) return;
        Product product = new Product(
                txtType.getText(),
                txtBrand.getText(),
                Long.parseLong(txtCode.getText()),
                Double.parseDouble(txtCost.getText()),
                Double.parseDouble(txtPrice.getText()),
                Integer.parseInt(txtQuantity.getText()),
                txtDescription.getText(),
                unitType()
        );
        productList.updateProduct(product);
        productService.updateProduct(product);
        updateTable();
        clearFields();
    }

    public void updateTable() {
        tblProduct.getItems().clear();
        tblProduct.getItems().addAll(productService.getAllProducts());
        tblProduct.refresh();
    }

    public void loadProductData() {
        for (Product product : productService.getAllProducts()) {
            this.productList.addProduct(product);
        }
        updateTable();
    }

    private void clearFields() {
        txtType.clear();
        txtBrand.clear();
        txtCode.clear();
        txtCost.clear();
        txtPrice.clear();
        txtQuantity.clear();
        txtDescription.clear();
        txtCode.setDisable(false);
        chkLts.setSelected(false);
        chkUnity.setSelected(false);
    }

    private void updateFields() {
        Product product = tblProduct.getSelectionModel().getSelectedItem();
        txtType.setText(product.getType());
        txtBrand.setText(product.getBrand());
        txtCode.setText(String.valueOf(product.getCode()));
        txtCost.setText(String.valueOf(product.getCost()));
        txtPrice.setText(String.valueOf(product.getPrice()));
        txtQuantity.setText(String.valueOf(product.getQuantity()));
        txtDescription.setText(product.getDescription());

        if(product.getUnitType().equals(Enums.LTS)) chkLts.setSelected(true);
        else chkUnity.setSelected(true);

        txtCode.setDisable(true);
    }

    @FXML
    void searchProduct(ActionEvent event) {
        searchProduct();
    }

    void searchProduct() {
        tblProduct.getItems().clear();
        tblProduct.getItems().addAll(productService.searchProduct(txtSearch.getText()));
        tblProduct.refresh();
    }

    @FXML
    void onlyOneSelected(MouseEvent event) {
        onlyOneSelected();
    }

    void onlyOneSelected() {
        chkLts.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                chkUnity.setSelected(false);
            }
        });
        chkUnity.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                chkLts.setSelected(false);
            }
        });
    }

    @FXML
    void goToMain(ActionEvent event) {
        System.out.println("Go to main");
        StageManager.changeScene(Paths.MAIN_FXML);
    }

    void copiCode(){
        tblProduct.setRowFactory(tv -> {
            TableRow<Product> row = new TableRow<>();
            MenuItem mi = new MenuItem("Copiar codigo");
            mi.setOnAction(event -> {
                Product product = row.getItem();
                Clipboard clipboard = Clipboard.getSystemClipboard();
                ClipboardContent content = new ClipboardContent();
                content.putString(String.valueOf(product.getCode()));
                clipboard.setContent(content);
            });
            ContextMenu contextMenu = new ContextMenu(mi);
            row.contextMenuProperty().bind(
                    javafx.beans.binding.Bindings.when(row.emptyProperty())
                            .then((ContextMenu) null)
                            .otherwise(contextMenu)
            );
            return row;
        });
    }

    @FXML
    void initialize() {
        colPrice.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);

                if (item == null || empty) {
                    setText(null);
                } else {
                    NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
                    setText(currencyFormat.format(item));
                }
            }
        });

        colCost.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);

                if (item == null || empty) {
                    setText(null);
                } else {
                    NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
                    setText(currencyFormat.format(item));
                }
            }
        });

        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colBrand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colCost.setCellValueFactory(new PropertyValueFactory<>("cost"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colUnidad.setCellValueFactory(new PropertyValueFactory<>("unitType"));

        tblProduct.setOnMouseClicked(event -> {
            if(tblProduct.getSelectionModel().getSelectedItem() != null) updateFields();
        });
        loadProductData();
        copiCode();
    }
}
