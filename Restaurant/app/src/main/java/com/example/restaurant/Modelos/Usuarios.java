package com.example.restaurant.Modelos;

import java.io.Serializable;

public class Usuarios implements Serializable {

    private String id;
    private String nombre;
    private String paterno;
    private String materno;
    private String usuario;
    private String tipo;
    private String fecha;
    private String estado;
    private String contrasena;

    public Usuarios(){
        id="";
        nombre="";
        paterno="";
        materno="";
        usuario="";
        tipo="";
        fecha="";
        estado="";
        contrasena="";
    }
    public Usuarios(String id,String nombre,String paterno,String materno,String usuario,String tipo,String fecha,String estado,String contrasena){
        this.id=id;
        this.nombre=nombre;
        this.paterno=paterno;
        this.materno=materno;
        this.usuario=usuario;
        this.tipo=tipo;
        this.fecha=fecha;
        this.estado=estado;
        this.contrasena=contrasena;
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
}
