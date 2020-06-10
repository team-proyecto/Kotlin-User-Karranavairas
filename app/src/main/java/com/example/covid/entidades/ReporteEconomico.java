package com.example.covid.entidades;

import java.util.Date;

public class ReporteEconomico {

    private int id;
    private boolean bonoAsignado;
    private double montoServicio;
    private String boletaImagen;
    private EstadoEconomico estadoEconomico;
    private boolean estado;
    private UsuarioCasos usuarioCaso;

    private Date fechaRegistro;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isBonoAsignado() {
        return bonoAsignado;
    }

    public void setBonoAsignado(boolean bonoAsignado) {
        this.bonoAsignado = bonoAsignado;
    }

    public double getMontoServicio() {
        return montoServicio;
    }

    public void setMontoServicio(double montoServicio) {
        this.montoServicio = montoServicio;
    }

    public String getBoletaImagen() {
        return boletaImagen;
    }

    public void setBoletaImagen(String boletaImagen) {
        this.boletaImagen = boletaImagen;
    }

    public EstadoEconomico getEstadoEconomico() {
        return estadoEconomico;
    }

    public void setEstadoEconomico(EstadoEconomico estadoEconomico) {
        this.estadoEconomico = estadoEconomico;
    }

    public boolean getEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public UsuarioCasos getUsuarioCasos() {
        return usuarioCaso;
    }

    public void setUsuarioCasos(UsuarioCasos usuarioCaso) {
        this.usuarioCaso = usuarioCaso;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
}
