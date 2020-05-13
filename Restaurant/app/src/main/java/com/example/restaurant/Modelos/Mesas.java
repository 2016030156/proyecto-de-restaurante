package com.example.restaurant.Modelos;

import java.io.Serializable;

public class Mesas  implements Serializable {
    private String id;
    private String tipo;
    private String status;

    public Mesas(){
        id="";
        tipo="";
        status="";
    }

    public Mesas(String id,String tipo,String status){
        this.id=id;
        this.tipo=tipo;
        this.status=status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
