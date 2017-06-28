package ar.com.fiuba.tddp1.gestorvida.calendario;

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

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.actividad_calendario_layout, parent, false);

        ActividadCalendarioViewHolder vh = new ActividadCalendarioViewHolder(v,activ);

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
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }


}
