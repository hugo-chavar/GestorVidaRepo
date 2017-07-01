package ar.com.fiuba.tddp1.gestorvida.dominio;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * Created by User on 11/06/2017.
 */

public class Actividad {

    private String nombre;
    private String descripcion;
    private boolean estaCompletada;
    private String foto;
    private String _id;

    private Fecha fechaInicio = null;
    private Fecha fechaFin = null;

    String prioridad = null;

    //private Set<String> etiquetas;
    private Set<Etiqueta> etiquetas = new HashSet<>();

    private Set<String> participantes;
    private List<Beneficio> beneficios = new ArrayList<Beneficio>();

    private Fecha fechaRecordatorio;
    private int periodicidad;
    private String horasEstimadas;
    private String minutosEstimados;
    private boolean esPrivada;
    private Integer diaEnQueSeCompleto;

    public Actividad(String nombre) {
        this.nombre = nombre;
        this.estaCompletada = false;
        this.diaEnQueSeCompleto = null;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public void setId(String id) {
        this._id = id;
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

    public boolean estaCompleta() {
        return this.estaCompletada;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void completar() {
        this.estaCompletada = true;
        //Por ahora el dia en que se completo va a ser random, para probarlo
        Random random = new Random();
        //Entre 1 y 7
        this.diaEnQueSeCompleto = random.nextInt(7) + 1;
    }

    private void pasarAPendiente() {
        this.estaCompletada = false;
        this.diaEnQueSeCompleto = null;
    }

    //public void setEtiquetas(Set<String> etiquetas) {
    public void setEtiquetas(Set<Etiqueta> etiquetas) {
        this.etiquetas.addAll(etiquetas);
    }

    public void setFechaRecordatorio(Fecha fechaRecordatorio) {
        this.fechaRecordatorio = fechaRecordatorio;
    }

    public void setParticipantes(Set<String> participantes) {
        this.participantes = participantes;
    }

    public void setPeriodicidad(int periodicidad) {
        this.periodicidad = periodicidad;
    }

    public void setTiempoEstimado(String horas, String minutos) {
        this.horasEstimadas = horas;
        this.minutosEstimados = minutos;
    }

    public Fecha getFechaInicio() {
        return fechaInicio;
    }

    public Fecha getFechaFin() {
        return fechaFin;
    }

    public void esActividadPrivada(boolean esPrivada) {
        this.esPrivada = esPrivada;
    }

    public void alternarEstadoCompleta() {
        if (this.estaCompleta()) {
            this.pasarAPendiente();
        }
        else {
            this.completar();
        }
    }

    public Integer getDiaEnQueSeCompleto() {
        return this.diaEnQueSeCompleto;
    }


    //public Set<String> getEtiquetas() {
    public Set<Etiqueta> getEtiquetas() {
        return this.etiquetas;
    }

    public Set<String> getNombresDeEtiquetas() {
        Set<String> nombresEtiquetas = new HashSet<>();
        for (Etiqueta etiqueta : this.etiquetas) {
            nombresEtiquetas.add(etiqueta.nombre);
        }
        return nombresEtiquetas;
    }


    public Fecha getFechaRecordatorio() {
        return this.fechaRecordatorio;

    }

    public int getPeriodicidad() {
        return this.periodicidad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void addBeneficio(Beneficio beneficio) {
        beneficios.add(beneficio);
    }
}
