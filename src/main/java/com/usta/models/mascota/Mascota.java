package com.usta.models.mascota;

import com.usta.models.usuario.Usuario;

public class Mascota {
    private int id;
    private String nombre;
    private String especie;
    private Usuario cuidador;

    public Mascota() {
    }

    public Mascota(String nombre, String especie, Usuario cuidador) {
        this.nombre = nombre;
        this.especie = especie;
        this.cuidador = cuidador;
    }

    public Mascota(int id, String nombre, String especie, Usuario cuidador) {
        this.id = id;
        this.nombre = nombre;
        this.especie = especie;
        this.cuidador = cuidador;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public Usuario getCuidador() {
        return cuidador;
    }

    public void setCuidador(Usuario cuidador) {
        this.cuidador = cuidador;
    }

    @Override
    public String toString() {
        return nombre;
    }
    

}