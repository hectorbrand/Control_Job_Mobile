package com.controljob.inventario.inventario_modulo;

import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.controljob.inventario.R;
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

        // --- BOTÓN ELIMINAR CORREGIDO ---
        holder.btnEliminar.setOnClickListener(v -> {
            // Esta es la forma correcta de obtener la posición actual
            int currentPosition = holder.getAdapterPosition();

            // Verificamos que la posición sea válida
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

                                        // Borramos usando la posición actualizada
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

        // Botón Entregar (opcional por ahora)
        holder.btnEntregar.setOnClickListener(v -> {
            Toast.makeText(v.getContext(), "Entregando: " + producto.getNombre(), Toast.LENGTH_SHORT).show();
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