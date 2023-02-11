package com.starblackdian.hotelalura;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;

import com.starblackdian.hotelalura.model.entity.Huesped;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class GuiApp extends Application {

    public static Stage PRIMARY_STAGE;
    
    public static void ejecutarApp(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        PRIMARY_STAGE = primaryStage;

        primaryStage.setTitle("Hotel Alura - Sistema de Gestión");
        primaryStage.setResizable(false);
        
        final Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("login.fxml"));

        final Scene scene = new Scene(root);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public void showExampleDialog() {
        final Dialog<Huesped> dialog = new Dialog<>();
        dialog.setTitle("Nuevo Huésped");
        dialog.setHeaderText("Por favor, ingrese los datos del nuevo huésped.");
        
        final ButtonType agregarButtonType = new ButtonType("Agregar", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(agregarButtonType, ButtonType.CANCEL);
        
        final GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        
        final TextField txtNombre = new TextField();
        final TextField txtApellido = new TextField();
        final DatePicker dtpFechaNac = new DatePicker(LocalDate.now());
        dtpFechaNac.setEditable(false); 
        final TextField txtNacionalidad = new TextField();
        final TextField txtTelefono = new TextField();
        
        grid.add(new Label("Nombre:"), 0, 0);
        grid.add(txtNombre, 1, 0);
        grid.add(new Label("Apellido(s):"), 0, 1);
        grid.add(txtApellido, 1, 1);
        grid.add(new Label("Fecha de nacimiento:"), 0, 2);
        grid.add(dtpFechaNac, 1, 2);
        grid.add(new Label("Nacionalidad:"), 0, 3);
        grid.add(txtNacionalidad, 1, 3);
        grid.add(new Label("Teléfono"), 0, 4);
        grid.add(txtTelefono, 1, 4);
        
        dialog.getDialogPane().setContent(grid);
        
        dialog.setResultConverter(pressedButton -> {
            if (pressedButton == agregarButtonType) {
                final Huesped huesped = new Huesped();
                
                huesped.setNombre(txtNombre.getText());
                huesped.setApellido(txtApellido.getText());
                huesped.setFechaNacimiento(Date.valueOf(dtpFechaNac.getValue()));
                huesped.setNacionalidad(txtNacionalidad.getText());
                huesped.setTelefono(txtTelefono.getText());
                
                return huesped;
            }
            
            return null;
        });
        
        final Optional<Huesped> resultado = dialog.showAndWait();
        
        resultado.ifPresentOrElse(huesped -> {
            final Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Registro exitoso");
            alert.setHeaderText("Se ha capturado la información."
                + " En esta lambda irían las validaciones de obligatoriedad.\n\n");
            alert.setContentText(huesped.toString());

            alert.showAndWait();
        }, () -> {
            final Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Operación cancelada");
            alert.setContentText("Registro de huésped cancelado.");

            alert.showAndWait();
        });
    }
}
