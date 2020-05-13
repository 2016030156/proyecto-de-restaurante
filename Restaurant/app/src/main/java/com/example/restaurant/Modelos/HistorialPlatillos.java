package com.example.restaurant.Modelos;

public class HistorialPlatillos {
    private String id;
    private String nombre;
    private float precio;
    private int cantidad;
    private float subtotal;

    public HistorialPlatillos(){
        setId("");
        setNombre("");
        setPrecio(0.0f);
        setCantidad(0);
        setSubtotal(0.0f);
    }
    public HistorialPlatillos(String id, String nombre, float precio, int cantidad, float subtotal){
        this.setId(id);
        this.setNombre(nombre);
        this.setPrecio(precio);
        this.setCantidad(cantidad);
        this.setSubtotal(subtotal);
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

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public float getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(float subtotal) {
        this.subtotal = subtotal;
    }
}
