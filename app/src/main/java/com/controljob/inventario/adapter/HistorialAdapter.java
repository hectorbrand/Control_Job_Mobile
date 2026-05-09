package com.controljob.inventario.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.controljob.inventario.R;
import com.controljob.inventario.model.Entrega;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class HistorialAdapter extends RecyclerView.Adapter<HistorialAdapter.ViewHolder> {

    private List<Entrega> listaEntregas;

    public HistorialAdapter(List<Entrega> listaEntregas) {
        this.listaEntregas = listaEntregas;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_entrega, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Entrega entrega = listaEntregas.get(position);

        holder.tvProducto.setText(entrega.getNombre_producto());
        holder.tvCantidad.setText(String.valueOf(entrega.getCantidadARestar()));
        holder.tvPersona.setText(entrega.getPersona_recibe());
        holder.tvArea.setText(entrega.getArea());

        // --- FORMATEO DE FECHA ISO 8601 ---
        String fechaServidor = entrega.getFecha();

        try {
            // Este formato lee exactamente: 2026-05-09T18:30:16.000Z
            SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
            parser.setTimeZone(TimeZone.getTimeZone("UTC")); // Importante para la hora correcta

            // Este formato escribe: 9/5/2026, 1:30 pm
            // Usamos 'd/M/yyyy' para que si es día 9 no salga 09
            SimpleDateFormat formatter = new SimpleDateFormat("d/M/yyyy, h:mm a", Locale.getDefault());

            Date date = parser.parse(fechaServidor);
            String fechaFormateada = formatter.format(date);

            holder.tvFecha.setText(fechaFormateada);

        } catch (Exception e) {
            // Si algo falla, limpiamos la "T" y la "Z" manualmente para que al menos sea legible
            if (fechaServidor != null) {
                String limpia = fechaServidor.replace("T", " ").substring(0, 19);
                holder.tvFecha.setText(limpia);
            } else {
                holder.tvFecha.setText("Sin fecha");
            }
        }
    }

    @Override
    public int getItemCount() {
        return listaEntregas.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvProducto, tvCantidad, tvPersona, tvArea, tvFecha;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvProducto = itemView.findViewById(R.id.tvProductoHistorial);
            tvCantidad = itemView.findViewById(R.id.tvCantidadHistorial);
            tvPersona = itemView.findViewById(R.id.tvPersonaHistorial);
            tvArea = itemView.findViewById(R.id.tvAreaHistorial);
            tvFecha = itemView.findViewById(R.id.tvFechaHistorial);
        }
    }
}