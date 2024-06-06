package com.example.bella_italia.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class OrdenesController {

    public VBox OrdenesMenu;
    public Pane optionPanePlatillos;
    public Pane optionPaneClientes;
    public Pane optionPaneSalir;
    public Pane optionPaneInventario;

    @FXML
    public void initialize() {
        System.out.println("Inicializando controlador...");
        
    }

    @FXML
    private void handleOrdenesPaneClick(MouseEvent event) {
        boolean isVisible = OrdenesMenu.isVisible();
        OrdenesMenu.setVisible(!isVisible);
    }

    @FXML
    private void handleInventarioClicked() {
        System.out.println("Inventario fue pulsado");
        Stage currentStage = (Stage) optionPaneInventario.getScene().getWindow();
        loadView("inventario-view.fxml", currentStage);
    }

    @FXML
    private void handlePlatillosClicked() {
        System.out.println("Ordenes fue pulsado");
        Stage currentStage = (Stage) optionPanePlatillos.getScene().getWindow();
        loadView("platillos-view.fxml", currentStage);
    }

    @FXML
    private void handleClientesClicked() {
        System.out.println("Clientes fue pulsado");
        Stage currentStage = (Stage) optionPaneClientes.getScene().getWindow();
        loadView("clientes-view.fxml", currentStage);
    }

    @FXML
    private void handleSalirClicked() {
        System.out.println("Salir fue pulsado");
        Stage currentStage = (Stage) optionPaneSalir.getScene().getWindow();
        loadView("menu-view.fxml", currentStage);
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
    private void loadView2(String fxmlFileName) {
        try {
            // Ruta completa al archivo FXML
            String fxmlFilePath = "/" + fxmlFileName;
            System.out.println("Cargando vista desde: " + fxmlFilePath);

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFilePath));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.err.println("El archivo FXML no se encontró: " + fxmlFileName);
            e.printStackTrace();
        }
    }

    @FXML
    private void handleCrearOrdenClicked(MouseEvent event) {
        // Crear la ventana del popup
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.initStyle(StageStyle.UTILITY);
        popupStage.setTitle("Crear Orden");

        // Crear el contenido del popup
        VBox popupVBox = new VBox(10);
        popupVBox.setPrefWidth(300);
        popupVBox.setPrefHeight(200);
        popupVBox.setAlignment(javafx.geometry.Pos.CENTER);

        Label nameLabel = new Label("Platillo");
        TextField nameField = new TextField();
        nameField.setPromptText("Nombre");

        Button createButton = new Button("Crear");
        createButton.setOnAction(e -> {
            popupStage.close();
        });

        popupVBox.getChildren().addAll(nameLabel, nameField, createButton);

        Scene popupScene = new Scene(popupVBox);
        popupStage.setScene(popupScene);
        popupStage.show();
    }

}
