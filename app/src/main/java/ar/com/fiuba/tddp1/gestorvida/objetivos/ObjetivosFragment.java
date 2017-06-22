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


        //POR AHORA CARGO LOS OBJETIVOS ACA
        ArrayList<Objetivo> listaDeObjetivos = new ArrayList<>();

        listaDeObjetivos.add(new Objetivo("Obj1"));

        Objetivo objetivoConActividades = new Objetivo("Obj2");
        objetivoConActividades.agregarActividad(new Actividad("Actividad1"));

        Actividad actividadCompletada = new Actividad("Actividad2");
        actividadCompletada.completar();
        objetivoConActividades.agregarActividad(actividadCompletada);

        actividadCompletada = new Actividad("Actividad3");
        actividadCompletada.completar();
        objetivoConActividades.agregarActividad(actividadCompletada);

        objetivoConActividades.agregarActividad(new Actividad("Actividad4"));
        listaDeObjetivos.add(objetivoConActividades);

        listaDeObjetivos.add(new Objetivo("Obj3"));
        listaDeObjetivos.add(new Objetivo("Obj4"));
        listaDeObjetivos.add(new Objetivo("Obj5"));
        listaDeObjetivos.add(new Objetivo("Obj6"));
        listaDeObjetivos.add(new Objetivo("Obj7"));
        listaDeObjetivos.add(new Objetivo("Obj8"));
        listaDeObjetivos.add(new Objetivo("Obj9"));
        listaDeObjetivos.add(new Objetivo("Obj10"));

        ObjetivoAdapter objetivoAdapter = new ObjetivoAdapter(listaDeObjetivos, getActivity());
        //DESPUES LOS OBJETIVOS SE VAN A SACAR DEL PERFIL
        //ObjetivoAdapter objetivoAdapter = new ObjetivoAdapter(Perfil.getObjetivos(), getActivity());
        this.setConfiguredAdapter(objetivoAdapter);
    }
}
