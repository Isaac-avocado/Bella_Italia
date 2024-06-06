package com.example.bella_italia.controllers;

import com.example.bella_italia.models.InventarioModel;
import com.example.bella_italia.models.Platillo;
import com.example.bella_italia.models.PlatillosModel;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class PlatillosController {
    @FXML
    private TableView<Platillo> tablePlatillos;
    @FXML
    private TableColumn<Platillo, String> colNombre;
    @FXML
    private TableColumn<Platillo, Float> colPrecio;
    @FXML
    private GridPane gridPlatillos;
    @FXML
    private Button crearPlatillo;
    @FXML
    private Pane optionPanePlatillos;
    @FXML
    private Pane optionPaneClientes;
    @FXML
    private Pane optionPaneOrdenes;
    @FXML
    private VBox InventarioMenu;
    @FXML
    private Pane optionPaneSalir;
    @FXML
    private Pane optionPaneInventario;
    @FXML
    private ComboBox<String> comboBoxMenu;
    @FXML
    private Button descargarPlatillo;
    @FXML
    private Button eliminarPlatillo;
    @FXML
    private Button editarPlatillo;
    @FXML
    private Button ingredientesPlatillo;
    private PlatillosModel platillosModel;

    @FXML
    public void initialize() {
        platillosModel = new PlatillosModel();

        // Cargar los platillos en la TableView
        cargarPlatillos();
    }



    @FXML
    public void handleCrearPlatilloButtonClick() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Crear Platillo");
        dialog.setHeaderText("Ingrese los detalles del nuevo platillo");
        dialog.setContentText("Nombre:");

        // Obtener el nombre del platillo
        String nombrePlatillo = dialog.showAndWait().orElse(null);

        if (nombrePlatillo != null) {
            TextInputDialog priceDialog = new TextInputDialog();
            priceDialog.setTitle("Crear Platillo");
            priceDialog.setHeaderText("Ingrese el precio del nuevo platillo");
            priceDialog.setContentText("Precio:");

            String precioPlatilloStr = priceDialog.showAndWait().orElse(null);

            if (precioPlatilloStr != null) {
                try {
                    Float precioPlatillo = Float.parseFloat(precioPlatilloStr);
                    platillosModel.createDish(nombrePlatillo, precioPlatillo);
                    cargarPlatillos();
                } catch (NumberFormatException e) {
                    // Manejar error de formato de precio
                    showAlert("Error", "El precio ingresado no es válido.");
                }
            }
        }
    }

    @FXML
    public void handleIngredientesPlatilloButtonClick() {
        // Obtener el nodo de la celda seleccionada
        Node node = obtenerCeldaSeleccionada();

        if (node != null) {
            // Obtener el rowIndex y colIndex de la celda seleccionada
            int rowIndex = GridPane.getRowIndex(node);
            int colIndex = GridPane.getColumnIndex(node);

            // Obtener el platillo seleccionado
            Platillo platilloSeleccionado = obtenerPlatilloSeleccionado(rowIndex, colIndex);

            if (platilloSeleccionado != null) {
                mostrarIngredientes(platilloSeleccionado);
            } else {
                showAlert("Error", "Seleccione un platillo primero.");
            }
        } else {
            showAlert("Error", "Seleccione una celda primero.");
        }
    }

    @FXML
    public void handleDescargarPlatilloButtonClick() {
        // Obtener el nodo de la celda seleccionada
        Node node = obtenerCeldaSeleccionada();

        if (node != null) {
            // Obtener el rowIndex y colIndex de la celda seleccionada
            int rowIndex = GridPane.getRowIndex(node);
            int colIndex = GridPane.getColumnIndex(node);

            // Obtener el platillo seleccionado
            Platillo platilloSeleccionado = obtenerPlatilloSeleccionado(rowIndex, colIndex);

            if (platilloSeleccionado != null) {
                descargarPlatillo(platilloSeleccionado);
            } else {
                showAlert("Error", "Seleccione un platillo primero.");
            }
        } else {
            showAlert("Error", "Seleccione una celda primero.");
        }
    }

    @FXML
    public void handleEliminarPlatilloButtonClick() {
        // Obtener el nodo de la celda seleccionada
        Node node = obtenerCeldaSeleccionada();

        if (node != null) {
            // Obtener el rowIndex y colIndex de la celda seleccionada
            int rowIndex = GridPane.getRowIndex(node);
            int colIndex = GridPane.getColumnIndex(node);

            // Obtener el platillo seleccionado
            Platillo platilloSeleccionado = obtenerPlatilloSeleccionado(rowIndex, colIndex);

            if (platilloSeleccionado != null) {
                // Confirmar la eliminación del platillo
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Eliminar Platillo");
                alert.setHeaderText("¿Está seguro de que desea eliminar este platillo?");
                alert.setContentText(platilloSeleccionado.getName());

                if (alert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
                    // Eliminar el platillo
                    eliminarPlatillo(platilloSeleccionado.getName());
                }
            } else {
                showAlert("Error", "Seleccione un platillo primero.");
            }
        } else {
            showAlert("Error", "Seleccione una celda primero.");
        }
    }

    @FXML
    public void handleEditarPlatilloButtonClick() {
        // Obtener el nodo de la celda seleccionada
        Node node = obtenerCeldaSeleccionada();

        if (node != null) {
            // Obtener el rowIndex y colIndex de la celda seleccionada
            int rowIndex = GridPane.getRowIndex(node);
            int colIndex = GridPane.getColumnIndex(node);

            // Obtener el platillo seleccionado
            Platillo platilloSeleccionado = obtenerPlatilloSeleccionado(rowIndex, colIndex);

            if (platilloSeleccionado != null) {
                // Editar el platillo seleccionado
                editarPlatillo(platilloSeleccionado);
            } else {
                showAlert("Error", "Seleccione un platillo primero.");
            }
        } else {
            showAlert("Error", "Seleccione una celda primero.");
        }
    }

    private Node obtenerCeldaSeleccionada() {
        // Iterar sobre los hijos del GridPane para encontrar el nodo seleccionado
        for (Node node : gridPlatillos.getChildren()) {
            if (node.isFocused()) {
                return node;
            }
        }
        return null;
    }

    private Platillo obtenerPlatilloSeleccionado(int rowIndex, int colIndex) {
        // Obtén el VBox de la celda seleccionada
        Node node = getNodeByRowColumnIndex(rowIndex, colIndex, gridPlatillos);

        // Verifica si el VBox contiene un Platillo
        if (node instanceof VBox) {
            VBox vBox = (VBox) node;

            // Recorre los nodos hijos del VBox para encontrar el Label que contiene el nombre del platillo
            for (Node child : vBox.getChildren()) {
                if (child instanceof Label) {
                    Label label = (Label) child;
                    // Obtén el nombre del platillo
                    String nombrePlatillo = label.getText().replace("Nombre: ", "");
                    // Busca el Platillo con el nombre
                    return platillosModel.getPlatillo(nombrePlatillo);
                }
            }
        }
        return null;
    }

    @FXML
    private void handleGridCellClick(MouseEvent event) {
        // Obtén la celda de la cuadrícula desde el evento
        Node source = (Node) event.getTarget();
        Integer colIndex = GridPane.getColumnIndex(source);
        Integer rowIndex = GridPane.getRowIndex(source);

        // Obtén el platillo correspondiente a la fila y columna seleccionadas
        Platillo platilloSeleccionado = obtenerPlatilloSeleccionado(rowIndex, colIndex);

        if (platilloSeleccionado != null) {
            mostrarIngredientes(platilloSeleccionado);
        } else {
            showAlert("Error", "No se pudo encontrar el platillo seleccionado.");
        }
    }

    private Node getNodeByRowColumnIndex(final int rowIndex, final int colIndex, GridPane gridPane) {
        Node result = null;
        ObservableList<Node> children = gridPane.getChildren();

        for (Node node : children) {
            if (GridPane.getRowIndex(node) == rowIndex && GridPane.getColumnIndex(node) == colIndex) {
                result = node;
                break;
            }
        }
        return result;
    }

    private void mostrarIngredientes(Platillo platillo) {
        // Crear una ventana emergente para mostrar los ingredientes
        Stage stage = new Stage();
        stage.setTitle("Ingredientes de " + platillo.getName());
        VBox vBox = new VBox(10);
        vBox.setPadding(new Insets(20));

        // Agregar los ingredientes al VBox
        for (InventarioModel.Ingrediente ingrediente : platillo.getIngredientes()) {
            Label label = new Label();
            vBox.getChildren().add(label);
        }

        // Crear un Scene y establecer el VBox como nodo raíz
        Scene scene = new Scene(vBox, 300, 200);
        stage.setScene(scene);

        // Mostrar la ventana emergente
        stage.show();
    }

    private void descargarPlatillo(Platillo platillo) {
        // Crear un archivo para guardar la información del platillo
        File file = new File(platillo.getName() + ".txt");

        try (PrintWriter writer = new PrintWriter(file)) {
            // Escribir la información del platillo en el archivo
            writer.println("Nombre: " + platillo.getName());
            writer.println("Precio: " + platillo.getPrice());
            writer.println("Ingredientes:");

            for (InventarioModel.Ingrediente ingrediente : platillo.getIngredientes()) {
                writer.println("- " + ingrediente);
            }

            writer.close();
            System.out.println("El platillo se ha descargado exitosamente como " + file.getName());
        } catch (IOException e) {
            System.err.println("Error al descargar el platillo: " + e.getMessage());
        }
    }



    @FXML
    private void handlePlatillosPaneClick(MouseEvent event) {
        boolean isVisible = InventarioMenu.isVisible();
        InventarioMenu.setVisible(!isVisible);
    }

    @FXML
    private void handleInventarioClicked() {
        System.out.println("Inventario fue pulsado");
        Stage currentStage = (Stage) optionPaneInventario.getScene().getWindow();
        loadView("inventario-view.fxml", currentStage);
    }

    @FXML
    private void handleOrdenesClicked() {
        System.out.println("Ordenes fue pulsado");
        Stage currentStage = (Stage) optionPaneOrdenes.getScene().getWindow();
        loadView("ordenes-view.fxml", currentStage);
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
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/" + fxmlFileName));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void cargarPlatillos() {
        gridPlatillos.getChildren().clear();
        List<Platillo> platillos = platillosModel.obtenerPlatillo();
        int column = 0;
        int row = 0;

        for (Platillo platillo : platillos) {
            VBox platilloBox = crearPlatilloItem(platillo);

            gridPlatillos.add(platilloBox, column, row);
            GridPane.setMargin(platilloBox, new Insets(10));

            column++;
            if (column == 4) {
                column = 0;
                row++;
            }
        }
    }

    private VBox crearPlatilloItem(Platillo platillo) {
        VBox itemBox = new VBox(10);
        itemBox.setPadding(new Insets(10));
        itemBox.setStyle("-fx-border-color: black; -fx-border-width: 1;");

        Label nameLabel = new Label("Nombre: " + platillo.getName());
        Label priceLabel = new Label("Precio: " + platillo.getPrice());

        Button editarButton = new Button("Editar");
        editarButton.setOnAction(e -> editarPlatillo(platillo));

        Button eliminarButton = new Button("Eliminar");
        eliminarButton.setOnAction(e -> eliminarPlatillo(platillo.getName()));

        Button verIngredientesButton = new Button("Ver Ingredientes");
        verIngredientesButton.setOnAction(e -> verIngredientes(platillo));

        Button agregarIngredienteButton = new Button("Agregar Ingrediente");
        agregarIngredienteButton.setOnAction(e -> agregarIngrediente(platillo));

        itemBox.getChildren().addAll(nameLabel, priceLabel, editarButton, eliminarButton, verIngredientesButton, agregarIngredienteButton);
        return itemBox;
    }

    private void agregarIngrediente(Platillo platillo) {
        // Mostrar un cuadro de diálogo para ingresar el nombre del ingrediente
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Agregar Ingrediente");
        dialog.setHeaderText("Ingrese el nombre del ingrediente a agregar");
        dialog.setContentText("Nombre:");

        String nombreIngrediente = dialog.showAndWait().orElse(null);

        if (nombreIngrediente != null) {
            // Verificar si el ingrediente existe en la base de datos
            boolean ingredienteExiste = platillosModel.verificarIngredienteExistente(nombreIngrediente);

            if (ingredienteExiste) {
                boolean ingredienteAgregado = platillosModel.agregarIngredienteAPlatillo(platillo, nombreIngrediente);
                if (ingredienteAgregado) {
                    showAlert("Éxito", "Ingrediente agregado correctamente al platillo.");
                    cargarPlatillos();
                } else {
                    showAlert("Error", "Hubo un problema al agregar el ingrediente al platillo.");
                }
            } else {
                // Mostrar una alerta indicando que el ingrediente no existe en la base de datos
                showAlert("Error", "No se cuenta con el ingrediente en la base de datos.");
            }
        }
    }

    private void editarPlatillo(Platillo platillo) {
        TextInputDialog dialog = new TextInputDialog(platillo.getName());
        dialog.setTitle("Editar Platillo");
        dialog.setHeaderText("Editar los detalles del platillo");
        dialog.setContentText("Nombre:");

        // Obtener el nombre del platillo
        String nombrePlatillo = dialog.showAndWait().orElse(null);

        if (nombrePlatillo != null) {
            TextInputDialog priceDialog = new TextInputDialog(platillo.getPrice().toString());
            priceDialog.setTitle("Editar Platillo");
            priceDialog.setHeaderText("Editar el precio del platillo");
            priceDialog.setContentText("Precio:");

            String precioPlatilloStr = priceDialog.showAndWait().orElse(null);

            if (precioPlatilloStr != null) {
                try {
                    Float precioPlatillo = Float.parseFloat(precioPlatilloStr);
                    platillosModel.editDish(platillo.getName(), nombrePlatillo, precioPlatillo);
                    cargarPlatillos();
                } catch (NumberFormatException e) {
                    // Manejar error de formato de precio
                    showAlert("Error", "El precio ingresado no es válido.");
                }
            }
        }
    }

    private void eliminarPlatillo(String nombre) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Eliminar Platillo");
        alert.setHeaderText("¿Está seguro de que desea eliminar este platillo?");
        alert.setContentText(nombre);

        if (alert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
            platillosModel.deleteDish(nombre);
            cargarPlatillos();
        }
    }

    private void verIngredientes(Platillo platillo) {
        // Crear un cuadro de diálogo de información
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Ingredientes de " + platillo.getName());
        alert.setHeaderText("Ingredientes:");

        // Crear un área de texto para mostrar los ingredientes
        TextArea textArea = new TextArea();
        textArea.setEditable(false);
        textArea.setWrapText(true);

        // Obtener la lista de ingredientes del platillo y mostrarlos en el área de texto
        StringBuilder ingredientes = new StringBuilder();
        for (InventarioModel.Ingrediente ingrediente : platillo.getIngredientes()) {
            ingredientes.append(ingrediente).append("\n");
        }
        textArea.setText(ingredientes.toString());

        // Establecer el contenido del cuadro de diálogo como el área de texto
        alert.getDialogPane().setContent(textArea);

        // Mostrar el cuadro de diálogo
        alert.showAndWait();
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
