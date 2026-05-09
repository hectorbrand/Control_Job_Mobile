package com.controljob.inventario;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.controljob.inventario.adapter.ProductoAdapter;
import com.controljob.inventario.inventario_modulo.ApiService;
import com.controljob.inventario.inventario_modulo.RetrofitClient;
import com.controljob.inventario.model.Producto;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProductoAdapter adapter;

    // Variables para el formulario y botones
    private EditText etNombre, etCantidad, etPrecio;
    private Button btnGuardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1. Conectar los elementos del formulario (ID's del XML)
        etNombre = findViewById(R.id.etNombre);
        etCantidad = findViewById(R.id.etCantidad);
        etPrecio = findViewById(R.id.etPrecio);
        btnGuardar = findViewById(R.id.btnGuardar);

        // 2. Conectar la lista
        recyclerView = findViewById(R.id.recyclerViewProductos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 3. Lógica para guardar el producto de forma real
        btnGuardar.setOnClickListener(v -> {
            String nombre = etNombre.getText().toString().trim();
            String cantidadStr = etCantidad.getText().toString().trim();
            String precioStr = etPrecio.getText().toString().trim();

            if (!nombre.isEmpty() && !cantidadStr.isEmpty() && !precioStr.isEmpty()) {
                try {
                    int cantidad = Integer.parseInt(cantidadStr);
                    double precio = Double.parseDouble(precioStr);

                    // Crear objeto producto
                    Producto nuevoProducto = new Producto(nombre, cantidad, precio);

                    // Enviar a la base de datos vía API
                    ApiService apiService = RetrofitClient.getApiService();
                    Call<Void> call = apiService.guardarProducto(nuevoProducto);

                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(MainActivity.this, "¡Producto guardado exitosamente!", Toast.LENGTH_SHORT).show();

                                // Limpiar el formulario
                                etNombre.setText("");
                                etCantidad.setText("");
                                etPrecio.setText("");

                                // Refrescar la lista automáticamente
                                obtenerProductos();
                            } else {
                                Toast.makeText(MainActivity.this, "Error al guardar: " + response.code(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Toast.makeText(MainActivity.this, "Error de red: " + t.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });

                } catch (NumberFormatException e) {
                    Toast.makeText(this, "Cantidad o Precio inválidos", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show();
            }
        });

        // Cargar los productos al iniciar
        obtenerProductos();
    }

    private void obtenerProductos() {
        ApiService apiService = RetrofitClient.getApiService();
        Call<List<Producto>> call = apiService.getProductos();

        call.enqueue(new Callback<List<Producto>>() {
            @Override
            public void onResponse(Call<List<Producto>> call, Response<List<Producto>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Producto> productos = response.body();
                    adapter = new ProductoAdapter(productos);
                    recyclerView.setAdapter(adapter);
                } else {
                    Toast.makeText(MainActivity.this, "Error al obtener productos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Producto>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error de conexión: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}