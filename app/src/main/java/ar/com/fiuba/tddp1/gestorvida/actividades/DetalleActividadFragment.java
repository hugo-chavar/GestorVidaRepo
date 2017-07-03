package ar.com.fiuba.tddp1.gestorvida.actividades;

import android.app.Fragment;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import ar.com.fiuba.tddp1.gestorvida.MainActivity;
import ar.com.fiuba.tddp1.gestorvida.R;
import ar.com.fiuba.tddp1.gestorvida.comunes.FragmentLoader;
import ar.com.fiuba.tddp1.gestorvida.dominio.Actividad;
import ar.com.fiuba.tddp1.gestorvida.dominio.Beneficio;
import ar.com.fiuba.tddp1.gestorvida.dominio.Contacto;
import ar.com.fiuba.tddp1.gestorvida.dominio.Etiqueta;
import ar.com.fiuba.tddp1.gestorvida.dominio.Fecha;

//import android.support.v4.app.Fragment;

public class DetalleActividadFragment extends Fragment {

    TextView mNombre;
    TextView mDescripcion;
    TextView mFechaInicio;
    TextView mFechaFin;
    TextView mPrioridad;
    TextView mEtiquetas;
    TextView mBeneficio;
    TextView mPrecio;
    TextView mDescuento;
    TextView mPrecioPremium;
    LinearLayout mListaEtiquetas;
    Button mBotonSeeMore;

    Actividad actividad;

    TextView mTextViewParticipantes;
    LinearLayout mLinearLayoutParticipantes;

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
        mBeneficio = (TextView) rootView.findViewById(R.id.beneficio_detalle);
        mPrecio = (TextView) rootView.findViewById(R.id.precio_detalle);
        mDescuento = (TextView) rootView.findViewById(R.id.descuento_detalle);
        mPrecioPremium = (TextView) rootView.findViewById(R.id.precio_premium_detalle);
        mListaEtiquetas = (LinearLayout) rootView.findViewById(R.id.lista_etiquetas_detalle);
        mBotonSeeMore = (Button) rootView.findViewById(R.id.boton_seemore_detalle);

        mTextViewParticipantes = (TextView) rootView.findViewById(R.id.textViewPaticipantes);
        mLinearLayoutParticipantes = (LinearLayout) rootView.findViewById(R.id.linearLayoutParticipantes);

        actividad = ((MainActivity) getActivity()).getActividad_detalle();

        inicializarTexto();
        inicializarBotonVerMasDetalles();
        FragmentLoader.setBackOptionEnabled(getActivity(), true);

    }

    protected void inicializarBotonVerMasDetalles() {
        mBotonSeeMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (actividad.getFechaInicio() != null) {
                    mFechaInicio.setVisibility(View.VISIBLE);
                }
                if (actividad.getFechaFin() != null) {
                    mFechaFin.setVisibility(View.VISIBLE);
                }
                if (actividad.getPrioridad() != null) {
                    mPrioridad.setVisibility(View.VISIBLE);
                }
                if (actividad.tieneEtiquetas()) {
                    mEtiquetas.setVisibility(View.VISIBLE);
                    mListaEtiquetas.setVisibility(View.VISIBLE);
                }
                if (actividad.tieneBeneficio()) {
                    mBeneficio.setVisibility(View.VISIBLE);
                    mPrecio.setVisibility(View.VISIBLE);
                    mDescuento.setVisibility(View.VISIBLE);
                    mPrecioPremium.setVisibility(View.VISIBLE);
                }
                if (actividad.tieneParticipantes()) {
                    mTextViewParticipantes.setVisibility(View.VISIBLE);
                    mLinearLayoutParticipantes.setVisibility(View.VISIBLE);
                    for (Contacto contacto : actividad.getParticipantes()) {
                        TextView participante = new TextView(getActivity());
                        participante.setText(contacto.getNombre());
                        mLinearLayoutParticipantes.addView(participante);
                    }
                }
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
        if (actividad.tieneBeneficio()) {
            Beneficio beneficio = actividad.getBeneficios().get(0);
            mBeneficio.setTextSize(18);
            mPrecio.setTextSize(16);
            mPrecio.setText("Precio normal: $" + Math.round(beneficio.getPrecio()));
            mDescuento.setTextSize(16);
            mDescuento.setText("Descuento Premium: " + Math.round(beneficio.getDescuento()) + "%");
            mPrecioPremium.setTextSize(24);
            mPrecioPremium.setText("Precio Premium: $" + Math.round(beneficio.getPrecio() * (1 - (beneficio.getDescuento() / 100))));
        }
    }

}
