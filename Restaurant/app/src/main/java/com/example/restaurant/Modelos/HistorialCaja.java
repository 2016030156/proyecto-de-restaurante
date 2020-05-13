package com.example.restaurant.Modelos;

public class HistorialCaja {
    private String id;
    private String mesa;
    private String total;
    private String observaciones;
    private String mesero;
    private String fecha;
    private String hora;
    private String status;

    public HistorialCaja(){
        setId("");
        setMesa("");
        setTotal("");
        setObservaciones("");
        setMesero("");
        setFecha("");
        setHora("");
        setStatus("");
    }
    public HistorialCaja(String id, String mesa, String total, String observaciones, String mesero,String fecha,String hora,String status){
        setId(id);
        setMesa(mesa);
        setTotal(total);
        setObservaciones(observaciones);
        setMesero(mesero);
        setFecha(fecha);
        setHora(hora);
        setStatus(status);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMesa() {
        return mesa;
    }

    public void setMesa(String mesa) {
        this.mesa = mesa;
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

    public String getMesero() {
        return mesero;
    }

    public void setMesero(String mesero) {
        this.mesero = mesero;
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
}
