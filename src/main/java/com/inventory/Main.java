package com.inventory;


import com.inventory.database.DatabaseConnection;
import com.inventory.views.MainLayout;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {


    @Override
    public void start(Stage stage) {


        // Test MySQL connection
        DatabaseConnection.getConnection();



        MainLayout mainLayout = new MainLayout();



        Scene scene = new Scene(
                mainLayout.createLayout(),
                1000,
                600
        );



        stage.setTitle(
                "Inventory Management System"
        );


        stage.setScene(scene);


        stage.show();

    }



    public static void main(String[] args) {

        launch(args);

    }

}