package com.example.restaurant.Gerentes;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.restaurant.Meseros.MenuMeseroActivity;
import com.example.restaurant.Modelos.Comandas;
import com.example.restaurant.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class NuevaComandaActivity extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener {

    private JsonObjectRequest jsonObjectRequest;
    private RequestQueue request;
    private String serverip = "http://examen.searvices.com/ws/";
    private Comandas comanda;
    private String idUsuario;
    private TextView lblTitulo;
    private EditText txtObservaciones;
    private EditText txtTotal;
    private EditText txtMesa;
    private Spinner spnPlatillos;
    private EditText txtCantidad;
    private Button btnAgregar;
    private Button btnAceptar;
    private Button btnRegresar;
    private boolean primeraVez=true;
    private int indexMesaSel=-1;
    private int tipo;
    ArrayList<String> lista = new ArrayList<>();
    ArrayList<String> listaIds = new ArrayList<>();
    ArrayList<String> listaPlatillos = new ArrayList<>();
    ArrayList<String> listaMesas = new ArrayList<>();
    String idComanda;
    String id;
    String nombre;
    String precio;
    String cantidad;
    String perfil;
    int platillosAgregados=0;
    float total=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_comanda);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        request = Volley.newRequestQueue(this);
        Bundle extras = getIntent().getExtras();
        lblTitulo = (TextView) findViewById(R.id.lblTituloNuevaComanda);
        txtObservaciones = (EditText) findViewById(R.id.txtObservacionesNuevaComanda);
        txtCantidad = (EditText) findViewById(R.id.txtCantidadNuevaComanda);
        txtTotal = (EditText) findViewById(R.id.txtTotalNuevaComanda);
        txtMesa = (EditText) findViewById(R.id.txtMesaNuevaComanda);
        spnPlatillos = (Spinner) findViewById(R.id.spnPlatillosNuevaComanda);
        btnAgregar = (Button) findViewById(R.id.btnAgregarNuevaComanda);
        btnAceptar = (Button) findViewById(R.id.btnAceptarNuevaComanda);
        btnRegresar = (Button) findViewById(R.id.btnRegresarNuevaComanda);

        comanda = (Comandas) extras.getSerializable("comanda");
        lblTitulo.setText(extras.getString("tipo"));
        idUsuario = extras.getString("idUsuario");
        perfil = extras.getString("perfil");
        spnPlatillos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(!primeraVez) {
                    id = spnPlatillos.getItemAtPosition(i).toString().split(" - ")[0];
                    nombre = spnPlatillos.getItemAtPosition(i).toString().split(" - ")[1];
                    precio = spnPlatillos.getItemAtPosition(i).toString().split(" - ")[2];
                }else
                    primeraVez=false;
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });
        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            if(!id.isEmpty() && !txtCantidad.getText().toString().isEmpty()) {
                total+=(Float.parseFloat(precio)*Float.parseFloat(txtCantidad.getText().toString()));
                String platillo = id + " - " + nombre + " - " + precio + " - " + txtCantidad.getText().toString();
                listaPlatillos.add(platillo);
                id = nombre = precio = "";
                txtCantidad.setText("");
                txtTotal.setText(total+"");
            }
            }
        });
        if(comanda!=null){
            txtObservaciones.setText(comanda.getObservaciones());
            txtMesa.setText(comanda.getIdMesa());
        }
        consultarMesas();
        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            if(listaIds.contains(txtMesa.getText().toString()))
                agregarComanda(idUsuario,txtTotal.getText().toString(),txtObservaciones.getText().toString(),txtMesa.getText().toString());
            else
                Toast.makeText(NuevaComandaActivity.this, "Las mesa no existe", Toast.LENGTH_SHORT).show();
            }
        });
        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("tipo","comanda");
                Intent intent;
                if(perfil.equals("mesero"))
                    intent= new Intent(NuevaComandaActivity.this, MenuMeseroActivity.class);
                else
                    intent = new Intent(NuevaComandaActivity.this, MenuGerentesActivity.class);
                intent.putExtras(bundle);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
    }

    public void agregarDetalles(){
        if(!listaPlatillos.isEmpty()) {
            id = listaPlatillos.get(platillosAgregados).split(" - ")[0];
            precio = listaPlatillos.get(platillosAgregados).split(" - ")[2];
            cantidad = listaPlatillos.get(platillosAgregados).split(" - ")[3];
            agregarDetalleComanda(id,cantidad,precio);
            platillosAgregados++;
        }
    }
    public void agregarComanda(String usuario,String total,String observaciones,String mesa) {
        String url = serverip + "wsJSONAgregarComandas.php?mesero="+usuario+"&total="+total+"&observaciones="+observaciones+"&mesa="+mesa;
        tipo=1;
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        request.add(jsonObjectRequest);
    }
    public void agregarDetalleComanda(String id,String cantidad,String precio) {
        String url = serverip + "wsJSONAgregarDetallesComandas.php?comanda="+idComanda+"&platillo="+id+"&cantidad="+cantidad+"&precio="+precio;
        tipo=4;
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        request.add(jsonObjectRequest);
    }
    public void consultarMesas() {
        String url = serverip + "wsJSONCargarMesas.php";
        tipo=2;
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        request.add(jsonObjectRequest);
    }
    public void consultarPlatillos() {
        String url = serverip + "wsJSONCargarPlatillos.php";
        tipo=3;
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        request.add(jsonObjectRequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        if(tipo==4)
            if(platillosAgregados<listaPlatillos.size())
                agregarDetalles();
            else
                btnRegresar.callOnClick();
    }

    @Override
    public void onResponse(JSONObject response) {
        String mesa = "";
        lista = new ArrayList<>();
        JSONObject jsonObject;
        switch (tipo){
            case 1:
                JSONArray json3 = response.optJSONArray("comandas");
                try {
                    for (int i = 0; i < json3.length(); i++) {
                        jsonObject = json3.getJSONObject(i);
                        idComanda=jsonObject.optString("id");
                    }
                    agregarDetalles();
                } catch (JSONException e) {e.printStackTrace();}
                break;
            case 2:
                JSONArray json = response.optJSONArray("mesas");
                try {
                    for (int i = 0; i < json.length(); i++) {
                        jsonObject = json.getJSONObject(i);
                        listaIds.add(jsonObject.optString("id"));
                    }
                } catch (JSONException e) {e.printStackTrace();}
                consultarPlatillos();
                break;
            case 3:
                listaMesas = new ArrayList<>();
                JSONArray json2 = response.optJSONArray("platillos");
                try {
                    for (int i = 0; i < json2.length(); i++) {
                        mesa = new String();
                        jsonObject = json2.getJSONObject(i);
                        if(jsonObject.optInt("status")==1) {
                            mesa = jsonObject.optString("id") + " - " + jsonObject.optString("nombre") + " - "+jsonObject.optString("precio");
                            lista.add(mesa);
                        }
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item, lista);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spnPlatillos.setAdapter(adapter);
                } catch (JSONException e) {e.printStackTrace();}
                break;
            case 4:
                if(platillosAgregados<listaPlatillos.size())
                    agregarDetalles();
                else
                    btnRegresar.callOnClick();
                break;
        }
    }
}
