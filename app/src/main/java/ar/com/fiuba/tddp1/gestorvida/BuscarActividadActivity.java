package ar.com.fiuba.tddp1.gestorvida;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.text.format.Time;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import ar.com.fiuba.tddp1.gestorvida.actividades.DetalleBuscarActividadFragment;
import ar.com.fiuba.tddp1.gestorvida.comunes.FragmentLoader;
import ar.com.fiuba.tddp1.gestorvida.dominio.Actividad;
import ar.com.fiuba.tddp1.gestorvida.dominio.Beneficio;
import ar.com.fiuba.tddp1.gestorvida.dominio.Etiqueta;
import ar.com.fiuba.tddp1.gestorvida.dominio.Fecha;
import ar.com.fiuba.tddp1.gestorvida.dominio.Perfil;

//import android.support.v4.app.Fragment;

public class BuscarActividadActivity extends Fragment {

    FloatingActionButton filterButton;
    LinkedList<Actividad> mockedActivities;

    private EditText mDesde;
    private EditText mHasta;

    private AutoCompleteTextView mFiltroEtiqueta;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_buscar_actividad, container, false);

        inicializarFiltroEtiquetas(rootView);

        mockearActividades();
        mostrarActividades(rootView);

        filterButton = (FloatingActionButton) rootView.findViewById(R.id.fab);
        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupInit();
            }
        });
        return rootView;
    }

    private void inicializarFiltroEtiquetas(View view) {
        mFiltroEtiqueta = (AutoCompleteTextView) view.findViewById(R.id.filtro_etiquetas);
        List<String> etiquetas = new ArrayList<>(Perfil.getNombresEtiquetas());
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_dropdown_item_1line, etiquetas);
        mFiltroEtiqueta.setAdapter(adapter);

        mFiltroEtiqueta.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {

                    mostrarActividades(getView());

                    return true;
                }
                return false;
            }
        });
    }

    public void popupInit() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
        builder.setTitle("Filtro");
        LinearLayout layout = new LinearLayout(this.getActivity());
        layout.setOrientation(LinearLayout.VERTICAL);
        EditText desde_filtro = new EditText(this.getActivity());
        EditText hasta_filtro = new EditText(this.getActivity());
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
        Date f_desde = ((MainActivity) getActivity()).getFiltro_desde();
        Date f_hasta = ((MainActivity) getActivity()).getFiltro_hasta();
        Calendar cal = Calendar.getInstance();
        cal.setTime(f_desde);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        if (day != 1 && month != 1 && year != 1900) {
            desde_filtro.setText(day + "/" + month + "/" + year);
        }
        cal.setTime(f_hasta);
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH) + 1;
        day = cal.get(Calendar.DAY_OF_MONTH);
        if (day != 31 && month != 12 && year != 2999) {
            hasta_filtro.setText(day + "/" + month + "/" + year);
        }
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
                        ((MainActivity) getActivity()).setFiltro_desde(formatter.parse(desde));
                    }
                    if (!hasta.equals("")) {
                        ((MainActivity) getActivity()).setFiltro_hasta(formatter.parse(hasta));
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                mostrarActividades(getView());
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ((MainActivity) getActivity()).inicializarFiltroFechas();
                mostrarActividades(getView());
            }
        });
        builder.show();
    }

    private void mostrarActividades(View view) {

        LinearLayout lista = (LinearLayout) view.findViewById(R.id.lista_actividades);
        lista.removeAllViews();

        //TODO: Esta debe ser la lista de todas las actividades en el server
        for (final Actividad actividad : this.mockedActivities) {
            agregarActividadEnLista(lista, actividad);
        }
    }

    private void agregarActividadEnLista(LinearLayout lista, final Actividad actividad) {
        LinearLayout elementoActividad = new LinearLayout(this.getActivity());
        elementoActividad.setOrientation(LinearLayout.VERTICAL);

        TextView nombre = new TextView(this.getActivity());
        nombre.setTextSize(24);
        String nombre_actividad = actividad.getNombre();
        nombre.setText(nombre_actividad);
        elementoActividad.addView(nombre);

        TextView inicio = new TextView(this.getActivity());
        inicio.setTextSize(16);
        Fecha fechaInicio = actividad.getFechaInicio();
        inicio.setText("Inicio: " + fechaInicio.dia + "/" + fechaInicio.mes + "/" + fechaInicio.anio);
        elementoActividad.addView(inicio);

        TextView fin = new TextView(this.getActivity());
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

        Set<String> etiquetas = actividad.getNombresDeEtiquetas();

        Date f_desde = ((MainActivity) getActivity()).getFiltro_desde();
        Date f_hasta = ((MainActivity) getActivity()).getFiltro_hasta();

        if (textoEnFiltroEtiquetas.equals("") || (etiquetas != null && matcheaEtiquetas(textoEnFiltroEtiquetas, etiquetas.toArray(new String[etiquetas.size()])))) {
            if ((f_desde.before(finicio) || f_desde.equals(finicio)) && (f_hasta.after(finicio) || f_hasta.equals(finicio))) {
                elementoActividad.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        actividadOnClick(v, actividad);
                    }
                });
                lista.addView(elementoActividad);
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
        actividad.setPrioridad("ALTA");
        Beneficio beneficio = new Beneficio();
        beneficio.setPrecio(100);
        beneficio.setDescuento(20);
        beneficio.setDescripcion("TETSTSTSTSTST");
        actividad.addBeneficio(beneficio);
        this.mockedActivities.add(actividad);

        Actividad actividad2 = new Actividad("Ir al cine");
        actividad2.setDescripcion("Quiero ir a ver Star Wars VIII");
        actividad2.setFechaInicio(new Fecha("15", "12", "2017"));
        actividad2.setFechaFin(new Fecha("15", "12", "2017"));
        Set<Etiqueta> etiquetas2 = new HashSet<>();
        etiquetas2.add(new Etiqueta("Cine", Color.GREEN));
        etiquetas2.add(new Etiqueta("Peliculas", Color.RED));
        actividad2.setEtiquetas(etiquetas2);
        actividad2.setPrioridad("BAJA");
        this.mockedActivities.add(actividad2);

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
        this.mockedActivities.add(actividad3);

        Actividad actividad4 = new Actividad("Jugar al fútbol");
        actividad4.setDescripcion("Futbol con los pibes");
        actividad4.setFechaInicio(new Fecha("5", "8", "2017"));
        actividad4.setFechaFin(new Fecha("5", "8", "2017"));
        Set<Etiqueta> etiquetas4 = new HashSet<>();
        etiquetas4.add(new Etiqueta("Deportes", Color.GREEN));
        actividad4.setEtiquetas(etiquetas4);
        this.mockedActivities.add(actividad4);

        Actividad actividad5 = new Actividad("Clases de guitarra");
        actividad5.setDescripcion("Clases para aprender a tocar");
        actividad5.setFechaInicio(new Fecha("5", "10", "2017"));
        actividad5.setFechaFin(new Fecha("10", "11", "2017"));
        Set<Etiqueta> etiquetas5 = new HashSet<>();
        etiquetas5.add(new Etiqueta("Musica", Color.GREEN));
        etiquetas5.add(new Etiqueta("Guitarra", Color.BLUE));
        actividad5.setEtiquetas(etiquetas5);
        this.mockedActivities.add(actividad5);

        Actividad actividad6 = new Actividad("Fiesta en mi casa");
        actividad6.setDescripcion("Bebidas y asado van por mi cuenta");
        actividad6.setFechaInicio(new Fecha("1", "10", "2017"));
        actividad6.setFechaFin(new Fecha("1", "10", "2017"));
        Set<Etiqueta> etiquetas6 = new HashSet<>();
        etiquetas6.add(new Etiqueta("Fiestas", Color.GREEN));
        actividad6.setEtiquetas(etiquetas6);
        this.mockedActivities.add(actividad6);

        Actividad actividad7 = new Actividad("Fiesta en Avellaneda");
        actividad7.setDescripcion("Festejo por la clasificación de Racing a la Libertadores");
        actividad7.setFechaInicio(new Fecha("6", "7", "2017"));
        actividad7.setFechaFin(new Fecha("6", "7", "2017"));
        Set<Etiqueta> etiquetas7 = new HashSet<>();
        etiquetas7.add(new Etiqueta("Fiestas", Color.GREEN));
        actividad7.setEtiquetas(etiquetas7);
        this.mockedActivities.add(actividad7);

        Actividad actividad8 = new Actividad("Cine gratis!");
        actividad8.setDescripcion("Pochoclos no");
        actividad8.setFechaInicio(new Fecha("6", "7", "2017"));
        actividad8.setFechaFin(new Fecha("20", "7", "2017"));
        Set<Etiqueta> etiquetas8 = new HashSet<>();
        etiquetas8.add(new Etiqueta("Cine", Color.GREEN));
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
        DatePickerDialog d = new DatePickerDialog(BuscarActividadActivity.this.getActivity(), dpd, date.year, date.month, date.monthDay);
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
        DatePickerDialog d = new DatePickerDialog(BuscarActividadActivity.this.getActivity(), dpd, date.year, date.month, date.monthDay);
        Calendar calendario = Calendar.getInstance();
        d.updateDate(calendario.get(Calendar.YEAR), calendario.get(Calendar.MONTH), calendario.get(Calendar.DAY_OF_MONTH));
        d.show();

    }

    public void actividadOnClick(final View v, final Actividad actividad) {
        ((MainActivity) getActivity()).setActividad_detalle(actividad);
        FragmentLoader.load(getActivity(), new DetalleBuscarActividadFragment());
    }

}