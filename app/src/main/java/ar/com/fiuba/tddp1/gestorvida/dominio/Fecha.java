package ar.com.fiuba.tddp1.gestorvida.dominio;

import android.util.Log;

/**
 * Created by User on 25/06/2017.
 */

public class Fecha {
    public String dia;
    public String mes;
    public String anio;

    public Fecha(String dia, String mes, String anio) {
        this.dia = dia;
        this.mes = mes;
        this.anio = anio;
    }

    public Fecha(String fecha) {
        String [] datos = fecha.split("/");

        this.dia = datos[0];
        this.mes = datos[1];
        this.anio = datos[2];
        Log.d("Fecha", toString() );
    }

    @Override
    public String toString() {
        return "dia: " + dia +", mes: " + mes +", anio: " + anio;
    }
}
