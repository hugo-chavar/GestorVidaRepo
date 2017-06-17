package ar.com.fiuba.tddp1.gestorvida.dominio;

/**
 * Created by User on 11/06/2017.
 */

public class Actividad {

    private String nombre;
    private boolean estaCompletada;

    public Actividad(String nombre) {
        this.nombre = nombre;
        this.estaCompletada = false;
    }

    public boolean estaCompletada() {
        return this.estaCompletada;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void completar() {
        this.estaCompletada = true;
    }
}
