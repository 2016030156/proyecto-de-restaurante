package com.example.restaurant.Gerentes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
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

public class ListaReservacionesActivity extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener {


    ListView listaReservaciones;
    private JsonObjectRequest jsonObjectRequest;
    private MyArrayAdapter adapter;
    private RequestQueue request;
    private String serverip = "http://192.168.0.16/WebServices/";
    private EditText txtFecha;
    private Button btnNueva;
    String perfil;
    String usuario;

    private Button btnBuscarfecha;
    final Calendar calendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_reservaciones);
        listaReservaciones = (ListView) findViewById(R.id.lstReservaciones);
        txtFecha = (EditText) findViewById(R.id.txtFechaReservaciones);
        request = Volley.newRequestQueue(this);
        perfil= getIntent().getStringExtra("perfil");
        usuario= getIntent().getStringExtra("idUsuario");

        btnBuscarfecha = (Button) findViewById(R.id.btnBuscar);

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
                new DatePickerDialog(ListaReservacionesActivity.this, date1, calendar.get(Calendar.YEAR)
                        ,calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        /*txtFecha.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {
                consultarReservaciones(txtFecha.getText().toString());
            }
        }); */


        btnNueva = (Button) findViewById(R.id.btnNuevaReservacion);

        btnBuscarfecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!txtFecha.getText().toString().equals("")){
                    consultarReservaciones(txtFecha.getText().toString());
                }
                else
                    Toast.makeText(ListaReservacionesActivity.this,"no a seleccionado fechas",Toast.LENGTH_SHORT).show();

            }
        });

        /*btnNueva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListaReservacionesActivity.this,NuevaReservacionActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("tipo","Agregar Reservación");
                bundle.putString("idUsuario",usuario);
                intent.putExtras(bundle);
                startActivityForResult(intent,0);
            }
        });*/
    }

    public void consultarReservaciones(String fecha) {
        String url = serverip + "wsJSONCargarReservaciones.php?fecha="+fecha;
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        request.add(jsonObjectRequest);
    }

    class MyArrayAdapter extends ArrayAdapter<Reservaciones> {
        Context context;
        int textViewRecursoId;
        ArrayList<Reservaciones> objects;

        public MyArrayAdapter(Context context, int textViewResourceId, ArrayList<Reservaciones> objects) {
            super(context, textViewResourceId, objects);
            this.context = context;
            this.textViewRecursoId = textViewResourceId;
            this.objects = objects;
        }

        public View getView(final int position, View convertView, ViewGroup viewGroup) {

            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(this.textViewRecursoId, null);

            TextView lblId = (TextView) view.findViewById(R.id.lblIdReservacion);
            TextView lblCliente = (TextView) view.findViewById(R.id.lblClienteReservacion);
            TextView lblNoMesa = (TextView) view.findViewById(R.id.lblNoMesaReservacion);
            TextView lblMesa = (TextView) view.findViewById(R.id.lblMesaReservacion);
            TextView lblFecha = (TextView) view.findViewById(R.id.lblFechaReservacion);
            TextView lblInicio = (TextView) view.findViewById(R.id.lblInicioReservacion);
            TextView lblFin = (TextView) view.findViewById(R.id.lblFinReservacion);
            TextView lblStatus = (TextView) view.findViewById(R.id.lblStatusReservacion);

            lblId.setText("#"+objects.get(position).getId());
            lblCliente.setText("Cliente:"+objects.get(position).getCliente());
            lblNoMesa.setText("No.Mesa:"+objects.get(position).getNoMesa());
            lblMesa.setText("Mesa:"+objects.get(position).getMesa());
            lblFecha.setText("Fecha:"+objects.get(position).getFecha());
            lblInicio.setText("Inicio:"+objects.get(position).getInicio());
            lblFin.setText("Fin:"+objects.get(position).getFin());
            if(objects.get(position).getStatus().equals("0"))
                lblStatus.setText("Status:Rechazada");
            else if(objects.get(position).getStatus().equals("1"))
                lblStatus.setText("Status:Nueva");
            else if(objects.get(position).getStatus().equals("2"))
                lblStatus.setText("Status:Terminada");
            return view;
        }

    }

    @Override
    public void onErrorResponse(VolleyError error) {}

    @Override
    public void onResponse(JSONObject response) {
        Reservaciones reservacion = null;
        final ArrayList<Reservaciones> lista = new ArrayList<>();
        JSONObject jsonObject;
        JSONArray json = response.optJSONArray("reservaciones");
        try {
            for (int i = 0; i < json.length(); i++) {
                reservacion = new Reservaciones();
                jsonObject = json.getJSONObject(i);
                reservacion.setId(jsonObject.optString("id"));
                reservacion.setNoCliente(jsonObject.optString("id_cliente"));
                reservacion.setCliente(jsonObject.optString("cliente"));
                reservacion.setNoMesa(jsonObject.optString("id_mesa"));
                if(jsonObject.optInt("tipo_mesa")==1)
                    reservacion.setMesa("Mesa para 2");
                else
                    reservacion.setMesa("Mesa para 4");
                reservacion.setFecha(jsonObject.optString("fecha"));
                reservacion.setHora(jsonObject.optString("hora"));
                reservacion.setInicio(jsonObject.optString("hora_inicio"));
                reservacion.setFin(jsonObject.optString("hora_fin"));
                reservacion.setStatus(jsonObject.optString("status"));
                lista.add(reservacion);
            }
            adapter = new MyArrayAdapter(this, R.layout.layout_reservacion, lista);
            listaReservaciones.setAdapter(adapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        consultarReservaciones(txtFecha.getText().toString());
    }
}

