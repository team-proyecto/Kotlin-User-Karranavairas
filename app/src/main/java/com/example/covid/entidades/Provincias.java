package com.example.covid.entidades;

import java.util.ArrayList;
import java.util.List;

public class Provincias {

    private int id;
    private String nombreProvincia;
    private Departamentos departamento;
    private List<Distritos> distrito;
    private boolean estado;

    public Provincias(){
        this.distrito= new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreProvincia() {
        return nombreProvincia;
    }

    public void setNombreProvincia(String nombreProvincia) {
        this.nombreProvincia = nombreProvincia;
    }

    public Departamentos getDepartamentos() {
        return departamento;
    }

    public void setDepartamentos(Departamentos departamento) {
        this.departamento = departamento;
    }

    public List<Distritos> getDistritos() {
        return distrito;
    }

    public void setDistritos(List<Distritos> distrito) {
        this.distrito = distrito;
    }

    public boolean getEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    @Override
    public String toString(){
        return nombreProvincia;
    }
}
