package com.example.restaurant.Clientes;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.restaurant.Gerentes.NuevaReservacionActivity;
import com.example.restaurant.R;

public class MenuClienteActivity extends AppCompatActivity {

    private Button btnReservaciones;
    private Button btnSalir;
    private SharedPreferences sharedPreferences;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPreferences = getSharedPreferences("datos", Context.MODE_PRIVATE);
        id = sharedPreferences.getString("id","");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_cliente);
        btnReservaciones = (Button) findViewById(R.id.btnReservacionesCliente);
        btnSalir = (Button) findViewById(R.id.btnSalirCliente);

        btnReservaciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("idUsuario",id);
                bundle.putString("perfil","cajero");
                Intent intent = new Intent(MenuClienteActivity.this, NuevaReservacionActivity.class);
                intent.putExtras(bundle);
                startActivityForResult(intent,0);
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
