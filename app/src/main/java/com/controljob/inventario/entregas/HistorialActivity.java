package com.controljob.inventario.entregas;

import android.os.Bundle;
import android.widget.Button; // Necesario para el botón negro
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.controljob.inventario.R;
import com.controljob.inventario.adapter.HistorialAdapter; // Importación corregida a la carpeta adapter
import com.controljob.inventario.inventario_modulo.ApiService;
import com.controljob.inventario.inventario_modulo.RetrofitClient;
import com.controljob.inventario.model.Entrega;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistorialActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private HistorialAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial);

        // 1. Configurar el botón negro "Volver al Inventario"
        Button btnVolver = findViewById(R.id.btnVolverInventario);
        btnVolver.setOnClickListener(v -> {
            finish(); // Cierra esta actividad y regresa a MainActivity
        });

        // 2. Configurar el RecyclerView para la lista
        recyclerView = findViewById(R.id.rvHistorial);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 3. Cargar los datos desde MySQL
        obtenerHistorial();
    }

    private void obtenerHistorial() {
        ApiService apiService = RetrofitClient.getApiService();
        apiService.getHistorial().enqueue(new Callback<List<Entrega>>() {
            @Override
            public void onResponse(Call<List<Entrega>> call, Response<List<Entrega>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    adapter = new HistorialAdapter(response.body());
                    recyclerView.setAdapter(adapter);
                } else {
                    Toast.makeText(HistorialActivity.this, "Error al cargar movimientos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Entrega>> call, Throwable t) {
                Toast.makeText(HistorialActivity.this, "Error de conexión con el servidor", Toast.LENGTH_SHORT).show();
            }
        });
    }
}