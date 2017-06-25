package ar.com.fiuba.tddp1.gestorvida.web;

import org.json.JSONObject;

/**
 * Created by hugo on 22/06/17.
 */

public interface ResponseListener {

    void onRequestCompleted(JSONObject response);

    void onRequestError(int codError, String errorMessage);


}
