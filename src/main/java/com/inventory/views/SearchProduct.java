package com.inventory.views;

import com.inventory.controllers.ProductController;
import com.inventory.models.Product;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;


public class SearchProduct {


    public VBox createPane() {


        Label title = new Label("Search Product");


        TextField idField = new TextField();

        idField.setPromptText("Enter Product ID");



        Button searchButton = new Button("Search");



        TextArea resultArea = new TextArea();

        resultArea.setEditable(false);



        searchButton.setOnAction(e -> {


            try {


                int id = Integer.parseInt(idField.getText());



                Product product = ProductController.searchProduct(id);



                if(product != null) {


                    resultArea.setText(
                            "Product Found\n\n" +
                            "ID: " + product.getId() +
                            "\nName: " + product.getName() +
                            "\nCategory: " + product.getCategory() +
                            "\nQuantity: " + product.getQuantity() +
                            "\nPrice: " + product.getPrice()
                    );


                } else {


                    resultArea.setText(
                            "Product not found!"
                    );

                }



            } catch(NumberFormatException ex) {


                resultArea.setText(
                        "Please enter a valid ID!"
                );

            }


        });



        VBox layout = new VBox(15);



        layout.getChildren().addAll(
                title,
                idField,
                searchButton,
                resultArea
        );



        layout.setAlignment(Pos.CENTER);



        return layout;

    }

}