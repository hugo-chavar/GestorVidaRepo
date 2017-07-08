package ar.com.fiuba.tddp1.gestorvida.web;

import android.app.Activity;
import android.graphics.Color;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import ar.com.fiuba.tddp1.gestorvida.R;
import ar.com.fiuba.tddp1.gestorvida.actividades.BuscarActividadesListener;
import ar.com.fiuba.tddp1.gestorvida.contactos.ContactosListener;
import ar.com.fiuba.tddp1.gestorvida.dominio.Actividad;
import ar.com.fiuba.tddp1.gestorvida.dominio.ActividadException;
import ar.com.fiuba.tddp1.gestorvida.dominio.Beneficio;
import ar.com.fiuba.tddp1.gestorvida.dominio.Contacto;
import ar.com.fiuba.tddp1.gestorvida.dominio.Etiqueta;
import ar.com.fiuba.tddp1.gestorvida.dominio.Fecha;
import ar.com.fiuba.tddp1.gestorvida.dominio.Perfil;
import ar.com.fiuba.tddp1.gestorvida.objetivos.ObjetivosListener;

public class Server {

    public static void loadUsers(Activity activity) {

        if (Perfil.conected) {
            RequestSender requestSender = new RequestSender(activity);
            ContactosListener listener = new ContactosListener(activity);

            String url = activity.getString(R.string.url) + "users";


            requestSender.doGet(listener, url);
        } else {
            Perfil.agregarContacto(new Contacto("lautaro", R.drawable.avatar));
            Perfil.agregarContacto(new Contacto("Definitely not Juanma", R.drawable.ic_filter));
            Perfil.agregarContacto(new Contacto("Mordekaiser", R.drawable.mercurio));
            Perfil.agregarContacto(new Contacto("Cosme Fulanito", R.drawable.circulo_color));
        }

    }

    public static void loadObjectives(Activity activity) {

        if (Perfil.conected) {
            RequestSender requestSender = new RequestSender(activity);
            ObjetivosListener listener = new ObjetivosListener(activity);

            String url = activity.getString(R.string.url) + "objectives";


            requestSender.doGet(listener, url);
        } else {
            Perfil.agregarContacto(new Contacto("Juanma", R.drawable.avatar));
            Perfil.agregarContacto(new Contacto("Definitely not Juanma", R.drawable.ic_filter));
            Perfil.agregarContacto(new Contacto("Mordekaiser", R.drawable.mercurio));
            Perfil.agregarContacto(new Contacto("Cosme Fulanito", R.drawable.circulo_color));
        }

    }

    public static void getAllPublicActivities(Activity activity){
        getAllPublicActivities(activity, new BuscarActividadesListener(activity) );
    }

    public static void getAllPublicActivities(Activity activity, ResponseListener listener){
        LinkedList<String> tokens = new LinkedList<>();

        tokens.add("trabajo");
        //tokens.add("a");
        //tokens.add("e");
        //tokens.add("i");
        //tokens.add("o");
        //tokens.add("u");

        searchPublicActivities(activity, tokens, null, listener);

    }

    public static void searchPublicActivities(Activity activity, List<String> tokens, String text){
        searchPublicActivities(activity, tokens, text, new BuscarActividadesListener(activity));
    }

    public static void searchPublicActivities(Activity activity, List<String> tokens, String text, ResponseListener listener){
        Perfil.getActividadesBuscadas().clear();

        if (Perfil.conected) {
            RequestSender requestSender = new RequestSender(activity);
            //BuscarActividadesListener listener = new BuscarActividadesListener(activity);

            String url = activity.getString(R.string.url) + "activities/search";

            JSONObject jsonObject = new JSONObject();
            try {
                if (text != null) {
                    jsonObject.put("texto", text);
                }


                JSONArray categorias = new JSONArray();
                for (String token: tokens) {
                    categorias.put(token);
                }

                jsonObject.put("categorias", categorias);

                requestSender.doPostGetArray(listener, url, jsonObject);


            } catch (JSONException e) {
                e.printStackTrace();
            }


        } else {
            try {

                Actividad actividad = new Actividad("Correr 4km");
                actividad.setDescripcion("Quiero correr para prepararme fisicamente");
                actividad.setFechaInicio(new Fecha("8", "9", "2017"));
                actividad.setFechaFin(new Fecha("8", "9", "2017"));
                actividad.setPrioridad("ALTA");
                Beneficio beneficio = new Beneficio();
                beneficio.setPrecio(100);
                beneficio.setDescuento(20);
                beneficio.setDescripcion("TETSTSTSTSTST");
                actividad.addBeneficio(beneficio);
                //mockedActivities.add(actividad);
                Perfil.agregarActividadBuscada(actividad);

                Actividad actividad2 = new Actividad("Ir al cine");
                actividad2.setDescripcion("Quiero ir a ver Star Wars VIII");
                actividad2.setFechaInicio(new Fecha("15", "12", "2017"));
                actividad2.setFechaFin(new Fecha("15", "12", "2017"));
                Set<Etiqueta> etiquetas2 = new HashSet<>();
                etiquetas2.add(new Etiqueta("Cine", Color.GREEN));
                etiquetas2.add(new Etiqueta("Peliculas", Color.RED));
                actividad2.setEtiquetas(etiquetas2);
                actividad2.setPrioridad("BAJA");
                //mockedActivities.add(actividad2);
                Perfil.agregarActividadBuscada(actividad2);

                Actividad actividad3 = new Actividad("Aprobar álgrebra");
                actividad3.setDescripcion("Quiero aprobarlaaa");
                actividad3.setFechaInicio(new Fecha("1", "7", "2017"));
                actividad3.setFechaFin(new Fecha("3", "7", "2017"));
                Set<Etiqueta> etiquetas3 = new HashSet<>();
                etiquetas3.add(new Etiqueta("Facultad", Color.GREEN));
                etiquetas3.add(new Etiqueta("Aburrido", Color.YELLOW));
                etiquetas3.add(new Etiqueta("Noooo", Color.RED));
                actividad3.setEtiquetas(etiquetas3);
                actividad3.setPrioridad("ALTA");
                //mockedActivities.add(actividad3);
                Perfil.agregarActividadBuscada(actividad3);

                Actividad actividad4 = new Actividad("Jugar al fútbol");
                actividad4.setDescripcion("Futbol con los pibes");
                actividad4.setFechaInicio(new Fecha("5", "8", "2017"));
                actividad4.setFechaFin(new Fecha("5", "8", "2017"));
                Set<Etiqueta> etiquetas4 = new HashSet<>();
                etiquetas4.add(new Etiqueta("Deportes", Color.GREEN));
                actividad4.setEtiquetas(etiquetas4);
                //mockedActivities.add(actividad4);
                Perfil.agregarActividadBuscada(actividad4);

                Actividad actividad5 = new Actividad("Clases de guitarra");
                actividad5.setDescripcion("Clases para aprender a tocar");
                actividad5.setFechaInicio(new Fecha("5", "10", "2017"));
                actividad5.setFechaFin(new Fecha("10", "11", "2017"));
                Set<Etiqueta> etiquetas5 = new HashSet<>();
                etiquetas5.add(new Etiqueta("Musica", Color.GREEN));
                etiquetas5.add(new Etiqueta("Guitarra", Color.BLUE));
                actividad5.setEtiquetas(etiquetas5);
                //mockedActivities.add(actividad5);
                Perfil.agregarActividadBuscada(actividad5);

                Actividad actividad6 = new Actividad("Fiesta en mi casa");
                actividad6.setDescripcion("Bebidas y asado van por mi cuenta");
                actividad6.setFechaInicio(new Fecha("1", "10", "2017"));
                actividad6.setFechaFin(new Fecha("1", "10", "2017"));
                Set<Etiqueta> etiquetas6 = new HashSet<>();
                etiquetas6.add(new Etiqueta("Fiestas", Color.GREEN));
                actividad6.setEtiquetas(etiquetas6);
                //mockedActivities.add(actividad6);
                Perfil.agregarActividadBuscada(actividad6);

                Actividad actividad7 = new Actividad("Fiesta en Avellaneda");
                actividad7.setDescripcion("Festejo por la clasificación de Racing a la Libertadores");
                actividad7.setFechaInicio(new Fecha("6", "7", "2017"));
                actividad7.setFechaFin(new Fecha("6", "7", "2017"));
                Set<Etiqueta> etiquetas7 = new HashSet<>();
                etiquetas7.add(new Etiqueta("Fiestas", Color.GREEN));
                actividad7.setEtiquetas(etiquetas7);
                //mockedActivities.add(actividad7);
                Perfil.agregarActividadBuscada(actividad7);

                Actividad actividad8 = new Actividad("Cine gratis!");
                actividad8.setDescripcion("Pochoclos no");
                actividad8.setFechaInicio(new Fecha("6", "7", "2017"));
                actividad8.setFechaFin(new Fecha("20", "7", "2017"));
                Set<Etiqueta> etiquetas8 = new HashSet<>();
                etiquetas8.add(new Etiqueta("Cine", Color.GREEN));
                actividad8.setEtiquetas(etiquetas8);
                //mockedActivities.add(actividad8);
                Perfil.agregarActividadBuscada(actividad8);

            } catch (ActividadException e) {
                //nada
            }
        }
    }
}
