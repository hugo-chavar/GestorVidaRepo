package ar.com.fiuba.tddp1.gestorvida.estadisticas;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ar.com.fiuba.tddp1.gestorvida.R;
import ar.com.fiuba.tddp1.gestorvida.dominio.Perfil;
import ar.com.fiuba.tddp1.gestorvida.web.Pair;

/**
 * Created by User on 26/06/2017.
 */



public class EstadisticasEtiquetasPieChartFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.layout_pie_chart, container, false);

        PieChart grafico = (PieChart) view.findViewById(R.id.pieChartEtiquetas);


        Map<String, Float> actividadDeEtiquetas = Perfil.getActividadDeEtiquetas();
        List<PieEntry> etiquetas = new ArrayList<>();
        for (Map.Entry<String, Float> etiqueta : actividadDeEtiquetas.entrySet()) {
            if (etiqueta.getValue() > 0) {
                etiquetas.add(new PieEntry(etiqueta.getValue(),etiqueta.getKey()));
            }
        }

        grafico.setUsePercentValues(true);
        grafico.setDrawHoleEnabled(false);
        grafico.setDrawSlicesUnderHole(true);
        //Para cambiar el color de la etiqueta de cada slice
        grafico.setEntryLabelColor(Color.BLACK);
        /*sirve para sacar el texto dentro de los slices pero esta deprecado
        grafico.setDrawSliceText(false);
        */

        //Despues se crea un set de datos con los puntos que se pasaron
        PieDataSet datosSet = new PieDataSet(etiquetas, "Distribucion de etiquetas en las actividades");
        datosSet.setValueTextSize(15);
        datosSet.setSliceSpace(2f);

        //Si hay mas de 10 etiquetas se empiezan a repetir colores
        final int[] DIEZ_COLORES = {
                Color.rgb(255, 0, 0), Color.rgb(0, 255, 0), Color.rgb(0, 0, 255),
                Color.rgb(255, 255, 0), Color.rgb(255, 0, 255), Color.rgb(0, 255, 255),
                Color.rgb(128,0, 255), Color.rgb(255, 0, 128), Color.rgb(128, 128, 128),
                Color.rgb(0, 128,0)
        };
        datosSet.setColors(DIEZ_COLORES);


        /*
        List<Integer> colores = new ArrayList<>();
        for (int color : ColorTemplate.VORDIPLOM_COLORS){
            colores.add(color);
        }
        for (int color : ColorTemplate.JOYFUL_COLORS){
            colores.add(color);
        }
        for (int color : ColorTemplate.COLORFUL_COLORS){
            colores.add(color);
        }
        for (int color : ColorTemplate.PASTEL_COLORS){
            colores.add(color);
        }
        for (int color : ColorTemplate.LIBERTY_COLORS){
            colores.add(color);
        }
        datosSet.setColors(colores);
        */

        //Por ultimo se crea una LineData a partir del LineDataSet y se agrega al grafico
        PieData pieData = new PieData(datosSet);
        pieData.setValueFormatter(new PercentFormatter());

        grafico.setData(pieData);



        Description descripcionVacia = new Description();
        descripcionVacia.setText("");
        grafico.setDescription(descripcionVacia);

        Legend leyenda = grafico.getLegend();

        //leyenda.setOrientation(Legend.LegendOrientation.VERTICAL);
        leyenda.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);

        leyenda.setWordWrapEnabled(true);

        grafico.invalidate(); //refresh


        return view;
    }
}
