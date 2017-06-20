package ar.com.fiuba.tddp1.gestorvida.actividades;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import ar.com.fiuba.tddp1.gestorvida.MainActivity;
import ar.com.fiuba.tddp1.gestorvida.R;

//import org.w3c.dom.Text;

public class ActividadViewHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener {

    public TextView textViewNombre;
    public TextView textViewCompletada;
    public int position;

    private MainActivity activ;

    public ActividadViewHolder(View itemView, MainActivity activ) {
        super(itemView);
        this.activ = activ;

        // hago los find y cargo los atributos
        textViewNombre = (TextView) itemView.findViewById(R.id.txtNombre);
        textViewCompletada = (TextView) itemView.findViewById(R.id.txtCompletada);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Log.d("ActividadViewHolder", "Se hizo click en item: " + position);
        activ.onActividadClic(position);
    }
}
