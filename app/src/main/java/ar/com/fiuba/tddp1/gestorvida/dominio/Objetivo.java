package ar.com.fiuba.tddp1.gestorvida.dominio;

import java.util.LinkedList;

/**
 * Created by User on 11/06/2017.
 */

public class Objetivo {

    private String nombre;
    private LinkedList<Actividad> actividades;

    public Objetivo(String nombre) {
        this.nombre = nombre;
        this.actividades = new LinkedList<>();
    }

    public String getNombre() {
        return this.nombre;
    }

    public void agregarActividad(Actividad actividad){
        this.actividades.add(actividad);
    }

    public String toString() {
        return this.nombre;
    }


    public float getProgreso() {
        float actividadesCompletadas = 0;
        if (this.tieneActividades()) {
            for (Actividad actividad : this.actividades) {
                if (actividad.estaCompletada()) {
                    actividadesCompletadas++;
                }
            }
            return (actividadesCompletadas/this.actividades.size())*100;
        }
        return 0;
    }

    public boolean tieneActividades() {
        return this.actividades.size() != 0;
    }
}
