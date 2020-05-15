package com.example.restaurant.Gerentes;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
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
import com.example.restaurant.Modelos.HistorialPlatillos;
import com.example.restaurant.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

import android.widget.DatePicker;
import android.app.DatePickerDialog;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class HistorialPlatillosActivity extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener {


    private ListView lstHistorialPlatillos;
    private JsonObjectRequest jsonObjectRequest;
    private MyArrayAdapter adapter;
    private RequestQueue request;
    private String serverip = "http://192.168.0.16/WebServices/";
    EditText txtFecha;

    private Button btnBuscarfecha;
    final Calendar calendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial_platillos);
        lstHistorialPlatillos = findViewById(R.id.lstHistorialPlatillos);
        request = Volley.newRequestQueue(this);
        txtFecha = findViewById(R.id.txtFechaPlatillos);

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
                new DatePickerDialog(HistorialPlatillosActivity.this, date1, calendar.get(Calendar.YEAR)
                        ,calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        btnBuscarfecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!txtFecha.getText().toString().equals("")){
                    consultarHistorial(txtFecha.getText().toString());
                }
                else


                    Toast.makeText(HistorialPlatillosActivity.this,"no a seleccionado fechas",Toast.LENGTH_SHORT).show();



            }
        });


        /*txtFecha.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {
                consultarHistorial(txtFecha.getText().toString());
            }
        });*/
    }

    public void consultarHistorial(String fecha) {
        String url = serverip + "wsJSONCargarHistorialPlatillos.php?fecha="+fecha;
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        request.add(jsonObjectRequest);
    }

    class MyArrayAdapter extends ArrayAdapter<HistorialPlatillos> {
        Context context;
        int textViewRecursoId;
        ArrayList<HistorialPlatillos> objects;

        public MyArrayAdapter(Context context, int textViewResourceId, ArrayList<HistorialPlatillos> objects) {
            super(context, textViewResourceId, objects);
            this.context = context;
            this.textViewRecursoId = textViewResourceId;
            this.objects = objects;
        }

        public View getView(final int position, View convertView, ViewGroup viewGroup) {

            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(this.textViewRecursoId, null);

            TextView lblId = (TextView) view.findViewById(R.id.lblIdHistorialPlatillos);
            TextView lblNombre = (TextView) view.findViewById(R.id.lblNombreHistorialPlatillos);
            TextView lblPrecio = (TextView) view.findViewById(R.id.lblPrecioHistorialPlatillos);
            TextView lblCantidad = (TextView) view.findViewById(R.id.lblCantidadHistorialPlatillos);
            TextView lblSubtotal = (TextView) view.findViewById(R.id.lblSubtotal);

            lblId.setText("#" + objects.get(position).getId());
            lblNombre.setText("Nombre:" + objects.get(position).getNombre());
            lblPrecio.setText("Precio:$" + objects.get(position).getPrecio());
            lblCantidad.setText("Cantidad:$" + objects.get(position).getCantidad());
            lblSubtotal.setText("Subtotal:$" + objects.get(position).getSubtotal());
            return view;
        }

    }


    @Override
    public void onErrorResponse(VolleyError error) {}

    @Override
    public void onResponse(JSONObject response) {
        HistorialPlatillos historialPlatillos = null;
        final ArrayList<HistorialPlatillos> lista = new ArrayList<>();
        JSONObject jsonObject;
        JSONArray json = response.optJSONArray("platillos");
        try {
            for (int i=0;i<json.length();i++){
                historialPlatillos  = new HistorialPlatillos();
                jsonObject = json.getJSONObject(i);
                historialPlatillos.setId((i+1)+"");
                historialPlatillos.setNombre(jsonObject.optString("nombre"));
                historialPlatillos.setPrecio(Float.parseFloat(jsonObject.optString("precio")));
                historialPlatillos.setCantidad(Integer.parseInt(jsonObject.optString("cantidad")));
                historialPlatillos.setSubtotal(historialPlatillos.getPrecio()*historialPlatillos.getCantidad());
                lista.add(historialPlatillos);
            }
            adapter = new MyArrayAdapter(this,R.layout.layout_historial_platillos,lista);
            lstHistorialPlatillos.setAdapter(adapter);
        }catch (JSONException e){
            e.printStackTrace();
        }
    }
}

