package com.example.restaurant.Gerentes;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.restaurant.Clientes.MenuClienteActivity;
import com.example.restaurant.Modelos.Reservaciones;
import com.example.restaurant.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.widget.DatePicker;
import android.app.DatePickerDialog;
import android.widget.Toast;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;

import java.util.Locale;

public class NuevaReservacionActivity extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener {

    private JsonObjectRequest jsonObjectRequest;
    private RequestQueue request;
    private String serverip = "http://192.168.0.16/WebServices/";
    private Reservaciones reservacion;
    private String idUsuario;
    private TextView lblTitulo;
    private EditText txtFecha;
    private EditText txtMesa;
    private Spinner spnHora;
    private Button btnAceptar;
    private Button btnRegresar;
    private String mesaSel="";
    private int indexMesaSel=-1;
    private int consulta;

    final Calendar calendar = Calendar.getInstance();

    String perfil;
    ArrayList<String> lista = new ArrayList<>();
    ArrayList<String> listaIdsMesas = new ArrayList<>();
    ArrayList<String> listaIdsHoras = new ArrayList<>();
    ArrayList<String> listaIdsClientes = new ArrayList<>();
    ArrayList<String> listaReservaciones = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_reservacion);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        request = Volley.newRequestQueue(this);
        Bundle extras = getIntent().getExtras();
        perfil = extras.getString("perfil");
        lblTitulo = (TextView) findViewById(R.id.lblTituloNuevaReservacion);
        txtFecha = (EditText) findViewById(R.id.txtFechaNuevaReservacion);
        txtMesa = (EditText) findViewById(R.id.txtMesaNuevaReservacion);
        spnHora = (Spinner) findViewById(R.id.spnHorarioNuevaReservacion);
        btnAceptar = (Button) findViewById(R.id.btnAceptarNuevaReservacion);
        btnRegresar = (Button) findViewById(R.id.btnRegresarNuevaReservacion);
        lblTitulo.setText(extras.getString("tipo"));
        idUsuario = extras.getString("idUsuario");

        final DatePickerDialog.OnDateSetListener date1 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String formateoFecha = "yyyy-MM-dd";
                SimpleDateFormat dateFormat = new SimpleDateFormat(formateoFecha, Locale.US);
                txtFecha.setText(dateFormat.format(calendar.getTime()));
            }
        };

        txtFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*actualizarInput1();*/
                new DatePickerDialog(NuevaReservacionActivity.this, date1, calendar.get(Calendar.YEAR)
                        ,calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!txtFecha.getText().toString().equals("") && !txtMesa.getText().toString().equals("")){
                    String hora = spnHora.getSelectedItem().toString().split(" -")[0];
                    agregarReservacion(idUsuario,txtFecha.getText().toString(),hora,listaIdsHoras.get(spnHora.getSelectedItemPosition()));
                    btnRegresar.callOnClick();
                }
                else
                    Toast.makeText(NuevaReservacionActivity.this,"no a seleccionado fechas ni mesas o hora",Toast.LENGTH_SHORT).show();

            }
        });
        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("tipo","reservacion");
                Intent intent = new Intent(NuevaReservacionActivity.this, MenuClienteActivity.class);
                intent.putExtras(bundle);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
        txtMesa.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {
                consultarHorarios(txtMesa.getText().toString());
            }
        });
    }

    public void agregarReservacion(String cliente,String fecha,String hora,String id_hora) {
        String url = serverip + "wsJSONAgregarReservaciones.php?cliente="+cliente+"&fecha="+fecha+"&hora="+hora+"&id_hora="+id_hora;
        consulta=0;
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        request.add(jsonObjectRequest);
    }
    public void consultarHorarios(String mesa) {
        String url = serverip + "wsJSONCargarHorariosMesa.php?id_mesa="+mesa;
        consulta=4;
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        request.add(jsonObjectRequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) {}

    @Override
    public void onResponse(JSONObject response) {
        try {
            String dato = "";
            JSONObject jsonObject;
            ArrayAdapter<String> adapter;
            lista = new ArrayList<>();
            switch(consulta){
                case 2:
                    listaIdsMesas = new ArrayList<>();
                    JSONArray json2 = response.optJSONArray("mesas");
                    for (int i = 0; i < json2.length(); i++) {
                        jsonObject = json2.getJSONObject(i);
                        if(jsonObject.optInt("tipo_mesa")==1)
                            listaIdsMesas.add(jsonObject.optString("id"));
                    }
                    break;
                case 4:
                    listaIdsHoras = new ArrayList<>();
                    JSONArray json4 = response.optJSONArray("horarios");
                    for (int i = 0; i < json4.length(); i++) {
                        dato = new String();
                        jsonObject = json4.getJSONObject(i);
                        dato = jsonObject.optString("hora_inicio")+" - "+jsonObject.optString("hora_fin");
                        int x=0;

                        for(x=0; x<listaReservaciones.size();x++) {
                            if (listaReservaciones.get(x).equals(jsonObject.optString("id")))
                                break;
                        }
                        if(x==listaReservaciones.size()){
                            lista.add(dato);
                            listaIdsHoras.add(jsonObject.optString("id"));
                        }
                    }
                    adapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item, lista);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spnHora.setAdapter(adapter);
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}

