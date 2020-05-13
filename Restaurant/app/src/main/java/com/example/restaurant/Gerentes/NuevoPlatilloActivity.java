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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.restaurant.Modelos.Platillos;
import com.example.restaurant.R;

import org.json.JSONObject;

public class NuevoPlatilloActivity extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener {

    private JsonObjectRequest jsonObjectRequest;
    private RequestQueue request;
    private String serverip = "http://examen.searvices.com/ws/";
    private Platillos platillo;
    private TextView txtTitulo;
    private EditText txtNombre;
    private EditText txtPrecio;
    private Button btnAceptar;
    private Button btnRegresar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_platillo);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        request = Volley.newRequestQueue(this);
        Bundle extras = getIntent().getExtras();
        txtTitulo = (TextView) findViewById(R.id.lblTituloNuevoPlatillo);
        txtNombre = (EditText) findViewById(R.id.txtNombreNuevoPlatillo);
        txtPrecio = (EditText) findViewById(R.id.txtPrecioNuevoPlatillo);
        btnAceptar = (Button) findViewById(R.id.btnAceptarNuevoPlatillo);
        btnRegresar = (Button) findViewById(R.id.btnRegresarNuevoPlatillo);
        platillo = (Platillos) extras.getSerializable("platillo");
        txtTitulo.setText(extras.getString("tipo"));

        if(platillo!=null){
            txtNombre.setText(platillo.getNombre());
            txtPrecio.setText(platillo.getPrecio()+"");
        }
        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            if(platillo==null)
                agregarPlatillo(txtNombre.getText().toString(),txtPrecio.getText().toString());
            else
                editarPlatillo(platillo.getId(),txtNombre.getText().toString(),txtPrecio.getText().toString());
            btnRegresar.callOnClick();
            }
        });
        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Bundle bundle = new Bundle();
            bundle.putString("tipo","platillo");
            Intent intent = new Intent(NuevoPlatilloActivity.this, MenuGerentesActivity.class);
            intent.putExtras(bundle);
            setResult(Activity.RESULT_OK, intent);
            finish();
            }
        });
    }

    public void agregarPlatillo(String nombre,String precio){
        String url = serverip + "wsJSONAgregarPlatillo.php?nombre="+nombre+"&precio="+precio;
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        request.add(jsonObjectRequest);
    }
    public void editarPlatillo(String id,String nombre,String precio){
        String url = serverip + "wsJSONModificarPlatillo.php?nombre="+nombre+"&precio="+precio+"&id="+id;
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        request.add(jsonObjectRequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) {}
    @Override
    public void onResponse(JSONObject response) {}

}
