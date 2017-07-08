package ar.com.fiuba.tddp1.gestorvida.actividades;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import ar.com.fiuba.tddp1.gestorvida.web.ResponseListener;

/**
 * Created by diegokim on 7/5/17.
 */

public class ActualizarActividadListener implements ResponseListener{

    private Context context;

    public ActualizarActividadListener(Context context) {

        this.context = context;

    }

    @Override
    public void onRequestCompleted(Object response) {
        Log.d("ActualizarAct", response.toString());
        Toast.makeText(context, "Invitacion/es enviada/s", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRequestError(int codError, String errorMessage) {
        String error = codError + ": " + errorMessage;
        Log.d("ActualizarAct", error);
        Toast.makeText(context, error, Toast.LENGTH_LONG).show();
    }
}
