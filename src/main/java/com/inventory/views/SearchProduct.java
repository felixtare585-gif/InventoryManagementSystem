package com.inventory.views;

import com.inventory.controllers.ProductController;
import com.inventory.models.Product;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class SearchProduct {

    public VBox createPane() {

        // Main Container setup
        VBox mainLayout = new VBox(25);
        mainLayout.setPadding(new Insets(30));
        mainLayout.setAlignment(Pos.TOP_LEFT);

        // Page Header
        Label title = new Label("Search Product");
        title.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #1e293b;");

        // Centered Main Card Container
        VBox cardContainer = new VBox(20);
        cardContainer.setMaxWidth(550);
        cardContainer.setPadding(new Insets(25));
        cardContainer.setStyle(
            "-fx-background-color: #ffffff; " +
            "-fx-background-radius: 10px; " +
            "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.08), 10, 0, 0, 4);"
        );

        // Search Control Bar (Input + Button)
        TextField idField = new TextField();
        idField.setPromptText("Enter Product ID");
        idField.setPrefHeight(38);
        idField.setStyle(
            "-fx-background-radius: 6px; " +
            "-fx-border-color: #cbd5e1; " +
            "-fx-border-radius: 6px; " +
            "-fx-padding: 5 10; " +
            "-fx-background-color: #f8fafc;"
        );

        Button searchButton = new Button("Search");
        searchButton.setPrefHeight(38);
        searchButton.setStyle(
            "-fx-background-color: #2563eb; " +
            "-fx-text-fill: #ffffff; " +
            "-fx-font-weight: bold; " +
            "-fx-padding: 8 20; " +
            "-fx-background-radius: 6px; " +
            "-fx-cursor: hand;"
        );

        HBox searchBox = new HBox(10, idField, searchButton);
        HBox.setHgrow(idField, Priority.ALWAYS);

        // Result Container Card (Initially Hidden / Empty state)
        VBox resultCard = new VBox(15);
        resultCard.setPadding(new Insets(20));
        resultCard.setStyle(
            "-fx-background-color: #f8fafc; " +
            "-fx-border-color: #e2e8f0; " +
            "-fx-border-radius: 8px; " +
            "-fx-background-radius: 8px;"
        );

        Label statusMessage = new Label("Enter a Product ID above to view details.");
        statusMessage.setStyle("-fx-text-fill: #64748b; -fx-font-size: 13px;");
        resultCard.getChildren().add(statusMessage);

        // Search Action Logic
        Runnable performSearch = () -> {
            resultCard.getChildren().clear();
            String inputText = idField.getText().trim();

            if (inputText.isEmpty()) {
                statusMessage.setText("Please enter a Product ID first.");
                statusMessage.setStyle("-fx-text-fill: #dc2626; -fx-font-weight: bold;");
                resultCard.getChildren().add(statusMessage);
                return;
            }

            try {
                int id = Integer.parseInt(inputText);
                Product product = ProductController.searchProduct(id);

                if (product != null) {
                    // Header for Found Product
                    Label cardTitle = new Label("Product Details");
                    cardTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #0f172a;");

                    GridPane detailsGrid = new GridPane();
                    detailsGrid.setHgap(20);
                    detailsGrid.setVgap(12);

                    // Structured Detail Rows
                    detailsGrid.add(createLabel("Product ID:"), 0, 0);
                    detailsGrid.add(createValueLabel(String.valueOf(product.getId())), 1, 0);

                    detailsGrid.add(createLabel("Product Name:"), 0, 1);
                    detailsGrid.add(createValueLabel(product.getName()), 1, 1);

                    detailsGrid.add(createLabel("Category:"), 0, 2);
                    detailsGrid.add(createValueLabel(product.getCategory()), 1, 2);

                    detailsGrid.add(createLabel("Quantity:"), 0, 3);
                    detailsGrid.add(createValueLabel(String.valueOf(product.getQuantity())), 1, 3);

                    detailsGrid.add(createLabel("Price:"), 0, 4);
                    detailsGrid.add(createValueLabel(String.format("KSh %,.2f", product.getPrice())), 1, 4);

                    // Stock Status Badge
                    detailsGrid.add(createLabel("Stock Status:"), 0, 5);
                    Label badge = new Label();
                    int qty = product.getQuantity();

                    if (qty == 0) {
                        badge.setText("OUT OF STOCK");
                        badge.setStyle("-fx-background-color: #fee2e2; -fx-text-fill: #b91c1c; -fx-font-weight: bold; -fx-background-radius: 12px; -fx-padding: 4 12;");
                    } else if (qty <= 5) {
                        badge.setText("LOW STOCK");
                        badge.setStyle("-fx-background-color: #fef3c7; -fx-text-fill: #b45309; -fx-font-weight: bold; -fx-background-radius: 12px; -fx-padding: 4 12;");
                    } else {
                        badge.setText("IN STOCK");
                        badge.setStyle("-fx-background-color: #dcfce7; -fx-text-fill: #15803d; -fx-font-weight: bold; -fx-background-radius: 12px; -fx-padding: 4 12;");
                    }

                    detailsGrid.add(badge, 1, 5);

                    resultCard.getChildren().addAll(cardTitle, detailsGrid);

                } else {
                    Label errorLbl = new Label("❌ No product found with ID: " + id);
                    errorLbl.setStyle("-fx-text-fill: #dc2626; -fx-font-weight: bold; -fx-font-size: 14px;");
                    resultCard.getChildren().add(errorLbl);
                }

            } catch (NumberFormatException ex) {
                Label errorLbl = new Label("⚠️ Invalid Input: Product ID must be a number!");
                errorLbl.setStyle("-fx-text-fill: #dc2626; -fx-font-weight: bold; -fx-font-size: 14px;");
                resultCard.getChildren().add(errorLbl);
            }
        };

        // Attach action events
        searchButton.setOnAction(e -> performSearch.run());
        idField.setOnAction(e -> performSearch.run()); // Press Enter to Search

        cardContainer.getChildren().addAll(searchBox, resultCard);
        mainLayout.getChildren().addAll(title, cardContainer);

        return mainLayout;
    }

    // Helper method for Detail Field Titles
    private Label createLabel(String text) {
        Label label = new Label(text);
        label.setStyle("-fx-font-weight: bold; -fx-text-fill: #475569; -fx-font-size: 13px;");
        return label;
    }

    // Helper method for Detail Values
    private Label createValueLabel(String text) {
        Label label = new Label(text);
        label.setStyle("-fx-font-weight: bold; -fx-text-fill: #0f172a; -fx-font-size: 14px;");
        return label;
    }
}