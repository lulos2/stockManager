package app.model;

public class Product {
    private int id;
    private String description;
    private String type;
    private String brand;
    private Long code;
    private double cost;
    private double price;
    private double quantity;
    private String unitType;
    private int version;
    private boolean active;
    private String created_at;
    private String updated_at;

    public Product() {
    }

    public Product(String type, String brand, Long code, double cost, double price, double quantity, String description, String unitType) {
        this.description = description;
        this.type = type;
        this.brand = brand;
        this.code = code;
        this.cost = cost;
        this.price = price;
        this.quantity = quantity;
        this.unitType = unitType;
    }

    public Product(int id, long code, String type, String brand, double cost, double price, double quantity, String description, String unitType) {
        this.id = id;
        this.code = code;
        this.type = type;
        this.brand = brand;
        this.price = price;
        this.quantity = quantity;
        this.description = description;
        this.unitType = unitType;
        this.cost = cost;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public String getUnitType() {
        return unitType;
    }

    public void setUnitType(String unitType) {
        this.unitType = unitType;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}
