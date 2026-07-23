package com.inventory.views;

import com.inventory.controllers.ProductController;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;


public class DeleteProduct {


    public VBox createPane() {


        Label title = new Label("Delete Product");


        TextField idField = new TextField();

        idField.setPromptText("Enter Product ID");



        Button deleteButton = new Button("Delete Product");



        deleteButton.setOnAction(e -> {


            try {


                int id = Integer.parseInt(idField.getText());



                boolean deleted = ProductController.deleteProduct(id);



                Alert alert;



                if(deleted) {


                    alert = new Alert(Alert.AlertType.INFORMATION);

                    alert.setTitle("Success");

                    alert.setHeaderText(null);

                    alert.setContentText(
                            "Product Deleted Successfully!"
                    );


                } 
                else {


                    alert = new Alert(Alert.AlertType.ERROR);

                    alert.setTitle("Not Found");

                    alert.setHeaderText(null);

                    alert.setContentText(
                            "Product ID does not exist!"
                    );

                }



                alert.showAndWait();


                idField.clear();



            } 
            catch(NumberFormatException ex) {


                Alert alert = new Alert(Alert.AlertType.ERROR);


                alert.setTitle("Invalid Input");


                alert.setHeaderText(null);


                alert.setContentText(
                        "Please enter a valid numeric ID!"
                );


                alert.showAndWait();

            }


        });



        VBox layout = new VBox(20);



        layout.getChildren().addAll(

                title,

                idField,

                deleteButton

        );



        layout.setAlignment(Pos.CENTER);



        return layout;

    }

}