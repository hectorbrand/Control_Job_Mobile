package com.controljob.inventario.model;

import com.google.gson.annotations.SerializedName;

public class Entrega {

    @SerializedName("nombre_producto")
    private String nombre_producto;

    @SerializedName("cantidad")
    private int cantidadARestar;

    private String persona_recibe;
    private String area;

    // AGREGAMOS ESTE CAMPO PARA LA FECHA
    @SerializedName("fecha_entrega")
    private String fecha;

    public Entrega(String nombre_producto, int cantidadARestar, String persona_recibe, String area, String fecha) {
        this.nombre_producto = nombre_producto;
        this.cantidadARestar = cantidadARestar;
        this.persona_recibe = persona_recibe;
        this.area = area;
        this.fecha = fecha;
    }

    // Getters y Setters
    public String getNombre_producto() { return nombre_producto; }
    public void setNombre_producto(String nombre_producto) { this.nombre_producto = nombre_producto; }

    public int getCantidadARestar() { return cantidadARestar; }
    public void setCantidadARestar(int cantidadARestar) { this.cantidadARestar = cantidadARestar; }

    public String getPersona_recibe() { return persona_recibe; }
    public void setPersona_recibe(String persona_recibe) { this.persona_recibe = persona_recibe; }

    public String getArea() { return area; }
    public void setArea(String area) { this.area = area; }

    // Getter y Setter para la fecha
    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }
}