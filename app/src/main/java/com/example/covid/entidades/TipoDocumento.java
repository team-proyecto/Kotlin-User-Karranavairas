package com.example.covid.entidades;

public class TipoDocumento {

private int id;
private String nombreDocumento;
private boolean estado;

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

    public boolean getEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    @Override
    public String toString(){
        return nombreDocumento;
    }
}
