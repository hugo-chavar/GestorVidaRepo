package ar.com.fiuba.tddp1.gestorvida.estadisticas;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

/**
 * Created by User on 03/07/2017.
 */
public class IntegerValueFormatter implements IValueFormatter {

    @Override
    public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
        return "" + ((int) value);
    }
}
