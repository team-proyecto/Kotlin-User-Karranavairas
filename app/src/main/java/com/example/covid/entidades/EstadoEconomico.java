package com.example.covid.entidades;

public class EstadoEconomico {
        private int id;
        private String nombreEconomico;
        private boolean estado;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreEconomico() {
        return nombreEconomico;
    }

    public void setNombreEconomico(String nombreEconomico) {
        this.nombreEconomico = nombreEconomico;
    }

    public boolean getEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
}
