package com.controljob.inventario.inventario_modulo;

import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.controljob.inventario.R;
import com.controljob.inventario.model.Entrega;
import com.controljob.inventario.model.Producto;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductoAdapter extends RecyclerView.Adapter<ProductoAdapter.ProductoViewHolder> {

    private List<Producto> listaProductos;

    public ProductoAdapter(List<Producto> listaProductos) {
        this.listaProductos = listaProductos;
    }

    @NonNull
    @Override
    public ProductoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_producto, parent, false);
        return new ProductoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductoViewHolder holder, int position) {
        Producto producto = listaProductos.get(position);

        holder.tvNombre.setText(producto.getNombre());
        holder.tvStock.setText(String.valueOf(producto.getCantidad()));

        // --- BOTÓN ELIMINAR ---
        holder.btnEliminar.setOnClickListener(v -> {
            int currentPosition = holder.getAdapterPosition();
            if (currentPosition != RecyclerView.NO_POSITION) {
                Producto productoAEliminar = listaProductos.get(currentPosition);

                new AlertDialog.Builder(v.getContext())
                        .setTitle("Eliminar")
                        .setMessage("¿Deseas eliminar " + productoAEliminar.getNombre() + "?")
                        .setPositiveButton("Sí", (dialog, which) -> {
                            ApiService apiService = RetrofitClient.getApiService();
                            apiService.eliminarProducto(productoAEliminar.getId()).enqueue(new Callback<Void>() {
                                @Override
                                public void onResponse(Call<Void> call, Response<Void> response) {
                                    if (response.isSuccessful()) {
                                        Toast.makeText(v.getContext(), "Producto eliminado", Toast.LENGTH_SHORT).show();
                                        listaProductos.remove(currentPosition);
                                        notifyItemRemoved(currentPosition);
                                        notifyItemRangeChanged(currentPosition, listaProductos.size());
                                    }
                                }
                                @Override
                                public void onFailure(Call<Void> call, Throwable t) {
                                    Toast.makeText(v.getContext(), "Error de conexión", Toast.LENGTH_SHORT).show();
                                }
                            });
                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });

        // --- BOTÓN ENTREGAR (AMARILLO) ---
        holder.btnEntregar.setOnClickListener(v -> {
            View dialogView = LayoutInflater.from(v.getContext()).inflate(R.layout.dialogo_entrega, null);
            EditText etCant = dialogView.findViewById(R.id.etCantidadEntrega);
            EditText etPers = dialogView.findViewById(R.id.etPersonaRecibe);
            EditText etArea = dialogView.findViewById(R.id.etAreaDestino);
            TextView tvTitu = dialogView.findViewById(R.id.tvTituloDialogo);

            tvTitu.setText("Entregar: " + producto.getNombre() + " (Disponible: " + producto.getCantidad() + ")");

            new AlertDialog.Builder(v.getContext())
                    .setView(dialogView)
                    .setPositiveButton("Confirmar Entrega", (dialog, which) -> {
                        String cantStr = etCant.getText().toString().trim();
                        String persona = etPers.getText().toString().trim();
                        String area = etArea.getText().toString().trim();

                        if (!cantStr.isEmpty() && !persona.isEmpty() && !area.isEmpty()) {
                            int cantidadAEntregar = Integer.parseInt(cantStr);

                            if (cantidadAEntregar <= producto.getCantidad()) {

                                // CORRECCIÓN: Ahora pasamos 5 parámetros (el último es la fecha como "" vacío)
                                Entrega nuevaEntrega = new Entrega(
                                        producto.getNombre(),
                                        cantidadAEntregar,
                                        persona,
                                        area,
                                        "" // Fecha vacía: el servidor la asignará
                                );

                                ApiService apiService = RetrofitClient.getApiService();
                                apiService.registrarEntrega(producto.getId(), nuevaEntrega).enqueue(new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call, Response<Void> response) {
                                        if (response.isSuccessful()) {
                                            Toast.makeText(v.getContext(), "✅ Entrega registrada: " + cantidadAEntregar + " a " + persona, Toast.LENGTH_LONG).show();

                                            producto.setCantidad(producto.getCantidad() - cantidadAEntregar);
                                            notifyItemChanged(holder.getAdapterPosition());
                                        } else {
                                            Toast.makeText(v.getContext(), "Error en el servidor: " + response.code(), Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Void> call, Throwable t) {
                                        Toast.makeText(v.getContext(), "Error de red: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } else {
                                Toast.makeText(v.getContext(), "⚠️ Stock insuficiente", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(v.getContext(), "Completa todos los campos", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("Cancelar", null)
                    .show();
        });
    }

    @Override
    public int getItemCount() {
        return listaProductos.size();
    }

    public static class ProductoViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombre, tvStock;
        Button btnEntregar, btnEliminar;

        public ProductoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tvNombre);
            tvStock = itemView.findViewById(R.id.tvStock);
            btnEntregar = itemView.findViewById(R.id.btnEntregar);
            btnEliminar = itemView.findViewById(R.id.btnEliminar);
        }
    }
}