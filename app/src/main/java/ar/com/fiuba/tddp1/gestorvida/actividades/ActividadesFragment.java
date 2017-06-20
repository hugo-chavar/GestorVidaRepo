package ar.com.fiuba.tddp1.gestorvida.actividades;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import ar.com.fiuba.tddp1.gestorvida.dominio.Actividad;

/**
 * Created by hugo on 20/06/17.
 */

public class ActividadesFragment extends RecyclerFragment {
    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(getActivity());
    }

    @Override
    protected void configureAdapter() {
        Actividad actividad;
        ArrayList<Actividad> lista = new ArrayList<Actividad>();
        actividad = new Actividad("Comprar verdura");
        lista.add(actividad);
        actividad.completar();
        actividad = new Actividad("Viajar a NY");
        lista.add(actividad);
        actividad = new Actividad("Conocer Ucrania");
        lista.add(actividad);
        actividad = new Actividad("Estudiar algebra");
        lista.add(actividad);
        actividad = new Actividad("Reparar el silenciador de la moto");
        lista.add(actividad);
        actividad = new Actividad("Limpiar piso");
        lista.add(actividad);
        actividad.completar();
        actividad = new Actividad("Conseguir la simultaneidad");
        lista.add(actividad);
        actividad = new Actividad("Ver que vitaminas tiene la banana");
        lista.add(actividad);
        actividad = new Actividad("Estudiar algebra de nuevo");
        lista.add(actividad);
        actividad = new Actividad("Festejar");
        lista.add(actividad);


        Activity activity = getActivity();

        ActividadAdapter adapter = new ActividadAdapter(lista, activity);

        setConfiguredAdapter(adapter);

    }


}
