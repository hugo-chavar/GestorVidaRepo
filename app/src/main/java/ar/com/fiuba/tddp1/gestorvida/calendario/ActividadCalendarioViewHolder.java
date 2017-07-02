package ar.com.fiuba.tddp1.gestorvida.calendario;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import ar.com.fiuba.tddp1.gestorvida.MainActivity;
import ar.com.fiuba.tddp1.gestorvida.R;
import ar.com.fiuba.tddp1.gestorvida.actividades.DetalleActividadFragment;
import ar.com.fiuba.tddp1.gestorvida.dominio.Actividad;

/**
 * Created by User on 28/06/2017.
 */

class ActividadCalendarioViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView textViewNombre;
    public LinearLayout layoutEtiquetas;
    public int position;

    public Actividad actividad;
    public ActividadCalendarioAdapter adapter;

    private MainActivity mainActivity;

    public ActividadCalendarioViewHolder(View itemView, MainActivity mainActivity) {
        super(itemView);
        this.mainActivity = mainActivity;

        // hago los find y cargo los atributos
        textViewNombre = (TextView) itemView.findViewById(R.id.textActividadCalendario);
        layoutEtiquetas = (LinearLayout) itemView.findViewById(R.id.layoutEtiquetasActividadCalendario);

    }

    @Override
    public void onClick(View view) {
        this.mainActivity.setActividad_detalle(this.actividad);
        this.mainActivity.setFragment(new DetalleActividadFragment());
    }

    public void asociarActividad(Actividad actividad) {
        this.actividad = actividad;
    }


}

