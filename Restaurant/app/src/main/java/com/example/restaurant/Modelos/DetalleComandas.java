package com.example.restaurant.Modelos;

public class DetalleComandas {
    private String idComanda;
    private String idPlatillo;
    private String cantidad;
    private String precio;
    private String status;
    private String platillo;

    public DetalleComandas() {
        this.setIdComanda("");
        this.setIdPlatillo("");
        this.setCantidad("");
        this.setPrecio("");
        this.setStatus("");
        this.setPlatillo("");
    }
    public DetalleComandas(String idComanda, String idPlatillo, String cantidad, String precio, String status, String platillo) {
        this.setIdComanda(idComanda);
        this.setIdPlatillo(idPlatillo);
        this.setCantidad(cantidad);
        this.setPrecio(precio);
        this.setStatus(status);
        this.setPlatillo(platillo);
    }

    public String getIdComanda() {
        return idComanda;
    }

    public void setIdComanda(String idComanda) {
        this.idComanda = idComanda;
    }

    public String getIdPlatillo() {
        return idPlatillo;
    }

    public void setIdPlatillo(String idPlatillo) {
        this.idPlatillo = idPlatillo;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPlatillo() {
        return platillo;
    }

    public void setPlatillo(String platillo) {
        this.platillo = platillo;
    }
}
