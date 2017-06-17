package ar.com.fiuba.tddp1.gestorvida;

import android.app.DatePickerDialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;

/**
 * Created by User on 09/06/2017.
 */

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener{

    private TextView textView;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){

        //La fecha de hoy se setea como default
        Calendar calendario = Calendar.getInstance();
        int anio = calendario.get(Calendar.YEAR);
        int mes = calendario.get(Calendar.MONTH);
        int dia = calendario.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog( this.getActivity(), this, anio, mes, dia);
    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        //Hacer algo cuando se setee la fecha
        this.textView.setText(dayOfMonth + "/" + month + "/" + year );
    }


    public void setTextView(TextView textView) {
        this.textView = textView;
    }
}
