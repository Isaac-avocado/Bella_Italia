package com.example.bella_italia.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class ClientesController {

    public Pane optionPaneSalir;
    @FXML
    private Pane ClientesPane;

    @FXML
    private VBox ClientesMenu;

    @FXML
    public void initialize() {
        System.out.println("Inicializando controlador...");
        ClientesPane.setOnMouseClicked(this::handleClientesPaneClick);
        optionPaneSalir.setOnMouseClicked(event -> handleSalirClicked());
    }

    private void handleSalirClicked() {
        System.out.println("Salir fue pulsado");
        Stage currentStage = (Stage) optionPaneSalir.getScene().getWindow();
        loadView("menu-view.fxml", currentStage);
    }

    @FXML
    private void handleClientesPaneClick(MouseEvent event) {
        boolean isVisible = ClientesMenu.isVisible();
        ClientesMenu.setVisible(!isVisible);
    }

    private void loadView(String fxmlFileName, Stage currentStage) {
        try {
            // Ruta completa al archivo FXML
            String fxmlFilePath = "/" + fxmlFileName;
            System.out.println("Cargando vista desde: " + fxmlFilePath);

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFilePath));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

            // Cerrar la ventana actual
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.err.println("El archivo FXML no se encontró: " + fxmlFileName);
            e.printStackTrace();
        }
    }
}
