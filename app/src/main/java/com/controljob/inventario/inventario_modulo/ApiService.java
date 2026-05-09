package com.controljob.inventario.inventario_modulo;

import com.controljob.inventario.model.Producto;
import com.controljob.inventario.model.Entrega;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {

    @GET("productos")
    Call<List<Producto>> getProductos();

    @POST("crear")
    Call<Void> guardarProducto(@Body Producto producto);

    // RUTA PARA ELIMINAR
    @DELETE("eliminar/{id}")
    Call<Void> eliminarProducto(@Path("id") int id);

    // RUTA PARA REGISTRAR LA ENTREGA (Sincronizada con tu server.js)
    @PUT("entregar/{id}")
    Call<Void> registrarEntrega(@Path("id") int id, @Body Entrega entrega);
}