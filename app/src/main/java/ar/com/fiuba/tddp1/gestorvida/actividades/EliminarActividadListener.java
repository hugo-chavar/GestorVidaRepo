package ar.com.fiuba.tddp1.gestorvida.actividades;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import ar.com.fiuba.tddp1.gestorvida.web.ResponseListener;


public class EliminarActividadListener implements ResponseListener {

    private Context context;

    public EliminarActividadListener(Context context) {
        this.context = context;
    }


    @Override
    public void onRequestCompleted(Object response) {
        Toast.makeText(context, "Actividad eliminada del servidor", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRequestError(int codError, String errorMessage) {
        String error = codError + ": " + errorMessage;
        Log.d("EliminarActividad", error);
        Toast.makeText(context, error, Toast.LENGTH_LONG).show();
    }
}
