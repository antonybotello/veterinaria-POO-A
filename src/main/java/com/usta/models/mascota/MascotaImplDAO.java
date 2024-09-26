package com.usta.models.mascota;


import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.usta.models.usuario.UsuarioImplDAO;
import com.usta.utils.*;

public class MascotaImplDAO implements GenericDAO<Mascota> {

    private Connection connection;
    UsuarioImplDAO usuarioDao= new UsuarioImplDAO();


    public MascotaImplDAO() {
        this.connection = ConnectionManager.getInstance().getConnection();
    }

    @Override
    public void add(Mascota obj) throws SQLException {

        String query = "INSERT INTO mascotas (nombre,especie,cuidador)" +
                "VALUES(?,?,?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, obj.getNombre());
            stmt.setString(2, obj.getEspecie());
            stmt.setInt(3, obj.getCuidador().getId());
            
            stmt.executeUpdate();
        }

    }

    @Override
    public void delete(int id) throws SQLException {
        String query = "DELETE FROM mascotas WHERE idMascotas=?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }

    }

    @Override
    public List<Mascota> getAll() throws SQLException {
        List<Mascota> mascotas = new ArrayList<>();
        // SELECT * FROM Mascotas
        String query = "SELECT * FROM mascotas";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query);) {
            while (rs.next()) {
                mascotas.add(new Mascota(rs.getInt("idMascotas"),
                        rs.getString("nombre"),
                        rs.getString("especie"),
                        usuarioDao.getById( rs.getInt("cuidador"))
                ));
            }

        }

        return mascotas;
    }

    @Override
    public Mascota getById(int id) throws SQLException {
        String query = "SELECT * FROM mascotas WHERE idMascota=?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Mascota mascota = new Mascota();
                    mascota.setId(rs.getInt("idMascotas"));
                    mascota.setNombre(rs.getString("nombre"));
                    mascota.setEspecie(rs.getString("especie"));
                    mascota.setCuidador(usuarioDao.getById( rs.getInt("cuidador")));
                
                    return mascota;
                }
            }
        }
        return null;
    }

    @Override
    public void update(Mascota obj) throws SQLException {
        String query = "UPDATE Mascotas SET especie=?, cuidador=?=? WHERE idMascotas=?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, obj.getNombre());
            stmt.setString(2, obj.getEspecie());
            stmt.setInt(3, obj.getCuidador().getId());
            stmt.setInt(4, obj.getId());
            stmt.executeUpdate();
        }
    }

}