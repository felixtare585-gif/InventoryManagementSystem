package com.inventory.controllers;

import com.inventory.database.DatabaseConnection;
import com.inventory.models.Product;
import com.inventory.utils.AlertHelper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class ProductController {

    // Add Product
    public static void addProduct(Product product) {

        String sql = "INSERT INTO products(id, name, category, quantity, price) VALUES(?,?,?,?,?)";

        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setInt(1, product.getId());
            stmt.setString(2, product.getName());
            stmt.setString(3, product.getCategory());
            stmt.setInt(4, product.getQuantity());
            stmt.setDouble(5, product.getPrice());

            stmt.executeUpdate();

            System.out.println("Product Saved Successfully!");

        } catch (SQLException e) {

            AlertHelper.showError(
                    "Database Error",
                    "Unable to add product.\n\nThe Product ID may already exist."
            );

        }

    }



    // View All Products
    public static ObservableList<Product> getProducts() {

        ObservableList<Product> products =
                FXCollections.observableArrayList();

        String sql = "SELECT * FROM products ORDER BY id ASC";

        try (
                Connection conn = DatabaseConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)
        ) {

            while (rs.next()) {

                Product product = new Product(

                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("category"),
                        rs.getInt("quantity"),
                        rs.getDouble("price")

                );

                products.add(product);

            }

        } catch (SQLException e) {

            AlertHelper.showError(
                    "Database Error",
                    "Unable to load products."
            );

        }

        return products;

    }



    // Search Product By ID
    public static Product searchProduct(int id) {

        String sql = "SELECT * FROM products WHERE id=?";

        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                return new Product(

                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("category"),
                        rs.getInt("quantity"),
                        rs.getDouble("price")

                );

            }

        } catch (SQLException e) {

            AlertHelper.showError(
                    "Database Error",
                    "Unable to search for the product."
            );

        }

        return null;

    }



    // Delete Product
    public static boolean deleteProduct(int id) {

        String sql = "DELETE FROM products WHERE id=?";

        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setInt(1, id);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {

            AlertHelper.showError(
                    "Database Error",
                    "Unable to delete the product."
            );

        }

        return false;

    }



    // Update Product
    public static boolean updateProduct(Product product) {

        String sql = "UPDATE products SET name=?, category=?, quantity=?, price=? WHERE id=?";

        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setString(1, product.getName());
            stmt.setString(2, product.getCategory());
            stmt.setInt(3, product.getQuantity());
            stmt.setDouble(4, product.getPrice());
            stmt.setInt(5, product.getId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {

            AlertHelper.showError(
                    "Database Error",
                    "Unable to update the product."
            );

        }

        return false;

    }
    // Total number of products
    public static int getTotalProducts() {

        String sql = "SELECT COUNT(*) FROM products";

        try (
                Connection conn = DatabaseConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)
        ) {

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }


    // Products with quantity less than 10
    public static int getLowStockCount() {

        String sql = "SELECT COUNT(*) FROM products WHERE quantity < 10";

        try (
                Connection conn = DatabaseConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)
        ) {

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }


    // Total inventory value
    public static double getInventoryValue() {

        String sql = "SELECT SUM(quantity * price) FROM products";

        try (
                Connection conn = DatabaseConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)
        ) {

            if (rs.next()) {
                return rs.getDouble(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }


    // Total quantity of all products
    public static int getTotalQuantity() {

        String sql = "SELECT SUM(quantity) FROM products";

        try (
                Connection conn = DatabaseConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)
        ) {

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }
}