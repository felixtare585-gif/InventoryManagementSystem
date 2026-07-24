package com.inventory.views;

import com.inventory.database.UserDAO;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class LoginView {

    private final Runnable onLoginSuccess;

    public LoginView(Runnable onLoginSuccess) {
        this.onLoginSuccess = onLoginSuccess;
    }

    public BorderPane createPane() {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #0f172a;");

        // Header / Logo
        Label logoLabel = new Label("📦 Inventory Core");
        logoLabel.getStyleClass().add("login-title");

        Label subtitleLabel = new Label("Sign in to access your dashboard");
        subtitleLabel.getStyleClass().add("subtitle");

        VBox headerBox = new VBox(6, logoLabel, subtitleLabel);
        headerBox.setAlignment(Pos.CENTER);

        // Inputs
        Label userLabel = new Label("Username");
        userLabel.getStyleClass().add("form-label");
        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter username");

        Label passLabel = new Label("Password");
        passLabel.getStyleClass().add("form-label");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter password");

        // Error Feedback Label
        Label errorLabel = new Label();
        errorLabel.getStyleClass().add("error-label");
        errorLabel.setVisible(false);

        // Sign In Button
        Button loginButton = new Button("Sign In");
        loginButton.getStyleClass().add("button-primary-large");
        loginButton.setMaxWidth(Double.MAX_VALUE);

        // Auth Handler — Authenticates via MySQL Database
        Runnable attemptLogin = () -> {
            String user = usernameField.getText().trim();
            String pass = passwordField.getText().trim();

            if (user.isEmpty() || pass.isEmpty()) {
                errorLabel.setText("Please fill in all fields!");
                errorLabel.setVisible(true);
                return;
            }

            boolean isValid = UserDAO.authenticate(user, pass);

            if (isValid) {
                errorLabel.setVisible(false);
                onLoginSuccess.run();
            } else {
                errorLabel.setText("Invalid username or password!");
                errorLabel.setVisible(true);
            }
        };

        loginButton.setOnAction(e -> attemptLogin.run());
        passwordField.setOnAction(e -> attemptLogin.run());

        VBox formBox = new VBox(12, userLabel, usernameField, passLabel, passwordField, errorLabel, loginButton);
        formBox.setAlignment(Pos.CENTER_LEFT);

        // Card Wrapper
        VBox card = new VBox(24, headerBox, formBox);
        card.getStyleClass().add("login-card");
        card.setPrefWidth(380);
        card.setMaxWidth(380);
        card.setAlignment(Pos.CENTER);

        root.setCenter(card);

        return root;
    }
}