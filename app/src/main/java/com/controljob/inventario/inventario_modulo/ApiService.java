package com.controljob.inventario.inventario_modulo;

import com.controljob.inventario.model.Producto;
import com.controljob.inventario.model.Entrega;
import com.controljob.inventario.model.Usuario;
import com.controljob.inventario.model.RespuestaServer; // Importamos el traductor de éxito/error
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {

    // --- PRODUCTOS ---
    @GET("productos")
    Call<List<Producto>> getProductos();

    @POST("crear")
    Call<Void> guardarProducto(@Body Producto producto);

    @DELETE("eliminar/{id}")
    Call<Void> eliminarProducto(@Path("id") int id);

    // --- ENTREGAS ---
    @PUT("entregar/{id}")
    Call<Void> registrarEntrega(@Path("id") int id, @Body Entrega entrega);

    @GET("historial")
    Call<List<Entrega>> getHistorial();

    // --- USUARIOS (Sincronizado con tu server.js) ---

    // Ruta para el botón Azul (Ingresar)
    @POST("login")
    Call<RespuestaServer> login(@Body Usuario usuario);

    // Ruta para el botón Verde (Registrarse)
    @POST("registro")
    Call<RespuestaServer> registrarUsuario(@Body Usuario usuario);
}