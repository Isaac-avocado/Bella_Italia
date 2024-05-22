package com.example.bella_italia.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

public class InicioController {

    @FXML
    private void handleButtonClick(javafx.event.ActionEvent event) {
        Button button = (Button) event.getSource();
        String buttonId = button.getId();
        String fxmlFileName = "";

        // Determinar el nombre del archivo FXML a cargar según el botón presionado
        switch (buttonId) {
            case "registrarmeButton":
                fxmlFileName = "registro.fxml";
                break;
            case "botonAcceder":
                fxmlFileName = "hello-view.fxml";
                break;
            default:
                break;
        }

        // Cargar y mostrar la vista correspondiente
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlFileName));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
