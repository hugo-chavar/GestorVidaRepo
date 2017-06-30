package ar.com.fiuba.tddp1.gestorvida.actividades;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashSet;
import java.util.Set;

import ar.com.fiuba.tddp1.gestorvida.dominio.Actividad;
import ar.com.fiuba.tddp1.gestorvida.dominio.Beneficio;
import ar.com.fiuba.tddp1.gestorvida.dominio.Fecha;
import ar.com.fiuba.tddp1.gestorvida.dominio.Perfil;
import ar.com.fiuba.tddp1.gestorvida.web.ResponseListener;

public class ActividadesListener implements ResponseListener {

    private Context context;

    public ActividadesListener(Context context) {
        this.context = context;
    }
    @Override
    public void onRequestCompleted(Object response) {

        JSONArray array = (JSONArray)response;
        Log.d("ActividadesListener", response.toString());
        for (int i = 0; i < array.length(); i++) {
            try {

                Log.d("ActividadesListener", "Actividad " + i);
                Perfil.agregarActividad(toActividad(array.getJSONObject(i)));


            } catch (JSONException e) {
                Log.d("ActividadesListener", e.getMessage());
            }

        }

        Log.d("ActividadesListener", response.toString());
        //Toast.makeText(context, response.toString(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRequestError(int codError, String errorMessage) {
        String error = codError + ": " + errorMessage;
        Log.d("ActividadesListener", error);
        Toast.makeText(context, error, Toast.LENGTH_LONG).show();
    }

    private Actividad toActividad(JSONObject jsonObject) throws JSONException {

        Actividad actividad = new Actividad(jsonObject.getString("nombre"));
        actividad.setDescripcion(jsonObject.getString("descripcion"));
        actividad.setFoto(jsonObject.getString("foto"));
        actividad.setId(jsonObject.getString("_id"));
        actividad.setFechaInicio(new Fecha(jsonObject.getString("fechaInicio")));
        actividad.setFechaFin(new Fecha(jsonObject.getString("fechaFin")));
        actividad.setFechaRecordatorio(new Fecha(jsonObject.getString("recordatorio")));
        actividad.setPeriodicidad(Integer.parseInt(jsonObject.getString("periodicidad")));
        actividad.setTiempoEstimado(String.valueOf(jsonObject.getInt("estimacion")), "0");

        JSONArray st = jsonObject.getJSONArray("categorias");
        Set<String> etiquetas = new HashSet<String>();
        for (int i = 0; i < st.length(); i++) {
            etiquetas.add(st.getString(i));
        }

        actividad.setEtiquetas(etiquetas);

        boolean completada = jsonObject.getBoolean("completada");
        if (completada) {
            actividad.completar();
        }

        st = jsonObject.getJSONArray("categorias");
        Set<String> participantes = new HashSet<String>();
        for (int i = 0; i < st.length(); i++) {
            participantes.add(st.getString(i));
        }

        actividad.setParticipantes(participantes);

        JSONArray benef = jsonObject.getJSONArray("beneficios");

        for (int i = 0; i < benef.length(); i++) {
            Beneficio beneficio = new Beneficio();
            JSONObject jo = benef.getJSONObject(i);
            beneficio.setDescripcion(jo.getString("descripcion"));
            beneficio.setDescuento(jo.getDouble("descuento"));
            beneficio.setPrecio(jo.getDouble("precio"));
            actividad.addBeneficio(beneficio);
        }



        return actividad;



    }
}
