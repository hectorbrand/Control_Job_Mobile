package com.controljob.inventario.inventario_modulo;

import com.controljob.inventario.model.Producto;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {

    @GET("productos")
    Call<List<Producto>> getProductos();

    // Cambiamos "productos" por "crear" para que coincida con tu server.js
    @POST("crear")
    Call<Void> guardarProducto(@Body Producto producto);
}