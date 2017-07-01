package ar.com.fiuba.tddp1.gestorvida.dominio;

import android.support.annotation.NonNull;

/**
 * Created by User on 30/06/2017.
 */

public class Etiqueta {

    public String nombre;
    public int color;


    public Etiqueta(String nombre, int color) {
        this.nombre = nombre;
        this.color = color;
    }


    @Override
    public int hashCode() {
        int hash = this.nombre.hashCode() + this.color;
        return hash;
    }

    @Override
    public boolean equals(Object e) {
        Etiqueta etiqueta2 = (Etiqueta) e;
        return ( (this.nombre.equals(etiqueta2.nombre)) && (this.color == etiqueta2.color));
    }
}
