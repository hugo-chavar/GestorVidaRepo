package ar.com.fiuba.tddp1.gestorvida.actividades;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import ar.com.fiuba.tddp1.gestorvida.BuscarActividadActivity;
import ar.com.fiuba.tddp1.gestorvida.MainActivity;
import ar.com.fiuba.tddp1.gestorvida.R;
import ar.com.fiuba.tddp1.gestorvida.dominio.Actividad;
import ar.com.fiuba.tddp1.gestorvida.dominio.Perfil;

public class DetalleActividadFragment extends Fragment {

    TextView mNombre;
    TextView mDescripcion;

    Actividad actividad;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.detalle_actividad_layout, container, false);

        mNombre = (TextView) rootView.findViewById(R.id.nombre_detalle);
        mDescripcion = (TextView) rootView.findViewById(R.id.descripcion_detalle);

        actividad = ((MainActivity) getActivity()).getActividad_detalle();

        inicializarTexto();

        return rootView;
    }

    private void inicializarTexto() {
        mNombre.setText(actividad.getNombre());
        mDescripcion.setText(actividad.getDescripcion());
    }

}
