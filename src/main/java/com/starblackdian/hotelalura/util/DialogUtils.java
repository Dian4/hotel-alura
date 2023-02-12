package com.starblackdian.hotelalura.util;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;

import com.starblackdian.hotelalura.model.entity.Huesped;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class DialogUtils {

    public static void mostrarInfo(String titulo, String cuerpo) {
        mostrarAlert(titulo, cuerpo, AlertType.INFORMATION);
    }

    public static void mostrarAdvertencia(String titulo, String cuerpo) {
        mostrarAlert(titulo, cuerpo, AlertType.WARNING);
    }

    public static Optional<Huesped> agregarHuespedDialog() {
        return agregarHuespedDialog(null);
    }
    
    public static Optional<Huesped> agregarHuespedDialog(Huesped existente) {
        final Dialog<Huesped> dialog = new Dialog<>();

        final String titulo = existente == null ? "Nuevo Huésped" : "Modificar Datos de Huésped";
        final String textoBoton = existente == null ? "Agregar" : "Modificar";

        dialog.setTitle(titulo);
        dialog.setHeaderText("Por favor, ingrese los datos del huésped.");

        final ButtonType agregarButtonType = new ButtonType(textoBoton, ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(agregarButtonType, ButtonType.CANCEL);

        final GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        final TextField txtNombre = new TextField();
        final TextField txtApellido = new TextField();
        final DatePicker dtpFechaNac = new DatePicker(LocalDate.now());
        final TextField txtNacionalidad = new TextField();
        final TextField txtTelefono = new TextField();

        if (existente != null) {
            txtNombre.setText(existente.getNombre());
            txtApellido.setText(existente.getApellido());
            dtpFechaNac.setValue(existente.getFechaNacimiento().toLocalDate());
            txtNacionalidad.setText(existente.getNacionalidad());
            txtTelefono.setText(existente.getTelefono());    
        }
        
        dtpFechaNac.setEditable(false);

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

                if (existente != null) {
                    huesped.setId(existente.getId());
                }

                return huesped;
            }

            return null;
        });

        final Button btnOk = (Button) dialog.getDialogPane().lookupButton(agregarButtonType);
        btnOk.addEventFilter(ActionEvent.ACTION, ev -> {
            final boolean nombreVacio = txtNombre.getText().isBlank();
            final boolean apellidoVacio = txtApellido.getText().isBlank();

            if (nombreVacio || apellidoVacio) {
                mostrarAdvertencia("Parámetros obligatorios",
                    "El nombre y el apellido son obligatorios.");

                ev.consume();
            }
        });

        return dialog.showAndWait();
    }

    private static void mostrarAlert(String titulo, String cuerpo, AlertType tipoAlerta) {
        final Alert alert = new Alert(tipoAlerta);

        alert.setTitle(titulo);
        alert.setContentText(cuerpo);
        alert.showAndWait();
    }
}
