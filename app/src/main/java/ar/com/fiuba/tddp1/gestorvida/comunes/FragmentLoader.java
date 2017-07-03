package ar.com.fiuba.tddp1.gestorvida.comunes;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;

import java.util.HashMap;
import java.util.Map;

import ar.com.fiuba.tddp1.gestorvida.BuscarActividadActivity;
import ar.com.fiuba.tddp1.gestorvida.DrawerLocker;
import ar.com.fiuba.tddp1.gestorvida.MainActivity;
import ar.com.fiuba.tddp1.gestorvida.R;
import ar.com.fiuba.tddp1.gestorvida.actividades.ActividadesFragment;
import ar.com.fiuba.tddp1.gestorvida.calendario.CalendarioFragment;
import ar.com.fiuba.tddp1.gestorvida.contactos.AgregarParticipantesFragment;
import ar.com.fiuba.tddp1.gestorvida.estadisticas.EstadisticasActividadesCompletadasFragment;
import ar.com.fiuba.tddp1.gestorvida.objetivos.ObjetivosFragment;

public class FragmentLoader {

    public static final int DEFAULT_FRAGMENT = R.id.nav_actividades;
    private static int current = -1;

    public static final String Actividades = "Actividades";
    public static final String AgregarParticipantes = "AgregarParticipantes";
    public static final String Calendario = "Calendario";
    public static final String Objetivos = "Objetivos";
    public static final String BuscarActividad = "BuscarActividad";
    public static final String EstadisticasActividadesCompletadas = "EstadisticasActividadesCompletadas";
    public static final String EstadisticasEtiquetasPieChart = "EstadisticasEtiquetasPieChart";
    public static final String AgregarActividad = "AgregarActividad";
    public static final String DetalleActividadAgregarParticipantes = "DetalleActividadAgregarParticipantes";
    public static final String DetalleBuscarActividad = "DetalleBuscarActividad";
    public static final String DetalleActividad = "DetalleActividad";

    private static final Map<String, Boolean> drawerVisibilityMap;
    static
    {
        drawerVisibilityMap = new HashMap<String, Boolean>();
        drawerVisibilityMap.put(Actividades, true);
        drawerVisibilityMap.put(AgregarParticipantes, true);
        drawerVisibilityMap.put(Objetivos, true);
        drawerVisibilityMap.put(Calendario, true);
        drawerVisibilityMap.put(BuscarActividad, true);
        drawerVisibilityMap.put(EstadisticasActividadesCompletadas, true);
        drawerVisibilityMap.put(EstadisticasEtiquetasPieChart, false);
        drawerVisibilityMap.put(AgregarActividad, false);
        drawerVisibilityMap.put(DetalleActividadAgregarParticipantes, true);
        drawerVisibilityMap.put(DetalleBuscarActividad, true);
        drawerVisibilityMap.put(DetalleActividad, false);


    }




    public static void load(Activity activity, Fragment fragment, String name) {
        Bundle bundle = new Bundle();

        fragment.setArguments(bundle);

        FragmentManager fragmentManager = activity.getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.contenedor, fragment)
                .addToBackStack(name)
                .commit();
        setDrawerEnabled((MainActivity)activity, drawerVisibilityMap.get(name));

    }

    public static boolean backFragment(Activity activity) {
        FragmentManager fragmentManager = activity.getFragmentManager();

        int stackCount = fragmentManager.getBackStackEntryCount();
        if ( stackCount > 1) {
            fragmentManager.popBackStack();
            setDrawerEnabled((MainActivity)activity, drawerVisibilityMap.get(getVisibleFragmentName(activity)));


            return true;
        } return false;
    }

    public static void load(Activity activity, int id) {
        Fragment fragment;
        String name;


        if (id == R.id.nav_perfil) {
            //Mostrar pantalla perfil
            fragment = new AgregarParticipantesFragment();
            name = AgregarParticipantes;
        } else if (id == R.id.nav_calendario) {
            fragment = new CalendarioFragment();
            name = Calendario;
        } else if (id == R.id.nav_objetivos) {
            fragment = new ObjetivosFragment();
            name = Objetivos;
        } else if (id == R.id.nav_buscar) {
            fragment = new BuscarActividadActivity();
            name = BuscarActividad;
        } else if (id == R.id.nav_estadisticas) {
            fragment = new EstadisticasActividadesCompletadasFragment();
            name = EstadisticasActividadesCompletadas;
        } else {

            fragment = new ActividadesFragment();
            name = Actividades;
        }


        String previous = getVisibleFragmentName(activity);
        //Log.d("Loader", "Nuevo: " + name + " visible: " + previous);

        if (!previous.equals(name)) {
            load(activity, fragment, name);
        }

        current = id;

    }

    private static void setDrawerEnabled(DrawerLocker locker, boolean enabled) {

        locker.setDrawerEnabled(enabled);
    }

    public static String getVisibleFragmentName(Activity activity){
        FragmentManager fragmentManager = activity.getFragmentManager();

        int count = fragmentManager.getBackStackEntryCount();
        if (count > 0) {
            return fragmentManager.getBackStackEntryAt(count - 1).getName();
        }

        return "";

    }


}
