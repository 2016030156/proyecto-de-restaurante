package com.example.restaurant.Modelos;

public class Usuario {
    private String id;
    private String nombre;
    private String paterno;
    private String materno;
    private String usuario;
    private String contra;
    private String idPerfil;
    private String fechaCreacion;
    private String status;

    public Usuario() {
        this.id = "";
        this.nombre = "";
        this.paterno = "";
        this.materno = "";
        this.usuario = "";
        this.contra = "";
        this.idPerfil = "";
        this.fechaCreacion = "";
        this.status = "";
    }

    public Usuario(String id, String nombre, String apellidoPaterno, String apellidoMaterno, String nombreDeUsuario, String contra, String idPerfil, String fechaCreacion, String status) {
        this.id = id;
        this.nombre = nombre;
        this.paterno = apellidoPaterno;
        this.materno = apellidoMaterno;
        this.usuario = nombreDeUsuario;
        this.contra = contra;
        this.idPerfil = idPerfil;
        this.fechaCreacion = fechaCreacion;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPaterno() {
        return paterno;
    }

    public void setPaterno(String paterno) {
        this.paterno = paterno;
    }

    public String getMaterno() {
        return materno;
    }

    public void setMaterno(String materno) {
        this.materno = materno;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContra() {
        return contra;
    }

    public void setContra(String contra) {
        this.contra = contra;
    }

    public String getIdPerfil() {
        return idPerfil;
    }

    public void setIdPerfil(String idPerfil) {
        this.idPerfil = idPerfil;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
