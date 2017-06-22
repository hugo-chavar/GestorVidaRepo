package ar.com.fiuba.tddp1.gestorvida.web;

import android.content.Context;

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


    public void get(Context context, String url) {
        //final TextView mTextView = (TextView) findViewById(R.id.textView2);

        RequestQueue queue = Volley.newRequestQueue(context);
        //String url ="http://127.0.0.1:8080/pasarela/";
        //mTextView.setText("Realizo request .. ");

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Hacer algo con la respuesta
                        //mTextView.setText("Response is: "+ response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //mTextView.setText("That didn't work!");
                //Hacer algo con el error
            }
        });

        queue.add(stringRequest);

    }


    public void post(Context context, String url, final Map<String,String> params){

        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest sr = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            //Hacer algo con la respuesta
        }
        }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            //Hacer algo con el error
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
