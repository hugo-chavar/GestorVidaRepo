package ar.com.fiuba.tddp1.gestorvida.dominio;

/**
 * Created by User on 05/07/2017.
 */

public class SinObjetivo extends Objetivo {

    public SinObjetivo() {
        super("Sin objetivo");
    }

    public boolean tieneActividades() {
        return false;
    }

}
