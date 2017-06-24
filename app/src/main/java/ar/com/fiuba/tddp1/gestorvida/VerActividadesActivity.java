package ar.com.fiuba.tddp1.gestorvida;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import ar.com.fiuba.tddp1.gestorvida.dominio.Actividad;
import ar.com.fiuba.tddp1.gestorvida.dominio.Objetivo;
import ar.com.fiuba.tddp1.gestorvida.dominio.Perfil;

public class VerActividadesActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private Map<Button, Actividad> botonesDeActividades = new HashMap<>();


    private String[] elementosDeLaLista = {"Objetivos", "Estadisticas"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_actividades);

        this.mostrarActividades();

        //this.cargarElementosAlDrawerList();
    }

    /*private void cargarElementosAlDrawerList() {
        ListView mDrawerList = (ListView) findViewById(R.id.navList);
        ArrayAdapter<String> mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, elementosDeLaLista);
        mDrawerList.setAdapter(mAdapter);
        mDrawerList.setOnItemClickListener(this);
    }*/

    private void mostrarActividades() {

        ViewGroup layout = (ViewGroup) findViewById(R.id.activity_ver_actividades);

        //Por ahora cargo los objetivos aca, para probrarlo
        Perfil.agregarObjetivo(new Objetivo("Obj1"));
        Perfil.agregarObjetivo(new Objetivo("Obj2"));
        Perfil.agregarObjetivo(new Objetivo("Obj3"));
        Perfil.agregarObjetivo(new Objetivo("Obj4"));

        for (Actividad actividad: Perfil.getActividadesPendientes()) {
            this.mostrarDatosActividad(actividad, layout);
        }
    }

    private void mostrarDatosActividad(Actividad actividad, ViewGroup layout) {

        TextView datosActividad = new TextView(this);
        datosActividad.setTextSize(20);
        String nombreActividad = actividad.getNombre();
        datosActividad.setText(nombreActividad);

        Button botonCompletarActividad = new Button(this);
        botonCompletarActividad.setText("Completar " + nombreActividad);
        this.botonesDeActividades.put(botonCompletarActividad, actividad);
        //Cuando un boton se aprieta, se llama a la funcion onClick de esta clase
        botonCompletarActividad.setOnClickListener(this);


        //Creo que con ListView se podria conseguir algo parecido y mejor organizado, no se, pero por ahora lo dejo asi para probarlo
        LinearLayout grupoDatosActividad = new LinearLayout(this);
        grupoDatosActividad.setOrientation(LinearLayout.VERTICAL);
        grupoDatosActividad.addView(datosActividad);
        grupoDatosActividad.addView(botonCompletarActividad);
        /*
        layout.addView(datosActividad);
        layout.addView(botonCompletarActividad);
        */
        layout.addView(grupoDatosActividad);
    }



    @Override
    public void onClick(View botonCompletarActividad) {
        Actividad actividadACompletar = this.botonesDeActividades.get((Button)botonCompletarActividad);
        actividadACompletar.completar();

        //Quiero que cuanndo se haga click en completar se remueva la actividad del layout
        ViewGroup grupoDatosActividad = ((ViewGroup)botonCompletarActividad.getParent());
        ((ViewGroup)grupoDatosActividad.getParent()).removeView(grupoDatosActividad);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String elementoSeleccionado = elementosDeLaLista[position];

        if (elementoSeleccionado.equals("Objetivos")) {
            Intent intent = new Intent(this, VerObjetivosActivity.class);
            startActivity(intent);
        }
    }
}
