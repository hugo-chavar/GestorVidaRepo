package ar.com.fiuba.tddp1.gestorvida.actividades;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import ar.com.fiuba.tddp1.gestorvida.MainActivity;
import ar.com.fiuba.tddp1.gestorvida.R;
import ar.com.fiuba.tddp1.gestorvida.dominio.Actividad;
import ar.com.fiuba.tddp1.gestorvida.dominio.Etiqueta;
import ar.com.fiuba.tddp1.gestorvida.dominio.Fecha;

public class DetalleActividadFragment extends Fragment {

    TextView mNombre;
    TextView mDescripcion;
    TextView mFechaInicio;
    TextView mFechaFin;
    TextView mPrioridad;
    TextView mEtiquetas;
    LinearLayout mListaEtiquetas;
    Button mBotonSeeMore;

    Actividad actividad;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.detalle_actividad_layout, container, false);

        init(rootView);

        return rootView;
    }

    protected void init(View rootView) {
        mNombre = (TextView) rootView.findViewById(R.id.nombre_detalle);
        mDescripcion = (TextView) rootView.findViewById(R.id.descripcion_detalle);
        mFechaInicio = (TextView) rootView.findViewById(R.id.fecha_inicio_detalle);
        mFechaFin = (TextView) rootView.findViewById(R.id.fecha_fin_detalle);
        mPrioridad = (TextView) rootView.findViewById(R.id.prioridad_detalle);
        mEtiquetas = (TextView) rootView.findViewById(R.id.etiquetas_detalle);
        mListaEtiquetas = (LinearLayout) rootView.findViewById(R.id.lista_etiquetas_detalle);
        mBotonSeeMore = (Button) rootView.findViewById(R.id.boton_seemore_detalle);

        actividad = ((MainActivity) getActivity()).getActividad_detalle();

        inicializarTexto();
        inicializarBotones();
    }

    private void inicializarBotones() {
        mBotonSeeMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFechaInicio.setVisibility(View.VISIBLE);
                mFechaFin.setVisibility(View.VISIBLE);
                mPrioridad.setVisibility(View.VISIBLE);
                mEtiquetas.setVisibility(View.VISIBLE);
                mListaEtiquetas.setVisibility(View.VISIBLE);
                mBotonSeeMore.setVisibility(View.GONE);
            }
        });
    }

    private void inicializarTexto() {
        mNombre.setTextSize(22);
        mNombre.setText(actividad.getNombre());
        mDescripcion.setTextSize(18);
        mDescripcion.setText(actividad.getDescripcion());
        Fecha fechaInicio = actividad.getFechaInicio();
        mFechaInicio.setTextSize(16);
        mFechaInicio.setText("Fecha de inicio: " + fechaInicio.dia + "/" + fechaInicio.mes + "/" + fechaInicio.anio);
        Fecha fechaFin = actividad.getFechaFin();
        mFechaFin.setTextSize(16);
        mFechaFin.setText("Fecha de fin: " + fechaFin.dia + "/" + fechaFin.mes + "/" + fechaFin.anio);
        mPrioridad.setTextSize(16);
        mPrioridad.setText("Prioridad: " + actividad.getPrioridad());
        for (Etiqueta etiqueta : actividad.getEtiquetas()) {
            TextView textViewEtiqueta = new TextView(getActivity());
            textViewEtiqueta.setText(etiqueta.nombre);
            GradientDrawable fondoEtiqueta = (GradientDrawable) getActivity().getDrawable(R.drawable.etiqueta_background);
            fondoEtiqueta.setColor(etiqueta.color);
            textViewEtiqueta.setBackground(fondoEtiqueta);
            mListaEtiquetas.addView(textViewEtiqueta);
        }
    }

}
