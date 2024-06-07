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
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            String sql = "CREATE TABLE IF NOT EXISTS Product (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "name TEXT NOT NULL," +
                    "type TEXT NOT NULL," +
                    "brand TEXT NOT NULL," +
                    "code INTEGER NOT NULL," +
                    "cost REAL NOT NULL," +
                    "price REAL NOT NULL," +
                    "quantity INTEGER NOT NULL" +
                    ");";

            stmt.executeUpdate(sql);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
