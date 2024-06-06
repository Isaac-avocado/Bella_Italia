package com.example.bella_italia.app;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;


import java.util.Objects;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/inicio-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        //primaryStage.setFullScreen(true);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Bella Italia : Sistema de Administración");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}