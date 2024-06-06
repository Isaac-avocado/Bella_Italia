package com.example.bella_italia.controllers;

import com.example.bella_italia.models.InventarioModel;
import com.example.bella_italia.models.InventarioModel.Ingrediente;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.List;

public class InventarioController {

    @FXML
    public Button crearIngrediente;
    public Pane InventarioPane;
    public VBox InventarioMenu;
    public Pane optionPanePlatillos;
    public Pane optionPaneOrdenes;
    public Pane optionPaneClientes;
    public Pane optionPaneSalir;
    @FXML
    private GridPane ingredientesContainer; // Cambiado a GridPane

    @FXML
    private ScrollPane ingredientesScrollPane;

    @FXML
    private VBox IngredientesAgotandose;

    private InventarioModel inventarioModel;

    @FXML
    public void initialize() {
        System.out.println("Inicializando controlador...");

        if (InventarioPane != null) {
            InventarioPane.setOnMouseClicked(this::handleInventarioPaneClick);
        } else {
            System.err.println("InventarioPane es nulo. Verifique que el ID coincida con el del archivo FXML.");
        }

        inventarioModel = new InventarioModel();
        cargarIngredientes();
    }

    public void cargarIngredientes() {
        if (ingredientesContainer != null && IngredientesAgotandose != null) {
            ingredientesContainer.getChildren().clear(); // Limpiar contenedor antes de agregar elementos
            IngredientesAgotandose.getChildren().clear(); // Limpiar contenedor antes de agregar elementos
            List<Ingrediente> ingredientes = inventarioModel.obtenerIngredientes();

            int column = 0;
            int row = 0;

            for (Ingrediente ingrediente : ingredientes) {
                VBox ingredienteItem = crearIngredienteItem(ingrediente);

                if (ingrediente.getUnits() <= 10) {
                    IngredientesAgotandose.getChildren().add(ingredienteItem);
                    // Añadir marco rojo
                    ingredienteItem.setStyle("-fx-border-color: red; -fx-border-width: 2;");
                } else {
                    ingredientesContainer.add(ingredienteItem, column, row);
                    column++;
                    if (column == 4) { // Cambiar a la siguiente fila después de 4 columnas
                        column = 0;
                        row++;
                    }
                }
            }
        } else {
            System.err.println("ingredientesContainer o IngredientesAgotandose es nulo. Verifique que los IDs coincidan con los del archivo FXML.");
        }
    }

    @FXML
    private void handleInventarioPaneClick(MouseEvent event) {
        boolean isVisible = InventarioMenu.isVisible();
        InventarioMenu.setVisible(!isVisible);
    }

    @FXML
    private void handlePlatillosClicked() {
        System.out.println("Platillos fue pulsado");
        Stage currentStage = (Stage) optionPanePlatillos.getScene().getWindow();
        loadView("platillos-view.fxml", currentStage);
    }

    @FXML
    private void handleOrdenesClicked() {
        System.out.println("Ordenes fue pulsado");
        Stage currentStage = (Stage) optionPaneOrdenes.getScene().getWindow();
        loadView("ordenes-view.fxml", currentStage);
    }

    @FXML
    private void handleInventarioClicked() {
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
            String fxmlFilePath = "/" + fxmlFileName;
            System.out.println("Cargando vista desde: " + fxmlFilePath);

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFilePath));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.err.println("El archivo FXML no se encontró: " + fxmlFileName);
            e.printStackTrace();
        }
    }

    @FXML
    private void handleCrearIngredienteClicked(MouseEvent event) {
        // Crear la ventana del popup
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.initStyle(StageStyle.UTILITY);
        popupStage.setTitle("Crear Ingrediente");

        // Crear el contenido del popup
        VBox popupVBox = new VBox(10);
        popupVBox.setPrefWidth(300);
        popupVBox.setPrefHeight(200);
        popupVBox.setAlignment(javafx.geometry.Pos.CENTER);

        Label nameLabel = new Label("Nombre del Ingrediente");
        TextField nameField = new TextField();
        nameField.setPromptText("Nombre");

        Label unitsLabel = new Label("Unidades del Ingrediente");
        TextField unitsField = new TextField();
        unitsField.setPromptText("Unidades");

        Button createButton = new Button("Crear");
        createButton.setOnAction(e -> {
            String nombreIngrediente = nameField.getText();
            Float unidadesIngrediente = Float.parseFloat(unitsField.getText());
            if (inventarioModel.createIngredient(nombreIngrediente, unidadesIngrediente)) {
                System.out.println("Ingrediente creado: " + nombreIngrediente);
                cargarIngredientes(); // Recargar la lista de ingredientes después de crear uno nuevo
            } else {
                System.err.println("Error al crear el ingrediente: " + nombreIngrediente);
            }
            popupStage.close();
        });

        popupVBox.getChildren().addAll(nameLabel, nameField, unitsLabel, unitsField, createButton);

        Scene popupScene = new Scene(popupVBox);
        popupStage.setScene(popupScene);
        popupStage.show();
    }

    private VBox crearIngredienteItem(Ingrediente ingrediente) {
        // Crear el VBox que contendrá todo
        VBox vbox = new VBox(10); // Espaciado de 10 entre elementos
        vbox.setPrefSize(200, 200);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(10));

        // Configurar fondo redondeado
        BackgroundFill backgroundFill = new BackgroundFill(Color.LIGHTGRAY, new CornerRadii(20), Insets.EMPTY);
        vbox.setBackground(new Background(backgroundFill));

        // Crear el label del nombre del ingrediente
        Label nombreIngrediente = new Label(ingrediente.getName());
        nombreIngrediente.setAlignment(Pos.CENTER);
        nombreIngrediente.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        // Crear el label de las unidades del ingrediente
        Label unidadesIngrediente = new Label(String.valueOf(ingrediente.getUnits()));
        unidadesIngrediente.setAlignment(Pos.CENTER);
        unidadesIngrediente.setStyle("-fx-font-size: 14px;");

        // Crear los botones
        HBox buttonBox = new HBox(10); // Espaciado de 10 entre botones
        buttonBox.setAlignment(Pos.CENTER);

        Button editarButton = new Button("Editar");
        editarButton.setOnAction(event -> editarIngrediente(ingrediente));

        Button eliminarButton = new Button("Eliminar");
        eliminarButton.setOnAction(event -> eliminarIngrediente(ingrediente.getName()));

        buttonBox.getChildren().addAll(editarButton, eliminarButton);

        // Agregar los elementos al VBox
        vbox.getChildren().addAll(nombreIngrediente, unidadesIngrediente, buttonBox);

        return vbox;
    }

    private void editarIngrediente(Ingrediente ingrediente) {
        // Crear la ventana del popup
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.initStyle(StageStyle.UTILITY);
        popupStage.setTitle("Editar Ingrediente");

        // Crear el contenido del popup
        VBox popupVBox = new VBox(10);
        popupVBox.setPrefWidth(300);
        popupVBox.setPrefHeight(200);
        popupVBox.setAlignment(javafx.geometry.Pos.CENTER);

        Label nameLabel = new Label("Nombre del Ingrediente");
        TextField nameField = new TextField();
        nameField.setPromptText("Nombre");
        nameField.setText(ingrediente.getName());

        Label unitsLabel = new Label("Unidades del Ingrediente");
        TextField unitsField = new TextField();
        unitsField.setPromptText("Unidades");
        unitsField.setText(String.valueOf(ingrediente.getUnits()));

        Button editButton = new Button("Editar");

        editButton.setOnAction(e -> {
            String oldName = ingrediente.getName(); // Nombre antiguo del ingrediente
            String newName = nameField.getText(); // Nuevo nombre del ingrediente
            Float units = Float.parseFloat(unitsField.getText()); // Nuevas unidades del ingrediente
            System.out.println("Editando ingrediente: " + oldName + " a " + newName + " con " + units + " unidades");
            if (inventarioModel.editIngredient(oldName, newName, units)) {
                System.out.println("Ingrediente editado: " + newName);
                cargarIngredientes(); // Recargar la lista de ingredientes después de editar uno
            } else {
                System.err.println("Error al editar el ingrediente: " + newName);
            }
            popupStage.close();
        });

        popupVBox.getChildren().addAll(nameLabel, nameField, unitsLabel, unitsField, editButton);

        Scene popupScene = new Scene(popupVBox);
        popupStage.setScene(popupScene);
        popupStage.show();
    }

    private void eliminarIngrediente(String nombreIngrediente) {
        inventarioModel.deleteIngredient(nombreIngrediente);
        cargarIngredientes(); // Recargar la lista de ingredientes después de eliminar uno
    }
}
