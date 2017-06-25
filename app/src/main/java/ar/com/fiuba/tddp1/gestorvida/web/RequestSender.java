package ar.com.fiuba.tddp1.gestorvida.web;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import ar.com.fiuba.tddp1.gestorvida.R;


public class RequestSender {

    private Activity context;
    private RequestQueue queue;

    public RequestSender(Activity context) {
        this.context = context;
        queue = Volley.newRequestQueue(context);
    }


    /*
    public void get(final ResponseListener listener, String url) {


        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Hacer algo con la respuesta
                        Log.d("sarasa", response);
                        listener.onRequestCompleted(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Hacer algo con el error
                Log.d("sarasa2", error.getMessage());
                listener.onRequestError(error.getMessage());
            }
        });

        queue.add(stringRequest);

    }*/

    public void post(final ResponseListener listener, String url, final JSONObject params){

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        listener.onRequestCompleted(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Pair<Integer, String> errorDetail;

                errorDetail = getError(error);

                listener.onRequestError(errorDetail.t, errorDetail.u);

            }
        });
        jsonObjectRequest.setShouldCache(false);
        queue.add(jsonObjectRequest);
    }

    @NonNull
    private Pair<Integer, String> getError(VolleyError error) {
        String errorDesc;
        Integer codError = 0;
        
        if (error instanceof TimeoutError || error instanceof NoConnectionError) {

            errorDesc = context.getString(R.string.error_network_timeout);

        } else if (error instanceof AuthFailureError) {

            errorDesc = "Error de autenticacion";

        } else if (error instanceof ServerError) {

            codError = error.networkResponse.statusCode;

            switch (codError) {
                case 409:
                    errorDesc = "Nombre de usuario ya existe";
                    break;
                case 400:
                    errorDesc = "No se recibieron todos los parametros";
                    break;
                case 404:
                    errorDesc = "La url invocada no corresponde a un servicio valido";
                    break;
                default:
                    errorDesc = new String(error.networkResponse.data);

            }
        } else if (error instanceof NetworkError) {

            errorDesc = "Error de red";

        } else if (error instanceof ParseError) {

            errorDesc = "Error de parseo";

        } else {

            errorDesc = "Error desconocido. ";

        }
        return new Pair<Integer, String>(codError, errorDesc);
    }

}
