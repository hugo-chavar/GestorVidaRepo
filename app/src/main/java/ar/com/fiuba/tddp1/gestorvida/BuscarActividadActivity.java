package ar.com.fiuba.tddp1.gestorvida;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.Time;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import ar.com.fiuba.tddp1.gestorvida.dominio.Actividad;
import ar.com.fiuba.tddp1.gestorvida.dominio.Fecha;
import ar.com.fiuba.tddp1.gestorvida.dominio.Perfil;

public class BuscarActividadActivity extends AppCompatActivity {

    FloatingActionButton filterButton;
    LinkedList<Actividad> mockedActivities;

    private EditText mDesde;
    private EditText mHasta;

    private Date filtro_desde;
    private Date filtro_hasta;

    private AutoCompleteTextView mFiltroEtiqueta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_actividad);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        inicializarFiltroFechas();
        inicializarFiltroEtiquetas();

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

    private void inicializarFiltroFechas() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        try {
            filtro_desde = formatter.parse("1/1/1900");
            filtro_hasta = formatter.parse("31/12/2999");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void inicializarFiltroEtiquetas() {
        mFiltroEtiqueta = (AutoCompleteTextView) findViewById(R.id.filtro_etiquetas);
        List<String> etiquetas = new ArrayList<>(Perfil.getActividadDeEtiquetas().keySet());
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, etiquetas);
        mFiltroEtiqueta.setAdapter(adapter);

        mFiltroEtiqueta.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {

                    mostrarActividades();

                    return true;
                }
                return false;
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
                String desde = "", hasta = "";
                if (mDesde != null) {
                    desde = mDesde.getText().toString();
                }
                if (mHasta != null) {
                    hasta = mHasta.getText().toString();
                }
                try {
                    if (!desde.equals("")) {
                        filtro_desde = formatter.parse(desde);
                    } else {
                        filtro_desde = formatter.parse("1/1/1900");
                    }
                    if (!hasta.equals("")) {
                        filtro_hasta = formatter.parse(hasta);
                    } else {
                        filtro_hasta = formatter.parse("31/12/2999");
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
        for (Actividad actividad : this.mockedActivities) {
            LinearLayout elementoActividad = new LinearLayout(this);
            elementoActividad.setOrientation(LinearLayout.VERTICAL);

            TextView nombre = new TextView(this);
            nombre.setTextSize(24);
            String nombre_actividad = actividad.getNombre();
            nombre.setText(nombre_actividad);
            elementoActividad.addView(nombre);

            TextView inicio = new TextView(this);
            inicio.setTextSize(16);
            Fecha fechaInicio = actividad.getFechaInicio();
            inicio.setText("Inicio: " + fechaInicio.dia + "/" + fechaInicio.mes + "/" + fechaInicio.anio);
            elementoActividad.addView(inicio);

            TextView fin = new TextView(this);
            fin.setTextSize(16);
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

            String textoEnFiltroEtiquetas = mFiltroEtiqueta.getText().toString();

            Set<String> etiquetas = actividad.getEtiquetas();

            if (textoEnFiltroEtiquetas.equals("") || (etiquetas != null && matcheaEtiquetas(textoEnFiltroEtiquetas, etiquetas.toArray(new String[etiquetas.size()])))) {
                if ((filtro_desde.before(finicio) || filtro_desde.equals(finicio)) && (filtro_hasta.after(finicio) || filtro_hasta.equals(finicio))) {
                    elementoActividad.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            actividadOnClick(v);
                        }
                    });
                    lista.addView(elementoActividad);
                }
            }
        }
    }

    public boolean matcheaEtiquetas(String etiqueta, String[] etiquetas) {
        for (String etiq : etiquetas) {
            if ((etiq.toLowerCase()).contains(etiqueta.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    private void mockearActividades() {
        this.mockedActivities = new LinkedList<>();

        Actividad actividad = new Actividad("Correr 4km");
        actividad.setDescripcion("Quiero correr para prepararme fisicamente");
        actividad.setFechaInicio(new Fecha("8", "9", "2017"));
        actividad.setFechaFin(new Fecha("8", "9", "2017"));
        Set<String> etiquetas = new HashSet<>();
        etiquetas.add("Deportes");
        actividad.setEtiquetas(etiquetas);
        this.mockedActivities.add(actividad);

        Actividad actividad2 = new Actividad("Ir al cine");
        actividad2.setDescripcion("Quiero ir a ver Star Wars VIII");
        actividad2.setFechaInicio(new Fecha("15", "12", "2017"));
        actividad2.setFechaFin(new Fecha("15", "12", "2017"));
        Set<String> etiquetas2 = new HashSet<>();
        etiquetas2.add("Cine");
        actividad2.setEtiquetas(etiquetas2);
        this.mockedActivities.add(actividad2);

        Actividad actividad3 = new Actividad("Aprobar álgrebra");
        actividad3.setDescripcion("Quiero aprobarlaaa");
        actividad3.setFechaInicio(new Fecha("1", "7", "2017"));
        actividad3.setFechaFin(new Fecha("3", "7", "2017"));
        Set<String> etiquetas3 = new HashSet<>();
        etiquetas3.add("Facultad");
        actividad3.setEtiquetas(etiquetas3);
        this.mockedActivities.add(actividad3);

        Actividad actividad4 = new Actividad("Jugar al fútbol");
        actividad4.setDescripcion("Futbol con los pibes");
        actividad4.setFechaInicio(new Fecha("5", "8", "2017"));
        actividad4.setFechaFin(new Fecha("5", "8", "2017"));
        Set<String> etiquetas4 = new HashSet<>();
        etiquetas4.add("Deportes");
        actividad4.setEtiquetas(etiquetas4);
        this.mockedActivities.add(actividad4);

        Actividad actividad5 = new Actividad("Clases de guitarra");
        actividad5.setDescripcion("Clases para aprender a tocar");
        actividad5.setFechaInicio(new Fecha("5", "10", "2017"));
        actividad5.setFechaFin(new Fecha("10", "11", "2017"));
        Set<String> etiquetas5 = new HashSet<>();
        etiquetas5.add("Musica");
        actividad5.setEtiquetas(etiquetas5);
        this.mockedActivities.add(actividad5);

        Actividad actividad6 = new Actividad("Fiesta en mi casa");
        actividad6.setDescripcion("Bebidas y asado van por mi cuenta");
        actividad6.setFechaInicio(new Fecha("1", "10", "2017"));
        actividad6.setFechaFin(new Fecha("1", "10", "2017"));
        Set<String> etiquetas6 = new HashSet<>();
        etiquetas6.add("Fiestas");
        actividad6.setEtiquetas(etiquetas6);
        this.mockedActivities.add(actividad6);

        Actividad actividad7 = new Actividad("Fiesta en Avellaneda");
        actividad7.setDescripcion("Festejo por la clasificación de Racing a la Libertadores");
        actividad7.setFechaInicio(new Fecha("6", "7", "2017"));
        actividad7.setFechaFin(new Fecha("6", "7", "2017"));
        Set<String> etiquetas7 = new HashSet<>();
        etiquetas7.add("Fiestas");
        actividad7.setEtiquetas(etiquetas7);
        this.mockedActivities.add(actividad7);

        Actividad actividad8 = new Actividad("Cine gratis!");
        actividad8.setDescripcion("Pochoclos no");
        actividad8.setFechaInicio(new Fecha("6", "7", "2017"));
        actividad8.setFechaFin(new Fecha("20", "7", "2017"));
        Set<String> etiquetas8 = new HashSet<>();
        etiquetas8.add("Cine");
        actividad8.setEtiquetas(etiquetas8);
        this.mockedActivities.add(actividad8);
    }

    public void desdeOnClick(final View v) {

        mDesde = (EditText) v;
        DatePickerDialog.OnDateSetListener dpd = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {

                int s = monthOfYear + 1;
                String a = dayOfMonth + "/" + s + "/" + year;
                ((EditText) v).setText("" + a);
            }
        };

        Time date = new Time();
        DatePickerDialog d = new DatePickerDialog(BuscarActividadActivity.this, dpd, date.year, date.month, date.monthDay);
        Calendar calendario = Calendar.getInstance();
        d.updateDate(calendario.get(Calendar.YEAR), calendario.get(Calendar.MONTH), calendario.get(Calendar.DAY_OF_MONTH));
        d.show();

    }

    public void hastaOnClick(final View v) {

        mHasta = (EditText) v;
        DatePickerDialog.OnDateSetListener dpd = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {

                int s = monthOfYear + 1;
                String a = dayOfMonth + "/" + s + "/" + year;
                ((EditText) v).setText("" + a);
            }
        };

        Time date = new Time();
        DatePickerDialog d = new DatePickerDialog(BuscarActividadActivity.this, dpd, date.year, date.month, date.monthDay);
        Calendar calendario = Calendar.getInstance();
        d.updateDate(calendario.get(Calendar.YEAR), calendario.get(Calendar.MONTH), calendario.get(Calendar.DAY_OF_MONTH));
        d.show();

    }

    public void actividadOnClick(final View v) {
        //setFragment(new VerActividadBuscadaFragment());
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