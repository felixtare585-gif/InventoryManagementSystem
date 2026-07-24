package com.inventory.views;

import java.util.List;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class MainLayout {

    private final BorderPane root = new BorderPane();

    public BorderPane createLayout() {

        // App Title Header in Sidebar
        Label appBrand = new Label("📦 Inventory Core");
        appBrand.setStyle(
            "-fx-font-size: 18px; " +
            "-fx-font-weight: bold; " +
            "-fx-text-fill: #ffffff; " +
            "-fx-padding: 0 0 10 5;"
        );

        // Sidebar Navigation Buttons
        Button homeButton = new Button("🏠 Dashboard");
        Button addButton = new Button("➕ Add Product");
        Button viewButton = new Button("📦 View Products");
        Button searchButton = new Button("🔍 Search Product");
        Button updateButton = new Button("✏ Update Product");
        Button deleteButton = new Button("🗑 Delete Product");

        List<Button> menuButtons = List.of(
            homeButton, addButton, viewButton, 
            searchButton, updateButton, deleteButton
        );

        // Apply base menu button styling class
        menuButtons.forEach(btn -> btn.getStyleClass().add("menu-button"));

        // Sidebar VBox Setup
        VBox sidebar = new VBox(12);
        sidebar.getStyleClass().add("sidebar");
        sidebar.setPadding(new Insets(25, 15, 25, 15));
        sidebar.setAlignment(Pos.TOP_LEFT);

        sidebar.getChildren().add(appBrand);
        sidebar.getChildren().addAll(menuButtons);

        root.setLeft(sidebar);

        // Set Default Active View (Dashboard)
        setActiveView(homeButton, new HomeDashboard().createPane(), menuButtons);

        // Navigation Actions
        homeButton.setOnAction(e -> setActiveView(homeButton, new HomeDashboard().createPane(), menuButtons));
        addButton.setOnAction(e -> setActiveView(addButton, new AddProduct().createPane(), menuButtons));
        viewButton.setOnAction(e -> setActiveView(viewButton, new ViewProducts().createPane(), menuButtons));
        searchButton.setOnAction(e -> setActiveView(searchButton, new SearchProduct().createPane(), menuButtons));
        updateButton.setOnAction(e -> setActiveView(updateButton, new UpdateProduct().createPane(), menuButtons));
        deleteButton.setOnAction(e -> setActiveView(deleteButton, new DeleteProduct().createPane(), menuButtons));

        return root;
    }

    /**
     * Updates the active view in the center pane and highlights the selected sidebar button.
     */
    private void setActiveView(Button selectedButton, Node viewPane, List<Button> allButtons) {
        // Remove active class from all buttons
        allButtons.forEach(btn -> btn.getStyleClass().remove("menu-button-active"));

        // Highlight selected button
        if (!selectedButton.getStyleClass().contains("menu-button-active")) {
            selectedButton.getStyleClass().add("menu-button-active");
        }

        // Load content pane into center
        root.setCenter(viewPane);
    }
}