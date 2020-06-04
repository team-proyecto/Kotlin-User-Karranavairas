package com.example.covid.entidades;

public class Clientes {
    private int idCliente;
    private String nombre;
    private String apellidos;
    private String nacionalidad;
    private TipoDocumento nombreDocumento;
    private int dni;
    private Distritos nombreDistrito;
    private Provincias nombreProvincia;
    private Departamentos nombreDepartamento;
    private int telefono;
    private String direccion;


    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
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

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public TipoDocumento getNombreDocumento() {
        return nombreDocumento;
    }

    public void setNombreDocumento(TipoDocumento nombreDocumento) {
        this.nombreDocumento = nombreDocumento;
    }

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public Distritos getNombreDistrito() {
        return nombreDistrito;
    }

    public void setNombreDistrito(Distritos nombreDistrito) {
        this.nombreDistrito = nombreDistrito;
    }

    public Provincias getNombreProvincia() {
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
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}
