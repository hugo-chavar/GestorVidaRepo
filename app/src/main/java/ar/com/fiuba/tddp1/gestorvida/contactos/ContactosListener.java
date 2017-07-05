package ar.com.fiuba.tddp1.gestorvida.contactos;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

import ar.com.fiuba.tddp1.gestorvida.R;
import ar.com.fiuba.tddp1.gestorvida.dominio.Contacto;
import ar.com.fiuba.tddp1.gestorvida.dominio.Perfil;
import ar.com.fiuba.tddp1.gestorvida.web.ResponseListener;

public class ContactosListener implements ResponseListener {

    private Map<String, Integer> photos = new HashMap<>();

    private Integer imageDefault = R.drawable.ic_menu_camera;

    private Context context;

    public ContactosListener(Context context) {

        this.context = context;
        //TODO Lautaro cargar imagenes aca
        photos.put("diego", R.drawable.avatar);
        photos.put("juanma", R.drawable.ic_filter);
        photos.put("hugo", R.drawable.mercurio);
        photos.put("lucas", R.drawable.circulo_color);
        photos.put("lucas17", R.drawable.gv_logo);
        photos.put("lautaro", R.drawable.ic_menu_camera);
        photos.put("profe", R.drawable.ic_menu_delete);
        photos.put("profe2", R.drawable.gv_logo);
        photos.put("profe3", R.drawable.gv_logo);

    }

    @Override
    public void onRequestCompleted(Object response) {

        JSONArray array = (JSONArray)response;
        Log.d("ContactosListener", response.toString());
        for (int i = 0; i < array.length(); i++) {
            try {

                Log.d("ContactosListener", "Contacto " + i);
                String name = array.getString(i);
                Integer photo = imageDefault;
                if (photos.containsKey(name)) {
                    photo = photos.get(name);
                }
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
