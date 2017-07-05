package ar.com.fiuba.tddp1.gestorvida.web;

import android.app.Activity;

import ar.com.fiuba.tddp1.gestorvida.R;
import ar.com.fiuba.tddp1.gestorvida.contactos.ContactosListener;
import ar.com.fiuba.tddp1.gestorvida.dominio.Contacto;
import ar.com.fiuba.tddp1.gestorvida.dominio.Perfil;
import ar.com.fiuba.tddp1.gestorvida.objetivos.ObjetivosListener;

/**
 * Created by hugo on 05/07/17.
 */

public class Server {

    public static void loadUsers(Activity activity) {

        if (Perfil.conected) {
            RequestSender requestSender = new RequestSender(activity);
            ContactosListener listener = new ContactosListener(activity);

            String url = activity.getString(R.string.url) + "users";


            requestSender.doGet(listener, url);
        } else {
            Perfil.agregarContacto(new Contacto("Juanma", R.drawable.avatar));
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
}
