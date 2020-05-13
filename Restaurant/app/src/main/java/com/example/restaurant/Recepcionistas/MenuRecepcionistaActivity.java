package com.example.restaurant.Recepcionistas;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.restaurant.Gerentes.ListaReservacionesActivity;
import com.example.restaurant.Gerentes.NuevaComandaActivity;
import com.example.restaurant.Meseros.MenuMeseroActivity;
import com.example.restaurant.R;

public class MenuRecepcionistaActivity extends AppCompatActivity {

    private Button btnReservaciones;
    private Button btnSalir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_recepcionista);
        btnReservaciones = (Button) findViewById(R.id.btnReservacionesRecepcionista);
        btnSalir = (Button) findViewById(R.id.btnSalirRecepcionista);

        btnReservaciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuRecepcionistaActivity.this, ListaReservacionesActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("perfil","recepcionista");
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

