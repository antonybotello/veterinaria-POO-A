package com.usta.controllers;

import java.io.IOException;
import java.sql.SQLException;

import com.usta.App;
import com.usta.models.mascota.Mascota;
import com.usta.models.mascota.MascotaImplDAO;
import com.usta.models.usuario.Usuario;
import com.usta.models.usuario.UsuarioImplDAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

public class MascotaController {

    MascotaImplDAO mascotaDao= new MascotaImplDAO();
    UsuarioImplDAO usuarioDao= new UsuarioImplDAO();

    //########################## para botones ##############
    @FXML
    private Button agregarBtn;
    @FXML
    private Button editarBtn;
    @FXML
    private Button eliminarBtn;
    @FXML
    private Button cancelarBtn;
    
    //########################## fin botones ##############

    @FXML
    private HBox perroPanel;
    @FXML
    private HBox gatoPanel;
    @FXML
    private HBox especiePanel;
    //########################## para entrada de datos ##############
    @FXML
    private ComboBox<String> especieCbx;
    @FXML
    private TextField nombreField;
    @FXML
    private TextField cuidadorField;
    

    @FXML
    private ComboBox<Usuario> usuariosCbx;
    private ObservableList<Usuario> usuariosDataList = FXCollections.observableArrayList();

    //########################## fin entrada de datos ##############

    //########################## para la tabla ##############
    @FXML
    private TableView<Mascota> mascotasTable; // le pedimos que asocie el objeto del fxml a esta variable
    @FXML
    private TableColumn<Mascota, String> nombreCol;
    @FXML
    private TableColumn<Mascota, String> especieCol;
    @FXML
    private TableColumn<Mascota, Usuario> cuidadorCol;

    private ObservableList<Mascota> mascotasDataList = FXCollections.observableArrayList();

    //########################## fintabla ##############

    @FXML
    public void selectorEspecieGato(){
            especiePanel.setVisible(false);
            gatoPanel.setVisible(true);

    }
    @FXML
    public void selectorEspeciePerro(){
            especiePanel.setVisible(false);
            perroPanel.setVisible(true);
            
    }


    @FXML
    public void initialize(){
       especieCbx.getItems().setAll("perro","Gato");
        nombreCol.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        especieCol.setCellValueFactory(new PropertyValueFactory<>("especie"));
        cuidadorCol.setCellValueFactory(new PropertyValueFactory<>("cuidador"));
        try {
            usuariosDataList.addAll(usuarioDao.getAll());
            usuariosCbx.setItems(usuariosDataList);
            mascotasDataList.addAll(mascotaDao.getAll());
            mascotasTable.setItems(mascotasDataList);
        } catch (SQLException e) {
            System.err.println("Error al Listar Mascota!");
            e.printStackTrace();
        }
    }

    @FXML
    public void agregarMascota(){
       
        String nombre= nombreField.getText();
        String especie= especieCbx.getSelectionModel().getSelectedItem();
        Usuario cuidador= usuariosCbx.getSelectionModel().getSelectedItem();

        Mascota nuevoMascota= new Mascota(nombre,especie,cuidador);
        try {
            mascotaDao.add(nuevoMascota);
        } catch (SQLException e) {
            System.err.println("Error al agregar Mascota!");
            e.printStackTrace();
        }
        mascotasDataList.add(nuevoMascota);
        mascotasTable.setItems(mascotasDataList);
        limpiarCampos();
    }
    @FXML
    public void editarMascota(){
        Mascota mascota= mascotasTable.getSelectionModel().getSelectedItem();
        String especie= especieCbx.getSelectionModel().getSelectedItem();
        String nombre= nombreField.getText();
        Usuario cuidador= usuariosCbx.getSelectionModel().getSelectedItem();
    
        Mascota mascotaEditado= new Mascota(mascota.getId(), nombre,especie, cuidador);
        try {
            mascotaDao.update(mascotaEditado);
            mascotasDataList.remove(mascotasTable.getSelectionModel().getSelectedItem());
            mascotasDataList.add(mascotaEditado);
        } catch (SQLException e) {
            System.err.println("Error al editar Mascota!");
            e.printStackTrace();
        }
        mascotasTable.setItems(mascotasDataList);
        limpiarCampos();
    }
    @FXML
    public void eliminarMascota(){
        Mascota mascota= mascotasTable.getSelectionModel().getSelectedItem();
        try {
            mascotaDao.delete(mascota.getId());
            mascotasDataList.remove(mascota);
        } catch (SQLException e) {
            System.err.println("Error al eliminar Mascota!");
            e.printStackTrace();
        }
        mascotasTable.setItems(mascotasDataList);
        limpiarCampos();
    }

    @FXML
    public void seleccionarMascota(){
        Mascota mascota= mascotasTable.getSelectionModel().getSelectedItem();
        especieCbx.getSelectionModel().select(mascota.getEspecie());
        nombreField.setText(mascota.getNombre());
        usuariosCbx.getSelectionModel().select(mascota.getCuidador());
        agregarBtn.setVisible(false);
        editarBtn.setVisible(true);
        eliminarBtn.setVisible(true);
        cancelarBtn.setVisible(true);



    }
    @FXML
    private void switchToMenu() throws IOException {
        App.setRoot("primary");
    }
    @FXML
    private void switchToMascota() throws IOException {
        App.setRoot("mascota");
    }
    @FXML
    private void switchToUsuario() throws IOException {
        App.setRoot("usuario");
    }


    @FXML
    public void limpiarCampos(){
        especiePanel.setVisible(true);
        gatoPanel.setVisible(false);
        perroPanel.setVisible(false);


        especieCbx.getSelectionModel().clearSelection();
        
        nombreField.setText("");
        usuariosCbx.getSelectionModel().clearSelection();
        agregarBtn.setVisible(true);
        editarBtn.setVisible(false);
        eliminarBtn.setVisible(false);
        cancelarBtn.setVisible(false);
    }
}