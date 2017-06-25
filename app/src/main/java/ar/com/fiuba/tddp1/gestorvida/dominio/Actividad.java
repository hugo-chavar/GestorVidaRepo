package ar.com.fiuba.tddp1.gestorvida.dominio;

/**
 * Created by User on 11/06/2017.
 */

public class Actividad {

    private String nombre;
    private String descripcion;
    private boolean estaCompletada;

    private Fecha fechaInicio = null;
    private Fecha fechaFin = null;

    String prioridad = null;

    public Actividad(String nombre) {
        this.nombre = nombre;
        this.estaCompletada = false;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setFechaInicio(Fecha fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public void setFechaFin(Fecha fechaFin) {
        this.fechaFin = fechaFin;
    }

    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
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
