package com.example.bella_italia.controllers;

import com.example.bella_italia.models.Cliente;
import com.example.bella_italia.models.Order;
import com.example.bella_italia.models.OrderModel;
import com.example.bella_italia.models.Platillo;
import javafx.collections.FXCollections;
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
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OrdenesController {

    public VBox OrdenesMenu;
    public Pane PlatillosPane;
    public Pane optionPaneInventario;
    public Pane optionPanePlatillos;
    public Pane optionPaneClientes;
    public Pane optionPaneSalir;
    public Pane OrdenesPane;
    private OrderModel orderModel = new OrderModel();

    @FXML
    private GridPane gridOrdenes;

    @FXML
    private TableView<Order> orderTable;

    @FXML
    private TableColumn<Order, Long> idColumn;

    @FXML
    private TableColumn<Order, Long> clientIdColumn;

    @FXML
    private TableColumn<Order, Double> totalColumn;

    @FXML
    private TableColumn<Order, String> orderDateColumn;

    @FXML
    private TableColumn<Order, Void> actionColumn;

    @FXML
    public void initialize() {
        cargarOrdenes();
    }

    private void cargarOrdenes() {
        List<Order> orders = orderModel.obtenerOrdenes();
        int row = 0;
        for (Order order : orders) {
            addOrderToGrid(order, row++);
        }
    }
    private void addOrderToGrid(Order order, int row) {
        VBox orderBox = new VBox(10);
        orderBox.setPrefWidth(200);
        orderBox.setPrefHeight(400);
        orderBox.setStyle("-fx-border-color: black; -fx-border-width: 1;");

        Label orderIdLabel = new Label("ID: " + order.getId());

        // Iterar sobre los platillos de la orden y obtener sus nombres
        StringBuilder platillosString = new StringBuilder();
        for (Platillo platillo : order.getPlatillos()) {
            platillosString.append(platillo.getName()).append(", ");
        }
        // Eliminar la coma adicional al final
        if (platillosString.length() > 0) {
            platillosString.delete(platillosString.length() - 2, platillosString.length());
        }
        Label platilloLabel = new Label("Platillos: " + platillosString.toString());

        Label totalLabel = new Label("Total: $" + order.getTotal());

        // Obtener el cliente asociado a la orden
        Cliente cliente = orderModel.getClientByOrderId(order.getId());
        Label emailLabel = new Label("Cliente: " + cliente.getEmail());

        Button downloadButton = new Button("Descargar");
        downloadButton.setOnAction(event -> descargarOrden(order));

        Button editButton = new Button("Editar");
        editButton.setOnAction(event -> handleEditarOrdenClicked(order));

        Button deleteButton = new Button("Eliminar");
        deleteButton.setOnAction(event -> eliminarOrden(order));

        orderBox.getChildren().addAll(orderIdLabel, emailLabel, platilloLabel, createSeparator(), totalLabel, downloadButton, editButton, deleteButton);

        // Agregar el VBox a la fila y columna correspondiente
        int column = row % 4; // Selecciona la columna en función del índice de fila
        gridOrdenes.add(orderBox, column, row / 4); // División entera para determinar la fila
    }

    private Node createSeparator() {
        Region separator = new Region();
        separator.setPrefHeight(10); // Define la altura del espacio vacío
        VBox.setMargin(separator, new Insets(5, 0, 5, 0)); // Ajusta los márgenes si es necesario
        return separator;
    }



    @FXML
    private void descargarOrden(Order order) {
        orderModel.descargarOrden(order);
    }

    @FXML
    private void handleEditarOrdenClicked(Order order) {
        // Crear la ventana del popup
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.initStyle(StageStyle.UTILITY);
        popupStage.setTitle("Editar Orden");

        // Crear el contenido del popup
        VBox popupVBox = new VBox(10);
        popupVBox.setPrefWidth(300);
        popupVBox.setPrefHeight(200);
        popupVBox.setAlignment(javafx.geometry.Pos.CENTER);

        // Agregar platillos actuales de la orden a una lista observable
        ObservableList<Platillo> platillosActuales = FXCollections.observableArrayList(order.getPlatillos());

        // Crear un ListView para mostrar los platillos actuales
        ListView<Platillo> platillosListView = new ListView<>(platillosActuales);
        platillosListView.setPrefHeight(100);

        // Crear botones para agregar o eliminar platillos
        Button agregarPlatilloButton = new Button("Agregar Platillo");
        agregarPlatilloButton.setOnAction(e -> {
            // Aquí puedes agregar la lógica para agregar platillos a la orden
            System.out.println("Agregar Platillo");
        });

        Button eliminarPlatilloButton = new Button("Eliminar Platillo");
        eliminarPlatilloButton.setOnAction(e -> {
            // Aquí puedes agregar la lógica para eliminar platillos de la orden
            System.out.println("Eliminar Platillo");
        });

        Button guardarCambiosButton = new Button("Guardar Cambios");
        guardarCambiosButton.setOnAction(e -> {
            // Aquí puedes agregar la lógica para guardar los cambios en la orden
            System.out.println("Guardar Cambios");
            popupStage.close();
        });

        popupVBox.getChildren().addAll(new Label("Platillos Actuales:"), platillosListView, agregarPlatilloButton, eliminarPlatilloButton, guardarCambiosButton);

        Scene popupScene = new Scene(popupVBox);
        popupStage.setScene(popupScene);
        popupStage.showAndWait();
    }


    @FXML
    private void eliminarOrden(Order order) {
        orderModel.eliminarOrden(order);
    }


    @FXML
    private void handlePlatillosClicked() {
        System.out.println("Platillos fue pulsado");
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
    private void handleInventarioClicked() {
        System.out.println("Inventario fue pulsado");
        Stage currentStage = (Stage) optionPaneInventario.getScene().getWindow();
        loadView("inventario-view.fxml", currentStage);
    }
    @FXML
    private void handleSalirClicked() {
        System.out.println("Salir fue pulsado");
        Stage currentStage = (Stage) optionPaneSalir.getScene().getWindow();
        loadView("menu-view.fxml", currentStage);
    }

    @FXML
    private void handleOrdenesPaneClick(MouseEvent event) {
        boolean isVisible = OrdenesMenu.isVisible();
        OrdenesMenu.setVisible(!isVisible);
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

        Label emailLabel = new Label("Correo del Cliente");
        TextField emailField = new TextField();
        emailField.setPromptText("Correo");

        Label dishLabel = new Label("Platillo");
        TextField dishField = new TextField();
        dishField.setPromptText("Nombre del Platillo");

        Button createButton = new Button("Crear");
        createButton.setOnAction(e -> {
            String email = emailField.getText();
            String dishName = dishField.getText();
            long clientId = orderModel.obtenerIdClientePorCorreo(email);
            Platillo platillo = orderModel.obtenerPlatilloPorNombre(dishName);
            System.out.println(clientId);
            System.out.println(dishName);
            if (clientId != -1) {
                if (platillo != null) {
                    List<Platillo> platillos = new ArrayList<>();
                    platillos.add(platillo);
                    orderModel.crearOrden(clientId, platillos);
                    popupStage.close();
                } else {
                    // Mostrar mensaje de error
                    mostrarAlerta("Error", "Platillo no encontrado", "El platillo con el nombre especificado no fue encontrado.");
                }
                List<Platillo> platillos = new ArrayList<>(); // Debes obtener los platillos de alguna manera
                orderModel.crearOrden(clientId, platillos);
                popupStage.close();
                cargarOrdenes();
            } else {
                // Mostrar mensaje de error
                mostrarAlerta("Error", "Cliente no encontrado", "El cliente con el correo especificado no fue encontrado.");
            }
        });

        popupVBox.getChildren().addAll(emailLabel, emailField, dishLabel, dishField, createButton);

        Scene popupScene = new Scene(popupVBox);
        popupStage.setScene(popupScene);
        popupStage.showAndWait();
    }
    private void mostrarAlerta(String titulo, String encabezado, String contenido) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(encabezado);
        alert.setContentText(contenido);
        alert.showAndWait();
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
}
