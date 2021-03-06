package ar.com.fiuba.tddp1.gestorvida.actividades;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import ar.com.fiuba.tddp1.gestorvida.dominio.Actividad;
import ar.com.fiuba.tddp1.gestorvida.dominio.Perfil;
import ar.com.fiuba.tddp1.gestorvida.web.RequestSender;

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
        Perfil.actividadActual = actividad;


        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {

        menuInflater.inflate(R.menu.menu_view_actividad, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {
            case R.id.action_delete_activity:
                showConfirmDeleteDialog();
                break;
            default:
                super.onOptionsItemSelected(item);
        }

        return true;
    }

    private void showConfirmDeleteDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        String title = getString(R.string.message_confirm_delete, actividad.getNombre());
        builder.setTitle(title);

        builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Log.d("AddPeople", "Borrando..");
                Toast.makeText(getActivity(), "Borrando actividad ... ", Toast.LENGTH_SHORT).show();

                deleteActividad(actividad);
                getActivity().onBackPressed();
            }
        });

        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void deleteActividad(Actividad actividad) {


        Perfil.eliminarActividad(actividad);

        EliminarActividadListener listener = new EliminarActividadListener(getActivity());;

        RequestSender requestSender = new RequestSender(getActivity());

        String url = getString(R.string.url) + "activities/" + actividad.getId();

        Log.d("Eliminando", actividad.getId());

        requestSender.doDelete(listener, url);

    }
}
