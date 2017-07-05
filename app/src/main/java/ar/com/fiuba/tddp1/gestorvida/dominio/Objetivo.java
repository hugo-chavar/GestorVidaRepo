package ar.com.fiuba.tddp1.gestorvida.dominio;

import java.util.LinkedList;

/**
 * Created by User on 11/06/2017.
 */

public class Objetivo {

    protected String nombre;
    protected LinkedList<Actividad> actividades;

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
                if (actividad.estaCompleta()) {
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

    public int getCantidadActividades() {
        return this.actividades.size();
    }

    public int getCantidadActividadesCompletadas() {
        int actividadesCompletadas = 0;
        for (Actividad actividad : this.actividades) {
            if (actividad.estaCompleta()) {
                actividadesCompletadas++;
            }
        }
        return actividadesCompletadas;
    }
}
