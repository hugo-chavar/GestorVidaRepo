package ar.com.fiuba.tddp1.gestorvida.contactos;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.HashSet;
import java.util.Set;

import ar.com.fiuba.tddp1.gestorvida.R;
import ar.com.fiuba.tddp1.gestorvida.dominio.Actividad;
import ar.com.fiuba.tddp1.gestorvida.dominio.Contacto;
import ar.com.fiuba.tddp1.gestorvida.dominio.Perfil;

/**
 * Created by User on 01/07/2017.
 */

public class AgregarParticipantesFragment extends Fragment {

    private RecyclerView recyclerContactos;
    private Set<Contacto> participantesAgregados = new HashSet<>();

    private Actividad actividad;


    public void setActividad(Actividad actividad) {
        this.actividad = actividad;
    }


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


        ContactosAdapter adapterContactos = new ContactosAdapter(Perfil.getContactos(), this.participantesAgregados);
        this.recyclerContactos.setAdapter(adapterContactos);


        Button buttonInvitar = (Button) rootView.findViewById(R.id.buttonInvitar);
        buttonInvitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actividad.agregarParticipantes(participantesAgregados);
            }
        });

        return rootView;

    }
}
