package ar.com.fiuba.tddp1.gestorvida.dominio;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import ar.com.fiuba.tddp1.gestorvida.R;

/**
 * Created by User on 11/06/2017.
 */

public class Actividad {

    private String nombre;
    private String descripcion;
    private boolean estaCompletada;
    private String foto = "32";
    private String tipo = "tipoXdefecto";
    private String _id;
    private String __v; //version en el servidor

    private Fecha fechaInicio = null;
    private Fecha fechaFin = null;

    private String horaInicio = "00:00";
    private String horaFin = "23:59";

    String prioridad = null;

    private Set<Etiqueta> etiquetas = new HashSet<>();

    private Set<Contacto> participantesAgregados = new HashSet<>();

    private List<Beneficio> beneficios = new ArrayList<Beneficio>();

    private Fecha fechaRecordatorio;
    private int periodicidad;
    private int horasEstimadas = 0;
    private String minutosEstimados;
    private boolean esPrivada;
    private Integer diaEnQueSeCompleto;


    public Actividad(String nombre) throws ActividadException {
        if (nombre.trim().length() < 3) {
            throw new ActividadException();
        }
        this.nombre = nombre;
        this.estaCompletada = false;
        this.diaEnQueSeCompleto = null;
    }

    public void set__v(String version) { this.__v = version; }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public void setId(String id) {
        this._id = id;
    }

    public String getId() { return _id; }

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

    public void setEtiquetas(Set<Etiqueta> etiquetas) {
        this.etiquetas.addAll(etiquetas);
    }

    public void setFechaRecordatorio(Fecha fechaRecordatorio) {
        this.fechaRecordatorio = fechaRecordatorio;
    }

    public void setParticipantes(Set<String> participantes) {
        for (String nombre : participantes) {
            //TODO setearle aca la foto de perfil de alguna manera
            this.participantesAgregados.add(new Contacto(nombre, R.drawable.avatar));
        }
    }

    public void setPeriodicidad(int periodicidad) {
        this.periodicidad = periodicidad;
    }

    public void setTiempoEstimado(String horas, String minutos) {
        this.horasEstimadas = Integer.parseInt(horas);
        this.minutosEstimados = minutos;
    }

    public Fecha getFechaInicio() {
        return fechaInicio;
    }

    public String getFechaInicioMesDia() {
        return fechaInicio.dia + Fecha.sep + fechaInicio.mes;
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


    public void agregarParticipantes(Set<Contacto> participantesAgregados) {
        if (participantesAgregados == null) {
            this.participantesAgregados = participantesAgregados;
        } else {
            this.participantesAgregados.addAll(participantesAgregados);
        }
    }

    public String getPrioridad() {
        return prioridad;
    }

    public boolean tieneBeneficio() {
        return !beneficios.isEmpty();
    }

    public List<Beneficio> getBeneficios() {
        return beneficios;
    }

    public boolean tieneEtiquetas() {
        return !etiquetas.isEmpty();
    }

    public Set<Contacto> getParticipantes() {
        return this.participantesAgregados;
    }

    public boolean tieneParticipantes() {
        return this.participantesAgregados.size() != 0;
    }

    public String getFoto() {
        return foto;
    }

    public int getHorasEstimadas() {
        return horasEstimadas;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return getNombre() + " " + getDescripcion() + " " + getPrioridad();
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public String getHoraFin() {
        return horaFin;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }
}
