package ar.com.fiuba.tddp1.gestorvida;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.LinkedList;

import ar.com.fiuba.tddp1.gestorvida.actividades.ActividadesFragment;
import ar.com.fiuba.tddp1.gestorvida.dominio.Objetivo;
import ar.com.fiuba.tddp1.gestorvida.dominio.Perfil;
import ar.com.fiuba.tddp1.gestorvida.estadisticas.EstadisticasFragment;
import ar.com.fiuba.tddp1.gestorvida.objetivos.ObjetivosFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private int fragmentActual = R.id.nav_actividades;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        //para que aparezca el boton
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);

        // para sincronizar el estado actual
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        //TODO: SACAR ESTO DE ACA DESPUES
        Perfil.agregarObjetivo(new Objetivo("Obj1"));
        Perfil.agregarObjetivo(new Objetivo("Obj2"));
        Perfil.agregarObjetivo(new Objetivo("Obj3"));
        Perfil.agregarObjetivo(new Objetivo("Obj4"));
        Perfil.agregarObjetivo(new Objetivo("Obj5"));
        Perfil.agregarObjetivo(new Objetivo("Obj6"));
        Perfil.agregarObjetivo(new Objetivo("Obj7"));
        Perfil.agregarObjetivo(new Objetivo("Obj8"));
        Perfil.agregarObjetivo(new Objetivo("Obj9"));
        Perfil.agregarObjetivo(new Objetivo("Obj10"));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);

        } else {

            if (fragmentActual != R.id.nav_actividades) {
                setFragment(new ActividadesFragment());
                fragmentActual = R.id.nav_actividades;
            } else {
                super.onBackPressed();
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        Log.d("MainActivity", "Se hizo clic en la opcion " + id);

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.nav_perfil) {
            //Mostrar pantalla perfil

        } else if (id == R.id.nav_actividades) {
            //Mostrar pantalla actividades

            setFragment(new ActividadesFragment());

        } else if (id == R.id.nav_calendario) {

            //Mostrar pantalla calendario

            setFragment(new EjemploFragment());


        } else if (id == R.id.nav_objetivos) {
            //Mostrar pantalla Objetivos
            /*
            Intent intent = new Intent(this, VerObjetivosActivity.class);
            startActivity(intent);
            */
            setFragment(new ObjetivosFragment());

        } else if (id == R.id.nav_buscar) {
            //Mostrar pantalla busqueda de actividades

        } else if (id == R.id.nav_estadisticas) {
            //Mostrar pantalla de estadisticas
            setFragment( new EstadisticasFragment() );
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        fragmentActual = id;
        return true;
    }

    public void onActividadClic(int position) {
        Log.d("MainActivity","Se hizo click en: " + position);
    }

    public void setFragment(Fragment fragment) {

        Bundle bundle = new Bundle();

        fragment.setArguments(bundle);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                        .replace(R.id.contenedor, fragment)
                        .commit();
    }
}
