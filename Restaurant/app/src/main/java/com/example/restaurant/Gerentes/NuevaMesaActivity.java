package com.example.restaurant.Gerentes;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.restaurant.Modelos.Mesas;
import com.example.restaurant.R;

import org.json.JSONObject;

import java.util.ArrayList;

public class NuevaMesaActivity extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener {


    private JsonObjectRequest jsonObjectRequest;
    private RequestQueue request;
    private String serverip = "http://192.168.0.16/WebServices/";
    private Mesas mesa;
    private TextView txtTitulo;
    private Button btnAceptar;
    private Button btnEliminar;
    private Button btnRegresar;

    private Spinner spnTipoMesa;
    private ArrayList<String> lista;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_mesa);
        request = Volley.newRequestQueue(this);
        Bundle extras = getIntent().getExtras();
        txtTitulo = (TextView) findViewById(R.id.lblTituloNuevaMesa);
        spnTipoMesa = (Spinner) findViewById(R.id.spnTipoNuevaMesa);
        btnAceptar = (Button) findViewById(R.id.btnAceptarNuevaMesa);
        btnEliminar = (Button) findViewById(R.id.btnEliminarMesa);
        btnRegresar = (Button) findViewById(R.id.btnRegresarNuevaMesa);
        mesa = (Mesas) extras.getSerializable("mesa");
        txtTitulo.setText(extras.getString("tipo"));

        lista = new ArrayList<String>();
        lista.add("Mesa Para 2");
        lista.add("Mesa Para 4");
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, lista);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnTipoMesa.setAdapter(adapter);


        if(mesa!=null){
            if(mesa.getTipo().equals("1"))
                spnTipoMesa.setSelection(0);
            else
                spnTipoMesa.setSelection(1);
        }

        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tipo;
                String status;
                if(spnTipoMesa.getSelectedItemPosition()==0)
                    tipo="1";
                else
                    tipo="2";
                if(mesa==null)
                    agregarMesa("1",tipo);
                else
                    editarMesa(mesa.getId(),"1",tipo);
                btnRegresar.callOnClick();
            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tipo;
                String status;
                if(spnTipoMesa.getSelectedItemPosition()==0)
                    tipo="1";
                else
                    tipo="2";
                if(mesa==null)
                    agregarMesa("1",tipo);
                else
                    editarMesa(mesa.getId(),"1",tipo);
                btnRegresar.callOnClick();
            }
        });


        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle = new Bundle();
                bundle.putString("tipo","mesa");
                Intent intent = new Intent(NuevaMesaActivity.this, MenuGerentesActivity.class);
                intent.putExtras(bundle);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
    }

    public void agregarMesa(String status,String tipo) {
        String url = serverip + "wsJSONAgregarMesa.php?status="+status+"&tipo="+tipo;
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        request.add(jsonObjectRequest);
    }
    public void editarMesa(String id,String status,String tipo) {
        String url = serverip + "wsJSONModificarMesa.php?status="+status+"&tipo="+tipo+"&id="+id;
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        request.add(jsonObjectRequest);
    }

    public void eliminarMesa(String id,String status,String tipo) {
        String url = serverip + "wsJSONEliminarMesa.php?status="+status+"&tipo="+tipo+"&id="+id;
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        request.add(jsonObjectRequest);
    }



    @Override
    public void onErrorResponse(VolleyError error) {}
    @Override
    public void onResponse(JSONObject response) {}
}
