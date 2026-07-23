package com.inventory.views;


import com.inventory.controllers.ProductController;
import com.inventory.models.Product;


import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;



public class UpdateProduct {



    public VBox createPane() {



        Label title = new Label("Update Product");



        TextField idField = new TextField();
        idField.setPromptText("Product ID");



        TextField nameField = new TextField();
        nameField.setPromptText("New Product Name");



        TextField categoryField = new TextField();
        categoryField.setPromptText("New Category");



        TextField quantityField = new TextField();
        quantityField.setPromptText("New Quantity");



        TextField priceField = new TextField();
        priceField.setPromptText("New Price");



        Button updateButton = new Button("Update Product");



        updateButton.setOnAction(e -> {


            try {


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



                boolean updated =
                        ProductController.updateProduct(product);



                Alert alert;



                if(updated){


                    alert = new Alert(
                            Alert.AlertType.INFORMATION
                    );

                    alert.setContentText(
                            "Product Updated Successfully!"
                    );


                } else {


                    alert = new Alert(
                            Alert.AlertType.ERROR
                    );

                    alert.setContentText(
                            "Product ID not found!"
                    );

                }



                alert.showAndWait();



            }
            catch(NumberFormatException ex){



                Alert alert =
                        new Alert(Alert.AlertType.ERROR);


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

                updateButton

        );



        layout.setAlignment(Pos.CENTER);



        return layout;


    }

}