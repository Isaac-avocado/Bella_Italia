package com.example.bella_italia.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class OrdenesController {

    public Button crearPlatillo;
    @FXML
    private Button botonMiniMenu;
    @FXML
    private Button descargarPlatillo;
    @FXML
    private Button eliminarPlatillo;
    @FXML
    private Button editarPlatillo;
    @FXML
    private Button ingredientesPlatillo;
    @FXML
    private ComboBox<String> comboBoxMenu;

    @FXML
    private Pane botonAcceder, botonAcceder1, botonAcceder2;

    @FXML
    public void initialize() {
        System.out.println("Inicializando controlador...");
        System.out.println("ComboBoxMenu es nulo? " + (comboBoxMenu == null));

        if (comboBoxMenu != null) {
            comboBoxMenu.getItems().addAll("Platillos", "Clientes", "Inventario", "Salir");
            comboBoxMenu.setOnAction(event -> handleComboBoxSelection());
        }
    }

    @FXML
    public void handleCrearPlatilloButtonClick() {
        Stage currentStage = (Stage) crearPlatillo.getScene().getWindow();
        loadView2("crearPlatillo-view.fxml");
    }

    @FXML
    public void handleIngredientesPlatilloButtonClick() {
        Stage currentStage = (Stage) ingredientesPlatillo.getScene().getWindow();
        loadView2("ingredientesPlatillo-view.fxml");
    }

    @FXML
    public void handleDescargarPlatilloButtonClick() {
        Stage currentStage = (Stage) descargarPlatillo.getScene().getWindow();
        loadView2("descargarPlatillo-view.fxml");
    }

    @FXML
    public void handleEliminarPlatilloButtonClick() {
        Stage currentStage = (Stage) eliminarPlatillo.getScene().getWindow();
        loadView2("eliminarPlatillo-view.fxml");
    }

    @FXML
    public void handleEditarPlatilloButtonClick() {
        Stage currentStage = (Stage) editarPlatillo.getScene().getWindow();
        loadView2("editarPlatillo-view.fxml");
    }

    @FXML
    public void handleMiniMenuButtonClick() {
        Stage currentStage = (Stage) botonMiniMenu.getScene().getWindow();
        loadView2("miniMenuPlatillo-view.fxml");
    }

    private void handleComboBoxSelection() {
        String selectedItem = comboBoxMenu.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            Stage currentStage = (Stage) comboBoxMenu.getScene().getWindow();
            switch (selectedItem) {
                case "Platillos":
                    loadView("platillos-view.fxml", currentStage);
                    break;
                case "Clientes":
                    loadView("clientes-view.fxml", currentStage);
                    break;
                case "Inventario":
                    loadView("inventario-view.fxml", currentStage);
                    break;
                case "Salir":
                    loadView("menu-view.fxml", currentStage);
                    break;
                default:
                    break;
            }
        }
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
}
