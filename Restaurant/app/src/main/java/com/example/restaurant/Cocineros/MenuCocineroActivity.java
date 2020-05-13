package com.example.restaurant.Cocineros;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.restaurant.Gerentes.ListaComandasActivity;
import com.example.restaurant.Gerentes.NuevaReservacionActivity;
import com.example.restaurant.R;

public class MenuCocineroActivity extends AppCompatActivity {

    private Button btnComandas;
    private Button btnSalir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_cocinero);
        btnComandas = (Button) findViewById(R.id.btnComandasCocinero);
        btnSalir = (Button) findViewById(R.id.btnSalirCocinero);

        btnComandas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MenuCocineroActivity.this, ListaComandasActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("perfil","cocinero");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        btnSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
