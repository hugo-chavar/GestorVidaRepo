package ar.com.fiuba.tddp1.gestorvida.estadisticas;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;


public class SlidePagerAdapter extends FragmentPagerAdapter
{
    private static final int NUM_PAGES = 2;
    private Fragment f = null;

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
            case 0 :
                if(f==null) {
                    f = new EstadisticasActividadesCompletadasFragment();
                    return f;
                }
                else
                    return f;

                case 1 : return new EstadisticasEtiquetasPieChartFragment();
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
