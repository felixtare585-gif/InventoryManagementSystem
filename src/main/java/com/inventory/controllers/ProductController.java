package com.inventory.controllers;

import com.inventory.database.DatabaseConnection;
import com.inventory.models.Product;

import java.sql.*;
import java.util.ArrayList;

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

            e.printStackTrace();

        }

    }



    // View All Products
    public static ArrayList<Product> getProducts() {


        ArrayList<Product> products = new ArrayList<>();


        String sql = "SELECT * FROM products";


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

            e.printStackTrace();

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



            if(rs.next()) {


                return new Product(

                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("category"),
                        rs.getInt("quantity"),
                        rs.getDouble("price")

                );

            }


        } catch (SQLException e) {

            e.printStackTrace();

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



        } catch(SQLException e) {

            e.printStackTrace();

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



        } catch(SQLException e) {


            e.printStackTrace();


        }



        return false;

    }


}