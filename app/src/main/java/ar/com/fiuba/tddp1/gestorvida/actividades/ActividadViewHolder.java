package ar.com.fiuba.tddp1.gestorvida.actividades;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import ar.com.fiuba.tddp1.gestorvida.MainActivity;
import ar.com.fiuba.tddp1.gestorvida.R;
import ar.com.fiuba.tddp1.gestorvida.dominio.Actividad;

//import org.w3c.dom.Text;

public class ActividadViewHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener {

    public TextView textViewNombre;
    public TextView textViewCompletada;
    public ImageView imageViewCompletada;
    public int position;

    public Actividad actividad;
    public ActividadAdapter adapter;

    private MainActivity activ;

    public ActividadViewHolder(View itemView, MainActivity activ) {
        super(itemView);
        this.activ = activ;

        // hago los find y cargo los atributos
        textViewNombre = (TextView) itemView.findViewById(R.id.txtNombre);
        textViewCompletada = (TextView) itemView.findViewById(R.id.txtCompletada);
        imageViewCompletada = (ImageView) itemView.findViewById(R.id.imageViewCompletarActividad);

        //itemView.setOnClickListener(this);


        itemView.findViewById(R.id.imageViewCompletarActividad).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        /*
        Log.d("ActividadViewHolder", "Se hizo click en item: " + position);
        activ.onActividadClic(position);
        */
        this.actividad.alternarEstadoCompleta();
        this.adapter.notifyDataSetChanged();

        this.mostrarImagenCompletada();
    }

    public void asociarActividad(Actividad actividad) {
        this.actividad = actividad;
        this.mostrarImagenCompletada();
    }

    private void mostrarImagenCompletada() {
        if (this.actividad.estaCompleta()) {
            this.imageViewCompletada.setImageResource(R.drawable.completar_actividad);
        }
        else {
            this.imageViewCompletada.setImageResource(R.drawable.deshacer_actividad);
        }
    }
}
