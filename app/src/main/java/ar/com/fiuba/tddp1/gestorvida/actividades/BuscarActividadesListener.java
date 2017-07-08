package ar.com.fiuba.tddp1.gestorvida.actividades;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import ar.com.fiuba.tddp1.gestorvida.dominio.Actividad;
import ar.com.fiuba.tddp1.gestorvida.dominio.ActividadFactory;
import ar.com.fiuba.tddp1.gestorvida.dominio.Perfil;
import ar.com.fiuba.tddp1.gestorvida.web.ResponseListener;


public class BuscarActividadesListener implements ResponseListener {

    private Context context;

    public BuscarActividadesListener(Context context) {

        this.context = context;

    }

    @Override
    public void onRequestCompleted(Object response) {

        Log.d("BuscarActiv", response.toString());
        JSONArray array = (JSONArray)response;
        for (int i = 0; i < array.length(); i++) {
            try {

                //Log.d("ActividadesListener", "Actividad " + i);
                Actividad actividad = ActividadFactory.fromJSONObject(array.getJSONObject(i));
                Perfil.agregarActividadBuscada(actividad);

            } catch (JSONException e) {
                Log.d("BuscarActiv", e.getMessage());
            }

        }

        //Log.d("BuscarActiv", response.toString());
        //Toast.makeText(context, "Ok", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRequestError(int codError, String errorMessage) {
        String error = codError + ": " + errorMessage;
        Log.d("BuscarActiv", error);
        Toast.makeText(context, error, Toast.LENGTH_LONG).show();
    }
}
