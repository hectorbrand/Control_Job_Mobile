package com.controljob.inventario.model;

public class Entrega {
    private String nombre_producto;
    private int cantidadARestar; // Nombre exacto que pide tu server.js
    private String persona_recibe;
    private String area; // Nombre exacto que pide tu server.js

    public Entrega(String nombre_producto, int cantidadARestar, String persona_recibe, String area) {
        this.nombre_producto = nombre_producto;
        this.cantidadARestar = cantidadARestar;
        this.persona_recibe = persona_recibe;
        this.area = area;
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
}