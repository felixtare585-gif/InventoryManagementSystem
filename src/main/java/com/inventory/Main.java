package com.inventory;

import com.inventory.database.DatabaseConnection;
import com.inventory.views.LoginView;
import com.inventory.views.MainLayout;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static Stage window;

    @Override
    public void start(Stage stage) {

        // Test MySQL connection at startup
        DatabaseConnection.getConnection();

        window = stage;

        // Callback function executed on successful login
        Runnable onLoginSuccess = () -> {
            MainLayout mainLayout = new MainLayout();

            Scene mainScene = new Scene(
                    mainLayout.createLayout(),
                    1100,
                    700
            );

            mainScene.getStylesheets().add(
                    getClass().getResource("/style.css").toExternalForm()
            );

            window.setScene(mainScene);
            window.centerOnScreen();
        };

        // Initialize with Login Screen
        LoginView loginView = new LoginView(onLoginSuccess);

        Scene loginScene = new Scene(
                loginView.createPane(),
                1100,
                700
        );

        loginScene.getStylesheets().add(
                getClass().getResource("/style.css").toExternalForm()
        );

        stage.setTitle("Inventory Management System");
        stage.setScene(loginScene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}