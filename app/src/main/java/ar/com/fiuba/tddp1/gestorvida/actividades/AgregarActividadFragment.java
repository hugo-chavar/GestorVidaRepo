package ar.com.fiuba.tddp1.gestorvida.actividades;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ar.com.fiuba.tddp1.gestorvida.DatePickerFragment;
import ar.com.fiuba.tddp1.gestorvida.R;
import ar.com.fiuba.tddp1.gestorvida.dominio.Actividad;
import ar.com.fiuba.tddp1.gestorvida.dominio.Fecha;
import ar.com.fiuba.tddp1.gestorvida.dominio.Objetivo;
import ar.com.fiuba.tddp1.gestorvida.dominio.Perfil;

/**
 * Created by User on 24/06/2017.
 */

public class AgregarActividadFragment extends Fragment{

    private Map<Integer, TextView> textosFechas = new HashMap<>();
    private Set<String> listaDeEtiquetas = new HashSet<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_agregar_actividad, container,false);

        //A cada boton de fecha le asocio un textView en donde se va a escribir la fecha seleccionada
        this.textosFechas.put(R.id.buttonInicioActividad, (TextView) view.findViewById(R.id.textViewInicioActividad) );
        this.textosFechas.put(R.id.buttonFinActividad, (TextView) view.findViewById(R.id.textViewFinActividad) );

        String[] prioridades = new String[] {"ALTA", "MEDIA", "BAJA"};
        Spinner spinnerPrioridades = (Spinner) view.findViewById(R.id.spinnerPrioridades);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), R.layout.support_simple_spinner_dropdown_item, prioridades);
        spinnerPrioridades.setAdapter(adapter);

        //No termino de entender para que sirve
        spinnerPrioridades.setPrompt("Como funciona esto, el prompt? no lo veo cuando lo corro");

        Spinner spinnerObjetivos = (Spinner) view.findViewById(R.id.spinnerObjetivos);
        LinkedList<Objetivo> objetivos = Perfil.getObjetivos();
        ArrayAdapter<Objetivo> adapterObjetivos = new ArrayAdapter<Objetivo>(this.getActivity(), R.layout.support_simple_spinner_dropdown_item, objetivos);
        spinnerObjetivos.setAdapter(adapterObjetivos);


        //Seteo el clickListener de los botones de fechas
        Button buttonInicioActividad = (Button) view.findViewById(R.id.buttonInicioActividad);
        buttonInicioActividad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDatePicker(v);
            }
        });

        Button buttonFinActividad = (Button) view.findViewById(R.id.buttonFinActividad);
        buttonFinActividad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDatePicker(v);
            }
        });


        Button buttonAgregarEtiqueta = (Button) view.findViewById(R.id.buttonAgregarEtiqueta);
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


        Button buttonAgregarActividad = (Button) view.findViewById(R.id.buttonAgregarActividad);
        buttonAgregarActividad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregarActividad(v);
            }
        });


        return view;
    }

    private void agregarEtiqueta(View view) {
        final EditText editTextEtiquetaIngresada = new EditText(this.getActivity());
        editTextEtiquetaIngresada.setMaxLines(1);
        editTextEtiquetaIngresada.setInputType(InputType.TYPE_CLASS_TEXT);

        AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
        builder.setTitle("Agregar etiqueta");
        builder.setView(editTextEtiquetaIngresada);
        builder.setPositiveButton("Agregar etiqueta", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                agregarEtiqueta(editTextEtiquetaIngresada.getText().toString());
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
        TextView textViewEtiquetaIngresada = new TextView(this.getActivity());
        textViewEtiquetaIngresada.setText(nombreEtiqueta);
        ( (LinearLayout) this.getView().findViewById(R.id.linearLayoutEtiquetas)).addView(textViewEtiquetaIngresada);
        this.listaDeEtiquetas.add(nombreEtiqueta);
    }


    public void mostrarDatePicker(View view) {
        TextView textoFecha = this.textosFechas.get(view.getId());
        DatePickerFragment datePickerFragment = new DatePickerFragment();
        datePickerFragment.setTextView(textoFecha);
        datePickerFragment.show(this.getFragmentManager(), "Ingrese una fecha");
    }

    public void clickTiempoEstimado(View view) {
        boolean estaCheckeado = ((CheckBox) view).isChecked();
        //El view que me llega es solamente el boton de checkear
        //Por eso para getear el editTextTiempoEstimado se tiene que primero acceder a root de view
        View tiempoEstimado = view.getRootView().findViewById(R.id.editTextTiempoEstimado);
        tiempoEstimado.setEnabled(estaCheckeado);
    }

    public void agregarActividad(View view) {

        //Lo mismo que en el caso del tiempo estimado
        View rootView = view.getRootView();
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
        Spinner spinnerPrioridades = (Spinner) view.findViewById(R.id.spinnerPrioridades);
        nuevaActividad.setPrioridad( (String) spinnerPrioridades.getSelectedItem() );

        nuevaActividad.setEtiquetas(this.listaDeEtiquetas);




        //Se le setea todo lo demas que haya que setearle





        //esto deberia ser solo si se eligio agregarlo a un objetivo
        Objetivo objetivoSeleccionado =  (Objetivo)((Spinner)rootView.findViewById(R.id.spinnerObjetivos)).getSelectedItem();
        objetivoSeleccionado.agregarActividad(nuevaActividad);
        Perfil.agregarActividad(nuevaActividad);
    }

    private Fecha parsearFecha(TextView textoFecha) {
        String fecha = textoFecha.getText().toString();
        String[] fechaParseada = fecha.split("/");
        if (fechaParseada.length == 3) {
            //Si el lenght es diferente de 3 entonces no hay una fecha ingresada
            return new Fecha(fechaParseada[0], fechaParseada[1], fechaParseada[2]);
        }
        return null;

    }
}
