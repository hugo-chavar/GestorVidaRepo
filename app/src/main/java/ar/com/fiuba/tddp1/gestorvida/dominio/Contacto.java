package ar.com.fiuba.tddp1.gestorvida.dominio;

/**
 * Created by User on 01/07/2017.
 */

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
}
