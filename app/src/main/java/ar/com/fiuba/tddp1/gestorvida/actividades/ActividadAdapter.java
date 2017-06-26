package ar.com.fiuba.tddp1.gestorvida.actividades;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ar.com.fiuba.tddp1.gestorvida.MainActivity;
import ar.com.fiuba.tddp1.gestorvida.R;
import ar.com.fiuba.tddp1.gestorvida.dominio.Actividad;


public class ActividadAdapter extends RecyclerView.Adapter<ActividadViewHolder>{

    private List<Actividad> lista;
    private MainActivity activ;

    public ActividadAdapter(List<Actividad> lista, Activity activ)
    {
        this.lista = lista;
        this.activ = (MainActivity)activ;
    }

    @Override
    public ActividadViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);

        ActividadViewHolder vh = new ActividadViewHolder(v,activ);

        return vh;
    }

    @Override
    public void onBindViewHolder(ActividadViewHolder holder, int position) {

        Actividad actividad = this.lista.get(position);
        String nombre = actividad.getNombre();
        String completada = "Completa: " + (actividad.estaCompleta() ? "SI" : "NO");

        TextView txtNombre = holder.textViewNombre;
        txtNombre.setText(nombre);

        TextView txtCompletada = holder.textViewCompletada;
        txtCompletada.setText(completada);

        // me guardo la posicion de los datos que cargue en este VH
        holder.position = position;

        //Guardo la actividad en el holder
        holder.asociarActividad(actividad);
        holder.adapter = this;
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }
}