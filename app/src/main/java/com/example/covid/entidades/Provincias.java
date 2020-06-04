package com.example.covid.entidades;

public class Provincias {

    private int id;
    private String nombreProvincia;

    public Provincias(){

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

    @Override
    public String toString(){
        return nombreProvincia;
    }
}
