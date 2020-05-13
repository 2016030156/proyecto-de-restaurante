package com.example.restaurant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.restaurant.Cajeros.MenuCajeroActivity;
import com.example.restaurant.Clientes.MenuClienteActivity;
import com.example.restaurant.Cocineros.MenuCocineroActivity;
import com.example.restaurant.Gerentes.MenuGerentesActivity;
import com.example.restaurant.Meseros.MenuMeseroActivity;
import com.example.restaurant.Modelos.Usuario;
import com.example.restaurant.Recepcionistas.MenuRecepcionistaActivity;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements Response.ErrorListener, Response.Listener<JSONObject> {

    private Intent intent = null;
    private Usuario usuario = new Usuario();
    private EditText txtUsuario;
    private EditText txtContra;
    private Button btnIniciarSesion;
    private Button btnSalir;
    private String serverip = "http://examen.searvices.com/ws/";
    private SharedPreferences sharedPreferences;
    private RequestQueue requestQueue;
    private JsonObjectRequest jsonObjectRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPreferences = getSharedPreferences("datos", Context.MODE_PRIVATE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestQueue = Volley.newRequestQueue(this);
        txtUsuario = (EditText) findViewById(R.id.txtUsuario);
        txtContra = (EditText) findViewById(R.id.txtContrasena);
        btnIniciarSesion = (Button) findViewById(R.id.btnIniciarSesion);
        btnSalir = (Button) findViewById(R.id.btnSalir);
        btnSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validarCampos();
            }
        });
    }


    public void validarCampos() {
        if(!txtUsuario.getText().toString().isEmpty() && !txtContra.getText().toString().isEmpty())
            iniciarSesion();
    }

    public void iniciarSesion() {
        String url = serverip+"wsJSONIniciarSesion.php?nombreDeUsuario=" + txtUsuario.getText().toString()+
                "&contrasenia="+txtContra.getText().toString();
        url.replace(" ","%20");
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        requestQueue.add(jsonObjectRequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        try{
            JSONArray jsonArray = response.getJSONArray("usuario");
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            if(jsonObject.optInt("id")!=0) {
                usuario.setId(jsonObject.optString("id"));
                usuario.setNombre(jsonObject.optString("nombre"));
                usuario.setPaterno(jsonObject.optString("paterno"));
                usuario.setMaterno(jsonObject.optString("materno"));
                usuario.setStatus(jsonObject.optString("status"));
                usuario.setUsuario(jsonObject.optString("usuario"));
                usuario.setContra(jsonObject.optString("contra"));
                usuario.setIdPerfil(jsonObject.optString("id_perfil"));
                usuario.setFechaCreacion(jsonObject.optString("create_at"));

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("id", usuario.getId() + "");
                editor.putString("nombre", usuario.getNombre());
                editor.putString("paterno", usuario.getPaterno());
                editor.putString("materno", usuario.getMaterno());
                editor.putString("idPerfil", usuario.getIdPerfil());
                editor.commit();
                if (usuario.getIdPerfil().equals("1") || usuario.getIdPerfil().equals("2"))
                    intent = new Intent(MainActivity.this, MenuGerentesActivity.class);
                else if (usuario.getIdPerfil().equals("3"))
                    intent = new Intent(MainActivity.this, MenuCajeroActivity.class);
                else if (usuario.getIdPerfil().equals("4"))
                    intent = new Intent(MainActivity.this, MenuRecepcionistaActivity.class);
                else if (usuario.getIdPerfil().equals("5"))
                    intent = new Intent(MainActivity.this, MenuCocineroActivity.class);
                else if (usuario.getIdPerfil().equals("6"))
                    intent = new Intent(MainActivity.this, MenuMeseroActivity.class);
                else if (usuario.getIdPerfil().equals("7"))
                    intent = new Intent(MainActivity.this, MenuClienteActivity.class);
                intent.putExtra("perfil",usuario.getIdPerfil());
                intent.putExtra("idUsuario",usuario.getId());
                startActivity(intent);
                finish();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
