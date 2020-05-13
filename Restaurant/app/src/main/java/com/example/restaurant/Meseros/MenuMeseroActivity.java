package com.example.restaurant.Meseros;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.restaurant.Cajeros.MenuCajeroActivity;
import com.example.restaurant.Gerentes.ListaComandasActivity;
import com.example.restaurant.Gerentes.ListaMesasActivity;
import com.example.restaurant.Gerentes.NuevaComandaActivity;
import com.example.restaurant.R;

public class MenuMeseroActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private String id;
    private Button btnComandas;
    private Button btnNuevaComanda;
    private Button btnMesas;
    private Button btnSalir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPreferences = getSharedPreferences("datos", Context.MODE_PRIVATE);
        id = sharedPreferences.getString("id","");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_mesero);
        btnMesas = (Button) findViewById(R.id.btnMesasMesero);
        btnComandas = (Button) findViewById(R.id.btnComandasMesero);
        btnNuevaComanda = (Button) findViewById(R.id.btnNuevaComandaMesero);
        btnSalir = (Button) findViewById(R.id.btnSalirMesero);

        btnComandas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuMeseroActivity.this, ListaComandasActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("perfil","mesero");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        btnMesas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuMeseroActivity.this, ListaMesasActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("perfil","mesero");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        btnNuevaComanda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuMeseroActivity.this, NuevaComandaActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("idUsuario",id);
                bundle.putString("perfil","mesero");
                bundle.putString("tipo","Nueva Comanda");
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {

    }
}
