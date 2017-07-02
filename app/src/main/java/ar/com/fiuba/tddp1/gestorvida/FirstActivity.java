package ar.com.fiuba.tddp1.gestorvida;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class FirstActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
    }

    public void goToRegister(View view) {
        //Mostrar pantalla Login
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public void goToLogin(View view) {
        //Mostrar pantalla principal
        //Intent intent = new Intent(this, LoginActivity.class);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
