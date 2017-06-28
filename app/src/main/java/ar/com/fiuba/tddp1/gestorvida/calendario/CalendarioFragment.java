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
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private CompactCalendarView calendario;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.layout_calendario, container, false);
        final Toolbar toolbar = (Toolbar) this.getActivity().findViewById(R.id.toolbar);

        this.calendario = (CompactCalendarView) rootView.findViewById(R.id.calendario);
        calendario.setUseThreeLetterAbbreviation(true);
        toolbar.setTitle(this.formatterToolbar.format(calendario.getFirstDayOfCurrentMonth()));

        this.cargarEventos();

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

        this.linearLayoutActividadesDia = (LinearLayout) rootView.findViewById(R.id.linearLayoutActividadesDia);
        this.linearLayoutActividadesDia.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);


        this.layoutNoHayActividades = (LinearLayout) rootView.findViewById(R.id.layoutNoHayActividades);


        this.recyclerActividadesDelDia = (RecyclerView) rootView.findViewById(R.id.recyclerActividadesDiaSeleccionado);
        this.inicializarRecyclerActividadesDelDia();


        return rootView;
    }

    private void cargarEventos() {

        //Le cargo todos los eventos a una fecha

        //Cargar fechas inicio, cargar fechas fin, cargar fechas recordatorio
        /*
            Azul->Inicio
            Rojo->Fin
            Verde->Recordatorio
         */
        this.cargarEventosDeListaDeFechas(Color.BLUE, Perfil.getFechasDeInicioDeActividades());
        this.cargarEventosDeListaDeFechas(Color.RED, Perfil.getFechasDeFinDeActividades());
        this.cargarEventosDeListaDeFechas(Color.GREEN, Perfil.getFechasDeRecordatoriosDeActividades());

    }

    private void cargarEventosDeListaDeFechas(int color, Map<Date, List<Actividad>> fechasDeActividades) {
        for (Map.Entry<Date, List<Actividad>> fecha : fechasDeActividades.entrySet()) {
            for (Actividad actividad : fecha.getValue()) {
                Event evento = new Event(color, fecha.getKey().getTime(), actividad);
                this.calendario.addEvent(evento,false);
            }
        }
    }

    private void inicializarRecyclerActividadesDelDia() {
        recyclerActividadesDelDia.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerActividadesDelDia.getItemAnimator().setAddDuration(1000);
        recyclerActividadesDelDia.getItemAnimator().setChangeDuration(1000);
        recyclerActividadesDelDia.getItemAnimator().setMoveDuration(1000);
        recyclerActividadesDelDia.getItemAnimator().setRemoveDuration(1000);

        /*
        DividerItemDecoration divisor = new DividerItemDecoration(recyclerActividadesDelDia.getContext(), DividerItemDecoration.VERTICAL);
        recyclerActividadesDelDia.addItemDecoration(divisor);
        */

        recyclerActividadesDelDia.setHasFixedSize(true);
        recyclerActividadesDelDia.addItemDecoration(new DividerItemDecoration(recyclerActividadesDelDia.getContext(), LinearLayoutManager.VERTICAL));

        /*ActividadesDelDiaAdapter actividadesDelDiaAdapter = new ActividadesDelDiaAdapter(actividades, getActivity());
        recyclerActividadesDelDia.setAdapter(actividadesDelDiaAdapter);
        */
        /*
        ObjetivoAdapter objetivoAdapter = new ObjetivoAdapter(Perfil.getObjetivos(), getActivity());
        recyclerActividadesDelDia.setAdapter(objetivoAdapter);
        */
    }

    boolean hayActividades = true;
    public void mostrarEventos(Date fecha) {

        List<Event> eventosFechaSeleccionada = this.calendario.getEvents(fecha);
        List<Actividad> actividadesFechaSeleccionada = new ArrayList<>();

        for (Event evento : eventosFechaSeleccionada) {
            actividadesFechaSeleccionada.add((Actividad) evento.getData());
        }


        if (actividadesFechaSeleccionada.size() > 0) {
            this.layoutNoHayActividades.setVisibility(View.GONE);



            ActividadAdapter objetivoAdapter = new ActividadAdapter(actividadesFechaSeleccionada, getActivity());
            List<SimpleSectionedRecyclerViewAdapter.Section> sections = new ArrayList<>();


            sections.add(new SimpleSectionedRecyclerViewAdapter.Section(0, "FAFAFAF"));
            sections.add(new SimpleSectionedRecyclerViewAdapter.Section(5, "IFHMLWCIBWESTD"));


            SimpleSectionedRecyclerViewAdapter.Section[] dummy = new SimpleSectionedRecyclerViewAdapter.Section[sections.size()];
            SimpleSectionedRecyclerViewAdapter mSectionedAdapter = new SimpleSectionedRecyclerViewAdapter(recyclerActividadesDelDia.getContext(), R.layout.section, R.id.section_text, objetivoAdapter);
            mSectionedAdapter.setSections(sections.toArray(dummy));

            recyclerActividadesDelDia.setAdapter(mSectionedAdapter);



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
