package ar.com.fiuba.tddp1.gestorvida;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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

    LinearLayout layoutOfPopup;
    PopupWindow popupFilter;
    Button filterOkButton;
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
        init();
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

    public void init() {
        filterOkButton = (Button) findViewById(R.id.button_ok_filter);
        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutOfPopup = (LinearLayout) inflater.inflate(R.layout.layout_filter, null);
    }

    public void popupInit() {
        popupFilter = new PopupWindow(layoutOfPopup, LinearLayout.LayoutParams.FILL_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        popupFilter.setContentView(layoutOfPopup);
        popupFilter.setFocusable(true);

        // Clear the default translucent background
        popupFilter.setBackgroundDrawable(new ColorDrawable(Color.WHITE));

        // Displaying the popup at the specified location, + offsets.
        popupFilter.showAtLocation(layoutOfPopup, Gravity.NO_GRAVITY, 0, Math.round(filterButton.getY()));
    }

    public void dismissPopup(View v) {

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        try {
            if (mDesde != null && mHasta != null) {
                this.filtro_desde = formatter.parse(mDesde.getText().toString());
                this.filtro_hasta = formatter.parse(mHasta.getText().toString());
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        mostrarActividades();
        popupFilter.dismiss();
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
        d.show();

    }

}
