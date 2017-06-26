package ar.com.fiuba.tddp1.gestorvida.objetivos;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.widget.EditText;

import java.util.ArrayList;

import ar.com.fiuba.tddp1.gestorvida.actividades.RecyclerFragment;
import ar.com.fiuba.tddp1.gestorvida.dominio.Actividad;
import ar.com.fiuba.tddp1.gestorvida.dominio.Objetivo;
import ar.com.fiuba.tddp1.gestorvida.dominio.Perfil;

/**
 * Created by User on 21/06/2017.
 */

public class ObjetivosFragment extends RecyclerFragment {


    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(getActivity());
    }

    @Override
    protected void configureAdapter() {
        ObjetivoAdapter objetivoAdapter = new ObjetivoAdapter(Perfil.getObjetivos(), getActivity());
        this.setConfiguredAdapter(objetivoAdapter);
    }

    @Override
    protected void goToAgregarElemento() {

        final EditText editTextObjetivoIngresado = new EditText(this.getActivity());
        editTextObjetivoIngresado.setMaxLines(1);
        editTextObjetivoIngresado.setInputType(InputType.TYPE_CLASS_TEXT);

        AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
        builder.setTitle("Agregar objetivo");
        builder.setView(editTextObjetivoIngresado);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                agregarObjetivo(editTextObjetivoIngresado.getText().toString());
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

    private void agregarObjetivo(String nombreObjetivo) {
        Perfil.agregarObjetivo( new Objetivo(nombreObjetivo) );
        this.getAdapter().notifyDataSetChanged();
    }
}
