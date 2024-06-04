package com.example.bella_italia.controllers;

import com.example.bella_italia.models.RegistroModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class RegistroController {

    @FXML
    private Pane botonRegistrar, botonSalir;

    @FXML
    private TextField cuadroEmail;

    @FXML
    private PasswordField cuadroContrasena, cuadroConfirmarContrasena;

    @FXML
    private CheckBox checkTerminosyCondiciones;

    @FXML
    private void handleRegisterButtonClick(MouseEvent event) {
        boolean emailValid = !cuadroEmail.getText().isEmpty();
        boolean passwordValid = !cuadroContrasena.getText().isEmpty();
        boolean confirmPasswordValid = !cuadroConfirmarContrasena.getText().isEmpty();
        boolean passwordsMatch = cuadroContrasena.getText().equals(cuadroConfirmarContrasena.getText());
        boolean termsAccepted = checkTerminosyCondiciones.isSelected();

        if (!emailValid) {
            cuadroEmail.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
        } else {
            cuadroEmail.setStyle(""); // Clear the style if the field is filled
        }

        if (!passwordValid) {
            cuadroContrasena.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
        } else {
            cuadroContrasena.setStyle(""); // Clear the style if the field is filled
        }

        if (!confirmPasswordValid) {
            cuadroConfirmarContrasena.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
        } else {
            cuadroConfirmarContrasena.setStyle(""); // Clear the style if the field is filled
        }

        if (!passwordsMatch) {
            cuadroContrasena.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            cuadroConfirmarContrasena.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
        }

        if (!termsAccepted) {
            checkTerminosyCondiciones.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
        } else {
            checkTerminosyCondiciones.setStyle(""); // Clear the style if the terms are accepted
        }

        if (emailValid && passwordValid && confirmPasswordValid && passwordsMatch && termsAccepted) {
            // Register the user
            if (registerUser(cuadroEmail.getText(), cuadroContrasena.getText())) {
                // If registration is successful, go back to the login screen
                navigateTo("inicio-view.fxml");
            }
        }
    }

    @FXML
    private void handleButtonClick(MouseEvent event) {
        navigateTo("inicio-view.fxml");
    }

    private boolean registerUser(String email, String password) {
        RegistroModel registroModel = new RegistroModel();
        return registroModel.register(email, password);
    }

    private void navigateTo(String fxmlFileName) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/" + fxmlFileName));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

            // Close the current window
            Stage currentStage = (Stage) botonRegistrar.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
