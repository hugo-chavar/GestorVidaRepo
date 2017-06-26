package ar.com.fiuba.tddp1.gestorvida.actividades;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;

import ar.com.fiuba.tddp1.gestorvida.web.ResponseListener;

public class ActividadesListener implements ResponseListener {

    private Context context;

    public ActividadesListener(Context context) {
        this.context = context;
    }
    @Override
    public void onRequestCompleted(JSONObject response) {

        Log.d("ActividadesListener", response.toString());
        Toast.makeText(context, "Ping to server ok", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRequestError(int codError, String errorMessage) {
        String error = codError + ": " + errorMessage;
        Log.d("ActividadesListener", error);
        Toast.makeText(context, error, Toast.LENGTH_LONG).show();
    }
}
