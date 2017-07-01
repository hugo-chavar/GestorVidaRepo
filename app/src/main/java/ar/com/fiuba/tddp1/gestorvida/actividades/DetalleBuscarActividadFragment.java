package ar.com.fiuba.tddp1.gestorvida.actividades;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import ar.com.fiuba.tddp1.gestorvida.BuscarActividadActivity;
import ar.com.fiuba.tddp1.gestorvida.MainActivity;
import ar.com.fiuba.tddp1.gestorvida.R;
import ar.com.fiuba.tddp1.gestorvida.dominio.Perfil;

public class DetalleBuscarActividadFragment extends DetalleActividadFragment {

    Button mBotonAgregar;
    Button mBotonCancelar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detalle_buscar_actividad_layout, container, false);

        init(view);

        mBotonAgregar = (Button) view.findViewById(R.id.boton_agregar_detalle);
        mBotonCancelar = (Button) view.findViewById(R.id.boton_cancelar_detalle);

        inicializarBotones();

        return view;
    }

    private void inicializarBotones() {
        mBotonCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).setFragment(new BuscarActividadActivity());
            }
        });

        mBotonAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Perfil.agregarActividad(actividad);
                ((MainActivity) getActivity()).setFragment(new BuscarActividadActivity());
            }
        });
    }
}
