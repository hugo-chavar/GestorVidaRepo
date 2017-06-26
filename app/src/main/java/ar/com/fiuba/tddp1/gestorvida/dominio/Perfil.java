package ar.com.fiuba.tddp1.gestorvida.dominio;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by User on 11/06/2017.
 */

public class Perfil {

    private static LinkedList<Objetivo> objetivos = new LinkedList<>();
    private static LinkedList<Actividad> actividades = new LinkedList<>();

    public static void agregarObjetivo(Objetivo objetivo) {
        Perfil.objetivos.add(objetivo);
    }

    public static LinkedList<Objetivo> getObjetivos() {
        return Perfil.objetivos;
    }

    public static String token;
    public static String id;

    public static void agregarActividad(Actividad nuevaActividad) {
        Perfil.actividades.add(nuevaActividad);
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

    public static Integer[] getActividadesCompletadasEnSemana() {
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
}
