package com.controljob.inventario.inventario_modulo;

import com.controljob.inventario.model.Producto;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {

    @GET("productos")
    Call<List<Producto>> getProductos();

    @POST("crear")
    Call<Void> guardarProducto(@Body Producto producto);

    // NUEVA RUTA PARA ELIMINAR
    @DELETE("eliminar/{id}")
    Call<Void> eliminarProducto(@Path("id") int id);
}