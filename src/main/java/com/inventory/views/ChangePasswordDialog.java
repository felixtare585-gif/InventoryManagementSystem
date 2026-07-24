package com.inventory.views;

import com.inventory.database.UserDAO;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ChangePasswordDialog {

    /**
     * Opens a popup window for updating the logged-in user's password.
     * @param activeUsername The currently logged-in username (optional default value)
     */
    public static void display(String activeUsername) {
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.setTitle("Change Password");

        // Header Title
        Label titleLabel = new Label("Change Account Password");
        titleLabel.setStyle("-fx-text-fill: #f8fafc; -fx-font-size: 18px; -fx-font-weight: bold;");

        // Username Field
        Label userLabel = new Label("Username");
        userLabel.setStyle("-fx-text-fill: #94a3b8; -fx-font-size: 12px;");
        TextField usernameField = new TextField(activeUsername != null ? activeUsername : "");
        usernameField.setPromptText("Enter username");

        // Current Password Field
        Label oldPassLabel = new Label("Current Password");
        oldPassLabel.setStyle("-fx-text-fill: #94a3b8; -fx-font-size: 12px;");
        PasswordField oldPassField = new PasswordField();
        oldPassField.setPromptText("Enter current password");

        // New Password Field
        Label newPassLabel = new Label("New Password");
        newPassLabel.setStyle("-fx-text-fill: #94a3b8; -fx-font-size: 12px;");
        PasswordField newPassField = new PasswordField();
        newPassField.setPromptText("Enter new password");

        // Status / Error Feedback Message
        Label statusLabel = new Label();
        statusLabel.setStyle("-fx-font-size: 12px;");
        statusLabel.setVisible(false);

        // Action Button
        Button submitButton = new Button("Update Password");
        submitButton.setStyle("-fx-background-color: #2563eb; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 15; -fx-cursor: hand;");
        submitButton.setMaxWidth(Double.MAX_VALUE);

        submitButton.setOnAction(e -> {
            String user = usernameField.getText().trim();
            String oldPass = oldPassField.getText().trim();
            String newPass = newPassField.getText().trim();

            if (user.isEmpty() || oldPass.isEmpty() || newPass.isEmpty()) {
                statusLabel.setText("All fields are required!");
                statusLabel.setStyle("-fx-text-fill: #ef4444;");
                statusLabel.setVisible(true);
                return;
            }

            // Database Update Operation
            boolean success = UserDAO.updatePassword(user, oldPass, newPass);

            if (success) {
                statusLabel.setText("Password updated successfully!");
                statusLabel.setStyle("-fx-text-fill: #10b981;");
                statusLabel.setVisible(true);

                // Clear password fields on success
                oldPassField.clear();
                newPassField.clear();
            } else {
                statusLabel.setText("Invalid username or current password!");
                statusLabel.setStyle("-fx-text-fill: #ef4444;");
                statusLabel.setVisible(true);
            }
        });

        // Form Layout Container
        VBox layout = new VBox(10, titleLabel, userLabel, usernameField, oldPassLabel, oldPassField, newPassLabel, newPassField, statusLabel, submitButton);
        layout.setPadding(new Insets(24));
        layout.setAlignment(Pos.CENTER_LEFT);
        layout.setStyle("-fx-background-color: #0f172a;");

        Scene scene = new Scene(layout, 360, 420);
        
        // Optional CSS styling attachment if resources/style.css exists
        try {
            if (ChangePasswordDialog.class.getResource("/style.css") != null) {
                scene.getStylesheets().add(ChangePasswordDialog.class.getResource("/style.css").toExternalForm());
            }
        } catch (Exception ignored) {
            // Falls back to direct inline styles if CSS isn't present
        }

        dialogStage.setScene(scene);
        dialogStage.showAndWait();
    }
}