package ar.com.fiuba.tddp1.gestorvida.web;

/**
 * Created by hugo on 22/06/17.
 */

public interface ResponseListener {

    //void onRequestCompleted(JSONObject response);

    //void onRequestCompleted(JSONArray response);

    void onRequestCompleted(Object response);

    void onRequestError(int codError, String errorMessage);


}
