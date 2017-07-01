package ar.com.fiuba.tddp1.gestorvida.dominio;

import android.graphics.Color;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashSet;
import java.util.Set;



public  class ActividadFactory {

    public static Actividad fromJSONObject(JSONObject jsonObject) throws JSONException {

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

        //TODO: aca ademas deberia parsearse el color
        Set<Etiqueta> etiquetas = new HashSet<Etiqueta>();
        for (int i = 0; i < st.length(); i++) {
            etiquetas.add(new Etiqueta(st.getString(i), Color.BLACK));// TODO: sacar el color BLACK hardcodeado y poner el del server
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

    public static JSONObject toJSONObject(Actividad actividad) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("nombre", actividad.getNombre());
            jsonObject.put("descripcion", actividad.getDescripcion());


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;


        /*actividad.setDescripcion(jsonObject.getString("descripcion"));
        actividad.setFoto(jsonObject.getString("foto"));
        actividad.setId(jsonObject.getString("_id"));
        actividad.setFechaInicio(new Fecha(jsonObject.getString("fechaInicio")));
        actividad.setFechaFin(new Fecha(jsonObject.getString("fechaFin")));
        actividad.setFechaRecordatorio(new Fecha(jsonObject.getString("recordatorio")));
        actividad.setPeriodicidad(Integer.parseInt(jsonObject.getString("periodicidad")));
        actividad.setTiempoEstimado(String.valueOf(jsonObject.getInt("estimacion")), "0");*/

    }
}
