package com.inventory.views;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;


public class DashboardLayout {

    private BorderPane root;


    public BorderPane createLayout() {

        root = new BorderPane();


        // Sidebar buttons
        Button dashboardBtn = new Button("Dashboard");
        Button addBtn = new Button("Add Product");
        Button viewBtn = new Button("View Products");
        Button searchBtn = new Button("Search Product");
        Button updateBtn = new Button("Update Product");
        Button deleteBtn = new Button("Delete Product");


        VBox sidebar = new VBox(15);

        sidebar.setPadding(new Insets(20));


        sidebar.getChildren().addAll(
                dashboardBtn,
                addBtn,
                viewBtn,
                searchBtn,
                updateBtn,
                deleteBtn
        );


        root.setLeft(sidebar);


        // Default page
        root.setCenter(
                new HomeDashboard().createPane()
        );


        // Navigation

        dashboardBtn.setOnAction(e -> {

            root.setCenter(
                    new HomeDashboard().createPane()
            );

        });


        addBtn.setOnAction(e -> {

            root.setCenter(
                    new AddProduct().createPane()
            );

        });


        viewBtn.setOnAction(e -> {

            root.setCenter(
                    new ViewProducts().createPane()
            );

        });


        searchBtn.setOnAction(e -> {

            root.setCenter(
                    new SearchProduct().createPane()
            );

        });


        updateBtn.setOnAction(e -> {

            root.setCenter(
                    new UpdateProduct().createPane()
            );

        });


        deleteBtn.setOnAction(e -> {

            root.setCenter(
                    new DeleteProduct().createPane()
            );

        });


        return root;
    }
}