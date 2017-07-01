package ar.com.fiuba.tddp1.gestorvida;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ar.com.fiuba.tddp1.gestorvida.actividades.ActividadAdapter;
import ar.com.fiuba.tddp1.gestorvida.dominio.Perfil;

/**
 * Created by User on 01/07/2017.
 */

public class AgregarParticipantesFragment extends Fragment {


    private RecyclerView recyclerContactos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.layout_recycler_agregar_contactos, container,false);

        this.recyclerContactos = (RecyclerView) rootView.findViewById(R.id.recyclerViewContactos);
        this.recyclerContactos.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        //Seteado por las dudas de que lo usemos
        this.recyclerContactos.getItemAnimator().setAddDuration(1000);
        this.recyclerContactos.getItemAnimator().setChangeDuration(1000);
        this.recyclerContactos.getItemAnimator().setMoveDuration(1000);
        this.recyclerContactos.getItemAnimator().setRemoveDuration(1000);


        ContactosAdapter adapterContactos = new ContactosAdapter(Perfil.getContactos());
        this.recyclerContactos.setAdapter(adapterContactos);


        return rootView;

    }

}
