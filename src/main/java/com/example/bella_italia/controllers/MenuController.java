package com.example.bella_italia.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuController {
    @FXML
    public Pane Salir;

    @FXML
    private void handlePaneClick(MouseEvent event) {
        Object source = event.getSource();

        if (source instanceof Pane) {
            Pane pane = (Pane) source;
            String paneId = pane.getId();
            String fxmlFileName = "";

            if (paneId.equals("salir")) {
                fxmlFileName = "inicio-view.fxml";
            } else if (paneId.equals("platillos")) {
                fxmlFileName = "platillos-view.fxml";
            }else if (paneId.equals("ordenes")) {
                fxmlFileName = "ordenes-view.fxml";
            }else if (paneId.equals("clientes")) {
                fxmlFileName = "clientes-view.fxml";
            }else if (paneId.equals("inventario")) {
                fxmlFileName = "inventario-view.fxml";
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

    @FXML
    private void handleLabelClick(MouseEvent event) {
        Object source = event.getSource();
        String fxmlFileName = "inicio-view.fxml";

        // Cargar y mostrar la vista correspondiente
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/" + fxmlFileName));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

            // Cerrar la ventana actual
            if (source instanceof Pane) {
                Stage currentStage = (Stage) ((Pane) source).getScene().getWindow();
                currentStage.close();
            } else if (source instanceof Label) {
                Stage currentStage = (Stage) ((Label) source).getScene().getWindow();
                currentStage.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
