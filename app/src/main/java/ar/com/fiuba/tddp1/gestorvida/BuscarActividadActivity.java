package ar.com.fiuba.tddp1.gestorvida;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.Time;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

import ar.com.fiuba.tddp1.gestorvida.dominio.Actividad;
import ar.com.fiuba.tddp1.gestorvida.dominio.Fecha;

public class BuscarActividadActivity extends AppCompatActivity {

    FloatingActionButton filterButton;
    LinkedList<Actividad> mockedActivities;

    private EditText mDesde;
    private EditText mHasta;

    private Date filtro_desde = null;
    private Date filtro_hasta = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_actividad);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mockearActividades();
        mostrarActividades();

        filterButton = (FloatingActionButton) findViewById(R.id.fab);
        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupInit();
            }
        });
    }

    public void popupInit() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Filtro");
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        EditText desde_filtro = new EditText(this);
        EditText hasta_filtro = new EditText(this);
        desde_filtro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                desdeOnClick(v);
            }
        });
        hasta_filtro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hastaOnClick(v);
            }
        });
        desde_filtro.setHint("Fecha desde");
        hasta_filtro.setHint("Fecha hasta");
        desde_filtro.setFocusable(false);
        hasta_filtro.setFocusable(false);
        layout.addView(desde_filtro);
        layout.addView(hasta_filtro);
        builder.setView(layout);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

                try {
                    if (mDesde != null && mHasta != null) {
                        filtro_desde = formatter.parse(mDesde.getText().toString());
                        filtro_hasta = formatter.parse(mHasta.getText().toString());
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                mostrarActividades();
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //No hacer nada
            }
        });
        builder.show();
    }

    private void mostrarActividades() {

        LinearLayout lista = (LinearLayout) findViewById(R.id.lista_actividades);
        lista.removeAllViews();

        //TODO: Esta debe ser la lista de todas las actividades en el server
        for (Actividad actividad: this.mockedActivities) {
            LinearLayout elementoActividad = new LinearLayout(this);
            elementoActividad.setOrientation(LinearLayout.VERTICAL);

            TextView nombre = new TextView(this);
            nombre.setTextSize(20);
            String nombre_actividad = actividad.getNombre();
            nombre.setText(nombre_actividad);
            elementoActividad.addView(nombre);

            TextView inicio = new TextView(this);
            inicio.setTextSize(14);
            Fecha fechaInicio = actividad.getFechaInicio();
            inicio.setText("Inicio: " + fechaInicio.dia + "/" + fechaInicio.mes + "/" + fechaInicio.anio);
            elementoActividad.addView(inicio);

            TextView fin = new TextView(this);
            fin.setTextSize(14);
            Fecha fechaFin = actividad.getFechaFin();
            fin.setText("Fin: " + fechaFin.dia + "/" + fechaFin.mes + "/" + fechaFin.anio);
            elementoActividad.addView(fin);

            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            Date finicio = null;
            try {
                finicio = formatter.parse(fechaFin.dia + "/" + fechaFin.mes + "/" + fechaFin.anio);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if ((filtro_hasta == null && filtro_desde == null) || (filtro_desde.before(finicio) && filtro_hasta.after(finicio))) {
                lista.addView(elementoActividad);
            }
        }
    }

    private void mockearActividades() {
        this.mockedActivities = new LinkedList<>();

        Actividad actividad = new Actividad("Correr 4km");
        actividad.setDescripcion("Quiero correr para prepararme fisicamente");
        actividad.setFechaInicio(new Fecha("1","6","2017"));
        actividad.setFechaFin(new Fecha("3","8","2017"));
        this.mockedActivities.add(actividad);

        Actividad actividad2 = new Actividad("Ir al cine");
        actividad2.setDescripcion("Quiero ir a ver Star Wars VIII");
        actividad2.setFechaInicio(new Fecha("15","12","2017"));
        actividad2.setFechaFin(new Fecha("15","12","2017"));
        this.mockedActivities.add(actividad2);

        Actividad actividad3 = new Actividad("Aprobar álgrebra");
        actividad3.setDescripcion("Quiero aprobarlaaa");
        actividad3.setFechaInicio(new Fecha("1","7","2017"));
        actividad3.setFechaFin(new Fecha("3","7","2017"));
        this.mockedActivities.add(actividad3);
    }

    public void desdeOnClick(final View v) {

        mDesde = (EditText) v;
        DatePickerDialog.OnDateSetListener dpd = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {

                int s=monthOfYear+1;
                String a = dayOfMonth+"/"+s+"/"+year;
                ((EditText)v).setText(""+a);
            }
        };

        Time date = new Time();
        DatePickerDialog d = new DatePickerDialog(BuscarActividadActivity.this, dpd, date.year ,date.month, date.monthDay);
        d.updateDate(2017,6,1);
        d.show();

    }

    public void hastaOnClick(final View v) {

        mHasta = (EditText) v;
        DatePickerDialog.OnDateSetListener dpd = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {

                int s=monthOfYear+1;
                String a = dayOfMonth+"/"+s+"/"+year;
                ((EditText)v).setText(""+a);
            }
        };

        Time date = new Time();
        DatePickerDialog d = new DatePickerDialog(BuscarActividadActivity.this, dpd, date.year ,date.month, date.monthDay);
        d.updateDate(2017,6,1);
        d.show();

    }

}
