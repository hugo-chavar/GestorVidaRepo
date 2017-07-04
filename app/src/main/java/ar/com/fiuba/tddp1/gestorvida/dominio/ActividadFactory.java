package ar.com.fiuba.tddp1.gestorvida.dominio;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashSet;
import java.util.Set;



public  class ActividadFactory {

    public static Actividad fromJSONObject(JSONObject jsonObject) throws JSONException {

        Actividad actividad = new Actividad(jsonObject.getString("nombre"));
        actividad.setDescripcion(jsonObject.getString("descripcion"));
        actividad.setPrioridad(jsonObject.getString("prioridad"));

        Log.d("P", actividad.toString());

        actividad.setFoto(jsonObject.getString("foto"));
        actividad.setTipo(jsonObject.getString("tipo"));
        actividad.setId(jsonObject.getString("_id"));
        actividad.setFechaInicio(new Fecha(jsonObject.getString("fechaInicio")));
        actividad.setFechaFin(new Fecha(jsonObject.getString("fechaFin")));
        actividad.setHoraInicio(jsonObject.getString("horaInicio"));
        actividad.setHoraFin(jsonObject.getString("horaFin"));
        actividad.setFechaRecordatorio(new Fecha(jsonObject.getString("recordatorio")));
        actividad.setPeriodicidad(Integer.parseInt(jsonObject.getString("periodicidad")));
        actividad.setTiempoEstimado(String.valueOf(jsonObject.getInt("estimacion")), "0");

        JSONArray st = jsonObject.getJSONArray("categorias");

        Set<Etiqueta> etiquetas = new HashSet<Etiqueta>();
        for (int i = 0; i < st.length(); i++) {
            etiquetas.add(new Etiqueta(st.getString(i)));
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
            jsonObject.put("_id", actividad.getId());
            jsonObject.put("foto", actividad.getFoto());
            jsonObject.put("prioridad", actividad.getPrioridad());
            jsonObject.put("tipo", actividad.getTipo());
            jsonObject.put("fechaInicio", actividad.getFechaInicio());
            jsonObject.put("fechaFin", actividad.getFechaFin());
            jsonObject.put("horaInicio", actividad.getHoraInicio());
            jsonObject.put("horaFin", actividad.getHoraFin());
            jsonObject.put("recordatorio", actividad.getFechaRecordatorio());
            jsonObject.put("periodicidad", actividad.getPeriodicidad());
            jsonObject.put("estimacion", actividad.getHorasEstimadas());
            jsonObject.put("completada", actividad.estaCompleta());

            JSONArray participantes = new JSONArray();

            for (Contacto contacto: actividad.getParticipantes()) {
                participantes.put(contacto.getNombre());
            }

            jsonObject.put("participantes", participantes);

            JSONArray categorias = new JSONArray();
            for (Etiqueta etiqueta: actividad.getEtiquetas()) {
                categorias.put(etiqueta.serializar());
            }

            jsonObject.put("categorias", categorias);

            JSONArray beneficios = new JSONArray();

            for (Beneficio beneficio: actividad.getBeneficios()) {
                JSONObject jsonObject1 = new JSONObject();
                jsonObject1.put("precio", beneficio.getPrecio());
                jsonObject1.put("descuento", beneficio.getDescuento());
                jsonObject1.put("descripcion", beneficio.getDescripcion());
                beneficios.put(jsonObject1);
            }

            jsonObject.put("beneficios", beneficios);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;

    }
}
