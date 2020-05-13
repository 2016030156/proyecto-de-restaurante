package com.example.restaurant.Cajeros;

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
import com.example.restaurant.MainActivity;
import com.example.restaurant.R;

public class MenuCajeroActivity extends AppCompatActivity {

    private Button btnComandas;
    private Button btnSalir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_cajero);
        btnComandas = (Button) findViewById(R.id.btnComandasCajero);
        btnSalir = (Button) findViewById(R.id.btnSalirCajero);

        btnComandas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuCajeroActivity.this, ListaComandasActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("perfil","cajero");
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
