package ar.com.fiuba.tddp1.gestorvida.web;

/**
 * Created by hugo on 22/06/17.
 */

public interface ResponseListener {

    void onRequestCompleted(String response);

    void onRequestError(String errorMessage);


}
