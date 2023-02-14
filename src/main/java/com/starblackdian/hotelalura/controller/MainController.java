package com.starblackdian.hotelalura.controller;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import com.starblackdian.hotelalura.model.dao.HuespedDao;
import com.starblackdian.hotelalura.model.entity.Huesped;
import com.starblackdian.hotelalura.util.DialogUtils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class MainController {
    
    @FXML
    private TextField txtBuscarHuesped;

    @FXML
    private TableView<Huesped> tblHuespedes;

    @FXML
    private TableColumn<Huesped, Integer> colIdHuesped;

    @FXML
    private TableColumn<Huesped, String> colNombre;

    @FXML
    private TableColumn<Huesped, String> colApellido;

    @FXML
    private TableColumn<Huesped, Date> colFechaNac;

    @FXML
    private TableColumn<Huesped, String> colNacionalidad;

    @FXML
    private TableColumn<Huesped, String> colTelefono;

    @FXML
    private void initialize() {
        inicializarTablaHuespedes();
        
    }

    public void agregarNuevoHuesped() {
        final Optional<Huesped> resultado = DialogUtils.agregarHuespedDialog();

        resultado.ifPresent(huesped -> {
            try (HuespedDao dao = new HuespedDao()) {
                dao.crear(huesped);

                DialogUtils.mostrarInfo("Registro Exitoso",
                    "Se ha registrado al huésped con éxito");
                
                tblHuespedes.getItems().clear();
                tblHuespedes.setItems(obtenerHuespedes());
            }
        });
    }

    public void verReservas() {
        final Huesped huesped = tblHuespedes.getSelectionModel().getSelectedItem();

        if (huesped == null) {
            DialogUtils.mostrarAdvertencia("Selección vacía", "Seleccione un huésped.");
        } else {
            DialogUtils.mostrarReservasDeHuesped(huesped);
        }
    }

    public void modificarHuesped() {
        final Huesped huesped = tblHuespedes.getSelectionModel().getSelectedItem();

        if (huesped == null) {
            DialogUtils.mostrarAdvertencia("Selección vacía", "Seleccione un huésped.");
        } else {
            final Optional<Huesped> resultado = DialogUtils.agregarHuespedDialog(huesped);

            resultado.ifPresent(huespedModificado -> {
                try (HuespedDao dao = new HuespedDao()) {
                    dao.actualizar(huespedModificado);

                    DialogUtils.mostrarInfo("Modificación Exitosa",
                        "Se han modificado los datos del huésped con éxito");

                    tblHuespedes.getItems().clear();
                    tblHuespedes.setItems(obtenerHuespedes());
                }
            });
        }
    }

    public void agregarNuevaReserva() {

    }

    public void verHuesped() {

    }

    public void modificarReserva() {

    }

    public void eliminarReserva() {
        
    }

    public void eliminarHuesped() {
        final Huesped huesped = tblHuespedes.getSelectionModel().getSelectedItem();

        if (huesped == null) {
            DialogUtils.mostrarAdvertencia("Selección vacía", "Seleccione un huésped.");
        } else {
            final String nombre = huesped.getId() + " - " + huesped.getNombre() + " " + huesped.getApellido();

            final Optional<ButtonType> result = DialogUtils.mostrarConfirmacion("Eliminar Huésped",
                "¿Desea eliminar al huésped '" + nombre + "'?");
            
            if (result.get() == ButtonType.OK) {
                try (HuespedDao dao = new HuespedDao()) {
                    dao.eliminar(huesped.getId());

                    DialogUtils.mostrarInfo("Huésped Eliminado",
                        "Se ha eliminado al huésped '" + nombre + "' con éxito.");

                    tblHuespedes.getItems().clear();
                    tblHuespedes.setItems(obtenerHuespedes());
                }
            }
        }
    }

    private void inicializarTablaHuespedes() {
        colIdHuesped.setCellValueFactory(new PropertyValueFactory<Huesped, Integer>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<Huesped, String>("nombre"));
        colApellido.setCellValueFactory(new PropertyValueFactory<Huesped, String>("apellido"));
        colFechaNac.setCellValueFactory(new PropertyValueFactory<Huesped, Date>("fechaNacimiento"));
        colNacionalidad.setCellValueFactory(new PropertyValueFactory<Huesped, String>("nacionalidad"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<Huesped, String>("telefono"));

        tblHuespedes.setItems(obtenerHuespedes());
    }

    private ObservableList<Huesped> obtenerHuespedes() {
        try (HuespedDao dao = new HuespedDao()) {
            final List<Huesped> huespedes = dao.listarTodos();

            return FXCollections.observableArrayList(huespedes);
        }
    }
}
