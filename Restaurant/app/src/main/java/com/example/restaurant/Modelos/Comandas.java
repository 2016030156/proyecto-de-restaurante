package com.example.restaurant.Modelos;

public class Comandas {
    private String id;
    private String idMesero;
    private String idMesa;
    private String total;
    private String observaciones;
    private String fecha;
    private String hora;
    private String status;
    private String mesero;
    private String mesa;

    public Comandas(){
        this.id = "";
        this.idMesero = "";
        this.idMesa = "";
        this.total = "";
        this.observaciones = "";
        this.fecha = "";
        this.hora = "";
        this.status = "";
        this.mesero = "";
        this.mesa = "";
    }

    public Comandas(String id, String idMesero, String idMesa, String total, String observaciones, String fecha, String hora, String status, String mesero, String mesa) {
        this.id = id;
        this.idMesero = idMesero;
        this.idMesa = idMesa;
        this.total = total;
        this.observaciones = observaciones;
        this.fecha = fecha;
        this.hora = hora;
        this.status = status;
        this.mesero = mesero;
        this.mesa = mesa;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdMesero() {
        return idMesero;
    }

    public void setIdMesero(String idMesero) {
        this.idMesero = idMesero;
    }

    public String getIdMesa() {
        return idMesa;
    }

    public void setIdMesa(String idMesa) {
        this.idMesa = idMesa;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMesero() {
        return mesero;
    }

    public void setMesero(String mesero) {
        this.mesero = mesero;
    }

    public String getMesa() {
        return mesa;
    }

    public void setMesa(String mesa) {
        this.mesa = mesa;
    }
}
