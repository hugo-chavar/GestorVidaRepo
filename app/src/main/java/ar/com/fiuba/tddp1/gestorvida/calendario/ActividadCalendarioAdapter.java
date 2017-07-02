package ar.com.fiuba.tddp1.gestorvida.calendario;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ar.com.fiuba.tddp1.gestorvida.MainActivity;
import ar.com.fiuba.tddp1.gestorvida.R;
import ar.com.fiuba.tddp1.gestorvida.actividades.ActividadViewHolder;
import ar.com.fiuba.tddp1.gestorvida.dominio.Actividad;
import ar.com.fiuba.tddp1.gestorvida.dominio.Etiqueta;

/**
 * Created by User on 28/06/2017.
 */

public class ActividadCalendarioAdapter extends RecyclerView.Adapter<ActividadCalendarioViewHolder>{

    private List<Actividad> lista;
    private MainActivity activ;

    public ActividadCalendarioAdapter(List<Actividad> lista, FragmentActivity activity) {
        this.lista = lista;
        this.activ = (MainActivity)activity;
    }


    @Override
    public ActividadCalendarioViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View actividadCalendarioView = LayoutInflater.from(parent.getContext()).inflate(R.layout.actividad_calendario_layout, parent, false);

        ActividadCalendarioViewHolder vh = new ActividadCalendarioViewHolder(actividadCalendarioView,activ);

        actividadCalendarioView.setOnClickListener(vh);

        return vh;
    }


    @Override
    public void onBindViewHolder(ActividadCalendarioViewHolder holder, int position) {

        Actividad actividad = this.lista.get(position);
        String nombre = actividad.getNombre();

        TextView txtNombre = holder.textViewNombre;
        txtNombre.setText(nombre);

        // me guardo la posicion de los datos que cargue en este VH
        holder.position = position;

        //Guardo la actividad en el holder
        holder.asociarActividad(actividad);
        holder.adapter = this;

        this.cargarEtiquetasAlHolder(holder, actividad);
    }

    private void cargarEtiquetasAlHolder(ActividadCalendarioViewHolder holder, Actividad actividad) {
        for (Etiqueta etiqueta : actividad.getEtiquetas()) {
            TextView textViewEtiqueta = new TextView(this.activ);
            textViewEtiqueta.setText(etiqueta.nombre);
            GradientDrawable fondoEtiqueta = (GradientDrawable) this.activ.getDrawable(R.drawable.etiqueta_background);
            fondoEtiqueta.setColor(etiqueta.color);
            textViewEtiqueta.setBackground(fondoEtiqueta);
            holder.layoutEtiquetas.addView(textViewEtiqueta);
        }
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }


}
