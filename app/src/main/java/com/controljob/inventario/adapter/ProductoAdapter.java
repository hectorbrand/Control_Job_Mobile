package com.controljob.inventario.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.controljob.inventario.R;
import com.controljob.inventario.model.Producto;
import java.util.List;

public class ProductoAdapter extends RecyclerView.Adapter<ProductoAdapter.ProductoViewHolder> {

    private List<Producto> listaProductos;

    public ProductoAdapter(List<Producto> listaProductos) {
        this.listaProductos = listaProductos;
    }

    @NonNull
    @Override
    public ProductoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_producto, parent, false);
        return new ProductoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductoViewHolder holder, int position) {
        Producto producto = listaProductos.get(position);
        holder.tvNombre.setText(producto.getNombre());
        holder.tvStock.setText(String.valueOf(producto.getCantidad()));

        // Aquí luego programaremos qué pasa al dar clic
        holder.btnEntregar.setOnClickListener(v -> {
            // Acción para entregar
        });

        holder.btnEliminar.setOnClickListener(v -> {
            // Acción para eliminar
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