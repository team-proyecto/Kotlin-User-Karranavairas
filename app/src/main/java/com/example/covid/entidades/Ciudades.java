package com.example.covid.entidades;

public class Ciudades {

    private int id;
    private String nombreCiudad;

    public Ciudades(){

    }

    public Ciudades(int id, String nombreCiudad) {
        this.id = id;
        this.nombreCiudad = nombreCiudad;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreCiudad() {
        return nombreCiudad;
    }

    public void setNombreCiudad(String nombreCiudad) {
        this.nombreCiudad = nombreCiudad;
    }

    @Override
    public String toString(){
        return nombreCiudad;
    }
}
