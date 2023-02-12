package com.starblackdian.hotelalura.controller;

import java.sql.Date;

import com.starblackdian.hotelalura.model.entity.Huesped;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
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
        final Huesped h1 = new Huesped();
        final Huesped h2 = new Huesped();

        h1.setId(1);
        h1.setNombre("Gatitos");
        h1.setApellido("Babie");
        h1.setFechaNacimiento(new Date(453453454234L));
        h1.setNacionalidad("Gatuno");
        h1.setTelefono("01-800-GATITOS_BABIE");

        h2.setId(2);
        h2.setNombre("Bolo");
        h2.setApellido("Ladrador");
        h2.setFechaNacimiento(new Date(153453454234L));
        h2.setNacionalidad("Lolesco");
        h2.setTelefono("01-800-MUSARAÑA");

        return FXCollections.observableArrayList(h1, h2);
    }
}
