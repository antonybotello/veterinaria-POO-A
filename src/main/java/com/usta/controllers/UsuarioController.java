package com.usta.controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.usta.App;
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

public class UsuarioController {


    UsuarioImplDAO usuariosDAO = new UsuarioImplDAO();

    @FXML
    private ComboBox<Usuario> usuariosCBX;
    //################ para los botones ##############
    @FXML
    private Button agregarBtn;
    @FXML
    private Button editarBtn;
    @FXML
    private Button eliminarBtn;
    @FXML
    private Button cancelarBtn;
    
    //################ fin los botones ##############



    //################ para entrada de datos ##############
    @FXML
    private TextField txtNombres;
    @FXML
    private TextField txtApellidos;
    @FXML
    private TextField txtDocumento;
    @FXML
    private TextField txtCorreo;
    //################ fin entrada de datos ################

    //################ para tabla de datos ##############
    @FXML
    private TableView<Usuario> usuariosTable;

    @FXML
    private TableColumn<Usuario,String> documentoCol;
    @FXML
    private TableColumn<Usuario,String> nombresCol;
    @FXML
    private TableColumn<Usuario,String> apellidosCol;
    @FXML
    private TableColumn<Usuario,String> correoCol;

    private ObservableList<Usuario> usuariosDataList= FXCollections.observableArrayList();
    //################ fin tabla de datos ##############


    @FXML
    private void initialize(){
        documentoCol.setCellValueFactory(new PropertyValueFactory<>("documento"));
        nombresCol.setCellValueFactory(new PropertyValueFactory<>("nombres"));
        apellidosCol.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
        correoCol.setCellValueFactory(new PropertyValueFactory<>("correo"));

        try {
            usuariosDataList.addAll(usuariosDAO.getAll());
            usuariosTable.setItems(usuariosDataList);
        } catch (Exception e) {
            System.err.println("Error al agregar Usuario!");
        }

        usuariosCBX.setItems(usuariosDataList);
        //usuariosCBX.getSelectionModel().getSelectedItem().getId();
    }

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }

    @FXML
    public void agregarUsuario() throws SQLException {
        try {
         
            Usuario usuario = new Usuario(
                    txtDocumento.getText(),
                    txtNombres.getText(),
                    txtApellidos.getText(),
                    txtCorreo.getText());
            usuariosDAO.add(usuario);
            usuariosDataList.add(usuario);
            usuariosTable.setItems(usuariosDataList);
            System.out.println("Usuario agregado con éxito.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error al agregar usuario!");
        }
    }


    @FXML
    public void seleccionarUsuario(){
        Usuario usuario= usuariosTable.getSelectionModel().getSelectedItem();

        agregarBtn.setVisible(false);
        editarBtn.setVisible(true);
        eliminarBtn.setVisible(true);
        cancelarBtn.setVisible(true);
        txtDocumento.setText(usuario.getDocumento());
        txtDocumento.setEditable(false);
        txtNombres.setText(usuario.getNombres());
        txtApellidos.setText(usuario.getApellidos());
        txtCorreo.setText(usuario.getCorreo());

    }
    @FXML
    public void cancelar()  {
        agregarBtn.setVisible(true);
        editarBtn.setVisible(false);
        eliminarBtn.setVisible(false);
        cancelarBtn.setVisible(false);
        

        txtDocumento.setText("");
        txtDocumento.setEditable(true);
        txtNombres.setText("");
        txtApellidos.setText("");
        txtCorreo.setText("");

    }
    @FXML
    public void eliminarUsuario() throws SQLException {
        Usuario usuario= usuariosTable.getSelectionModel().getSelectedItem();

        try {
            usuariosDAO.delete(usuario.getId());
            usuariosDataList.remove(usuario);
            usuariosTable.setItems(usuariosDataList);
            System.out.println("Usuario eliminado con éxito.");
            cancelar();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error al eliminar usuario!");
        }
    }
    @FXML
    public void editarUsuario() throws SQLException {
        Usuario usuario= usuariosTable.getSelectionModel().getSelectedItem();

        usuariosDataList.remove(usuario);
        usuariosTable.setItems(usuariosDataList);
        usuario= new Usuario(txtDocumento.getText(),
        txtNombres.getText(),
        txtApellidos.getText(),
        txtCorreo.getText());

        try {
            usuariosDAO.update(usuario);
            usuariosDataList.add(usuario);
            usuariosTable.setItems(usuariosDataList);
            System.out.println("Usuario actualizado con éxito.");
            cancelar();

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error al actualizar usuario!");
        }
    }

    public void obtenerUsuarioPorId(int id) throws SQLException {
        try {
            Usuario usuario = usuariosDAO.getById(id);
            
            if (usuario != null) {
                System.out.println(usuario);
            } else {
                System.err.println("Usuario no encontrado con el ID: " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error al obtener usuario!");
        }
    }
}
