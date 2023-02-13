package com.starblackdian.hotelalura.util;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.starblackdian.hotelalura.model.dao.ReservaDao;
import com.starblackdian.hotelalura.model.entity.Huesped;
import com.starblackdian.hotelalura.model.entity.Reserva;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

public class DialogUtils {

    public static void mostrarInfo(String titulo, String cuerpo) {
        mostrarAlert(titulo, cuerpo, AlertType.INFORMATION);
    }

    public static void mostrarAdvertencia(String titulo, String cuerpo) {
        mostrarAlert(titulo, cuerpo, AlertType.WARNING);
    }

    public static Optional<ButtonType> mostrarConfirmacion(String titulo, String cuerpo) {
        final Alert alert = new Alert(AlertType.CONFIRMATION);

        alert.setTitle(titulo);
        alert.setContentText(cuerpo);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);

        return alert.showAndWait();
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

    public static void mostrarReservasDeHuesped(Huesped huesped) {
        final Alert alert = new Alert(AlertType.INFORMATION);
        final String nombreHuesped = huesped.getId() + " - " + huesped.getNombre() + " " + huesped.getApellido();

        alert.setTitle("Reservas del huésped");
        alert.setHeaderText("Estas son las reservas a nombre del huésped: " + nombreHuesped);

        final TableView<Reserva> tblReservas = new TableView<>();
        final TableColumn<Reserva, Date> colFechaEntrada = new TableColumn<>("Fecha de Entrada");
        final TableColumn<Reserva, Date> colFechaSalida = new TableColumn<>("Fecha de Salida");
        final TableColumn<Reserva, BigDecimal> colValor = new TableColumn<>("Valor");
        final TableColumn<Reserva, String> colFormaPago = new TableColumn<>("Forma de Pago");

        colFechaEntrada.setCellValueFactory(new PropertyValueFactory<Reserva, Date>("fechaentrada"));
        colFechaSalida.setCellValueFactory(new PropertyValueFactory<Reserva, Date>("fechasalida"));
        colValor.setCellValueFactory(new PropertyValueFactory<Reserva, BigDecimal>("valor"));
        colFormaPago.setCellValueFactory(new PropertyValueFactory<Reserva, String>("formapago"));

        tblReservas.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tblReservas.getColumns().addAll(colFechaEntrada, colFechaSalida, colValor, colFormaPago);

        try (ReservaDao dao = new ReservaDao()) {
            final List<Reserva> reservas = dao.listarPorHuespedId(huesped.getId());
            final ObservableList<Reserva> reservasFilas = FXCollections.observableArrayList(reservas);

            tblReservas.setItems(reservasFilas);
        }

        GridPane.setVgrow(tblReservas, Priority.ALWAYS);
        GridPane.setHgrow(tblReservas, Priority.ALWAYS);

        final GridPane gridPane = new GridPane();
        gridPane.add(tblReservas, 0, 0);

        alert.getDialogPane().setExpandableContent(gridPane);
        alert.show();
    }

    private static void mostrarAlert(String titulo, String cuerpo, AlertType tipoAlerta) {
        final Alert alert = new Alert(tipoAlerta);

        alert.setTitle(titulo);
        alert.setContentText(cuerpo);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.showAndWait();
    }
}
