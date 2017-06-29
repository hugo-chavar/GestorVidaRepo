package ar.com.fiuba.tddp1.gestorvida.web;

import android.app.Activity;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;


public class RequestSender {

    private Activity context;
    private RequestQueue queue;

    public RequestSender(Activity context) {
        this.context = context;
        queue = Volley.newRequestQueue(context);
    }

    public void doRequest(JsonRequest request){

        queue.add(request);
    }

    public void doRequest(final ResponseListener listener, String url, final JSONObject params){

        JsonObjectRequest jsonObjectRequest = new CustomJsonObjetRequest(url, params, listener);

        queue.add(jsonObjectRequest);
    }

    public void doPost(final ResponseListener listener, String url, final JSONObject jsonObject){
        Log.d("RequestSender", "Sending post to " + url + " params " + jsonObject.toString());
        //doRequest(listener, url, jsonObject);

        doRequest(new CustomJsonObjetRequest(url, jsonObject, listener));

    }

    public void doGet(final ResponseListener listener, String url){
        Log.d("RequestSender", "Sending get to " + url );
        doRequest(listener, url, null);

    }

}
