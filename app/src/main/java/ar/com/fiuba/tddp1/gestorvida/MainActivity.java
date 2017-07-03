package ar.com.fiuba.tddp1.gestorvida;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import ar.com.fiuba.tddp1.gestorvida.comunes.FragmentLoader;
import ar.com.fiuba.tddp1.gestorvida.dominio.Actividad;
import ar.com.fiuba.tddp1.gestorvida.dominio.Contacto;
import ar.com.fiuba.tddp1.gestorvida.dominio.Objetivo;
import ar.com.fiuba.tddp1.gestorvida.dominio.Perfil;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, DrawerLocker {

    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    private Toolbar toolbar;

    private Actividad actividad_detalle;

    private Date filtro_desde = null;
    private Date filtro_hasta = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        //para que aparezca el boton
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);

        // para sincronizar el estado actual
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        //TODO: SACAR ESTO DE ACA DESPUES
        Perfil.agregarObjetivo(new Objetivo("Sin objetivo"));
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

        Perfil.agregarContacto(new Contacto("Juanma", R.drawable.avatar));
        Perfil.agregarContacto(new Contacto("Definitely not Juanma", R.drawable.ic_filter));
        Perfil.agregarContacto(new Contacto("Mordekaiser", R.drawable.mercurio));
        Perfil.agregarContacto(new Contacto("Cosme Fulanito", R.drawable.circulo_color));

        //FragmentLoader.setBackOptionEnabled(this, false);
        FragmentLoader.load(this, R.id.nav_actividades);
        inicializarFiltroFechas(); //Esto es para BuscarActividades

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);

        } else {

            if (!FragmentLoader.backFragment(this)) {
                showExitDialog();
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

        drawer.closeDrawer(GravityCompat.START);

        Log.d("MainActivity", "Se hizo clic en la opcion " + id);


        switch (id) {
            case R.id.action_settings:
                Log.d("MainActivity", "Clic en configuracion");
                break;
            default:
                return super.onOptionsItemSelected(item);
        }


        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        FragmentLoader.load(this, id);

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onActividadClic(int position) {
        Log.d("MainActivity","Se hizo click en: " + position);
    }

    public Actividad getActividad_detalle() {
        return actividad_detalle;
    }

    public void setActividad_detalle(Actividad actividad_detalle) {
        this.actividad_detalle = actividad_detalle;
    }

    public Date getFiltro_desde() {
        return filtro_desde;
    }

    public void setFiltro_desde(Date filtro_desde) {
        this.filtro_desde = filtro_desde;
    }

    public Date getFiltro_hasta() {
        return filtro_hasta;
    }

    public void setFiltro_hasta(Date filtro_hasta) {
        this.filtro_hasta = filtro_hasta;
    }

    public void inicializarFiltroFechas() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        try {
            setFiltro_desde(formatter.parse("1/1/1900"));
            setFiltro_hasta(formatter.parse("31/12/2999"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setDrawerEnabled(final boolean enabled) {

        int lockMode = enabled ? DrawerLayout.LOCK_MODE_UNLOCKED :
                DrawerLayout.LOCK_MODE_LOCKED_CLOSED;

        drawer.setDrawerLockMode(lockMode);
        toggle.setDrawerIndicatorEnabled(enabled);

        ActionBar actionBar = getSupportActionBar();


        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(!enabled);
            actionBar.setDisplayShowHomeEnabled(enabled);
            actionBar.setHomeButtonEnabled(enabled);
        }

        toggle.syncState();

        toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!toggle.isDrawerIndicatorEnabled())
                    onBackPressed();

            }
        });
    }

    private void showExitDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Desea salir de la app?");
        builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                finishAffinity();
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
