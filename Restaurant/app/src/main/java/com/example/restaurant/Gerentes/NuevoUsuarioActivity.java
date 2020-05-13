package com.example.restaurant.Gerentes;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.restaurant.Modelos.Usuarios;
import com.example.restaurant.R;

import org.json.JSONObject;

public class NuevoUsuarioActivity extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener {


    private JsonObjectRequest jsonObjectRequest;
    private RequestQueue request;
    private String serverip = "http://examen.searvices.com/ws/";
    private Usuarios usuario;
    private String txtPerfil;
    private TextView txtTipo;
    private EditText txtUsuario;
    private EditText txtNombre;
    private EditText txtPaterno;
    private EditText txtMaterno;
    private EditText txtContrasena;
    private EditText txtConfirmarContrasena;
    private Button btnAceptar;
    private Button btnRegresar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_usuario);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        request = Volley.newRequestQueue(this);
        Bundle extras = getIntent().getExtras();
        txtTipo = (TextView) findViewById(R.id.lblTituloNuevoUsuario);
        usuario = (Usuarios) extras.getSerializable("usuario");
        txtTipo.setText(extras.getString("tipo"));
        txtPerfil = extras.getString("perfil");
        txtUsuario = (EditText) findViewById(R.id.txtUsuarioNuevoUsuario);
        txtNombre = (EditText) findViewById(R.id.txtNombreNuevoUsuario);
        txtPaterno = (EditText) findViewById(R.id.txtPaternoNuevoUsuario);
        txtMaterno = (EditText) findViewById(R.id.txtMaternoNuevoUsuario);
        txtContrasena = (EditText) findViewById(R.id.txtContrasenaNuevoUsuario);
        txtConfirmarContrasena = (EditText) findViewById(R.id.txtConfirmarContrasenaNuevoUsuario);
        btnAceptar = (Button) findViewById(R.id.btnAceptarNuevoUsuario);
        btnRegresar = (Button) findViewById(R.id.btnRegresarNuevoUsuario);

        if(usuario!=null){
            txtUsuario.setText(usuario.getUsuario());
            txtNombre.setText(usuario.getNombre());
            txtPaterno.setText(usuario.getPaterno());
            txtMaterno.setText(usuario.getMaterno());
        }

        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(usuario == null){
                    if(validarCamposAgregar()) {
                        agregarUsuario();
                        btnRegresar.callOnClick();
                    }
                }
                else
                    if(validarCamposEditar()) {
                        modificarUsuario();
                        btnRegresar.callOnClick();
                    }
            }
        });
        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                if(txtTipo.getText().toString().contains("Mesero"))
                    bundle.putString("tipo","Mesero");
                else if(txtTipo.getText().toString().contains("Cajero"))
                    bundle.putString("tipo","Cajero");
                else if(txtTipo.getText().toString().contains("Gerente"))
                    bundle.putString("tipo","Gerente");
                else if(txtTipo.getText().toString().contains("Recepcionista"))
                    bundle.putString("tipo","Recepcionista");
                else if(txtTipo.getText().toString().contains("Cocinero"))
                    bundle.putString("tipo","Cocinero");
                else if(txtTipo.getText().toString().contains("Cliente"))
                    bundle.putString("tipo","Cliente");
                Intent intent = new Intent(NuevoUsuarioActivity.this, MenuGerentesActivity.class);
                intent.putExtras(bundle);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
    }

    public boolean validarCamposAgregar(){
        if(txtUsuario.getText().toString().isEmpty() || txtNombre.getText().toString().isEmpty()
                || txtPaterno.getText().toString().isEmpty() || txtMaterno.getText().toString().isEmpty()
                || txtContrasena.getText().toString().isEmpty() || txtConfirmarContrasena.getText().toString().isEmpty()) {
            Toast.makeText(this, "Rellena todos los campos", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!txtContrasena.getText().toString().equals(txtConfirmarContrasena.getText().toString())){
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    public boolean validarCamposEditar(){
        if(txtUsuario.getText().toString().isEmpty() || txtNombre.getText().toString().isEmpty()
                || txtPaterno.getText().toString().isEmpty() || txtMaterno.getText().toString().isEmpty()
                || txtContrasena.getText().toString().isEmpty() || txtConfirmarContrasena.getText().toString().isEmpty()) {
            Toast.makeText(this, "Rellena todos los campos", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!txtContrasena.getText().toString().equals(txtConfirmarContrasena.getText().toString())){
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public void limpiar(){
        txtNombre.setText("");
        txtTipo.setText("");
        txtMaterno.setText("");
        txtPaterno.setText("");
        txtContrasena.setText("");
        txtUsuario.setText("");
        txtConfirmarContrasena.setText("");
    }

    public void agregarUsuario() {
        String url = serverip + "wsJSONAgregarUsuarios.php?usuario="+txtUsuario.getText().toString()+"&contrasena="+
                txtContrasena.getText().toString()+"&nombre="+txtNombre.getText().toString()+"&paterno="+txtPaterno.getText().toString()+
                "&materno="+txtMaterno.getText().toString()+"&perfil="+txtPerfil;
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        request.add(jsonObjectRequest);
        limpiar();
    }
    public void modificarUsuario() {
        String url = serverip + "wsJSONModificarUsuarios.php?id="+usuario.getId()+"&usuario="+txtUsuario.getText().toString()
                +"&nombre="+txtNombre.getText().toString()+"&paterno="+txtPaterno.getText().toString()+
                "&materno="+txtMaterno.getText().toString()+"&contrasena="+txtContrasena.getText().toString();
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        request.add(jsonObjectRequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) {}

    @Override
    public void onResponse(JSONObject response) {
        Toast.makeText(this, "Agregado", Toast.LENGTH_SHORT).show();
    }
}


