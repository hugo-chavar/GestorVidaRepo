package ar.com.fiuba.tddp1.gestorvida.comunes;

import java.util.HashMap;
import java.util.Map;

import ar.com.fiuba.tddp1.gestorvida.R;


public class Imagenes {
    private static Map<String, Integer> photos;

    static {
        photos = new HashMap<>();
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

    private static Integer imageDefault = R.drawable.ic_menu_camera;

    public static Integer get(String name) {
        Integer photo = imageDefault;
        if (photos.containsKey(name)) {
            photo = photos.get(name);
        }
        return photo;
    }
}
