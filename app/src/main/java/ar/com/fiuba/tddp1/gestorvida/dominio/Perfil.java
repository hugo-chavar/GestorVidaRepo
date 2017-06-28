package ar.com.fiuba.tddp1.gestorvida.dominio;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by User on 11/06/2017.
 */

public class Perfil {

    private static LinkedList<Objetivo> objetivos = new LinkedList<>();
    private static LinkedList<Actividad> actividades = new LinkedList<>();
    private static Map<Date, List<Actividad>> fechasDeActividades = new HashMap<>();

    public static void agregarObjetivo(Objetivo objetivo) {
        Perfil.objetivos.add(objetivo);
    }

    public static LinkedList<Objetivo> getObjetivos() {
        return Perfil.objetivos;
    }

    public static String token;
    public static String id;


    private static Set<String> etiquetas = new HashSet<>();

    public static void agregarActividad(Actividad nuevaActividad) {
        Perfil.actividades.add(nuevaActividad);
        Perfil.etiquetas.addAll(nuevaActividad.getEtiquetas());

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");


        if (nuevaActividad.getFechaInicio() != null) {
            Fecha fechaInicio = nuevaActividad.getFechaInicio();
            try {
                Date date = formatter.parse(fechaInicio.anio + "/" + fechaInicio.mes + "/" + fechaInicio.dia);
                if ( !Perfil.fechasDeActividades.containsKey(date) ) {
                    Perfil.fechasDeActividades.put( date, new ArrayList<Actividad>() );
                }
                Perfil.fechasDeActividades.get(date).add(nuevaActividad);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }


    }


    public static LinkedList<Actividad> getActividadesPendientes() {
        //Se podria tener dos listas separadas de actividades pendientes y completadas, usando un Observer para moverlas de una lista a la otra
        LinkedList<Actividad> actividadesPendientes = new LinkedList<>();
        for (Actividad actividad: Perfil.actividades) {
            if (!actividad.estaCompleta()) {
                actividadesPendientes.add(actividad);
            }
        }
        return actividadesPendientes;
    }

    public static List<Actividad> getTodasLasActividades() {
        return Perfil.actividades;
    }

    public static Integer[] getCantidadActividadesCompletadasEnSemana() {
        Integer[] actividadesCompletadasEnSemana = new Integer[7];
        for (int i = 0; i < 7; i++) {
            actividadesCompletadasEnSemana[i] = 0;
        }

        for (Actividad actividad : Perfil.getTodasLasActividades()) {
            if (actividad.estaCompleta()) {
                actividadesCompletadasEnSemana[actividad.getDiaEnQueSeCompleto() - 1]++;
            }
        }

        return actividadesCompletadasEnSemana;
    }


    public static Map<String,Float> getActividadDeEtiquetas() {
        Map<String, Float> actividadDeEtiquetas = new HashMap<>();
        for ( String etiqueta : Perfil.etiquetas ) {
            actividadDeEtiquetas.put(etiqueta, 0f);
        }

        for (Actividad actividad : Perfil.getActividadesCompletadas()) {
            Set<String> etiquetasDeActividad = actividad.getEtiquetas();
            float pesoEtiqueta = (float) 1/etiquetasDeActividad.size();

            for (String etiqueta : etiquetasDeActividad) {
                float actividadActualizada = actividadDeEtiquetas.get(etiqueta) + pesoEtiqueta;
                actividadDeEtiquetas.put(etiqueta, actividadActualizada);
            }
        }

        return actividadDeEtiquetas;
    }

    public static List<Actividad> getActividadesCompletadas() {
        List<Actividad> actividadesCompletadas = new ArrayList<>();
        for (Actividad actividad : Perfil.getTodasLasActividades()) {
            if (actividad.estaCompleta()) {
                actividadesCompletadas.add(actividad);
            }
        }
        return actividadesCompletadas;
    }

    public static Map<Date,List<Actividad>> getFechasDeActividades() {
        return Perfil.fechasDeActividades;
    }
}
