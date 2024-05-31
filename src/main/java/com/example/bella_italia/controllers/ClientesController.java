package com.example.bella_italia.controllers;

import com.example.bella_italia.models.Cliente;
import com.example.bella_italia.models.ClientesModel;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;
import java.util.HashMap;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Popup;

public class ClientesController {

    private Cliente clienteActual;

    @FXML
    private Button historialButton;

    @FXML
    private Button direccionesButton;

    @FXML
    private GridPane content_container;

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
    private TextField espacio_nombre;

    @FXML
    private TextField espacio_correo;

    @FXML
    private TextField espacio_direccion;

    @FXML
    private TextField espacio_ciudad;

    @FXML
    private TextField espacio_zip;

    private final ClientesModel clientesModel = new ClientesModel();

    @FXML
    public void initialize() {
        System.out.println("Inicializando controlador...");

        if (ClientesMenu != null && content_container != null) {
            cargarClientesDesdeBaseDeDatos();
        }

        if (ClientesMenu != null) {
        } else {
            System.err.println("ClientesMenu es nulo. Verifique que el ID coincida con el del archivo FXML.");
        }

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

    @FXML
    private void handleGuardarAction(ActionEvent event) {
        String nombre = espacio_nombre.getText();
        String correo = espacio_correo.getText();
        String direccion = espacio_direccion.getText();
        String ciudad = espacio_ciudad.getText();
        String codigoPostal = espacio_zip.getText();

        try {
            if (clienteActual == null) {
                clientesModel.insertCliente(nombre, correo, direccion, ciudad, codigoPostal);
                mostrarAlerta("Éxito", "Cliente creado exitosamente.", Alert.AlertType.INFORMATION);
            } else {
                clienteActual.setName(nombre);
                clienteActual.setEmail(correo);
                clienteActual.setAddress(direccion);
                clienteActual.setCity(ciudad);
                clienteActual.setZip(codigoPostal);
                clientesModel.actualizarCliente(clienteActual);
                mostrarAlerta("Éxito", "Cliente actualizado exitosamente.", Alert.AlertType.INFORMATION);
            }

            limpiarCampos();
            cargarClientesDesdeBaseDeDatos();
            clienteActual = null;
        } catch (Exception e) {
            mostrarAlerta("Error", "Hubo un error al guardar el cliente. Por favor, inténtalo de nuevo.", Alert.AlertType.ERROR);
        }
    }
    private void mostrarAlerta(String titulo, String contenido, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(contenido);
        alert.showAndWait();
    }

    @FXML
    private void handleCargarClicked(MouseEvent event) {
        System.out.println("Cargar fue pulsado");
        cargarClientesDesdeBaseDeDatos();
    }

    @FXML
    private void handleCrearClicked(MouseEvent event) {
        System.out.println("Crear fue pulsado 0");

        ClientesMenu.setDisable(true);
        loadViewNoClose("crearCliente-view.fxml");

        PauseTransition pause = new PauseTransition(Duration.millis(500));
        pause.setOnFinished(e -> ClientesMenu.setDisable(false));
        pause.play();
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
    private void handleClientesPaneClick(MouseEvent event) {
        boolean isVisible = ClientesMenu.isVisible();
        ClientesMenu.setVisible(!isVisible);
    }

    @FXML
    private void handleEliminarClicked(ActionEvent event) {
        Button eliminarButton = (Button) event.getSource();
        Cliente clienteAEliminar = obtenerClienteDesdeBoton(eliminarButton);

        if (clienteAEliminar != null) {
            clientesModel.eliminarCliente(clienteAEliminar.getId());
            eliminarClienteDeLaVista(clienteAEliminar);
        }
    }

    @FXML
    private void handleDireccionesClicked(ActionEvent event) {
        Button direccionesButton = (Button) event.getSource();
        Cliente clienteDirecciones = obtenerClienteDesdeBoton(direccionesButton);

        if (clienteDirecciones != null) {
            // Crear el contenido del popup con los datos del cliente
            GridPane direccionGrid = new GridPane();
            direccionGrid.setPrefSize(330, 340);
            direccionGrid.add(new Label("Dirección del cliente"), 0, 0); // Título
            // Agregar etiquetas para mostrar la dirección del cliente
            direccionGrid.add(new Label("Dirección: " + clienteDirecciones.getAddress()), 0, 1);
            direccionGrid.add(new Label("Ciudad: " + clienteDirecciones.getCity()), 0, 2);
            direccionGrid.add(new Label("Código postal: " + clienteDirecciones.getZip()), 0, 3);

            // Crear el popup y agregar el contenido
            Popup popup = new Popup();
            popup.getContent().add(direccionGrid);

            // Mostrar el popup
            popup.show(new Stage());
        } else {
            System.out.println("No se ha encontrado el cliente asociado al botón de direcciones.");
        }
    }

    private Cliente obtenerClienteDesdeBoton(Button button) {
        return (Cliente) button.getUserData();
    }

    private void eliminarClienteDeLaVista(Cliente cliente) {
        content_container.getChildren().removeIf(node -> {
            if (node instanceof Pane) {
                Pane clientePane = (Pane) node;
                Cliente clienteEnPane = (Cliente) clientePane.getUserData();
                if (clienteEnPane != null && clienteEnPane.getId() == cliente.getId()) {
                    return true;
                }
            }
            return false;
        });
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


    private void limpiarCampos() {
        espacio_nombre.clear();
        espacio_correo.clear();
        espacio_direccion.clear();
        espacio_ciudad.clear();
        espacio_zip.clear();
    }

    // Mapa para almacenar la asociación entre cliente y Pane
    private Map<Cliente, Pane> mapaClientes = new HashMap<>();

    // Método para asociar un cliente con su Pane correspondiente
    private void asociarClienteConPane(Cliente cliente, Pane clientePane) {
        mapaClientes.put(cliente, clientePane);
    }

    private Cliente obtenerClienteAsociado(Pane clientePane) {
        System.out.println("Buscando cliente asociado al panel...");
        for (Map.Entry<Cliente, Pane> entry : mapaClientes.entrySet()) {
            if (entry.getValue().equals(clientePane)) {
                System.out.println("¡Cliente encontrado!");
                return entry.getKey();
            }
        }
        System.out.println("¡Cliente no encontrado!");
        return null; // Si no se encuentra el cliente asociado al Pane
    }


    @FXML
    private void cargarClientesDesdeBaseDeDatos() {
        List<Cliente> clientes = clientesModel.obtenerClientes();

        content_container.getChildren().clear();

        int column = 0;
        int row = 0;

        for (Cliente cliente : clientes) {
            Pane clientePane = crearClienteGridPane(cliente);

            // Asociar el cliente con su clientePane
            asociarClienteConPane(cliente, clientePane);

            content_container.add(clientePane, column, row);

            column++;
            if (column == 3) {
                column = 0;
                row++;
            }
        }
    }


    private Pane crearClienteGridPane(Cliente cliente) {
        Pane clientePane = new Pane();
        clientePane.setPrefSize(329, 103);

        Pane rectangle158 = new Pane();
        rectangle158.setLayoutX(0);
        rectangle158.setLayoutY(0);
        rectangle158.setPrefSize(329, 103);
        rectangle158.setStyle("-fx-background-color: rgba(254,249,244,1); -fx-border-color: black; -fx-border-radius: 20; -fx-background-radius: 20;");

        Label nombreLabel = new Label(cliente.getName());
        nombreLabel.setLayoutX(15);
        nombreLabel.setLayoutY(10);
        nombreLabel.setPrefSize(198, 40);
        nombreLabel.setStyle("-fx-font-family: 'Rufina'; -fx-font-size: 24; -fx-text-fill: black;");

        Label idLabel = new Label("ID " + cliente.getId());
        idLabel.setLayoutX(186);
        idLabel.setLayoutY(14);
        idLabel.setPrefSize(131, 37);
        idLabel.setStyle("-fx-font-family: 'Rufina'; -fx-font-size: 20; -fx-text-fill: black;");

        Pane direcciones = new Pane();
        direcciones.setPrefSize(137, 25);
        direcciones.setLayoutX(177);
        direcciones.setLayoutY(64);

        Pane rectangle93 = new Pane();
        rectangle93.setPrefSize(137, 25);
        rectangle93.setLayoutX(0);
        rectangle93.setLayoutY(0);
        rectangle93.setStyle("-fx-background-color: rgba(250,180,119,1); -fx-background-radius: 10;");

        Button direccionesButton = new Button("Direcciones");
        direccionesButton.setLayoutX(0);
        direccionesButton.setLayoutY(0);
        direccionesButton.setPrefSize(137, 25);
        direccionesButton.setStyle("-fx-background-color: rgba(250,180,119,1); -fx-background-radius: 10;");
        direccionesButton.setUserData(cliente);

        direccionesButton.setOnAction(e -> {
            System.out.println("Mostrar direccion, se pulsó...");
            // Obtener el cliente asociado al botón de direcciones
            Cliente clienteDirecciones = (Cliente) direccionesButton.getUserData();

            if (clienteDirecciones != null) {
                System.out.println("Mostrar direccion, cargando...");
                mostrarDireccionesClienteDeBaseDeDatos(clienteDirecciones, direccionesButton);
            }else{
                System.out.println("Mostrar direccion, es null");
            }
        });

        direcciones.getChildren().addAll(rectangle93, direccionesButton);

        Pane historial = new Pane();
        historial.setPrefSize(137, 25);
        historial.setLayoutX(15);
        historial.setLayoutY(65);

        Pane rectangle92 = new Pane();
        rectangle92.setPrefSize(137, 25);
        rectangle92.setLayoutX(0);
        rectangle92.setLayoutY(0);
        rectangle92.setStyle("-fx-background-color: rgba(250,180,119,1); -fx-background-radius: 10;");

        Button historialButton = new Button("Historial");
        historialButton.setLayoutX(0);
        historialButton.setLayoutY(0);
        historialButton.setPrefSize(137, 25);
        historialButton.setStyle("-fx-background-color: rgba(250,180,119,1); -fx-background-radius: 10;");
        historialButton.setOnAction(e -> mostrarHistorial());

        historial.getChildren().addAll(rectangle92, historialButton);

        Label emailLabel = new Label(cliente.getEmail());
        emailLabel.setLayoutX(132);
        emailLabel.setLayoutY(39);
        emailLabel.setPrefSize(194, 30);
        emailLabel.setStyle("-fx-font-family: 'Rufina'; -fx-font-size: 16; -fx-text-fill: black;");
//
        //      Label telefonoLabel = new Label(cliente.getTelefono());
        //    telefonoLabel.setLayoutX(16);
        //  telefonoLabel.setLayoutY(39);
        //telefonoLabel.setPrefSize(111, 30);
        //telefonoLabel.setStyle("-fx-font-family: 'Rufina'; -fx-font-size: 16; -fx-text-fill: black;");

        Button editarButton = new Button("🖋️");
        editarButton.setStyle("-fx-font-family: Twemoji; -fx-background-color: transparent; -fx-border-color: transparent; -fx-font-size: 15; -fx-text-fill: black;");
        editarButton.setPrefSize(50, 50);
        editarButton.setLayoutX(240);
        editarButton.setLayoutY(-10);
        editarButton.setUserData(cliente); // Establecer el cliente como userData del botón
        editarButton.setOnAction(e -> {
            Cliente clienteAEditar = (Cliente) editarButton.getUserData();
            if (clienteAEditar != null) {
                cargarVistaEdicion(clienteAEditar);
            }
        });

        Button eliminarButton = new Button("⊠");
        eliminarButton.setStyle("-fx-font-family: Twemoji; -fx-background-color: transparent; -fx-border-color: transparent; -fx-font-size: 30; -fx-text-fill: black;");
        eliminarButton.setPrefSize(50, 50);
        eliminarButton.setLayoutX(280);
        eliminarButton.setLayoutY(-20);
        eliminarButton.setUserData(cliente); // Establecer el cliente como userData del botón
        eliminarButton.setOnAction(e -> {
            // Obtener el cliente asociado al botón de eliminación
            Cliente clienteAEliminar = (Cliente) eliminarButton.getUserData();

            // Verificar si se pudo obtener el cliente correctamente
            if (clienteAEliminar != null) {
                // Eliminar el cliente de la base de datos
                eliminarClienteDeBaseDeDatos(clienteAEliminar);

                // Actualizar la interfaz de usuario (opcional)
                // Por ejemplo, podrías eliminar el cliente de la vista
                eliminarClienteDeLaVista(clienteAEliminar);
            }
        });
        clientePane.getChildren().addAll(rectangle158, nombreLabel, idLabel, direcciones, historial, emailLabel, editarButton, eliminarButton);


        return clientePane;
    }

    private void eliminarClienteDeBaseDeDatos(Cliente cliente) {
        try {
            clientesModel.eliminarCliente(cliente.getId());
            System.out.println("Cliente eliminado de la base de datos correctamente");
        } catch (Exception e) {
            System.err.println("Error al eliminar el cliente de la base de datos: " + e.getMessage());
            // Aquí podrías mostrar una alerta o realizar alguna otra acción en caso de error
        }
    }

    private void mostrarDireccionesClienteDeBaseDeDatos(Cliente clienteDirecciones, Node ownerNode) {
        System.out.println("Mostrar direccion, cargando...");
        try {
            if (clienteDirecciones != null) {
                System.out.println("Mostrar direccion, cargado!");

                // Crear el contenido del popup con los datos del cliente
                GridPane direccionGrid = new GridPane();
                direccionGrid.setPrefSize(330, 340);
                direccionGrid.setStyle("-fx-padding: 10; -fx-background-color: white; -fx-border-color: black; -fx-border-width: 1;");
                direccionGrid.setHgap(10);
                direccionGrid.setVgap(10);

                // Agregar etiquetas para mostrar la dirección del cliente
                direccionGrid.add(new Label("Dirección del cliente"), 0, 0); // Título
                direccionGrid.add(new Label("Dirección: " + clienteDirecciones.getZip()), 0, 1);
                direccionGrid.add(new Label("Ciudad: " + clienteDirecciones.getCity()), 0, 2);
                direccionGrid.add(new Label("Código postal: " + clienteDirecciones.getAddress()), 0, 3);

                // Crear el popup y agregar el contenido
                Popup popup = new Popup();
                popup.getContent().add(direccionGrid);
                popup.setAutoHide(true);

                // Mostrar el popup anclado al nodo propietario
                Bounds ownerBounds = ownerNode.localToScreen(ownerNode.getBoundsInLocal());
                popup.show(ownerNode, ownerBounds.getMinX(), ownerBounds.getMaxY());
                System.out.println("Mostrar direccion, finalizado");
            } else {
                System.out.println("No se ha encontrado el cliente asociado al botón de direcciones.");
            }
        } catch (Exception e) {
            System.err.println("Error al mostrar el popup: " + e.getMessage());
            // Aquí podrías mostrar una alerta o realizar alguna otra acción en caso de error
        }
    }






    @FXML
    private void handleEditarAction(ActionEvent event) {
        // Obtener los nuevos datos del cliente de los campos de texto
        String nombre = espacio_nombre.getText();
        String correo = espacio_correo.getText();
        String direccion = espacio_direccion.getText();
        String ciudad = espacio_ciudad.getText();
        String codigoPostal = espacio_zip.getText();

        try {
            // Actualizar los datos del cliente actual en la base de datos
            clienteActual.setName(nombre);
            clienteActual.setEmail(correo);
            clienteActual.setAddress(direccion);
            clienteActual.setCity(ciudad);
            clienteActual.setZip(codigoPostal);
            clientesModel.actualizarCliente(clienteActual);

            // Mostrar una alerta de éxito
            mostrarAlerta("Éxito", "Cliente actualizado exitosamente.", Alert.AlertType.INFORMATION);

            // Limpiar los campos de texto y cargar nuevamente los clientes desde la base de datos
            limpiarCampos();
            cargarClientesDesdeBaseDeDatos();
            clienteActual = null;
        } catch (Exception e) {
            mostrarAlerta("Error", "Hubo un error al guardar el cliente. Por favor, inténtalo de nuevo.", Alert.AlertType.ERROR);
        }
    }

    private void cargarVistaEdicion(Cliente cliente) {
        System.out.println("Cargando vista de edición...");
        ClientesMenu.setDisable(true);
        loadViewNoClose("editarCliente-view.fxml");
        System.out.println("Vista de edición cargada.");

        clienteActual = cliente;
        espacio_nombre.setText(cliente.getName());
        espacio_correo.setText(cliente.getEmail());
        espacio_direccion.setText(cliente.getAddress());
        espacio_ciudad.setText(cliente.getCity());
        espacio_zip.setText(cliente.getZip());
    }

    private void mostrarHistorial() {
        System.out.println("Se pulsó Historial.");

        // Crea una nueva ventana emergente (Popup) para mostrar el historial de órdenes
        Popup popup = new Popup();

        // Configura el contenido de la ventana emergente para mostrar el historial de órdenes
        GridPane historialGrid = new GridPane();
        historialGrid.setPrefSize(330, 340);
        historialGrid.add(new Label("Historial de órdenes"), 0, 0); // Título
        // Aquí puedes agregar otros elementos para mostrar el historial de órdenes

        // Agrega el contenido al popup
        popup.getContent().add(historialGrid);

        // Muestra la ventana emergente
        popup.show(new Stage());
    }

    private void mostrarDireccion(Cliente clienteAsociado) {
        System.out.println("Se pulsó Dirección.");

        // Verificar si se ha seleccionado un cliente
        if (clienteAsociado != null) {
            // Crea una nueva ventana emergente (Popup) para mostrar la dirección del cliente
            Popup popup = new Popup();

            // Configura el contenido de la ventana emergente para mostrar la dirección del cliente
            GridPane direccionGrid = new GridPane();
            direccionGrid.setPrefSize(330, 340);
            direccionGrid.add(new Label("Dirección del cliente"), 0, 0); // Título
            // Agrega etiquetas para mostrar la dirección del cliente
            direccionGrid.add(new Label("Dirección: " + clienteAsociado.getAddress()), 0, 1);
            direccionGrid.add(new Label("Ciudad: " + clienteAsociado.getCity()), 0, 2);
            direccionGrid.add(new Label("Código postal: " + clienteAsociado.getZip()), 0, 3);

            // Agrega el contenido al popup
            popup.getContent().add(direccionGrid);

            // Muestra la ventana emergente
            popup.show(new Stage());
        } else {
            System.out.println("No se ha seleccionado ningún cliente.");
        }
    }


}
