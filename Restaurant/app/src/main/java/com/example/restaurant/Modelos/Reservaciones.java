package com.example.restaurant.Modelos;

public class Reservaciones {
    private String id;
    private String noCliente;
    private String cliente;
    private String noMesa;
    private String mesa;
    private String fecha;
    private String hora;
    private String inicio;
    private String fin;
    private String status;

    public Reservaciones() {
        this.id = "";
        this.cliente = "";
        this.noMesa = "";
        this.mesa = "";
        this.fecha = "";
        this.hora = "";
        this.inicio = "";
        this.fin = "";
        this.status = "";
    }

    public Reservaciones(String id, String noCliente, String cliente, String noMesa, String mesa, String fecha, String hora, String inicio, String fin, String status) {
        this.id = id;
        this.noCliente = noCliente;
        this.cliente = cliente;
        this.noMesa = noMesa;
        this.mesa = mesa;
        this.fecha = fecha;
        this.hora = hora;
        this.inicio = inicio;
        this.fin = fin;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getNoCliente() {
        return noCliente;
    }

    public void setNoCliente(String noCliente) {
        this.noCliente = noCliente;
    }

    public String getNoMesa() {
        return noMesa;
    }

    public void setNoMesa(String noMesa) {
        this.noMesa = noMesa;
    }

    public String getMesa() {
        return mesa;
    }

    public void setMesa(String mesa) {
        this.mesa = mesa;
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

    public void setHora(String fecha) {
        this.hora = hora;
    }

    public String getInicio() {
        return inicio;
    }

    public void setInicio(String inicio) {
        this.inicio = inicio;
    }

    public String getFin() {
        return fin;
    }

    public void setFin(String fin) {
        this.fin = fin;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
