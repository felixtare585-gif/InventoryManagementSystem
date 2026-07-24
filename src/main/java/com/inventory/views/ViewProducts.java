package com.inventory.views;

import com.inventory.controllers.ProductController;
import com.inventory.models.Product;

import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class ViewProducts {

    public BorderPane createPane() {
        BorderPane root = new BorderPane();

        // Title Header
        Label title = new Label("Product Inventory");
        title.getStyleClass().add("title");

        Label subtitle = new Label("View and filter all products in real-time");
        subtitle.getStyleClass().add("subtitle");

        VBox headerBox = new VBox(4, title, subtitle);

        // Search Input Bar
        TextField searchField = new TextField();
        searchField.setPromptText("🔍 Search by ID, Name, or Category...");
        searchField.getStyleClass().add("text-field");
        searchField.setPrefWidth(320);

        HBox topBar = new HBox(headerBox, searchField);
        topBar.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(headerBox, Priority.ALWAYS);

        // Build Table
        TableView<Product> table = new TableView<>();

        TableColumn<Product, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleIntegerProperty(data.getValue().getId()).asObject());
        idColumn.setPrefWidth(60);

        TableColumn<Product, String> nameColumn = new TableColumn<>("Product Name");
        nameColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getName()));

        TableColumn<Product, String> categoryColumn = new TableColumn<>("Category");
        categoryColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getCategory()));

        TableColumn<Product, Integer> quantityColumn = new TableColumn<>("Quantity");
        quantityColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleIntegerProperty(data.getValue().getQuantity()).asObject());

        TableColumn<Product, Double> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleDoubleProperty(data.getValue().getPrice()).asObject());
        priceColumn.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(Double price, boolean empty) {
                super.updateItem(price, empty);
                setText(empty || price == null ? null : String.format("KSh %,.2f", price));
            }
        });

        TableColumn<Product, String> statusColumn = new TableColumn<>("Status");
        statusColumn.setCellValueFactory(data -> {
            int qty = data.getValue().getQuantity();
            if (qty == 0) return new javafx.beans.property.SimpleStringProperty("OUT OF STOCK");
            if (qty <= 5) return new javafx.beans.property.SimpleStringProperty("LOW STOCK");
            return new javafx.beans.property.SimpleStringProperty("IN STOCK");
        });

        statusColumn.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(String status, boolean empty) {
                super.updateItem(status, empty);
                if (empty || status == null) {
                    setText(null);
                    setGraphic(null);
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

        table.getColumns().addAll(idColumn, nameColumn, categoryColumn, quantityColumn, priceColumn, statusColumn);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setPlaceholder(new Label("No products match your search."));

        // ================= Filter Logic =================
        FilteredList<Product> filteredData = new FilteredList<>(ProductController.getProducts(), p -> true);

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(product -> {
                // If search text is empty, show all items
                if (newValue == null || newValue.isBlank()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase().trim();

                // Match against Product Name
                if (product.getName() != null && product.getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                // Match against Category
                if (product.getCategory() != null && product.getCategory().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                // Match against ID
                if (String.valueOf(product.getId()).contains(lowerCaseFilter)) {
                    return true;
                }

                return false; // No match
            });
        });

        // Wrap FilteredList in a SortedList to preserve column sorting functionality
        SortedList<Product> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedData);

        VBox content = new VBox(20, topBar, table);
        content.setPadding(new Insets(30));

        root.setCenter(content);
        return root;
    }
}