package com.example.covid.entidades;

import java.util.List;

public class Distritos {
    private  int id;
    private String nombreDistrito;
    private Provincias provincia;
    private List<UsuarioCasos> usuarioCaso;
    private boolean estado;

    public Distritos(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreDistrito() {
        return nombreDistrito;
    }

    public void setNombreDistrito(String nombreDistrito) {
        this.nombreDistrito = nombreDistrito;
    }

    public Provincias getProvincias() {
        return provincia;
    }

    public void setProvincias(Provincias provincia) {
        this.provincia = provincia;
    }

    public List<UsuarioCasos> getUsuarioCasos() {
        return usuarioCaso;
    }

    public void setUsuarioCasos(List<UsuarioCasos> usuarioCaso) {
        this.usuarioCaso = usuarioCaso;
    }

    public boolean getEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    @Override
    public String toString(){
        return nombreDistrito;
    }
}

