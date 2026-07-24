package com.inventory.views;

import com.inventory.controllers.ProductController;
import com.inventory.models.Product;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class AddProduct {

    public VBox createPane() {

        // Main Container setup
        VBox mainLayout = new VBox(25);
        mainLayout.setPadding(new Insets(30));
        mainLayout.setAlignment(Pos.TOP_LEFT);

        // Page Header
        Label title = new Label("Add New Product");
        title.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #1e293b;");

        // Centered Card Container for Form Controls
        VBox cardContainer = new VBox(20);
        cardContainer.setMaxWidth(500);
        cardContainer.setPadding(new Insets(25));
        cardContainer.setStyle(
            "-fx-background-color: #ffffff; " +
            "-fx-background-radius: 10px; " +
            "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.08), 10, 0, 0, 4);"
        );

        // Form Layout Grid
        GridPane formGrid = new GridPane();
        formGrid.setHgap(15);
        formGrid.setVgap(15);

        // Form Fields setup
        TextField idField = createStyledTextField("e.g. 101");
        TextField nameField = createStyledTextField("e.g. Mechanical Keyboard");
        TextField categoryField = createStyledTextField("e.g. Accessories");
        TextField quantityField = createStyledTextField("e.g. 25");
        TextField priceField = createStyledTextField("e.g. 3500.00");

        // Add Labels and Input Fields into Grid Layout
        formGrid.add(createFieldLabel("Product ID:"), 0, 0);
        formGrid.add(idField, 1, 0);

        formGrid.add(createFieldLabel("Product Name:"), 0, 1);
        formGrid.add(nameField, 1, 1);

        formGrid.add(createFieldLabel("Category:"), 0, 2);
        formGrid.add(categoryField, 1, 2);

        formGrid.add(createFieldLabel("Quantity:"), 0, 3);
        formGrid.add(quantityField, 1, 3);

        formGrid.add(createFieldLabel("Price (KSh):"), 0, 4);
        formGrid.add(priceField, 1, 4);

        // Save Action Button
        Button saveButton = new Button("Save Product");
        saveButton.setMaxWidth(Double.MAX_VALUE);
        saveButton.setStyle(
            "-fx-background-color: #2563eb; " +
            "-fx-text-fill: #ffffff; " +
            "-fx-font-size: 14px; " +
            "-fx-font-weight: bold; " +
            "-fx-padding: 10 20; " +
            "-fx-background-radius: 6px; " +
            "-fx-cursor: hand;"
        );

        // Action Logic (Preserved)
        saveButton.setOnAction(e -> {
            try {
                if (idField.getText().isEmpty() ||
                    nameField.getText().isEmpty() ||
                    categoryField.getText().isEmpty() ||
                    quantityField.getText().isEmpty() ||
                    priceField.getText().isEmpty()) {

                    showAlert(
                        Alert.AlertType.WARNING,
                        "Missing Information",
                        "Please fill all fields!"
                    );
                    return;
                }

                int id = Integer.parseInt(idField.getText().trim());
                String name = nameField.getText().trim();
                String category = categoryField.getText().trim();
                int quantity = Integer.parseInt(quantityField.getText().trim());
                double price = Double.parseDouble(priceField.getText().trim());

                // Check if product ID already exists
                if (ProductController.searchProduct(id) != null) {
                    showAlert(
                        Alert.AlertType.ERROR,
                        "Duplicate Product ID",
                        "Product ID already exists!"
                    );
                    return;
                }

                Product product = new Product(id, name, category, quantity, price);
                ProductController.addProduct(product);

                showAlert(
                    Alert.AlertType.INFORMATION,
                    "Success",
                    "Product Added Successfully!"
                );

                // Clear input fields
                idField.clear();
                nameField.clear();
                categoryField.clear();
                quantityField.clear();
                priceField.clear();

            } catch (NumberFormatException ex) {
                showAlert(
                    Alert.AlertType.ERROR,
                    "Invalid Input",
                    "ID, Quantity and Price must be valid numbers!"
                );
            }
        });

        cardContainer.getChildren().addAll(formGrid, saveButton);
        mainLayout.getChildren().addAll(title, cardContainer);

        return mainLayout;
    }

    // Helper method to style Labels
    private Label createFieldLabel(String text) {
        Label label = new Label(text);
        label.setStyle("-fx-font-weight: bold; -fx-text-fill: #475569; -fx-font-size: 13px;");
        return label;
    }

    // Helper method to style TextFields
    private TextField createStyledTextField(String placeholder) {
        TextField tf = new TextField();
        tf.setPromptText(placeholder);
        tf.setPrefHeight(36);
        tf.setPrefWidth(280);
        tf.setStyle(
            "-fx-background-radius: 6px; " +
            "-fx-border-color: #cbd5e1; " +
            "-fx-border-radius: 6px; " +
            "-fx-padding: 5 10; " +
            "-fx-background-color: #f8fafc;"
        );
        return tf;
    }

    // Alert helper method
    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}