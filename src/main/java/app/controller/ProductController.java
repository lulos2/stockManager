package app.controller;
import app.model.Product;
import app.model.ProductList;
import app.service.ProductService;
import app.util.Enums;
import app.util.ShowAlert;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseEvent;


public class ProductController extends BaseController{

    private final ProductService productService = new ProductService();
    private final ProductList productList;
    private boolean isUpdating = false;

    @FXML
    public TextField txtCostPrice;
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
    private TableColumn <Product, Double> colQuantity;
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
            if (Double.parseDouble(txtQuantity.getText()) < 0) return null;
            if(productList.existCode(Long.parseLong(txtCode.getText()))) {
                ShowAlert.productExists();
                return null;
            }
            double quantity = Double.parseDouble(txtQuantity.getText());
            double finalQuantity = chkUnity.isSelected() ? Math.round(quantity) : quantity;
            return new Product(
                    txtType.getText(),
                    txtBrand.getText(),
                    Long.parseLong(txtCode.getText()),
                    Double.parseDouble(txtCost.getText()),
                    Double.parseDouble(txtPrice.getText()),
                    finalQuantity,
                    txtDescription.getText(),
                    unitType()
            );
        }
        catch (NumberFormatException e){
            ShowAlert.invalidData(e);
            return null;
        }
    }

    private String unitType() {
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
        double quantity = Double.parseDouble(txtQuantity.getText());
        double finalQuantity = chkUnity.isSelected() ? Math.round(quantity) : quantity;
        Product product = new Product(
                txtType.getText(),
                txtBrand.getText(),
                Long.parseLong(txtCode.getText()),
                Double.parseDouble(txtCost.getText()),
                Double.parseDouble(txtPrice.getText()),
                finalQuantity,
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
        tblProduct.getItems().addAll(productService.getActiveProducts());
        tblProduct.refresh();
    }

    public void loadProductData() {
        for (Product product : productService.getActiveProducts()) {
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

    void calculatePrice() {
        if(txtCost.getText().isEmpty()){
            txtPrice.clear();
            txtCostPrice.clear();
            return;
        }
        if(txtCostPrice.getText().isEmpty())return;
        try {
            isUpdating = true;
            double cost = Double.parseDouble(txtCost.getText());
            double costPrice = Double.parseDouble(txtCostPrice.getText());
            Integer price = (int) (cost * ((costPrice/100)+1));
            txtPrice.setText(String.valueOf(price));
        } catch (NumberFormatException e) {
            System.out.println("Error calculate price: " + e.getMessage());
        } finally {
            isUpdating = false;
        }
    }

    void calculateCostPrice() {
        if (txtPrice.getText().isEmpty() || txtCost.getText().isEmpty()) return;
        try {
            isUpdating = true;
            double cost = Double.parseDouble(txtCost.getText());
            double price = Double.parseDouble(txtPrice.getText());
            Integer costPrice = (int) (((price - cost)/cost) * 100);
            txtCostPrice.setText(String.valueOf(costPrice));
        } catch (NumberFormatException e) {
            System.out.println("Error calculate costPice: " + e.getMessage());
        } finally {
            isUpdating = false;
        }
    }

    @FXML
    void initialize() {
        setupIntegerValidation(txtCode);
        setupDoubleValidation(txtCost);
        setupDoubleValidation(txtPrice);
        setupDoubleValidation(txtQuantity);
        currencyFormaterCellFactory(colPrice);
        currencyFormaterCellFactory(colCost);

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

        txtCost.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!isUpdating) {
                calculatePrice();
            }
        });
        txtCostPrice.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!isUpdating) {
                calculatePrice();
            }
        });
        txtPrice.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!isUpdating) {
                calculateCostPrice();
            }
        });

        loadProductData();
        copiCode();
    }
}
