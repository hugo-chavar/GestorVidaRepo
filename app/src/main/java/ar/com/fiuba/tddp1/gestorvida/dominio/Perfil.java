package ar.com.fiuba.tddp1.gestorvida.dominio;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class Perfil {

    private static LinkedList<Objetivo> objetivos = new LinkedList<>();
    private static LinkedList<Actividad> actividades = new LinkedList<>();
    private static Map<Date, List<Actividad>> fechasDeInicioDeActividades = new HashMap<>();
    private static Map<Date, List<Actividad>> fechasDeFinDeActividades = new HashMap<>();
    private static Map<Date, List<Actividad>> fechasDeRecordatoriosDeActividades = new HashMap<>();;
    private static List<Contacto> listaDeContactos = new ArrayList<>();

    public static void agregarObjetivo(Objetivo objetivo) {
        Perfil.objetivos.add(objetivo);
    }

    public static LinkedList<Objetivo> getObjetivos() {
        return Perfil.objetivos;
    }

    public static String token;
    public static String username;
    public static String id;
    public static boolean conected = false;

    public static Actividad actividadActual = null;


    //private static Set<String> etiquetas = new HashSet<>();
    //private static HashMap<String, Integer> etiquetasColor = new HashMap<>();
    private static Set<Etiqueta> etiquetas = new HashSet<>();

    public static void agregarActividad(Actividad nuevaActividad) {
        Perfil.actividades.add(nuevaActividad);

        Perfil.etiquetas.addAll(nuevaActividad.getEtiquetas());


        Perfil.cargarFecha(nuevaActividad, nuevaActividad.getFechaInicio(), Perfil.fechasDeInicioDeActividades);
        Perfil.cargarFecha(nuevaActividad, nuevaActividad.getFechaFin(), Perfil.fechasDeFinDeActividades);
        Perfil.cargarFecha(nuevaActividad, nuevaActividad.getFechaRecordatorio(), Perfil.fechasDeRecordatoriosDeActividades);

    }

    public static void eliminarActividad(Actividad actividad) {
        //TODO falta borrar las etiquetas si ya no tienen actividades
        for(Iterator<Actividad> it = actividades.iterator(); it.hasNext(); ) {
            if(it.next().getId()== actividad.getId()) {
                it.remove();
                break;
            }
        }
    }

    private static void cargarFecha(Actividad nuevaActividad, Fecha fecha, Map<Date, List<Actividad>> fechaDeActividad) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        if (fecha != null) {
            try {
                Date date = formatter.parse(fecha.anio + "/" + fecha.mes + "/" + fecha.dia);
                if ( !fechaDeActividad.containsKey(date) ) {
                    fechaDeActividad.put( date, new ArrayList<Actividad>() );
                }
                fechaDeActividad.get(date).add(nuevaActividad);


                //Si la periodicidad es mayor a 0 --> cargarla de nuevo en otra fecha
                int periodicidad = nuevaActividad.getPeriodicidad();
                if (periodicidad > 0) {
                    Calendar fechaCalendario = Calendar.getInstance();
                    fechaCalendario.setTime(date);
                    int mesInicial = fechaCalendario.get(Calendar.MONTH);

                    //Por ahora solo lo repite 10 veces
                    while ( (mesInicial + 3) >=  fechaCalendario.get(Calendar.MONTH) ) {
                        fechaCalendario.add(Calendar.DATE, periodicidad);
                        date = fechaCalendario.getTime();

                        if ( !fechaDeActividad.containsKey(date) ) {
                            fechaDeActividad.put( date, new ArrayList<Actividad>() );
                        }
                        fechaDeActividad.get(date).add(nuevaActividad);

                    }
                }

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

    public static Map<Etiqueta,Float> getActividadDeEtiquetas() {
        Map<Etiqueta, Float> actividadDeEtiquetas = new HashMap<>();
        for ( Etiqueta etiqueta : Perfil.etiquetas ) {
            actividadDeEtiquetas.put(etiqueta, 0f);
        }

        for (Actividad actividad : Perfil.getActividadesCompletadas()) {
            Set<Etiqueta> etiquetasDeActividad = actividad.getEtiquetas();
            float pesoEtiqueta = (float) 1/etiquetasDeActividad.size();

            for (Etiqueta etiqueta : etiquetasDeActividad) {
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

    public static Map<Date,List<Actividad>> getFechasDeInicioDeActividades() {
        return Perfil.fechasDeInicioDeActividades;
    }


    public static Map<Date, List<Actividad>> getFechasDeFinDeActividades() {
        return Perfil.fechasDeFinDeActividades;
    }

    public static Map<Date,List<Actividad>> getFechasDeRecordatoriosDeActividades() {
        return Perfil.fechasDeRecordatoriosDeActividades;
    }

    public static Set<String> getNombresEtiquetas() {
        Set<String> nombresEtiquetas = new HashSet<>();
        for (Etiqueta etiqueta : Perfil.etiquetas){
            nombresEtiquetas.add(etiqueta.nombre);
        }
        //return Perfil.etiquetas;
        return nombresEtiquetas;
    }

    public static List<Contacto> getContactos() {
        return Perfil.listaDeContactos;
    }

    public static List<Contacto> getContactosFaltantesActividad() {
        if (actividadActual == null) {
            return Perfil.listaDeContactos;
        }

        List<Contacto> contactos = new LinkedList<>();

        for (Contacto c: Perfil.listaDeContactos) {

            if (!(actividadActual.getParticipantes().contains(c))) {
                contactos.add(c);
            }
        }

        return contactos;
    }

    public static void agregarContacto(Contacto contacto) {
        String nombre = contacto.getNombre().trim().toLowerCase();

        if (!nombre.equals(Perfil.username) && !nombre.equals("superadmin")) {
            Perfil.listaDeContactos.add(contacto);
        }
    }

    public static void eliminarContatos() {
        Perfil.listaDeContactos.clear();
    }

}
