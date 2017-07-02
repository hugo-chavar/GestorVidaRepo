package ar.com.fiuba.tddp1.gestorvida.calendario;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import ar.com.fiuba.tddp1.gestorvida.R;
import ar.com.fiuba.tddp1.gestorvida.dominio.Actividad;
import ar.com.fiuba.tddp1.gestorvida.dominio.Perfil;


public class CalendarioFragment extends Fragment {

    public static final int COLOR_INICIO = Color.BLUE;
    public static final int COLOR_FIN = Color.RED;
    public static final int COLOR_RECORDATORIO = Color.GREEN;

    private SimpleDateFormat formatterToolbar = new SimpleDateFormat("MMMM, yyyy");
    private LinearLayout linearLayoutActividadesDia;
    private LinearLayout layoutNoHayActividades;
    private RecyclerView recyclerActividadesDelDia;
    private CompactCalendarView calendario;
    private TextView textoFecha;

    private Map<String, String> traduccionesDias = new HashMap<>();
    String[] traduccionesMeses;
    private Toolbar toolbar;
    private CharSequence textoToolbarPrevio;

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        this.toolbar.setTitle(R.string.app_name);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        this.traduccionesDias.put("Monday", "Lunes");
        this.traduccionesDias.put("Tuesday", "Martes");
        this.traduccionesDias.put("Wednesday", "Miercoles");
        this.traduccionesDias.put("Thursday", "Jueves");
        this.traduccionesDias.put("Friday", "Viernes");
        this.traduccionesDias.put("Saturday", "Sabado");
        this.traduccionesDias.put("Sunday", "Domingo");

        this.traduccionesMeses = new String[]{"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};;


        View rootView = inflater.inflate(R.layout.layout_calendario, container, false);
        this.toolbar = (Toolbar) this.getActivity().findViewById(R.id.toolbar);
        this.textoToolbarPrevio = toolbar.getTitle();

        this.calendario = (CompactCalendarView) rootView.findViewById(R.id.calendario);
        this.calendario.setUseThreeLetterAbbreviation(false);
        calendario.setUseThreeLetterAbbreviation(true);

        this.setMesAnioToolbar(calendario.getFirstDayOfCurrentMonth());

        this.cargarEventos();

        calendario.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                mostrarEventos(dateClicked);
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                setMesAnioToolbar(firstDayOfNewMonth);
            }
        });

        this.linearLayoutActividadesDia = (LinearLayout) rootView.findViewById(R.id.linearLayoutActividadesDia);
        this.textoFecha = (TextView) rootView.findViewById(R.id.textoFecha);

        this.linearLayoutActividadesDia.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);


        this.layoutNoHayActividades = (LinearLayout) rootView.findViewById(R.id.layoutNoHayActividades);


        this.recyclerActividadesDelDia = (RecyclerView) rootView.findViewById(R.id.recyclerActividadesDiaSeleccionado);
        this.inicializarRecyclerActividadesDelDia();


        return rootView;
    }

    private void cargarEventos() {

        //Le cargo todos los eventos a una fecha
        this.cargarEventosDeListaDeFechas(COLOR_INICIO, Perfil.getFechasDeInicioDeActividades());
        this.cargarEventosDeListaDeFechas(COLOR_FIN, Perfil.getFechasDeFinDeActividades());
        //this.cargarEventosDeListaDeFechas(COLOR_RECORDATORIO, Perfil.getFechasDeRecordatoriosDeActividades());

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


        recyclerActividadesDelDia.setHasFixedSize(true);
        recyclerActividadesDelDia.addItemDecoration(new DividerItemDecoration(recyclerActividadesDelDia.getContext(), LinearLayoutManager.VERTICAL));

    }





    public void mostrarEventos(Date fecha) {

        //No aparece en espaniol
        String diaDeLaSemana = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(fecha);
        diaDeLaSemana = this.traduccionesDias.get(diaDeLaSemana);

        Calendar numerosFecha = Calendar.getInstance();
        numerosFecha.setTime(fecha);
        int dia = numerosFecha.get(Calendar.DAY_OF_MONTH);
        String nombreMes = this.traduccionesMeses[numerosFecha.get(Calendar.MONTH)];
        int anio = numerosFecha.get(Calendar.YEAR);

        this.textoFecha.setText(diaDeLaSemana + " " + dia + " de " + nombreMes + " del " + anio);

        List<Event> eventosFechaSeleccionada = this.calendario.getEvents(fecha);
        List<Actividad> todasLasActividadesFechaSeleccionada = new ArrayList<>();

        HashMap<Integer, List<Actividad>> fechasDeAcividadesPorColor = new HashMap<>();
        fechasDeAcividadesPorColor.put(COLOR_INICIO, new ArrayList<Actividad>());
        fechasDeAcividadesPorColor.put(COLOR_FIN, new ArrayList<Actividad>());
        fechasDeAcividadesPorColor.put(COLOR_RECORDATORIO, new ArrayList<Actividad>());

        for (Event evento : eventosFechaSeleccionada) {
            //Segun el color del evento, agrego actividades a su correspondiente lista
            fechasDeAcividadesPorColor.get(evento.getColor()).add((Actividad) evento.getData());
        }
        //A la  lista de todas las actividades le agrego primero las de inicio, luego las de fin y por ultimo los recordatorios
        todasLasActividadesFechaSeleccionada.addAll(fechasDeAcividadesPorColor.get(COLOR_INICIO));
        todasLasActividadesFechaSeleccionada.addAll(fechasDeAcividadesPorColor.get(COLOR_FIN));
        //todasLasActividadesFechaSeleccionada.addAll(fechasDeAcividadesPorColor.get(COLOR_RECORDATORIO));


        if (todasLasActividadesFechaSeleccionada.size() > 0) {
            this.layoutNoHayActividades.setVisibility(View.GONE);

            //Cortesia de https://gist.github.com/gabrielemariotti/4c189fb1124df4556058
            ActividadCalendarioAdapter actividadCalendarioAdapter = new ActividadCalendarioAdapter(todasLasActividadesFechaSeleccionada, getActivity());
            List<SimpleSectionedRecyclerViewAdapter.Section> sections = new ArrayList<>();

            int posicionSiguienteSeccion = 0;
            if (fechasDeAcividadesPorColor.get(COLOR_INICIO).size() > 0) {
                sections.add(new SimpleSectionedRecyclerViewAdapter.Section(posicionSiguienteSeccion, "Actividades que inician"));
                posicionSiguienteSeccion += fechasDeAcividadesPorColor.get(COLOR_INICIO).size();
            }
            if (fechasDeAcividadesPorColor.get(COLOR_FIN).size() > 0) {
                sections.add(new SimpleSectionedRecyclerViewAdapter.Section(posicionSiguienteSeccion, "Actividades que finalizan"));
                posicionSiguienteSeccion += fechasDeAcividadesPorColor.get(COLOR_FIN).size();
            }
            /*
            if (fechasDeAcividadesPorColor.get(COLOR_RECORDATORIO).size() > 0) {
                sections.add(new SimpleSectionedRecyclerViewAdapter.Section(posicionSiguienteSeccion, "Recordatorio:"));
                posicionSiguienteSeccion += fechasDeAcividadesPorColor.get(COLOR_RECORDATORIO).size();
            }
            */

            SimpleSectionedRecyclerViewAdapter.Section[] dummy = new SimpleSectionedRecyclerViewAdapter.Section[sections.size()];
            SimpleSectionedRecyclerViewAdapter mSectionedAdapter = new SimpleSectionedRecyclerViewAdapter(recyclerActividadesDelDia.getContext(), R.layout.section, R.id.section_text, actividadCalendarioAdapter);
            mSectionedAdapter.setSections(sections.toArray(dummy));

            recyclerActividadesDelDia.setAdapter(mSectionedAdapter);
            recyclerActividadesDelDia.setVisibility(View.VISIBLE);
        }
        else {
            this.layoutNoHayActividades.setVisibility(View.VISIBLE);
            this.recyclerActividadesDelDia.setVisibility(View.GONE);
        }
    }

    public void setMesAnioToolbar(Date date) {
        Calendar calendarioAuxiliar = Calendar.getInstance();
        calendarioAuxiliar.setTime(date);
        String mesAnio = traduccionesMeses[calendarioAuxiliar.get(Calendar.MONTH)] + ", " + calendarioAuxiliar.get(Calendar.YEAR);
        //toolbar.setTitle(formatterToolbar.format(firstDayOfNewMonth));
        toolbar.setTitle(mesAnio);
    }
}
