package com.academix;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import util.DBUtil;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Use leading slash to load from resources root
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"));
        Scene scene = new Scene(root);

        primaryStage.setTitle("Academix - Login");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void stop() {
        // Clean up database connections when application closes
        DBUtil.closePool();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
