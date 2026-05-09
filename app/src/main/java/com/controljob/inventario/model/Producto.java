package com.controljob.inventario.model;

public class Producto {
    // 1. Definimos las variables (columnas de tu DB)
    private int id;
    private String nombre;
    private int cantidad;
    private double precio;

    // 2. El Constructor original (Para cuando traes datos de la DB con ID)
    public Producto(int id, String nombre, int cantidad, double precio) {
        this.id = id;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.precio = precio;
    }

    // 3. NUEVO Constructor (Para GUARDAR productos nuevos sin ID)
    public Producto(String nombre, int cantidad, double precio) {
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.precio = precio;
    }

    // 4. Los Getters (Para leer los datos)
    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public int getCantidad() { return cantidad; }
    public double getPrecio() { return precio; }

    // 5. Los Setters (Por si necesitas cambiar algún dato luego)
    public void setId(int id) { this.id = id; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }
    public void setPrecio(double precio) { this.precio = precio; }
}