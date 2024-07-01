package app.util;

import app.model.Product;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;

public class CSVLoader {

    private final Connection connection;

    public CSVLoader() throws SQLException {
        this.connection = DatabaseUtil.getConnection();
    }

    public void loadCSVs(String fileToRead) throws IOException, SQLException {
        ArrayList<Product> products = new ArrayList<>();
        Reader in = new FileReader(fileToRead);
        Iterable<CSVRecord> records = CSVFormat.RFC4180.builder()
                .setHeader("type", "brand", "code", "price", "cost", "description", "quantity", "unitType")
                .setSkipHeaderRecord(true)
                .build()
                .parse(in);
        for (CSVRecord record : records) {
            String type = record.get("type");
            String brand = record.get("brand");
            String code = record.get("code");
            double price = Double.parseDouble(record.get("price"));
            double cost = Double.parseDouble(record.get("cost"));
            String description = record.get("description");
            double quantity = Double.parseDouble(record.get("quantity"));
            String unitType = Objects.equals(record.get("unitType"), "LTS") ? Enums.LTS : Enums.UNITS;
            products.add(new Product(type, brand, Long.parseLong(code), cost, price, (int) quantity, description, unitType));
        }
        insertIntoTable(products);
    }

    private void insertIntoTable(ArrayList<Product> products) throws SQLException {
        String sql = "INSERT INTO Product" +
                "(type, brand, code, price, cost, description, quantity, unitType) VALUES (?, ?, ?, ?, ?, ?, ?,?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        for (Product product : products) {
            statement.setString(1, product.getType());
            statement.setString(2, product.getBrand());
            statement.setLong(3, product.getCode());
            statement.setDouble(4, product.getPrice());
            statement.setDouble(5, product.getCost());
            statement.setString(6, product.getDescription());
            statement.setInt(7, product.getQuantity());
            statement.setString(8, product.getUnitType());
            statement.executeUpdate();
        }
    }

    public void exportToCSV(String fileToWrite) throws IOException, SQLException {
        String sql = "SELECT * FROM Product";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        FileWriter fileWriter = new FileWriter(fileToWrite);
        fileWriter.append("type,brand,code,price,cost,description,quantity,unitType\n");
        while (resultSet.next()) {
            fileWriter.append(resultSet.getString("type"));
            fileWriter.append(",");
            fileWriter.append(resultSet.getString("brand"));
            fileWriter.append(",");
            fileWriter.append(String.valueOf(resultSet.getLong("code")));
            fileWriter.append(",");
            fileWriter.append(String.valueOf(resultSet.getDouble("price")));
            fileWriter.append(",");
            fileWriter.append(String.valueOf(resultSet.getDouble("cost")));
            fileWriter.append(",");
            fileWriter.append(resultSet.getString("description"));
            fileWriter.append(",");
            fileWriter.append(String.valueOf(resultSet.getInt("quantity")));
            fileWriter.append(",");
            fileWriter.append(resultSet.getString("unitType"));
            fileWriter.append("\n");
        }
        fileWriter.flush();
        fileWriter.close();
    }
}