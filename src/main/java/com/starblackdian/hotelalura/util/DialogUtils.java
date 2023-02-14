package com.starblackdian.hotelalura.util;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.starblackdian.hotelalura.model.dao.HuespedDao;
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
import javafx.scene.control.ComboBox;
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

    public static Optional<Reserva> agregarReservaDialog() {
        return agregarReservaDialog(null);
    }

    public static Optional<Reserva> agregarReservaDialog(Reserva existente) {
        final Dialog<Reserva> dialog = new Dialog<>();

        final String titulo = existente == null ? "Nueva Reserva" : "Editar Reserva";
        final String textoBoton = existente == null ? "Agregar" : "Modificar";

        dialog.setTitle(titulo);
        dialog.setHeaderText("Por favor, ingrese los datos de la reserva.");

        final ButtonType agregarButtonType = new ButtonType(textoBoton, ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(agregarButtonType, ButtonType.CANCEL);

        final GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        final DatePicker dtpFechaEntrada = new DatePicker(LocalDate.now());
        final DatePicker dtpFechaSalida = new DatePicker(LocalDate.now());
        final TextField txtValor = new TextField();
        final TextField txtFormaPago = new TextField();
        final ComboBox<Huesped> cmbHuesped = new ComboBox<>(obtenerHuespedes());

        if (existente != null) {
            dtpFechaEntrada.setValue(existente.getFechaEntrada().toLocalDate());
            dtpFechaSalida.setValue(existente.getFechaSalida().toLocalDate());
            txtValor.setText(existente.getValor().toString());
            txtFormaPago.setText(existente.getFormaPago());
            
            for (Huesped huesped : cmbHuesped.getItems()) {
                if (huesped.getId() == existente.getIdHuesped()) {
                    cmbHuesped.getSelectionModel().select(huesped);
                }
            }
        }

        dtpFechaEntrada.setEditable(false);
        dtpFechaSalida.setEditable(false);

        grid.add(new Label("Fecha de Entrada:"), 0, 0);
        grid.add(dtpFechaEntrada, 1, 0);
        grid.add(new Label("Fecha de Salida:"), 0, 1);
        grid.add(dtpFechaSalida, 1, 1);
        grid.add(new Label("Importe de la instancia:"), 0, 2);
        grid.add(txtValor, 1, 2);
        grid.add(new Label("Forma de Pago:"), 0, 3);
        grid.add(txtFormaPago, 1, 3);
        grid.add(new Label("Huésped:"), 0, 4);
        grid.add(cmbHuesped, 1, 4);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(pressedButton -> {
            if (pressedButton == agregarButtonType) {
                final Reserva reserva = new Reserva();

                reserva.setFechaEntrada(Date.valueOf(dtpFechaEntrada.getValue()));
                reserva.setFechaSalida(Date.valueOf(dtpFechaSalida.getValue()));
                reserva.setValor(new BigDecimal(txtValor.getText()));
                reserva.setFormaPago(txtFormaPago.getText());
                reserva.setIdHuesped(cmbHuesped.getSelectionModel().getSelectedItem().getId());

                if (existente != null) {
                    reserva.setId(existente.getId());
                }

                return reserva;
            }

            return null;
        });

        final Button btnOk = (Button) dialog.getDialogPane().lookupButton(agregarButtonType);
        btnOk.addEventFilter(ActionEvent.ACTION, ev -> {
            final boolean formaPagoVacia = txtFormaPago.getText().isBlank();
            final boolean hayHuespedSeleccionado = cmbHuesped.getValue() == null;

            if (formaPagoVacia || hayHuespedSeleccionado) {
                mostrarAdvertencia("Parámetros obligatorios",
                    "La forma de pago, el importe y el huésped son obligatorios.");

                ev.consume();
            }

            try {
                new BigDecimal(txtValor.getText());
            } catch (Exception e) {
                mostrarAdvertencia("Parámetros inválidos",
                    "El importe debe ser un número entero o decimal.");

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

        colFechaEntrada.setCellValueFactory(new PropertyValueFactory<Reserva, Date>("fechaEntrada"));
        colFechaSalida.setCellValueFactory(new PropertyValueFactory<Reserva, Date>("fechaSalida"));
        colValor.setCellValueFactory(new PropertyValueFactory<Reserva, BigDecimal>("valor"));
        colFormaPago.setCellValueFactory(new PropertyValueFactory<Reserva, String>("formaPago"));

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

    private static ObservableList<Huesped> obtenerHuespedes() {
        try (HuespedDao dao = new HuespedDao()) {
            final List<Huesped> huespedes = dao.listarTodos();

            return FXCollections.observableArrayList(huespedes);
        }
    }
}
