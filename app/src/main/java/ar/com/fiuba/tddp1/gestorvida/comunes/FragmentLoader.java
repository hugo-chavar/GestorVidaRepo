package ar.com.fiuba.tddp1.gestorvida.comunes;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;

import ar.com.fiuba.tddp1.gestorvida.R;

public class FragmentLoader {

    public static void load(Activity activity, Fragment fragment) {
        Bundle bundle = new Bundle();

        fragment.setArguments(bundle);

        FragmentManager fragmentManager = activity.getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.contenedor, fragment)
                .commit();

    }
}
