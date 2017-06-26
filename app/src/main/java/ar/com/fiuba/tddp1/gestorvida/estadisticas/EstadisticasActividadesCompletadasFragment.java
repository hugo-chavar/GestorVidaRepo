package ar.com.fiuba.tddp1.gestorvida.estadisticas;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

import ar.com.fiuba.tddp1.gestorvida.R;
import ar.com.fiuba.tddp1.gestorvida.dominio.Perfil;

/**
 * Created by User on 26/06/2017.
 */

public class EstadisticasActividadesCompletadasFragment extends Fragment{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.layout_line_chart, container,false);

        LineChart grafico = (LineChart) view.findViewById(R.id.ejemploGrafico);

        //Primero se crea una lista de Entry (un punto X,Y) con los valores que pongamos
        Integer[] valoresXDias = new Integer[]{1,2,3,4,5,6,7}; //Para la demo solo vamos a usar 1 semana, 7 dias
        //Integer[] valoresYCantidadActividadesCompletadas = new Integer[]{1,2,8,4,2,6,8};
        Integer[] valoresYCantidadActividadesCompletadas = Perfil.getCantidadActividadesCompletadasEnSemana();
        List<Entry> puntos = new ArrayList<Entry>();
        for (int i = 0; i < valoresXDias.length; i++ ) {
            puntos.add( new Entry(valoresXDias[i], valoresYCantidadActividadesCompletadas[i]) );
        }

        //Despues se crea un set de datos con los puntos que se pasaron
        LineDataSet datosSet = new LineDataSet(puntos, "Actividades completadas desde el 1/6/17 hasta el 7/6/17");
        /*Aca se le puede dar formato
        datos.setColor(...)
        datos.setValueTextColor(...);
        */
        datosSet.setValueTextSize(15);

        //Por ultimo se crea una LineData a partir del LineDataSet y se agrega al grafico
        LineData lineaData = new LineData(datosSet);
        grafico.setData(lineaData);

        //Para que los dias aparezcan abajo
        grafico.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);

        Description descripcionVacia = new Description();
        descripcionVacia.setText("");
        grafico.setDescription(descripcionVacia);

        Legend leyenda = grafico.getLegend();
        leyenda.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        leyenda.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);

        grafico.invalidate(); //refresh


        return view;
    }
}
