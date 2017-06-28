package ar.com.fiuba.tddp1.gestorvida.calendario;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ar.com.fiuba.tddp1.gestorvida.R;
import ar.com.fiuba.tddp1.gestorvida.actividades.ActividadAdapter;
import ar.com.fiuba.tddp1.gestorvida.dominio.Actividad;
import ar.com.fiuba.tddp1.gestorvida.dominio.Perfil;
import ar.com.fiuba.tddp1.gestorvida.objetivos.ObjetivoAdapter;

/**
 * Created by User on 27/06/2017.
 */

public class CalendarioFragment extends Fragment {

    private SimpleDateFormat formatterToolbar = new SimpleDateFormat("MMMM, yyyy");
    private LinearLayout linearLayoutActividadesDia;
    private LinearLayout layoutNoHayActividades;
    private RecyclerView recyclerActividadesDelDia;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.layout_calendario, container, false);

        final Toolbar toolbar = (Toolbar) this.getActivity().findViewById(R.id.toolbar);
        String fecha = 2017 + "/" + 7 + "/" + 4;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");

        CompactCalendarView calendario = (CompactCalendarView) rootView.findViewById(R.id.calendario);
        calendario.setUseThreeLetterAbbreviation(true);


        toolbar.setTitle(this.formatterToolbar.format(calendario.getFirstDayOfCurrentMonth()));

        Date dia = null;
        try {
            dia = formatter.parse(fecha);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long diaEpoch = dia.getTime();

        Event ev1 = new Event(Color.BLUE, diaEpoch, "FR,LOL;MII,USA");
        calendario.addEvent(ev1,false);


        this.linearLayoutActividadesDia = (LinearLayout) rootView.findViewById(R.id.linearLayoutActividadesDia);
        this.linearLayoutActividadesDia.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);


        this.layoutNoHayActividades = (LinearLayout) rootView.findViewById(R.id.layoutNoHayActividades);


        this.recyclerActividadesDelDia = (RecyclerView) rootView.findViewById(R.id.recyclerActividadesDiaSeleccionado);
        this.inicializarRecyclerActividadesDelDia();


        calendario.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {

                //Perfil, buscar eventos para el dia seleccionado
                //Crear la vista para todos esos eventos y agregarlas
                mostrarEventos(dateClicked);
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                toolbar.setTitle(formatterToolbar.format(firstDayOfNewMonth));
            }
        });

        return rootView;
    }

    private void inicializarRecyclerActividadesDelDia() {
        recyclerActividadesDelDia.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerActividadesDelDia.getItemAnimator().setAddDuration(1000);
        recyclerActividadesDelDia.getItemAnimator().setChangeDuration(1000);
        recyclerActividadesDelDia.getItemAnimator().setMoveDuration(1000);
        recyclerActividadesDelDia.getItemAnimator().setRemoveDuration(1000);

        DividerItemDecoration divisor = new DividerItemDecoration(recyclerActividadesDelDia.getContext(), DividerItemDecoration.VERTICAL);
        recyclerActividadesDelDia.addItemDecoration(divisor);


        List<Actividad> actividades = new ArrayList<>();
        /*ActividadesDelDiaAdapter actividadesDelDiaAdapter = new ActividadesDelDiaAdapter(actividades, getActivity());
        recyclerActividadesDelDia.setAdapter(actividadesDelDiaAdapter);
        */
        /*
        ObjetivoAdapter objetivoAdapter = new ObjetivoAdapter(Perfil.getObjetivos(), getActivity());
        recyclerActividadesDelDia.setAdapter(objetivoAdapter);
        */
        ActividadAdapter objetivoAdapter = new ActividadAdapter(Perfil.getTodasLasActividades(), getActivity());
        recyclerActividadesDelDia.setAdapter(objetivoAdapter);
    }

    boolean hayActividades = true;
    public void mostrarEventos(Date dia) {


        if (hayActividades) {
            this.layoutNoHayActividades.setVisibility(View.GONE);
            recyclerActividadesDelDia.setVisibility(View.VISIBLE);
            this.hayActividades = !this.hayActividades;
        }
        else {
            this.layoutNoHayActividades.setVisibility(View.VISIBLE);
            this.recyclerActividadesDelDia.setVisibility(View.GONE);
            this.hayActividades = !this.hayActividades;
        }
    }
}
