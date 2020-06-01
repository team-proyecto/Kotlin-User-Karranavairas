package com.example.covid.entidades;

public class Departamentos {

    private int id;
    private String nombreDepartamento;

    public Departamentos(){

    }

    public Departamentos(int id, String nombreDepartamento) {
        this.id = id;
        this.nombreDepartamento = nombreDepartamento;
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
    @Override
    public String toString(){
        return nombreDepartamento;
    }
}
