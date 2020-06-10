package com.example.covid.entidades;

import java.util.Date;

public class Gps {

        private int id;
        private int latitud;
        private int longitud;
        private String direccionGps;
        private boolean estado;
        private Date fechaRegistro;
        private UsuarioCasos usuarioCaso;

    public void prePersist() {
        this.fechaRegistro = new Date();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLatitud() {
        return latitud;
    }

    public void setLatitud(int latitud) {
        this.latitud = latitud;
    }

    public int getLongitud() {
        return longitud;
    }

    public void setLongitud(int longitud) {
        this.longitud = longitud;
    }

    public String getDireccionGps() {
        return direccionGps;
    }

    public void setDireccionGps(String direccionGps) {
        this.direccionGps = direccionGps;
    }

    public boolean getEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public UsuarioCasos getUsuarioCasos() {
        return usuarioCaso;
    }

    public void setUsuarioCasos(UsuarioCasos usuarioCaso) {
        this.usuarioCaso = usuarioCaso;
    }
}
