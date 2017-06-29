package ar.com.fiuba.tddp1.gestorvida.web;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;

import java.util.HashMap;
import java.util.Map;

import ar.com.fiuba.tddp1.gestorvida.dominio.Perfil;


public class RequestHelper {

    public static Pair<Integer, String> getError(VolleyError error) {
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

    public static Map getHeaders() {

        Map headers = new HashMap<>();
        String auth = Perfil.token;
        headers.put("Content-Type", "application/json");
        if (Perfil.token != null) {
            headers.put("Authorization", auth);
        }

        return headers;
    }
}
