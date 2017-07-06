package ar.com.fiuba.tddp1.gestorvida.dominio;

import android.graphics.Color;

import java.util.Random;

/**
 * Created by User on 30/06/2017.
 */

public class Etiqueta {

    public String nombre;
    public int color;

    public static int[] COLORES_ETIQUETAS = new int[]{ Color.rgb(250,250,250), Color.rgb(255,138,128), Color.rgb(255,209,128),
                                                      Color.rgb(255,255,141), Color.rgb(204,255,144),Color.rgb(167,255,235),
                                                      Color.rgb(128,216,255),Color.rgb(207,216,220) };

    public static int[] COLORES_BORDES = new int[]{ Color.rgb(202,202,202), Color.rgb(224,124,114), Color.rgb(227,187,116),
                                                    Color.rgb(204,204,148), Color.rgb(173,212,129),Color.rgb(154,210,196),
                                                    Color.rgb(114,193,228),Color.rgb(180,185,189) };

    public static String SEPARADOR_COLOR_NOMBRE = "_";


    public Etiqueta(String etiquetaSerializada) {
        String[] etiquetaParseada = etiquetaSerializada.split(SEPARADOR_COLOR_NOMBRE);
        if (etiquetaParseada.length > 1) {
            try {
                color = Etiqueta.COLORES_ETIQUETAS[Integer.parseInt(etiquetaParseada[0])];
            } catch (NumberFormatException e) {
                Random random = new Random();
                color = COLORES_ETIQUETAS[random.nextInt(COLORES_ETIQUETAS.length)];
            }

            nombre = etiquetaParseada[1];
        } else {
            Random random = new Random();

            color = COLORES_ETIQUETAS[random.nextInt(COLORES_ETIQUETAS.length)];
            nombre = etiquetaParseada[0];
        }

    }

    public String serializar() {
        return (this.getIndiceColor() + "_" + this.nombre);
    }


    public Etiqueta(String nombre, int color) {
        this.nombre = nombre;
        this.color = color;
    }


    @Override
    public int hashCode() {
        int hash = this.nombre.hashCode() + this.color;
        return hash;
    }

    @Override
    public boolean equals(Object e) {
        Etiqueta etiqueta2 = (Etiqueta) e;
        return ( (this.nombre.equals(etiqueta2.nombre)) && (this.color == etiqueta2.color));
    }


    public String getIndiceColor() {
        int indice = 0;
        boolean indiceNoEncontrado = true;
        while (indiceNoEncontrado) {
            if (Etiqueta.COLORES_ETIQUETAS[indice] == this.color) {
                indiceNoEncontrado = false;
            }
            else {
                indice++;
            }
        }
        return "" + indice;
    }
}
