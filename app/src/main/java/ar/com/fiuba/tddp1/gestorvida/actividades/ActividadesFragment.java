package ar.com.fiuba.tddp1.gestorvida.actividades;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import ar.com.fiuba.tddp1.gestorvida.comunes.FragmentLoader;
import ar.com.fiuba.tddp1.gestorvida.dominio.Perfil;


public class ActividadesFragment extends RecyclerFragment {

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(getActivity());
    }

    @Override
    protected void configureAdapter() {

        //ActividadAdapter adapter = new ActividadAdapter(Perfil.getActividadesPendientes(), activity);
        ActividadAdapter adapter = new ActividadAdapter(Perfil.getTodasLasActividades(), getActivity());

        setConfiguredAdapter(adapter);

    }

    @Override
    protected void goToAgregarElemento() {
        //Lo que quiero es setear un fragmento de agregar actividades
        FragmentLoader.load(getActivity(), new AgregarActividadFragment(), FragmentLoader.AgregarActividad);
    }

}
