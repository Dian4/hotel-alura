package com.starblackdian.hotelalura.controller;

import java.io.IOException;

import com.starblackdian.hotelalura.GuiApp;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Region;

public class LoginController {
    
    @FXML
    private TextField txtUsuario;

    @FXML
    private PasswordField txtPassword;

    public void iniciarSesion() throws IOException {
        final String usuario = txtUsuario.getText();
        final String password = txtPassword.getText();

        if (usuario.equals("were") && password.equals("tortuga")) {
            final Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("main.fxml"));
            final Scene scene = new Scene(root);

            GuiApp.PRIMARY_STAGE.setScene(scene);
        } else {
            final Alert alert = new Alert(AlertType.WARNING);

            alert.setTitle("Inicio de sesión fallido");
            alert.setHeaderText("Credenciales incorrectas");
            alert.setContentText("Sus credenciales son incorrectas. Por favor, introdúzcalas de nuevo.");
            alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);

            alert.show();
        }
    }
}
