package ar.com.fiuba.tddp1.gestorvida.actividades;


import android.app.Fragment;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.widget.Space;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ar.com.fiuba.tddp1.gestorvida.DatePickerFragment;
import ar.com.fiuba.tddp1.gestorvida.MainActivity;
import ar.com.fiuba.tddp1.gestorvida.R;
import ar.com.fiuba.tddp1.gestorvida.TimePickerFragment;
import ar.com.fiuba.tddp1.gestorvida.comunes.FragmentLoader;
import ar.com.fiuba.tddp1.gestorvida.dominio.Actividad;
import ar.com.fiuba.tddp1.gestorvida.dominio.Etiqueta;
import ar.com.fiuba.tddp1.gestorvida.dominio.Fecha;
import ar.com.fiuba.tddp1.gestorvida.dominio.Objetivo;
import ar.com.fiuba.tddp1.gestorvida.dominio.Perfil;


public class AgregarActividadFragment extends Fragment {

    private Map<Integer, TextView> textosFechas = new HashMap<>();
    private Set<Etiqueta> listaDeEtiquetas = new HashSet<>();
    private LayoutInflater inflater;

    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        this.inflater = inflater;
        view = this.inflater.inflate(R.layout.activity_agregar_actividad, container,false);

        //A cada boton de fecha le asocio un textView en donde se va a escribir la fecha seleccionada
        this.textosFechas.put(R.id.imageViewInicioActividad, (TextView) view.findViewById(R.id.textViewInicioActividad) );
        this.textosFechas.put(R.id.imageViewFinActividad, (TextView) view.findViewById(R.id.textViewFinActividad) );
        this.textosFechas.put(R.id.imageViewRecordatorio, (TextView) view.findViewById(R.id.textViewFechaRecordatorio) );

        String[] prioridades = new String[] {"ALTA", "MEDIA", "BAJA"};
        Spinner spinnerPrioridades = (Spinner) view.findViewById(R.id.spinnerPrioridades);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), R.layout.support_simple_spinner_dropdown_item, prioridades);
        spinnerPrioridades.setAdapter(adapter);

        //No termino de entender para que sirve
        //spinnerPrioridades.setPrompt("Como funciona esto, el prompt? no lo veo cuando lo corro");

        Spinner spinnerObjetivos = (Spinner) view.findViewById(R.id.spinnerObjetivos);
        LinkedList<Objetivo> objetivos = Perfil.getObjetivos();
        ArrayAdapter<Objetivo> adapterObjetivos = new ArrayAdapter<Objetivo>(this.getActivity(), R.layout.support_simple_spinner_dropdown_item, objetivos);
        spinnerObjetivos.setAdapter(adapterObjetivos);

        //Seteo el clickListener de los botones de fechas
        ImageView buttonInicioActividad = (ImageView) view.findViewById(R.id.imageViewInicioActividad);
        buttonInicioActividad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDatePicker(v);
            }
        });

        ImageView buttonFinActividad = (ImageView) view.findViewById(R.id.imageViewFinActividad);
        buttonFinActividad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDatePicker(v);
            }
        });


        ImageView buttonAgregarEtiqueta = (ImageView) view.findViewById(R.id.buttonAgregarEtiqueta);
        buttonAgregarEtiqueta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregarEtiqueta(v);
            }
        });


        CheckBox checkBoxTiempoEstimado = (CheckBox) view.findViewById(R.id.checkBoxTiempoEstimado);
        checkBoxTiempoEstimado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickTiempoEstimado(v);
            }
        });

        ImageView buttonRecordatorio = (ImageView) view.findViewById(R.id.imageViewRecordatorio);
        buttonRecordatorio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDatePicker(v);
            }
        });


        RadioGroup groupPeriodicidad = (RadioGroup) view.findViewById(R.id.radioGroupPerioridicidad);
        groupPeriodicidad.check(R.id.radioButtonPeriodicidad1SolaVez);
        groupPeriodicidad.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                //El edit text de X dias SOLO se activa si se eligio el radioButton corespondiente
                getView().findViewById(R.id.editTextXDias).setEnabled( checkedId == R.id.radioButtonPeriodicidadCadaXDias );
            }
        });

        //deshabilito el navigationDrawer
        FragmentLoader.setDrawerEnabled((MainActivity)getActivity(), false);

        setHasOptionsMenu(true);


        //TODO: ESTO SOLO ESTA ACA PARA TESTEAR
        final TextView tiempo  = (TextView) view.findViewById(R.id.textViewParaTestearElTimePicker);
        tiempo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarTimePicker(v, tiempo);
            }
        });


        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {

        menuInflater.inflate(R.menu.menu_buscar_actividad, menu);

    }


    public void mostrarTimePicker(View view, TextView tiempo) {
        TimePickerFragment timePickerFragment = new TimePickerFragment();
        timePickerFragment.setTextView((TextView) view);
        timePickerFragment.show(this.getFragmentManager(), "timePicker");
    }


    private Map<ImageView, Integer> mapaBotonColor;
    private Integer colorEtiquetaElegido = Color.rgb(250,250,250);
    private ImageView imageViewColorElegido = null;

    private void agregarEtiqueta(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
        builder.setTitle("Agregar etiqueta");


        final LinearLayout parametrosEtiqueta = new LinearLayout(this.getActivity());
        parametrosEtiqueta.setOrientation(LinearLayout.VERTICAL);

        //TODO: agregarle un autocompletar y un color para seleccionar
        final AutoCompleteTextView nombreEtiquetaAutoComplete = new AutoCompleteTextView(this.getActivity());
        List<String> etiquetas = new ArrayList<>(Perfil.getNombresEtiquetas());
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_dropdown_item_1line, etiquetas);
        nombreEtiquetaAutoComplete.setAdapter(adapter);
        parametrosEtiqueta.addView(nombreEtiquetaAutoComplete);


        LinearLayout linearLayoutColores = new LinearLayout(this.getActivity());
        linearLayoutColores.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        linearLayoutColores.setOrientation(LinearLayout.HORIZONTAL);

        Space espacioInicialEntreColores = new Space(this.getActivity());
        espacioInicialEntreColores.setLayoutParams(new LinearLayout.LayoutParams( 0, 1, 1));
        linearLayoutColores.addView(espacioInicialEntreColores);




        this.mapaBotonColor = new HashMap<>();
        this.colorEtiquetaElegido = Color.rgb(250,250,250);
        for (int i = 0; i < Etiqueta.COLORES_ETIQUETAS.length; i++) {
            ImageView circuloColorImage = new ImageView(this.getActivity());
            GradientDrawable circuloColor = (GradientDrawable) this.getActivity().getDrawable(R.drawable.circulo_color);
            circuloColor.setColor(Etiqueta.COLORES_ETIQUETAS[i]);
            circuloColor.setStroke(1, Etiqueta.COLORES_BORDES[i]);


            circuloColorImage.setBackground(circuloColor);
            //Seteo el default
            if (i == 0) {
                circuloColorImage.setImageResource(R.drawable.check);
                this.imageViewColorElegido = circuloColorImage;
            }

            int radio = getContext().getResources().getDimensionPixelSize(R.dimen.circulo_size);
            circuloColorImage.setLayoutParams( new LinearLayout.LayoutParams( radio, radio));
            //circuloColorImage.setLayoutParams( new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)  );
            //circuloColorImage.setLayoutParams( new ViewGroup.LayoutParams(10,10)  );
            //circuloColorImage.setLayoutParams( new LinearLayout.LayoutParams( 0, radio , 1));

            Space espacioEntreColores = new Space(this.getActivity());
            espacioEntreColores.setLayoutParams(new LinearLayout.LayoutParams( 0, 1, 1));


            this.mapaBotonColor.put(circuloColorImage, Etiqueta.COLORES_ETIQUETAS[i]);
            circuloColorImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View imageColorClickeada) {
                    //La anterior elegida se pone sin el check
                    imageViewColorElegido.setImageResource(android.R.color.transparent);
                    colorEtiquetaElegido = mapaBotonColor.get(imageColorClickeada);
                    ((ImageView)imageColorClickeada).setImageResource(R.drawable.check);
                    imageViewColorElegido = (ImageView) imageColorClickeada;
                }
            });

            linearLayoutColores.addView(circuloColorImage);
            linearLayoutColores.addView(espacioEntreColores);
        }
        parametrosEtiqueta.addView(linearLayoutColores);

        builder.setView(parametrosEtiqueta);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                agregarEtiqueta(nombreEtiquetaAutoComplete.getText().toString());
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

    public void agregarEtiqueta(String nombreEtiqueta) {

        LinearLayout grupoEtiquetasView = ( (LinearLayout) this.getView().findViewById(R.id.linearLayoutEtiquetas));

        View etiquetaIndividualView = inflater.inflate(R.layout.layout_etiqueta, null);

        TextView textViewEtiquetaIngresada = (TextView) etiquetaIndividualView.findViewById(R.id.nombreEtiqueta);
        textViewEtiquetaIngresada.setText(nombreEtiqueta);

        grupoEtiquetasView.addView(etiquetaIndividualView);


        this.listaDeEtiquetas.add(new Etiqueta(nombreEtiqueta, colorEtiquetaElegido));
    }

    public void mostrarDatePicker(View view) {
        TextView textoFecha = this.textosFechas.get(view.getId());
        DatePickerFragment datePickerFragment = new DatePickerFragment();
        datePickerFragment.setTextView(textoFecha);
        datePickerFragment.show(((AppCompatActivity)getActivity()).getSupportFragmentManager(),"Ingrese una fecha");
        //datePickerFragment.show(this.getFragmentManager(), "Ingrese una fecha");
    }

    public void clickTiempoEstimado(View view) {
        boolean estaCheckeado = ((CheckBox) view).isChecked();
        //El view que me llega es solamente el boton de checkear
        //Por eso para getear el editTextTiempoEstimado se tiene que primero acceder a root de view
        View tiempoEstimado = view.getRootView().findViewById(R.id.editTextTiempoEstimado);
        tiempoEstimado.setEnabled(estaCheckeado);
    }

    public void agregarActividad(/*View view*/) {

        //Lo mismo que en el caso del tiempo estimado
        //View rootView = view.getRootView();
        View rootView = view;
        //Se crea la actividad con el nombre
        Actividad nuevaActividad = new Actividad(  ((EditText) rootView.findViewById(R.id.edittextNombre)).getText().toString()   );

        //Se le setea la descripcion
        nuevaActividad.setDescripcion( ((EditText) rootView.findViewById(R.id.edittextDescripcion)).getText().toString() );


        //Parseo la fecha
        Fecha fechaInicio = this.parsearFecha( (TextView) rootView.findViewById(R.id.textViewInicioActividad));
        Fecha fechaFin = this.parsearFecha( (TextView) rootView.findViewById(R.id.textViewFinActividad));
        nuevaActividad.setFechaInicio(fechaInicio);
        nuevaActividad.setFechaFin(fechaFin);

        //Agrego la prioridad, por ahora es solamente un string con 3 opciones ALTA, MEDIA, BAJA
        Spinner spinnerPrioridades = (Spinner) rootView.findViewById(R.id.spinnerPrioridades);
        String prioridadSeleccionada = (String) spinnerPrioridades.getSelectedItem();
        nuevaActividad.setPrioridad( prioridadSeleccionada );

        nuevaActividad.setEtiquetas(this.listaDeEtiquetas);

        Fecha fechaRecordatorio = this.parsearFecha( (TextView) rootView.findViewById(R.id.textViewFechaRecordatorio));
        nuevaActividad.setFechaRecordatorio(fechaRecordatorio);

        RadioGroup groupPeriodicidad = (RadioGroup) rootView.findViewById(R.id.radioGroupPerioridicidad);
        int periodicidad = 0;
        int periodicidadSeleccionada = groupPeriodicidad.getCheckedRadioButtonId();
        if (periodicidadSeleccionada == R.id.radioButtonPeriodicidadDiario) {
            periodicidad = 1;
        }
        else if (periodicidadSeleccionada == R.id.radioButtonPeriodicidadSemanal) {
            periodicidad = 7;
        }
        else if (periodicidadSeleccionada == R.id.radioButtonPeriodicidadCadaXDias) {
            periodicidad = Integer.parseInt( ( (EditText) rootView.findViewById(R.id.editTextXDias)).getText().toString() );
        }
        nuevaActividad.setPeriodicidad(periodicidad);


        CheckBox checkBoxTiempoEstimado = (CheckBox) rootView.findViewById(R.id.checkBoxTiempoEstimado);
        if (checkBoxTiempoEstimado.isChecked()) {
            EditText editTextTiempoEstimado = (EditText) rootView.findViewById(R.id.editTextTiempoEstimado);
            String[] tiempoEstimado = editTextTiempoEstimado.getText().toString().split(":");
            nuevaActividad.setTiempoEstimado(tiempoEstimado[0], tiempoEstimado[1]);
        }


        RadioGroup groupTipoActividad = (RadioGroup) rootView.findViewById(R.id.radioGroupTipoActividad);
        nuevaActividad.esActividadPrivada(groupTipoActividad.getCheckedRadioButtonId() == R.id.radioButtonActividadPrivada);

        //esto deberia ser solo si se eligio agregarlo a un objetivo
        Objetivo objetivoSeleccionado =  (Objetivo)((Spinner)rootView.findViewById(R.id.spinnerObjetivos)).getSelectedItem();
        objetivoSeleccionado.agregarActividad(nuevaActividad);

        addActividad(nuevaActividad);

    }

    private Fecha parsearFecha(TextView textoFecha) {
        return new Fecha (textoFecha.getText().toString());
    }

    private void addActividad(Actividad actividad) {


        Perfil.agregarActividad(actividad);

        /*
        AgregarActividadListener listener = new AgregarActividadListener(getActivity(), actividad);;

        RequestSender requestSender = new RequestSender(getActivity());
        Map<String,String> _params;
        _params = new HashMap<String,String>();
        _params.put("username", name);
        _params.put("password", password);

        JSONObject obj = new JSONObject(_params);

        String url = getString(R.string.url) + "users/authenticate";


        requestSender.doPost(listener, url, new JSONObject(_params));
        */
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        Log.d("AgregarActividad", "Se hizo clic en la opcion " + id);

        switch (id) {
            case R.id.action_save_activity:
                Log.d("AgregarActividad", "Grabando..");
                Toast.makeText(getActivity(), "Grabando actividad ... ", Toast.LENGTH_SHORT).show();
                agregarActividad();
                getActivity().onBackPressed();
                break;
            default:
                super.onOptionsItemSelected(item);
        }


        return true;
    }

}
