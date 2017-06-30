package ar.com.fiuba.tddp1.gestorvida.web;


public interface ResponseListener {

    void onRequestCompleted(Object response);

    void onRequestError(int codError, String errorMessage);


}
