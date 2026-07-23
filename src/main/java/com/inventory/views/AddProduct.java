package com.inventory.views;

import com.inventory.controllers.ProductController;
import com.inventory.models.Product;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;


public class AddProduct {


    public VBox createPane() {


        Label title = new Label("Add New Product");


        TextField idField = new TextField();
        idField.setPromptText("Product ID");


        TextField nameField = new TextField();
        nameField.setPromptText("Product Name");


        TextField categoryField = new TextField();
        categoryField.setPromptText("Category");


        TextField quantityField = new TextField();
        quantityField.setPromptText("Quantity");


        TextField priceField = new TextField();
        priceField.setPromptText("Price");


        Button saveButton = new Button("Save Product");


        saveButton.setOnAction(e -> {

            try {


                if(idField.getText().isEmpty() ||
                   nameField.getText().isEmpty() ||
                   categoryField.getText().isEmpty() ||
                   quantityField.getText().isEmpty() ||
                   priceField.getText().isEmpty()) {


                    Alert alert = new Alert(Alert.AlertType.WARNING);

                    alert.setTitle("Missing Information");
                    alert.setHeaderText(null);
                    alert.setContentText("Please fill all fields!");

                    alert.showAndWait();

                    return;
                }



                int id = Integer.parseInt(idField.getText());

                String name = nameField.getText();

                String category = categoryField.getText();

                int quantity = Integer.parseInt(quantityField.getText());

                double price = Double.parseDouble(priceField.getText());



                Product product = new Product(
                        id,
                        name,
                        category,
                        quantity,
                        price
                );



                ProductController.addProduct(product);



                Alert alert = new Alert(Alert.AlertType.INFORMATION);

                alert.setTitle("Success");

                alert.setHeaderText(null);

                alert.setContentText(
                        "Product Added Successfully!"
                );

                alert.showAndWait();



                idField.clear();
                nameField.clear();
                categoryField.clear();
                quantityField.clear();
                priceField.clear();



            } catch(NumberFormatException ex) {


                Alert alert = new Alert(Alert.AlertType.ERROR);

                alert.setTitle("Invalid Input");

                alert.setHeaderText(null);

                alert.setContentText(
                        "ID, Quantity and Price must be numbers!"
                );

                alert.showAndWait();

            }

        });



        VBox layout = new VBox(15);


        layout.getChildren().addAll(
                title,
                idField,
                nameField,
                categoryField,
                quantityField,
                priceField,
                saveButton
        );


        layout.setAlignment(Pos.CENTER);


        return layout;

    }

}