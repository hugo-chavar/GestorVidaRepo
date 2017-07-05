package ar.com.fiuba.tddp1.gestorvida.contactos;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import ar.com.fiuba.tddp1.gestorvida.comunes.Imagenes;
import ar.com.fiuba.tddp1.gestorvida.dominio.Contacto;
import ar.com.fiuba.tddp1.gestorvida.dominio.Perfil;
import ar.com.fiuba.tddp1.gestorvida.web.ResponseListener;

public class ContactosListener implements ResponseListener {

    private Context context;

    public ContactosListener(Context context) {

        this.context = context;

    }

    @Override
    public void onRequestCompleted(Object response) {

        JSONArray array = (JSONArray)response;
        Log.d("ContactosListener", response.toString());
        for (int i = 0; i < array.length(); i++) {
            try {

                //Log.d("ContactosListener", "Contacto " + i);
                String name = array.getString(i);
                // TODO lautaro ver clase Imagenes
                Integer photo = Imagenes.get(name);

                Perfil.agregarContacto(new Contacto(name, photo));

            } catch (JSONException e) {
                Log.d("ContactosListener", e.getMessage());
            }

        }

        Log.d("ContactosListener", response.toString());
        //Toast.makeText(context, response.toString(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRequestError(int codError, String errorMessage) {
        String error = codError + ": " + errorMessage;
        Log.d("ContactosListener", error);
        Toast.makeText(context, error, Toast.LENGTH_LONG).show();
    }
}
