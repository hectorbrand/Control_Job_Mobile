package com.controljob.inventario.inventario_modulo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.controljob.inventario.model.Producto;
import java.util.List;

// Este es el "motor" que llena la lista de productos
public class ProductoAdapter extends RecyclerView.Adapter<ProductoAdapter.ProductoViewHolder> {

    private List<Producto> listaProductos;

    public ProductoAdapter(List<Producto> listaProductos) {
        this.listaProductos = listaProductos;
    }

    @NonNull
    @Override
    public ProductoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Usamos un diseño predefinido de Android para ir más rápido
        View view = LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_2, parent, false);
        return new ProductoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductoViewHolder holder, int position) {
        Producto producto = listaProductos.get(position);
        // Ponemos el nombre del producto arriba y el stock abajo
        holder.textNombre.setText(producto.getNombre());
        holder.textCantidad.setText("Stock actual: " + producto.getCantidad());
    }

    @Override
    public int getItemCount() {
        return listaProductos.size();
    }

    public static class ProductoViewHolder extends RecyclerView.ViewHolder {
        TextView textNombre, textCantidad;

        public ProductoViewHolder(@NonNull View itemView) {
            super(itemView);
            textNombre = itemView.findViewById(android.R.id.text1);
            textCantidad = itemView.findViewById(android.R.id.text2);
        }
    }
}