package com.controljob.inventario.model;

public class Usuario {
    // Estos nombres son los que pide tu server.js para el Login
    private String usuarioIngresado;
    private String claveIngresada;

    // Estos son los que pide tu server.js para el Registro
    private String nuevoUsuario;
    private String nuevaClave;
    private String rol;

    // Constructor para el Inicio de Sesión (Login)
    public Usuario(String usuarioIngresado, String claveIngresada) {
        this.usuarioIngresado = usuarioIngresado;
        this.claveIngresada = claveIngresada;
    }

    // Constructor para el Registro de nuevos usuarios
    public Usuario(String nuevoUsuario, String nuevaClave, String rol) {
        this.nuevoUsuario = nuevoUsuario;
        this.nuevaClave = nuevaClave;
        this.rol = "usuario"; // Le asignamos el rol por defecto
    }

    // Getters (necesarios para que Retrofit lea los datos)
    public String getUsuarioIngresado() { return usuarioIngresado; }
    public String getClaveIngresada() { return claveIngresada; }
    public String getNuevoUsuario() { return nuevoUsuario; }
    public String getNuevaClave() { return nuevaClave; }
    public String getRol() { return rol; }
}