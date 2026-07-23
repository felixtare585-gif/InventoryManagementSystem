package com.inventory.views;

import com.inventory.controllers.ProductController;
import com.inventory.models.Product;

import javafx.scene.control.*;
import javafx.scene.layout.VBox;


public class ViewProducts {


    public VBox createPane() {


        Label title = new Label("All Products");


        TableView<Product> table = new TableView<>();


        TableColumn<Product, Integer> idColumn =
                new TableColumn<>("ID");

        idColumn.setCellValueFactory(
                data -> new javafx.beans.property.SimpleIntegerProperty(
                        data.getValue().getId()
                ).asObject()
        );



        TableColumn<Product, String> nameColumn =
                new TableColumn<>("Name");

        nameColumn.setCellValueFactory(
                data -> new javafx.beans.property.SimpleStringProperty(
                        data.getValue().getName()
                )
        );



        TableColumn<Product, String> categoryColumn =
                new TableColumn<>("Category");

        categoryColumn.setCellValueFactory(
                data -> new javafx.beans.property.SimpleStringProperty(
                        data.getValue().getCategory()
                )
        );



        TableColumn<Product, Integer> quantityColumn =
                new TableColumn<>("Quantity");

        quantityColumn.setCellValueFactory(
                data -> new javafx.beans.property.SimpleIntegerProperty(
                        data.getValue().getQuantity()
                ).asObject()
        );



        TableColumn<Product, Double> priceColumn =
                new TableColumn<>("Price");

        priceColumn.setCellValueFactory(
                data -> new javafx.beans.property.SimpleDoubleProperty(
                        data.getValue().getPrice()
                ).asObject()
        );



        table.getColumns().addAll(
                idColumn,
                nameColumn,
                categoryColumn,
                quantityColumn,
                priceColumn
        );



        table.getItems().addAll(
                ProductController.getProducts()
        );



        VBox layout = new VBox(20);


        layout.getChildren().addAll(
                title,
                table
        );


        return layout;

    }

}