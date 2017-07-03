package ar.com.fiuba.tddp1.gestorvida.estadisticas;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

import ar.com.fiuba.tddp1.gestorvida.R;

//import android.support.v4.app.Fragment;

/**
 * Created by User on 24/06/2017.
 */

public class EstadisticasFragment extends Fragment {




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_line_chart, container,false);

        LineChart grafico = (LineChart) view.findViewById(R.id.ejemploGrafico);

        //Primero se crea una lista de Entry (un punto X,Y) con los valores que pongamos
        Integer[] valoresX = new Integer[]{1,2,3,4,5,6};
        Integer[] valoresY = new Integer[]{1,2,8,4,2,6};
        List<Entry> puntos = new ArrayList<Entry>();
        for (int i = 0; i < valoresX.length; i++ ) {
            puntos.add( new Entry(valoresX[i], valoresY[i]) );
        }

        //Despues se crea un set de datos con los puntos que se pasaron
        LineDataSet datosSet = new LineDataSet(puntos, "Etiqueta");
        /*Aca se le puede dar formato
        datos.setColor(...)
        datos.setValueTextColor(...);
        */

        //Por ultimo se crea una LineData a partir del LineDataSet y se agrega al grafico
        LineData lineaData = new LineData(datosSet);
        grafico.setData(lineaData);
        grafico.invalidate(); //refresh

        return view;
    }
}
