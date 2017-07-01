package ar.com.fiuba.tddp1.gestorvida.actividades;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import ar.com.fiuba.tddp1.gestorvida.dominio.Actividad;
import ar.com.fiuba.tddp1.gestorvida.web.ResponseListener;


public class AgregarActividadListener implements ResponseListener {

    private Context context;
    private Actividad actividad;

    public AgregarActividadListener(Context context, Actividad actividad) {
        this.actividad = actividad;
        this.context = context;
    }

    @Override
    public void onRequestCompleted(Object response) {

    }

    @Override
    public void onRequestError(int codError, String errorMessage) {
        String error = codError + ": " + errorMessage;
        Log.d("ActividadesListener", error);
        Toast.makeText(context, error, Toast.LENGTH_LONG).show();
    }
}
