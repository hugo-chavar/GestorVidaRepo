package ar.com.fiuba.tddp1.gestorvida.actividades;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

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
        try {
            JSONObject jsonObject = (JSONObject)response;

            actividad.set__v(jsonObject.getString("__v"));
            actividad.setId(jsonObject.getString("_id"));


            Log.d("AgregarAct", "Id: " + actividad.getId());


        } catch (JSONException e) {
            Log.d("ActividadesListener", e.getMessage());
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onRequestError(int codError, String errorMessage) {
        String error = codError + ": " + errorMessage;
        Log.d("ActividadesListener", error);
        Toast.makeText(context, error, Toast.LENGTH_LONG).show();
    }
}
