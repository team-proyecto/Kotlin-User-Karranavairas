package com.example.covid.entidades;

public class Distritos {
    private  int id;
    private String nombreDistrito;

    public Distritos(){

    }

    public Distritos(int id, String nombreDistrito) {
        this.id = id;
        this.nombreDistrito = nombreDistrito;
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

    @Override
    public String toString(){
        return nombreDistrito;
    }
}

