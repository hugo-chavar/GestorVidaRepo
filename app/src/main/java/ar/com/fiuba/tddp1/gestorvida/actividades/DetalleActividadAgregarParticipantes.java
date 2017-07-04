package ar.com.fiuba.tddp1.gestorvida.actividades;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import ar.com.fiuba.tddp1.gestorvida.R;
import ar.com.fiuba.tddp1.gestorvida.comunes.FragmentLoader;
import ar.com.fiuba.tddp1.gestorvida.contactos.AgregarParticipantesFragment;

/**
 * Created by User on 02/07/2017.
 */

public class DetalleActividadAgregarParticipantes extends DetalleActividadFragment {

    private FloatingActionButton fabAgregarParticipante;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detalle_actividad_agregar_participantes_layout, container, false);
        init(view);


        this.fabAgregarParticipante = (FloatingActionButton) view.findViewById(R.id.fabAgregarParticipante);
        this.fabAgregarParticipante.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                FragmentLoader.load(getActivity(), new AgregarParticipantesFragment(), FragmentLoader.AgregarParticipantes);
            }

        });

        setHasOptionsMenu(true);


        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {

        menuInflater.inflate(R.menu.menu_view_actividad, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        Log.d("AddPeople", "Se hizo clic en la opcion " + id);

        switch (id) {
            case R.id.action_delete_activity:
                Log.d("AddPeople", "Borrando..");
                Toast.makeText(getActivity(), "Borrando actividad ... ", Toast.LENGTH_SHORT).show();
                //agregarActividad();
                getActivity().onBackPressed();
                break;
            default:
                super.onOptionsItemSelected(item);
        }


        return true;
    }
}
