package ar.com.fiuba.tddp1.gestorvida.contactos;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.json.JSONObject;

import java.util.HashSet;
import java.util.Set;

import ar.com.fiuba.tddp1.gestorvida.MainActivity;
import ar.com.fiuba.tddp1.gestorvida.R;
import ar.com.fiuba.tddp1.gestorvida.actividades.ActualizarActividadListener;
import ar.com.fiuba.tddp1.gestorvida.dominio.Actividad;
import ar.com.fiuba.tddp1.gestorvida.dominio.ActividadFactory;
import ar.com.fiuba.tddp1.gestorvida.dominio.Contacto;
import ar.com.fiuba.tddp1.gestorvida.dominio.Perfil;
import ar.com.fiuba.tddp1.gestorvida.web.RequestSender;

/**
 * Created by User on 01/07/2017.
 */

public class AgregarParticipantesFragment extends Fragment {

    private RecyclerView recyclerContactos;
    private ContactosAdapter adapterContactos;
    private Set<Contacto> participantesAgregados = new HashSet<>();


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


        adapterContactos = new ContactosAdapter(Perfil.getContactos(), this.participantesAgregados);
        this.recyclerContactos.setAdapter(adapterContactos);

        Button buttonInvitar = (Button) rootView.findViewById(R.id.buttonInvitar);
        buttonInvitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Actividad actividad = ((MainActivity) getActivity()).getActividad_detalle();
                actividad.agregarParticipantes(participantesAgregados);
                updateActividad(actividad);
                getActivity().onBackPressed();
            }
        });

        return rootView;

    }
        private void updateActividad(Actividad actividad) {
            JSONObject jsonObject = ActividadFactory.toJSONObject(actividad);
            ActualizarActividadListener listener = new ActualizarActividadListener(getActivity());
            String url = getActivity().getString(R.string.url) + "activities/" + actividad.getId();
            RequestSender sender = new RequestSender(getActivity());
            //sender.doPost(listener, url, jsonObject);
            sender.doPut(listener, url, jsonObject);
        }
}
