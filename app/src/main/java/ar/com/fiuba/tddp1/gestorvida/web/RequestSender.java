package ar.com.fiuba.tddp1.gestorvida.web;

import android.app.Activity;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

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
            listener.onRequestError(error.getMessage());
        }
        }){
            @Override
            protected Map<String,String> getParams(){

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> header_params = new HashMap<String, String>();
                header_params.put("Content-Type","application/x-www-form-urlencoded");
                return header_params;
            }
        };
        queue.add(sr);
    }

}
