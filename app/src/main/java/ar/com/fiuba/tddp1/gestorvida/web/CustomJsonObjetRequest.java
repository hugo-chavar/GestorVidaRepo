package ar.com.fiuba.tddp1.gestorvida.web;


import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import ar.com.fiuba.tddp1.gestorvida.dominio.Perfil;

public class CustomJsonObjetRequest extends JsonObjectRequest {


    public CustomJsonObjetRequest(String url, JSONObject jsonRequest, final ResponseListener listener) {
        super
            (
                url,
                jsonRequest,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        listener.onRequestCompleted(response);
                    }
                },
                new Response.ErrorListener() {
                    private Pair<Integer, String> getError(VolleyError error) {
                        String errorDesc;
                        Integer codError = 0;

                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {

                            errorDesc = "El servidor tardo mucho en responder";

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
                            error.printStackTrace();

                        } else {

                            errorDesc = "Error desconocido. ";

                        }
                        return new Pair<Integer, String>(codError, errorDesc);
                    }

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Pair<Integer, String> errorDetail;

                        errorDetail = getError(error);

                        listener.onRequestError(errorDetail.t, errorDetail.u);

                    }
                }
            );
        setShouldCache(false);

    }

    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {

        if (response.data == null || response.data.length == 0) {
            Log.d("CustomJsonObjetRequest", "response is null");
            return Response.success(new JSONObject(), HttpHeaderParser.parseCacheHeaders(response));
        } else {
            return super.parseNetworkResponse(response);
        }
    }

    @Override
    public Map getHeaders() throws AuthFailureError {
        Map headers = new HashMap<>();
        String auth = Perfil.token;
        headers.put("Content-Type", "application/json");
        if (Perfil.token != null) {
            headers.put("Authorization", auth);
        }


        return headers;
    }

    /*@Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        try {
            if (response.data.length == 0) {
                byte[] responseData = "{}".getBytes("UTF8");
                response = new NetworkResponse(response.statusCode, responseData, response.headers, response.notModified);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return super.parseNetworkResponse(response);
    }*/


}
