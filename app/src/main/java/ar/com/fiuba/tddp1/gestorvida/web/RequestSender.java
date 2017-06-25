package ar.com.fiuba.tddp1.gestorvida.web;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class RequestSender {

    private Activity context;
    private RequestQueue queue;

    public RequestSender(Activity context) {
        this.context = context;
        queue = Volley.newRequestQueue(context);
    }


    public void get(final ResponseListener listener, String url) {
        //final TextView mTextView = (TextView) findViewById(R.id.textView2);

        //RequestQueue queue = Volley.newRequestQueue(context);

        // Request a string response from the provided URL.
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

    }


    public void post(final ResponseListener listener, String url, final Map<String,String> params){

        //RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest sr = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            //Hacer algo con la respuesta
            listener.onRequestCompleted(response);
        }
        }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            //Hacer algo con el error
            String errorDesc;
            errorDesc = getError(error);

            listener.onRequestError(errorDesc);
        }
        }){
            @Override
            protected Map<String,String> getParams(){

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> header_params = new HashMap<String, String>();
                header_params.put("Content-Type","application/json");
                return header_params;
            }
        };
        sr.setShouldCache(false);
        queue.add(sr);
    }

    public void post(final ResponseListener listener, String url, final JSONObject params){

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            listener.onRequestCompleted(response.toString(4));
                            //VolleyLog.v("Response:%n %s", response.toString(4));
                        } catch (JSONException e) {
                            e.printStackTrace();
                            listener.onRequestCompleted("JSONException");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String errorDesc;
                errorDesc = getError(error);

                listener.onRequestError(errorDesc);

            }
        });
        jsonObjectRequest.setShouldCache(false);
        queue.add(jsonObjectRequest);
    }

    @NonNull
    private String getError(VolleyError error) {
        String errorDesc;
        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
            //context.getString(R.string.error_network_timeout),
            errorDesc = "Timeout: verifique su servidor.";
        } else if (error instanceof AuthFailureError) {
            //TODO
            errorDesc = "Error de autenticacion";
        } else if (error instanceof ServerError) {
            //TODO
            errorDesc = "Error de servidor. Cod: " + error.networkResponse.statusCode + ", " +  new String(error.networkResponse.data) ;
        } else if (error instanceof NetworkError) {
            //TODO
            errorDesc = "Error de red";
        } else if (error instanceof ParseError) {
            //TODO
            errorDesc = "Error de parseo";
        } else {
            errorDesc = "Error desconocido. ";
        }
        return errorDesc;
    }

}
