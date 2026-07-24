package com.inventory.views;

import com.inventory.controllers.ProductController;
import com.inventory.models.Product;

import java.util.Optional;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class DeleteProduct {

    public VBox createPane() {

        // Main Container setup
        VBox mainLayout = new VBox(25);
        mainLayout.setPadding(new Insets(30));
        mainLayout.setAlignment(Pos.TOP_LEFT);

        // Page Header
        Label title = new Label("Delete Product");
        title.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #1e293b;");

        // Centered Card Container
        VBox cardContainer = new VBox(20);
        cardContainer.setMaxWidth(550);
        cardContainer.setPadding(new Insets(25));
        cardContainer.setStyle(
            "-fx-background-color: #ffffff; " +
            "-fx-background-radius: 10px; " +
            "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.08), 10, 0, 0, 4);"
        );

        // Input Field & Search Button
        TextField idField = new TextField();
        idField.setPromptText("Enter Product ID to Delete");
        idField.setPrefHeight(38);
        idField.setStyle(
            "-fx-background-radius: 6px; " +
            "-fx-border-color: #cbd5e1; " +
            "-fx-border-radius: 6px; " +
            "-fx-padding: 5 10; " +
            "-fx-background-color: #f8fafc;"
        );

        Button checkBtn = new Button("Check Product");
        checkBtn.setPrefHeight(38);
        checkBtn.setStyle(
            "-fx-background-color: #0284c7; " +
            "-fx-text-fill: #ffffff; " +
            "-fx-font-weight: bold; " +
            "-fx-padding: 8 16; " +
            "-fx-background-radius: 6px; " +
            "-fx-cursor: hand;"
        );

        HBox idBox = new HBox(10, idField, checkBtn);
        HBox.setHgrow(idField, Priority.ALWAYS);

        // Product Preview Card
        VBox previewCard = new VBox(12);
        previewCard.setPadding(new Insets(20));
        previewCard.setStyle(
            "-fx-background-color: #f8fafc; " +
            "-fx-border-color: #e2e8f0; " +
            "-fx-border-radius: 8px; " +
            "-fx-background-radius: 8px;"
        );

        Label statusMessage = new Label("Enter a Product ID to inspect before deleting.");
        statusMessage.setStyle("-fx-text-fill: #64748b; -fx-font-size: 13px;");
        previewCard.getChildren().add(statusMessage);

        // Delete Button (Initially Disabled until product is checked)
        Button deleteButton = new Button("Delete Product");
        deleteButton.setMaxWidth(Double.MAX_VALUE);
        deleteButton.setDisable(true);
        deleteButton.setStyle(
            "-fx-background-color: #dc2626; " +
            "-fx-text-fill: #ffffff; " +
            "-fx-font-size: 14px; " +
            "-fx-font-weight: bold; " +
            "-fx-padding: 10 20; " +
            "-fx-background-radius: 6px; " +
            "-fx-cursor: hand;"
        );

        // Check Product Action
        Runnable inspectProduct = () -> {
            previewCard.getChildren().clear();
            deleteButton.setDisable(true);
            String input = idField.getText().trim();

            if (input.isEmpty()) {
                statusMessage.setText("Please enter a Product ID.");
                statusMessage.setStyle("-fx-text-fill: #dc2626; -fx-font-weight: bold;");
                previewCard.getChildren().add(statusMessage);
                return;
            }

            try {
                int id = Integer.parseInt(input);
                Product product = ProductController.searchProduct(id);

                if (product != null) {
                    Label cardTitle = new Label("Product Selected for Deletion");
                    cardTitle.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: #dc2626;");

                    GridPane details = new GridPane();
                    details.setHgap(15);
                    details.setVgap(8);

                    details.add(createLabel("ID:"), 0, 0);
                    details.add(createValueLabel(String.valueOf(product.getId())), 1, 0);

                    details.add(createLabel("Name:"), 0, 1);
                    details.add(createValueLabel(product.getName()), 1, 1);

                    details.add(createLabel("Category:"), 0, 2);
                    details.add(createValueLabel(product.getCategory()), 1, 2);

                    details.add(createLabel("Price:"), 0, 3);
                    details.add(createValueLabel(String.format("KSh %,.2f", product.getPrice())), 1, 3);

                    previewCard.getChildren().addAll(cardTitle, details);
                    deleteButton.setDisable(false); // Enable delete button once verified

                } else {
                    Label errorLbl = new Label("❌ Product ID " + id + " not found!");
                    errorLbl.setStyle("-fx-text-fill: #dc2626; -fx-font-weight: bold;");
                    previewCard.getChildren().add(errorLbl);
                }

            } catch (NumberFormatException ex) {
                Label errorLbl = new Label("⚠️ ID must be a numeric value!");
                errorLbl.setStyle("-fx-text-fill: #dc2626; -fx-font-weight: bold;");
                previewCard.getChildren().add(errorLbl);
            }
        };

        checkBtn.setOnAction(e -> inspectProduct.run());
        idField.setOnAction(e -> inspectProduct.run());

        // Delete Logic with Confirmation Window
        deleteButton.setOnAction(e -> {
            try {
                int id = Integer.parseInt(idField.getText().trim());

                Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
                confirmAlert.setTitle("Confirm Deletion");
                confirmAlert.setHeaderText("Delete Product ID: " + id + "?");
                confirmAlert.setContentText("Are you sure you want to delete this product? This action cannot be undone.");

                Optional<ButtonType> result = confirmAlert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {

                    boolean deleted = ProductController.deleteProduct(id);

                    if (deleted) {
                        showAlert(Alert.AlertType.INFORMATION, "Success", "Product Deleted Successfully!");
                        idField.clear();
                        previewCard.getChildren().clear();
                        previewCard.getChildren().add(statusMessage);
                        deleteButton.setDisable(true);
                    } else {
                        showAlert(Alert.AlertType.ERROR, "Not Found", "Product ID does not exist!");
                    }
                }

            } catch (NumberFormatException ex) {
                showAlert(Alert.AlertType.ERROR, "Invalid Input", "Please enter a valid numeric ID!");
            }
        });

        cardContainer.getChildren().addAll(idBox, previewCard, deleteButton);
        mainLayout.getChildren().addAll(title, cardContainer);

        return mainLayout;
    }

    private Label createLabel(String text) {
        Label label = new Label(text);
        label.setStyle("-fx-font-weight: bold; -fx-text-fill: #475569; -fx-font-size: 13px;");
        return label;
    }

    private Label createValueLabel(String text) {
        Label label = new Label(text);
        label.setStyle("-fx-font-weight: bold; -fx-text-fill: #0f172a; -fx-font-size: 13px;");
        return label;
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}