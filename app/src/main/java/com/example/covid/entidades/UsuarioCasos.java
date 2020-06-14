package com.example.covid.entidades;

import java.util.Date;

public class UsuarioCasos {
    private int id;
    private String nombre;
    private String apellidos;
    private String numeroDocumento;
    private TipoDocumento tipoDocumento;
    private int dni;
    private Date fechaNacimiento;
    private Distritos distrito;
    //private Provincias nombreProvincia;
    //private Departamentos nombreDepartamento;
    private String telefono;
    private String direccionDomicilio;
    private int codigoConfirmacion;
    private boolean condicionUso;
    private Date fechaRegistro;
    private Nacionalidad nacionalidad;
    private Gps gps;
    private TipoUsuario tipoUsuario;
    private ReporteEconomico reporteEconomico;
    private ReporteMedico reporteMedico;
    private boolean estado;


    public void prePersist() {
        this.fechaRegistro = new Date();
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public TipoDocumento getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(TipoDocumento tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Distritos getDistritos() {
        return distrito;
    }

    public void setDistritos(Distritos distrito) {
        this.distrito = distrito;
    }

    /*public Provincias getNombreProvincia() {
        return nombreProvincia;
    }

    public void setNombreProvincia(Provincias nombreProvincia) {
        this.nombreProvincia = nombreProvincia;
    }

    public Departamentos getNombreDepartamento() {
        return nombreDepartamento;
    }

    public void setNombreDepartamento(Departamentos nombreDepartamento) {
        this.nombreDepartamento = nombreDepartamento;
    }*/

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccionDomicilio() {
        return direccionDomicilio;
    }

    public void setDireccionDomicilio(String direccionDomicilio) {
        this.direccionDomicilio = direccionDomicilio;
    }

    public int getCodigoConfirmacion() {
        return codigoConfirmacion;
    }

    public void setCodigoConfirmacion(int codigoConfirmacion) {
        this.codigoConfirmacion = codigoConfirmacion;
    }

    public boolean getCondicionUso() {
        return condicionUso;
    }

    public void setCondicionUso(boolean condicionUso) {
        this.condicionUso = condicionUso;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Nacionalidad getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(Nacionalidad nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public Gps getGps() {
        return gps;
    }

    public void setGps(Gps gps) {
        this.gps = gps;
    }

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public ReporteEconomico getReporteEconomico() {
        return reporteEconomico;
    }

    public void setReporteEconomico(ReporteEconomico reporteEconomico) {
        this.reporteEconomico = reporteEconomico;
    }

    public ReporteMedico getReporteMedico() {
        return reporteMedico;
    }

    public void setReporteMedico(ReporteMedico reporteMedico) {
        this.reporteMedico = reporteMedico;
    }

    public boolean getEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
}
