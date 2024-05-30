package com.example.bella_italia.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class ClientesController {

    @FXML
    private Button crearCliente;

    @FXML
    private VBox ClientesMenu;

    @FXML
    private Pane optionPanePlatillos;

    @FXML
    private Pane optionPaneOrdenes;

    @FXML
    private Pane optionPaneInventario;

    @FXML
    private Pane optionPaneSalir;

    @FXML
    private Pane ClientesPane;

    @FXML
    public void initialize() {
        System.out.println("Inicializando controlador...");

        // Manejar clics en el VBox cuando está visible
        ClientesMenu.addEventHandler(MouseEvent.MOUSE_CLICKED, this::handleClientesMenuClicked);

        if (ClientesPane != null) {
            ClientesPane.setOnMouseClicked(this::handleClientesPaneClick);
        } else {
            System.err.println("ClientesPane es nulo. Verifique que el ID coincida con el del archivo FXML.");
        }

        if (optionPanePlatillos != null) {
            optionPanePlatillos.setOnMouseClicked(event -> handlePlatillosClicked());
        } else {
            System.err.println("optionPanePlatillos es nulo. Verifique que el ID coincida con el del archivo FXML.");
        }

        if (optionPaneOrdenes != null) {
            optionPaneOrdenes.setOnMouseClicked(event -> handleOrdenesClicked());
        } else {
            System.err.println("optionPaneOrdenes es nulo. Verifique que el ID coincida con el del archivo FXML.");
        }

        if (optionPaneInventario != null) {
            optionPaneInventario.setOnMouseClicked(event -> handleInventarioClicked());
        } else {
            System.err.println("optionPaneInventario es nulo. Verifique que el ID coincida con el del archivo FXML.");
        }

        if (optionPaneSalir != null) {
            optionPaneSalir.setOnMouseClicked(event -> handleSalirClicked());
        } else {
            System.err.println("optionPaneSalir es nulo. Verifique que el ID coincida con el del archivo FXML.");
        }
    }

    private void handleClientesMenuClicked(MouseEvent event) {
        // Cambiar la visibilidad del VBox
        ClientesMenu.setVisible(!ClientesMenu.isVisible());

        // Configurar pickOnBounds dependiendo de la visibilidad del VBox
        ClientesMenu.setPickOnBounds(!ClientesMenu.isVisible());
    }

    @FXML
    private void handleCrearClicked(MouseEvent event) {
        System.out.println("Crear fue pulsado");
        loadViewNoClose("crearCliente-view.fxml");
    }

    private void handlePlatillosClicked() {
        System.out.println("Platillos fue pulsado");
        Stage currentStage = (Stage) optionPanePlatillos.getScene().getWindow();
        loadView("platillos-view.fxml", currentStage);
    }

    private void handleOrdenesClicked() {
        System.out.println("Ordenes fue pulsado");
        Stage currentStage = (Stage) optionPaneOrdenes.getScene().getWindow();
        loadView("ordenes-view.fxml", currentStage);
    }

    private void handleInventarioClicked() {
        System.out.println("Inventario fue pulsado");
        Stage currentStage = (Stage) optionPaneInventario.getScene().getWindow();
        loadView("inventario-view.fxml", currentStage);
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
            String fxmlFilePath = "/" + fxmlFileName;
            System.out.println("Cargando vista desde: " + fxmlFilePath);

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFilePath));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

            currentStage.close();
        } catch (IOException e) {
            System.err.println("Error al cargar el archivo FXML: " + fxmlFileName);
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.err.println("El archivo FXML no se encontró: " + fxmlFileName);
            e.printStackTrace();
        }
    }

    private void loadViewNoClose(String fxmlFileName) {
        try {
            String fxmlFilePath = "/" + fxmlFileName;
            System.out.println("Cargando vista desde: " + fxmlFilePath);

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFilePath));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            System.err.println("Error al cargar el archivo FXML: " + fxmlFileName);
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.err.println("El archivo FXML no se encontró: " + fxmlFileName);
            e.printStackTrace();
        }
    }
}
