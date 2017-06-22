package ar.com.fiuba.tddp1.gestorvida.web;

import android.content.Context;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import ar.com.fiuba.tddp1.gestorvida.R;

/**
 * Created by hugo on 21/06/17.
 */

public class RequestSender {

    private Context context;
    private RequestQueue queue;

    public RequestSender(Context context) {
        this.context = context;
        queue = Volley.newRequestQueue(context);
    }


    public void sendHttpRequest(String url) {
        //final TextView mTextView = (TextView) findViewById(R.id.textView2);


        // Instantiate the RequestQueue.
        //RequestQueue queue = Volley.newRequestQueue(context);
        //String url ="http://127.0.0.1:8080/pasarela/";
        //mTextView.setText("Realizo request .. ");

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        mTextView.setText("Response is: "+ response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mTextView.setText("That didn't work!");
            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);

    }
}
