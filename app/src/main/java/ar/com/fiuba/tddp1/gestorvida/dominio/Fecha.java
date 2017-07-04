package ar.com.fiuba.tddp1.gestorvida.dominio;

/**
 * Created by User on 25/06/2017.
 */

public class Fecha {
    public static String ANIO_NULO = "1900";
    private static final String sep  = "/";

    public String dia = "01";
    public String mes = "01";
    public String anio = Fecha.ANIO_NULO;

    public Fecha(String dia, String mes, String anio) {
        this.dia = dia;
        this.mes = mes;
        this.anio = anio;
    }

    public Fecha(String fecha) {
        String [] datos = fecha.split(sep);

        if (datos.length == 3) {
            dia = datos[0];
            mes = datos[1];
            anio = datos[2];
        }
        //Log.d("Fecha", toString() );
    }

    @Override
    public String toString() {
        return dia + sep + mes + sep + anio;
    }

    public static boolean esFechaNula(Fecha fecha) {
        return fecha.anio.equals(Fecha.ANIO_NULO);
    }
}
