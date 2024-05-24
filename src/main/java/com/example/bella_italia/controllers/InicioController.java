package com.example.bella_italia.controllers;

import com.example.bella_italia.models.InicioModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class InicioController {

    @FXML
    private Pane botonAcceder, botonAcceder1, botonAcceder2;

    @FXML
    private Label _registrarme;

    @FXML
    private TextField cuadroEmail;

    @FXML
    private PasswordField cuadroContrasena;

    @FXML
    private void handleButtonClick(MouseEvent event) {
        Object source = event.getSource();
        boolean emailValid = !cuadroEmail.getText().isEmpty();
        boolean passwordValid = !cuadroContrasena.getText().isEmpty();

        // Aplicar estilo de error si el campo está vacío
        if (!emailValid) {
            cuadroEmail.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
        } else {
            cuadroEmail.setStyle(""); // Quitar el estilo si el campo está lleno
        }

        if (!passwordValid) {
            cuadroContrasena.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
        } else {
            cuadroContrasena.setStyle(""); // Quitar el estilo si el campo está lleno
        }
        if (source instanceof Pane) {
            Pane pane = (Pane) source;
            String paneId = pane.getId();
            String fxmlFileName = "";

            if (paneId.equals("botonAcceder1")) {
                // Verificar el inicio de sesión antes de cargar la vista del menú
                if (login()) {
                    fxmlFileName = "menu-view.fxml";
                } else {
                    System.out.println("Inicio de sesión fallido.");
                    return;
                }
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
    private void handleRegistroClick(MouseEvent event) {
        Object source = event.getSource();
        String fxmlFileName = "registro-view.fxml";


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

    // Método para verificar el inicio de sesión
    private boolean login() {
        // Obtener el correo electrónico y la contraseña del usuario desde la interfaz gráfica
        String email = cuadroEmail.getText();
        String password = cuadroContrasena.getText(); // Por ahora, asumimos que la contraseña es constante

        // Instanciar el modelo de inicio de sesión
        InicioModel inicioModel = new InicioModel();

        // Verificar el inicio de sesión utilizando el modelo
        return inicioModel.login(email, password);
    }
}
