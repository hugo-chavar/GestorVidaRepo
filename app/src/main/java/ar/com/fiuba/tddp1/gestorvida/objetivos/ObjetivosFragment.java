package ar.com.fiuba.tddp1.gestorvida.objetivos;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import ar.com.fiuba.tddp1.gestorvida.actividades.RecyclerFragment;
import ar.com.fiuba.tddp1.gestorvida.dominio.Actividad;
import ar.com.fiuba.tddp1.gestorvida.dominio.Objetivo;
import ar.com.fiuba.tddp1.gestorvida.dominio.Perfil;

/**
 * Created by User on 21/06/2017.
 */

public class ObjetivosFragment extends RecyclerFragment {

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(getActivity());
    }

    @Override
    protected void configureAdapter() {
        ObjetivoAdapter objetivoAdapter = new ObjetivoAdapter(Perfil.getObjetivos(), getActivity());
        this.setConfiguredAdapter(objetivoAdapter);
    }

    @Override
    protected void goToAgregarElemento() {

    }
}
