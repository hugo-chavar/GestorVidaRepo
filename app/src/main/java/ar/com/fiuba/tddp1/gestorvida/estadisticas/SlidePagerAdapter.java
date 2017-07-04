package ar.com.fiuba.tddp1.gestorvida.estadisticas;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentStatePagerAdapter;


public class SlidePagerAdapter extends FragmentStatePagerAdapter
{
    private static final int NUM_PAGES = 2;
    private Fragment f1 = new EstadisticasActividadesCompletadasFragment();
    private Fragment f2 = new EstadisticasEtiquetasPieChartFragment();

    public SlidePagerAdapter(FragmentManager fm)
    {
        super(fm);
    }

    @Override
    public Fragment getItem(int position)
    {
        // Segun la posicion, creo el fragment correspondiente y lo devuelvo
        switch(position)
        {
            case 0 : return f1;
            case 1 : return f2;
        }
        return null;
    }

    @Override
    public int getCount()
    {
        return NUM_PAGES;
    }


    // Devolvemos el titulo de cada Fragment
    @Override
    public CharSequence getPageTitle(int position)
    {
        switch(position)
        {
            case 0: return "Finalizadas";
            default: return "Distribución";
        }
    }
}
