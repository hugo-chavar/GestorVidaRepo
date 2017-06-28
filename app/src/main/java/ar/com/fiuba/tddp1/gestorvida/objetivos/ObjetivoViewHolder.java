package ar.com.fiuba.tddp1.gestorvida.objetivos;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import ar.com.fiuba.tddp1.gestorvida.MainActivity;
import ar.com.fiuba.tddp1.gestorvida.R;

/**
 * Created by User on 21/06/2017.
 */

class ObjetivoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView textViewNombreObjetivo;
    public TextView textViewProgresoObjetivo;
    public ProgressBar barraDeProgreso;
    public LinearLayout layoutProgreso;

    private MainActivity activ;

    public ObjetivoViewHolder(View objetivoView, MainActivity activ) {
        super(objetivoView);
        this.activ = activ;

        this.textViewNombreObjetivo = (TextView) objetivoView.findViewById(R.id.txtNombreObjetivo);
        this.textViewProgresoObjetivo = (TextView) objetivoView.findViewById(R.id.txtProgresoObjetivo);
        this.barraDeProgreso = (ProgressBar) objetivoView.findViewById(R.id.progressBarObjetivo);
        this.layoutProgreso = (LinearLayout) objetivoView.findViewById(R.id.linearLayoutProgreso);

        objetivoView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        //No se hace nada por ahora
    }
}
