package com.example.covid.entidades;

public class TipoDocumento {

private int id;
private String nombreDocumento;

    public TipoDocumento(){

    }
    public TipoDocumento(int id, String nombreDocumento) {
        this.id = id;
        this.nombreDocumento = nombreDocumento;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreDocumento() {
        return nombreDocumento;
    }

    public void setNombreDocumento(String nombreDocumento) {
        this.nombreDocumento = nombreDocumento;
    }

    @Override
    public String toString(){
        return nombreDocumento;
    }
}
