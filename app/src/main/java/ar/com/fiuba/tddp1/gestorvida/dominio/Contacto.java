package ar.com.fiuba.tddp1.gestorvida.dominio;

import android.util.Log;

public class Contacto {

    private String nombre;
    private int fotoPerfilID;

    public Contacto(String nombre, int fotoPerfilID) {
        this.nombre = nombre;
        this.fotoPerfilID = fotoPerfilID;
    }

    public String getNombre() {
        return this.nombre;
    }

    public int getFotoPerfilID() {
        return this.fotoPerfilID;
    }

    @Override
    public boolean equals(Object obj) {

        if (obj instanceof Contacto) {
            return nombre.equals(((Contacto) obj).getNombre());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return nombre.hashCode();
    }
}
