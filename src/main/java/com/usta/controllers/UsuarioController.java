package com.usta.controllers;

import java.io.IOException;
import java.sql.SQLException;

import com.usta.App;
import com.usta.models.usuario.Usuario;
import com.usta.models.usuario.UsuarioImplDAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class UsuarioController {

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



    //########################## para entrada de datos ##############
    @FXML
    private TextField documentoField;
    @FXML
    private TextField nombresField;
    @FXML
    private TextField apellidosField;
    @FXML
    private TextField correoField;
    //########################## fin entrada de datos ##############

    //########################## para la tabla ##############
    @FXML
    private TableView<Usuario> usuariosTable; // le pedimos que asocie el objeto del fxml a esta variable
    @FXML
    private TableColumn<Usuario, String> documentoCol;
    @FXML
    private TableColumn<Usuario, String> nombresCol;
    @FXML
    private TableColumn<Usuario, String> apellidosCol;
    @FXML
    private TableColumn<Usuario, String> correoCol;

    private ObservableList<Usuario> usuariosDataList = FXCollections.observableArrayList();

    //########################## fintabla ##############

    @FXML
    public void initialize(){
       
        documentoCol.setCellValueFactory(new PropertyValueFactory<>("documento"));
        nombresCol.setCellValueFactory(new PropertyValueFactory<>("nombres"));
        apellidosCol.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
        correoCol.setCellValueFactory(new PropertyValueFactory<>("correo"));
        try {
            usuariosDataList.addAll(usuarioDao.getAll());
            usuariosTable.setItems(usuariosDataList);
        } catch (SQLException e) {
            System.err.println("Error al agregar Usuario!");
            e.printStackTrace();
        }
    }

    @FXML
    public void agregarUsuario(){
        String documento= documentoField.getText();
        String nombres= nombresField.getText();
        String apellidos= apellidosField.getText();
        String correo= correoField.getText();
    
        Usuario nuevoUsuario= new Usuario(documento, nombres, apellidos, correo);
        try {
            usuarioDao.add(nuevoUsuario);
        } catch (SQLException e) {
            System.err.println("Error al agregar Usuario!");
            e.printStackTrace();
        }
        usuariosDataList.add(nuevoUsuario);
        usuariosTable.setItems(usuariosDataList);
        limpiarCampos();
    }
    @FXML
    public void editarUsuario(){
        Usuario usuario= usuariosTable.getSelectionModel().getSelectedItem();
        String documento= documentoField.getText();
        String nombres= nombresField.getText();
        String apellidos= apellidosField.getText();
        String correo= correoField.getText();
    
        Usuario usuarioEditado= new Usuario(usuario.getId(),documento, nombres, apellidos, correo);
        try {
            usuarioDao.update(usuarioEditado);
            usuariosDataList.remove(usuariosTable.getSelectionModel().getSelectedItem());
            usuariosDataList.add(usuarioEditado);
        } catch (SQLException e) {
            System.err.println("Error al editar Usuario!");
            e.printStackTrace();
        }
        usuariosTable.setItems(usuariosDataList);
        limpiarCampos();
    }
    @FXML
    public void eliminarUsuario(){
        Usuario usuario= usuariosTable.getSelectionModel().getSelectedItem();
        try {
            usuarioDao.delete(usuario.getId());
            usuariosDataList.remove(usuario);
        } catch (SQLException e) {
            System.err.println("Error al eliminar Usuario!");
            e.printStackTrace();
        }
        usuariosTable.setItems(usuariosDataList);
        limpiarCampos();
    }

    @FXML
    public void seleccionarUsuario(){
        Usuario usuario= usuariosTable.getSelectionModel().getSelectedItem();
        documentoField.setText(usuario.getDocumento());
        documentoField.setEditable(false);
        nombresField.setText(usuario.getNombres());
        apellidosField.setText(usuario.getApellidos());
        correoField.setText(usuario.getCorreo());
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
        documentoField.setText("");
        documentoField.setEditable(true);
        nombresField.setText("");
        apellidosField.setText("");
        correoField.setText("");
        agregarBtn.setVisible(true);
        editarBtn.setVisible(false);
        eliminarBtn.setVisible(false);
        cancelarBtn.setVisible(false);
    }
}