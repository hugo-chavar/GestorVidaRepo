package ar.com.fiuba.tddp1.gestorvida.objetivos;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.LinkedList;
import java.util.List;

import ar.com.fiuba.tddp1.gestorvida.MainActivity;
import ar.com.fiuba.tddp1.gestorvida.R;
import ar.com.fiuba.tddp1.gestorvida.dominio.Objetivo;

/**
 * Created by User on 21/06/2017.
 */

class ObjetivoAdapter extends RecyclerView.Adapter<ObjetivoViewHolder> {


    private List<Objetivo> listaObjetivos;
    private MainActivity activ;

    public ObjetivoAdapter(List<Objetivo> objetivos, Activity activ) {
        this.listaObjetivos = objetivos;
        this.activ = (MainActivity)activ;
    }

    @Override
    public ObjetivoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View objetivoView = LayoutInflater.from(parent.getContext()).inflate(R.layout.objetivo_layout, parent, false);

        ObjetivoViewHolder objetivoViewHolder = new ObjetivoViewHolder(objetivoView, this.activ);

        return objetivoViewHolder;
    }

    @Override
    public void onBindViewHolder(ObjetivoViewHolder holder, int position) {
        Objetivo objetivo = this.listaObjetivos.get(position);

        String nombreObjetivo = objetivo.getNombre();
        String progreso = String.format("%.2f", objetivo.getProgreso());
        String textoProgreso =  objetivo.tieneActividades() ? ("Progreso: " + progreso + "%") : "";

        holder.textViewNombreObjetivo.setText(nombreObjetivo);
        holder.textViewProgresoObjetivo.setText(textoProgreso);

        if ( objetivo.tieneActividades() ) {
            holder.barraDeProgreso.setMax(objetivo.getCantidadActividades());
            holder.barraDeProgreso.setProgress(objetivo.getCantidadActividadesCompletadas());
        }
        else {
            holder.layoutProgreso.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return this.listaObjetivos.size();
    }
}















