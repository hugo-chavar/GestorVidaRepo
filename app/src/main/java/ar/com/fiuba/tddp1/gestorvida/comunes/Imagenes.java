package ar.com.fiuba.tddp1.gestorvida.comunes;

import java.util.HashMap;
import java.util.Map;

import ar.com.fiuba.tddp1.gestorvida.R;


public class Imagenes {
    private static Map<String, Integer> photos;

    static {
        photos = new HashMap<>();
        photos.put("diego", R.drawable.imagen3);
        photos.put("juanma", R.drawable.imagen5);
        photos.put("hugo", R.drawable.imagen1);
        photos.put("lucas", R.drawable.imagen2);
        photos.put("lucas17", R.drawable.imagen9);
        photos.put("lautaro", R.drawable.imagen8);
        photos.put("profe", R.drawable.imagen6);
        photos.put("profe2", R.drawable.imagen4);
        photos.put("profe3", R.drawable.imagen7);
    }

    private static Integer imageDefault = R.drawable.imagen9;

    public static Integer get(String name) {
        Integer photo = imageDefault;
        if (photos.containsKey(name)) {
            photo = photos.get(name);
        }
        return photo;
    }
}
