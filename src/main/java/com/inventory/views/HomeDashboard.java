package com.inventory.views;

import com.inventory.controllers.ProductController;
import com.inventory.models.Product;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class HomeDashboard {

    public BorderPane createPane() {

        BorderPane root = new BorderPane();

        Label title = new Label("Inventory Dashboard");
        title.getStyleClass().add("title");

        // Summary Cards Section
        VBox totalProducts = createCard(
                "Total Products",
                String.valueOf(ProductController.getTotalProducts())
        );

        VBox totalQuantity = createCard(
                "Total Quantity",
                String.valueOf(ProductController.getTotalQuantity())
        );

        VBox lowStock = createCard(
                "Low Stock",
                String.valueOf(ProductController.getLowStockCount())
        );

        VBox inventoryValue = createCard(
                "Inventory Value",
                String.format("KSh %,.2f", ProductController.getInventoryValue())
        );

        HBox cards = new HBox(20);
        cards.getChildren().addAll(
                totalProducts,
                totalQuantity,
                lowStock,
                inventoryValue
        );
        cards.setAlignment(Pos.CENTER_LEFT);

        // Recent / Main Overview Table
        TableView<Product> productTable = createProductTable();

        VBox content = new VBox(25);
        content.getChildren().addAll(
                title,
                cards,
                productTable
        );

        content.setPadding(new Insets(30));

        root.setCenter(content);

        return root;
    }

    private VBox createCard(String titleText, String valueText) {

        Label title = new Label(titleText);
        title.getStyleClass().add("card-title");

        Label value = new Label(valueText);
        value.getStyleClass().add("card-number");

        // Scale down text size slightly for long currency strings to prevent truncation
        if (valueText.length() > 10) {
            value.setStyle("-fx-font-size: 20px;");
        }

        VBox card = new VBox(8);
        card.getChildren().addAll(title, value);

        card.getStyleClass().add("card");
        card.setAlignment(Pos.CENTER_LEFT);
        card.setPrefSize(240, 110);

        return card;
    }

    private TableView<Product> createProductTable() {

        TableView<Product> table = new TableView<>();

        // ID Column
        TableColumn<Product, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getId()).asObject());
        idColumn.setPrefWidth(60);

        // Name Column
        TableColumn<Product, String> nameColumn = new TableColumn<>("Product Name");
        nameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));

        // Category Column
        TableColumn<Product, String> categoryColumn = new TableColumn<>("Category");
        categoryColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCategory()));

        // Quantity Column
        TableColumn<Product, Integer> quantityColumn = new TableColumn<>("Quantity");
        quantityColumn.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getQuantity()).asObject());

        // Price Column
        TableColumn<Product, Double> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getPrice()).asObject());
        priceColumn.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(Double price, boolean empty) {
                super.updateItem(price, empty);
                if (empty || price == null) {
                    setText(null);
                } else {
                    setText(String.format("KSh %,.2f", price));
                }
            }
        });

        // Status Badge Column
        TableColumn<Product, String> statusColumn = new TableColumn<>("Status");
        statusColumn.setCellValueFactory(data -> {
            int qty = data.getValue().getQuantity();
            if (qty == 0) return new SimpleStringProperty("OUT OF STOCK");
            if (qty <= 5) return new SimpleStringProperty("LOW STOCK");
            return new SimpleStringProperty("IN STOCK");
        });

        statusColumn.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(String status, boolean empty) {
                super.updateItem(status, empty);

                if (empty || status == null) {
                    setText(null);
                    setGraphic(null);
                    setStyle("");
                } else {
                    Label badge = new Label(status);
                    badge.setPadding(new Insets(4, 10, 4, 10));

                    if (status.equalsIgnoreCase("IN STOCK")) {
                        badge.setStyle("-fx-background-color: #dcfce7; -fx-text-fill: #15803d; -fx-font-weight: bold; -fx-background-radius: 12px; -fx-font-size: 11px;");
                    } else if (status.equalsIgnoreCase("LOW STOCK")) {
                        badge.setStyle("-fx-background-color: #fef3c7; -fx-text-fill: #b45309; -fx-font-weight: bold; -fx-background-radius: 12px; -fx-font-size: 11px;");
                    } else {
                        badge.setStyle("-fx-background-color: #fee2e2; -fx-text-fill: #b91c1c; -fx-font-weight: bold; -fx-background-radius: 12px; -fx-font-size: 11px;");
                    }

                    setGraphic(badge);
                    setText(null);
                    setAlignment(Pos.CENTER);
                }
            }
        });

        table.getColumns().addAll(
                idColumn,
                nameColumn,
                categoryColumn,
                quantityColumn,
                priceColumn,
                statusColumn
        );

        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setPlaceholder(new Label("No products available in inventory."));
        table.setItems(ProductController.getProducts());
        table.setPrefHeight(400);

        return table;
    }
}