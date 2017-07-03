package ar.com.fiuba.tddp1.gestorvida.actividades;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ar.com.fiuba.tddp1.gestorvida.R;
import ar.com.fiuba.tddp1.gestorvida.comunes.FragmentLoader;
import ar.com.fiuba.tddp1.gestorvida.contactos.AgregarParticipantesFragment;

/**
 * Created by User on 02/07/2017.
 */

public class DetalleActividadAgregarParticipantes extends DetalleActividadFragment {

    private FloatingActionButton fabAgregarParticipante;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detalle_actividad_agregar_participantes_layout, container, false);
        init(view);


        this.fabAgregarParticipante = (FloatingActionButton) view.findViewById(R.id.fabAgregarParticipante);
        this.fabAgregarParticipante.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                FragmentLoader.load(getActivity(), new AgregarParticipantesFragment(), FragmentLoader.AgregarParticipantes);
            }

        });


        return view;
    }
}
