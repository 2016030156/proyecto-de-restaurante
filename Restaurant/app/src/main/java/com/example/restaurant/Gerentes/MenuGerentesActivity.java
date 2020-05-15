package com.example.restaurant.Gerentes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.restaurant.R;


public class MenuGerentesActivity extends AppCompatActivity {

    private Button btnHistorialCaja;
    private Button btnHistorialPlatillos;
    private Button btnCajeros;
    private Button btnClientes;
    private Button btnCocineros;
    private Button btnComandas;
    private Button btnGerentes;
    private Button btnMesas;
    private Button btnMeseros;
    private Button btnPlatillos;
    private Button btnRecepcionistas;
    private Button btnReservaciones;
    private Button btnReportesRangos;
    private Button btnReportesDiarios;
    private Button btnSalir;
    String perfil;
    String idUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_gerentes);
        perfil= getIntent().getStringExtra("perfil");
        idUsuario= getIntent().getStringExtra("idUsuario");
        btnHistorialCaja = (Button) findViewById(R.id.btnHistorialCajaGerente);
        btnHistorialPlatillos = (Button) findViewById(R.id.btnHistorialPlatillosGerente);
        btnCajeros = (Button) findViewById(R.id.btnCajerosGerente);
        btnClientes = (Button) findViewById(R.id.btnClientesGerente);
        btnCocineros = (Button) findViewById(R.id.btnCocinerosGerente);
        btnComandas = (Button) findViewById(R.id.btnComandasGerente);
        btnGerentes = (Button) findViewById(R.id.btnGerentesGerente);
        //btnMesas = (Button) findViewById(R.id.btnMesasGerente);
        btnMeseros = (Button) findViewById(R.id.btnMeserosGerente);
        btnPlatillos = (Button) findViewById(R.id.btnPlatillosGerente);
        btnRecepcionistas = (Button) findViewById(R.id.btnRecepcionistasGerente);
        btnReservaciones = (Button) findViewById(R.id.btnReservacionesGerente);
        btnReportesRangos = (Button) findViewById(R.id.btnReporteRangoGerente);
        btnReportesDiarios = (Button) findViewById(R.id.btnReporteDiarioGerente);
        btnSalir = (Button) findViewById(R.id.btnSalirGerente);

        btnHistorialCaja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuGerentesActivity.this,HistorialCajaActivity.class));
            }
        });
        btnHistorialPlatillos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuGerentesActivity.this,HistorialPlatillosActivity.class));
            }
        });
        btnCajeros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuGerentesActivity.this,ListaCajerosActivity.class));
            }
        });
        btnClientes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuGerentesActivity.this,ListaClientesActivity.class));
            }
        });
        btnCocineros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuGerentesActivity.this,ListaCocinerosActivity.class));
            }
        });
        btnComandas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuGerentesActivity.this,ListaComandasActivity.class);
                intent.putExtra("idUsuario",idUsuario);
                intent.putExtra("perfil",perfil);
                startActivity(intent);
            }
        });
        btnGerentes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuGerentesActivity.this,ListaGerentesActivity.class));
            }
        });
        /*btnMesas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuGerentesActivity.this,ListaMesasActivity.class));
            }
        });*/
        btnMeseros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuGerentesActivity.this,ListaMeserosActivity.class));
            }
        });
        btnPlatillos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuGerentesActivity.this,ListaPlatillosActivity.class));
            }
        });
        btnRecepcionistas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuGerentesActivity.this,ListaRecepcionistasActivity.class));
            }
        });
        btnReservaciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuGerentesActivity.this,ListaReservacionesActivity.class);
                intent.putExtra("idUsuario",idUsuario);
                intent.putExtra("perfil",perfil);
                startActivity(intent);
            }
        });
        btnReportesDiarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuGerentesActivity.this,ReportesDiariosActivity.class));
            }
        });
        btnReportesRangos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuGerentesActivity.this,ReportesRangosActivity.class));
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
