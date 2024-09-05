package com.usta.controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.usta.App;
import com.usta.models.usuario.Usuario;
import com.usta.models.usuario.UsuarioImplDAO;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class UsuarioController {
    @FXML
    private TextField txtNombres;
    @FXML
    private TextField txtApellidos;
    @FXML
    private TextField txtDocumento;
    @FXML
    private TextField txtCorreo;

    UsuarioImplDAO usuariosDAO = new UsuarioImplDAO();

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
            System.out.println("Usuario agregado con éxito.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error al agregar usuario!");
        }
    }

    public void listarUsuarios() throws SQLException {
        try {
            List<Usuario> usuarios = usuariosDAO.getAll();
            for (Usuario usuario : usuarios) {
                System.out.println(usuario);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error al listar usuarios!");
        }
    }

    public void eliminarUsuario(int id) throws SQLException {
        try {
            usuariosDAO.delete(id);
            System.out.println("Usuario eliminado con éxito.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error al eliminar usuario!");
        }
    }

    public void actualizarUsuario(Usuario usuario) throws SQLException {
        try {
            usuariosDAO.update(usuario);
            System.out.println("Usuario actualizado con éxito.");
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
