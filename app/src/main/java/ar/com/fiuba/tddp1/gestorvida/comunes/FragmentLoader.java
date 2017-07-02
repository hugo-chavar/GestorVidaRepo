package ar.com.fiuba.tddp1.gestorvida.comunes;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;

import ar.com.fiuba.tddp1.gestorvida.BuscarActividadActivity;
import ar.com.fiuba.tddp1.gestorvida.DrawerLocker;
import ar.com.fiuba.tddp1.gestorvida.R;
import ar.com.fiuba.tddp1.gestorvida.actividades.ActividadesFragment;
import ar.com.fiuba.tddp1.gestorvida.calendario.CalendarioFragment;
import ar.com.fiuba.tddp1.gestorvida.contactos.AgregarParticipantesFragment;
import ar.com.fiuba.tddp1.gestorvida.estadisticas.EstadisticasActividadesCompletadasFragment;
import ar.com.fiuba.tddp1.gestorvida.objetivos.ObjetivosFragment;

public class FragmentLoader {

    private static int current = R.id.nav_actividades;

    public static void load(Activity activity, Fragment fragment) {
        Bundle bundle = new Bundle();

        fragment.setArguments(bundle);

        FragmentManager fragmentManager = activity.getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.contenedor, fragment)
                .commit();
        current = -1;

    }

    public static void load(Activity activity, int id) {
        Fragment fragment;

        if (id == R.id.nav_perfil) {
            //Mostrar pantalla perfil
            //AgregarParticipantesFragment fragment1 = new AgregarParticipantesFragment();
            //fragment1.setActividad(actividadGlobalMain);
            fragment = new AgregarParticipantesFragment();

        } else if (id == R.id.nav_calendario) {

            //Mostrar pantalla calendario

            //setFragment(new EjemploFragment());
            fragment = new CalendarioFragment();


        } else if (id == R.id.nav_objetivos) {
            //Mostrar pantalla Objetivos

            fragment = new ObjetivosFragment();

        } else if (id == R.id.nav_buscar) {
            //Mostrar pantalla busqueda de actividades
            fragment = new BuscarActividadActivity();

        } else if (id == R.id.nav_estadisticas) {
            //Mostrar pantalla de estadisticas
            fragment = new EstadisticasActividadesCompletadasFragment();
        } else {
            //id == R.id.nav_actividades
            //Mostrar pantalla actividades
            fragment = new ActividadesFragment();
        }

        load(activity, fragment);

        current = id;


    }

    public static void setDrawerEnabled(DrawerLocker locker, boolean enabled) {

        locker.setDrawerEnabled(enabled);
    }

    public static boolean shouldExit() {
        return current == R.id.nav_actividades;

    }
}
