package ar.com.fiuba.tddp1.gestorvida;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

public class BuscarActividadActivity extends AppCompatActivity {

    LinearLayout layoutOfPopup;
    PopupWindow popupFilter;
    Button filterOkButton;
    FloatingActionButton filterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_actividad);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        init();

        filterButton = (FloatingActionButton) findViewById(R.id.fab);
        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupInit();
            }
        });
    }

    public void init() {
        filterOkButton = (Button) findViewById(R.id.button_ok_filter);
        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutOfPopup = (LinearLayout) inflater.inflate(R.layout.layout_filter, null);
    }

    public void popupInit() {
        popupFilter = new PopupWindow(layoutOfPopup, LinearLayout.LayoutParams.FILL_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        popupFilter.setContentView(layoutOfPopup);
        popupFilter.setFocusable(true);

        // Clear the default translucent background
        popupFilter.setBackgroundDrawable(new ColorDrawable(Color.WHITE));

        // Displaying the popup at the specified location, + offsets.
        popupFilter.showAtLocation(layoutOfPopup, Gravity.NO_GRAVITY, 0, Math.round(filterButton.getY()));

        // Getting a reference to Close button, and close the popup when clicked.
        /*filterOkButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                popupFilter.dismiss();
            }
        });*/
    }

    public void dismissPopup(View v) {
        popupFilter.dismiss();
    }

}
