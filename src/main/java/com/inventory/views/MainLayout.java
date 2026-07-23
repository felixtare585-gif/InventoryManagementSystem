package com.inventory.views;


import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;



public class MainLayout {


    private BorderPane root = new BorderPane();



    public BorderPane createLayout() {



        Button addButton = new Button("Add Product");

        Button viewButton = new Button("View Products");

        Button searchButton = new Button("Search Product");

        Button updateButton = new Button("Update Product");

        Button deleteButton = new Button("Delete Product");



        VBox menu = new VBox(20);



        menu.getChildren().addAll(
                addButton,
                viewButton,
                searchButton,
                updateButton,
                deleteButton
        );



        menu.setPadding(
                new Insets(20)
        );



        root.setLeft(menu);



        // Default screen
        root.setCenter(
                new AddProduct().createPane()
        );



        addButton.setOnAction(e -> {

            root.setCenter(
                    new AddProduct().createPane()
            );

        });



        viewButton.setOnAction(e -> {

            root.setCenter(
                    new ViewProducts().createPane()
            );

        });



        searchButton.setOnAction(e -> {

            root.setCenter(
                    new SearchProduct().createPane()
            );

        });



        updateButton.setOnAction(e -> {

            root.setCenter(
                    new UpdateProduct().createPane()
            );

        });



        deleteButton.setOnAction(e -> {

            root.setCenter(
                    new DeleteProduct().createPane()
            );

        });



        return root;

    }

}