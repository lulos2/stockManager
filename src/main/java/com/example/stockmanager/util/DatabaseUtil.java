package com.example.stockmanager.util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseUtil {
    private static final String DB_URL = "jdbc:sqlite:stock.db";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    public static void createTablesIfNotExist() {
       createProductTable();
       createBillTable();
       createServiceTable();
       createBillProductTable();
       createBillServiceTable();
    }

    private static void createProductTable() {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            String sql = "CREATE TABLE IF NOT EXISTS Product (" +
                    "id INTEGER," +
                    "type TEXT NOT NULL," +
                    "brand TEXT NOT NULL," +
                    "code INTEGER PRIMARY KEY UNIQUE," +
                    "cost REAL NOT NULL," +
                    "price REAL NOT NULL," +
                    "quantity INTEGER NOT NULL ," +
                    "description TEXT" +
                    ");";
            stmt.executeUpdate(sql);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void createBillTable() {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            String sql = "CREATE TABLE IF NOT EXISTS Bill (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "date TEXT NOT NULL," +
                    "total REAL NOT NULL" +
                    ");";
            stmt.executeUpdate(sql);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void createBillProductTable() {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            String sql = "CREATE TABLE IF NOT EXISTS Bill_Product (" +
                    "bill_id INTEGER," +
                    "product_code INTEGER," +
                    "quantity INTEGER NOT NULL," +
                    "FOREIGN KEY(bill_id) REFERENCES Bills(id)," +
                    "FOREIGN KEY(product_code) REFERENCES Product(code)" +
                    ");";
            stmt.executeUpdate(sql);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void createServiceTable() {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            String sql = "CREATE TABLE IF NOT EXISTS Service (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "name TEXT NOT NULL," +
                    "price REAL NOT NULL," +
                    "duration INTEGER," +
                    "description TEXT" +
                    ");";
            stmt.executeUpdate(sql);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void createBillServiceTable() {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            String sql = "CREATE TABLE IF NOT EXISTS Bill_Service (" +
                    "bill_id INTEGER," +
                    "service_id INTEGER," +
                    "quantity INTEGER NOT NULL," +
                    "FOREIGN KEY(bill_id) REFERENCES Bills(id)," +
                    "FOREIGN KEY(service_id) REFERENCES Service(id)" +
                    ");";
            stmt.executeUpdate(sql);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
