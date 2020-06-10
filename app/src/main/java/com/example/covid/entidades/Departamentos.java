package com.example.covid.entidades;

import java.util.ArrayList;
import java.util.List;

public class Departamentos {

    private int id;
    private String nombreDepartamento;
    private List<Provincias> provincia;
    private boolean estado;

    public Departamentos(){
        this.provincia = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreDepartamento() {
        return nombreDepartamento;
    }

    public void setNombreDepartamento(String nombreDepartamento) {
        this.nombreDepartamento = nombreDepartamento;
    }

    public List<Provincias> getProvincias() {
        return provincia;
    }

    public void setProvincias(List<Provincias> provincia) {
        this.provincia = provincia;
    }

    public boolean getEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    @Override
    public String toString(){
        return nombreDepartamento;
    }
}
