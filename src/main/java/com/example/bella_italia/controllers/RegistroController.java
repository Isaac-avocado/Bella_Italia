package com.example.bella_italia.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class RegistroController {
    @FXML
    public Pane botonSalir;
    @FXML
    private Pane botonAcceder1;

    @FXML
    private void handleButtonClick(MouseEvent event) {
        Object source = event.getSource();

        if (source instanceof Pane) {
            Pane pane = (Pane) source;
            String paneId = pane.getId();
            String fxmlFileName = "";

            if (paneId.equals("botonAcceder1") || paneId.equals("botonSalir")) {
                fxmlFileName = "inicio-view.fxml";
            } else {
                System.out.println("Pane ID no reconocido: " + paneId);
                return;
            }

            // Cargar y mostrar la vista correspondiente
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/" + fxmlFileName));
                Parent root = fxmlLoader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();

                // Cerrar la ventana actual
                Stage currentStage = (Stage) ((Pane) source).getScene().getWindow();
                currentStage.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("El evento no proviene de un Pane.");
        }
    }
}
