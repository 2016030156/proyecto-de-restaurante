package com.example.restaurant.Modelos;

import java.io.Serializable;

public class Platillos implements Serializable {
    private String id;
    private String nombre;
    private float precio;
    private String fecha;
    private String status;

    public Platillos(){
        id="";
        nombre="";
        precio=0.0f;
        fecha="";
        status="";
    }
    public Platillos(String id,String nombre,float precio,String fecha,String status){
        this.id=id;
        this.nombre=nombre;
        this.precio=precio;
        this.fecha=fecha;
        this.status=status;
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

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
