package ar.com.fiuba.tddp1.gestorvida;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * Created by User on 01/07/2017.
 */

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    TextView textViewTiempoElegido;

    public void setTextView (TextView textViewTiempoElegido) {
        this.textViewTiempoElegido = textViewTiempoElegido;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar calendario = Calendar.getInstance();
        int horas = calendario.get(Calendar.HOUR_OF_DAY);
        int minutos = calendario.get(Calendar.MINUTE);

        //Si la activity esta configurada para 24hs entonces da true, sino, false
        boolean es24Horas = DateFormat.is24HourFormat(getActivity());
        es24Horas = true;
        return new TimePickerDialog(getActivity(), this, horas, minutos, es24Horas);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        this.textViewTiempoElegido.setText(hourOfDay + ":" + minute);
    }
}
