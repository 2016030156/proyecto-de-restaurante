package com.example.restaurant.Gerentes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.restaurant.Modelos.HistorialCaja;
import com.example.restaurant.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HistorialCajaActivity extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener {


    private ListView lstHistorialCajas;
    private JsonObjectRequest jsonObjectRequest;
    private MyArrayAdapter adapter;
    private RequestQueue request;
    private String serverip = "http://examen.searvices.com/ws/";
    EditText txtFecha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial_caja);
        request = Volley.newRequestQueue(this);
        lstHistorialCajas = (ListView) findViewById(R.id.lstHistorialCaja);
        txtFecha = findViewById(R.id.txtFechaCaja);
        txtFecha.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {
                consultarHistorial(txtFecha.getText().toString());
            }
        });
    }

    class MyArrayAdapter extends ArrayAdapter<HistorialCaja> {
        Context context;
        int textViewRecursoId;
        ArrayList<HistorialCaja> objects;

        public MyArrayAdapter(Context context, int textViewResourceId, ArrayList<HistorialCaja> objects) {
            super(context, textViewResourceId, objects);
            this.context = context;
            this.textViewRecursoId = textViewResourceId;
            this.objects = objects;
        }

        public View getView(final int position, View convertView, ViewGroup viewGroup) {

            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(this.textViewRecursoId, null);

            TextView lblId = (TextView) view.findViewById(R.id.lblIdHistorialCaja);
            TextView lblMesa = (TextView) view.findViewById(R.id.lblMesaHistorialCaja);
            TextView lblTotal = (TextView) view.findViewById(R.id.lblTotalHistorialCaja);
            TextView lblObservaciones = (TextView) view.findViewById(R.id.lblObservacionesHistorialCaja);
            TextView lblMesero = (TextView) view.findViewById(R.id.lblMeseroHistorialCaja);
            TextView lblFechaHora = (TextView) view.findViewById(R.id.lblFechaHoraHistorialCaja);
            TextView lblEstado = (TextView) view.findViewById(R.id.lblEstadoHistorialCaja);

            lblId.setText("#" + objects.get(position).getId());
            lblMesa.setText("Mesa:" + objects.get(position).getMesa());
            lblTotal.setText("Total:$" + objects.get(position).getTotal());
            lblObservaciones.setText("Observaciones:" + objects.get(position).getObservaciones());
            lblMesero.setText("Mesero:" + objects.get(position).getMesero());
            lblFechaHora.setText("Fecha y Hora:" + objects.get(position).getFecha() + " " + objects.get(position).getHora());
            lblEstado.setText("Estado:Pagada");

            return view;
        }

    }


    public void consultarHistorial(String fecha) {
        String url = serverip + "wsJSONCargarHistorialCaja.php?fecha="+fecha;
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        request.add(jsonObjectRequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) {}

    @Override
    public void onResponse(JSONObject response) {
        HistorialCaja historialCaja = null;
        final ArrayList<HistorialCaja> lista = new ArrayList<HistorialCaja>();
        JSONObject jsonObject;
        JSONArray json = response.optJSONArray("historial");
        try {
            Toast.makeText(this, ""+json.length(), Toast.LENGTH_SHORT).show();
            for (int i=0;i<json.length();i++){
                historialCaja = new HistorialCaja();
                jsonObject = json.getJSONObject(i);
                historialCaja.setId(jsonObject.optString("id"));
                historialCaja.setMesa(jsonObject.optString("mesa"));
                historialCaja.setTotal(jsonObject.optString("total"));
                historialCaja.setObservaciones(jsonObject.optString("observaciones"));
                historialCaja.setMesero(jsonObject.optString("mesero"));
                historialCaja.setFecha(jsonObject.optString("fecha"));
                historialCaja.setHora(jsonObject.optString("hora"));
                historialCaja.setStatus(jsonObject.optString("status"));
                lista.add(historialCaja);
            }
            Toast.makeText(this, ""+lista.size(), Toast.LENGTH_SHORT).show();
            adapter = new MyArrayAdapter(this,R.layout.layout_historial_caja,lista);
            lstHistorialCajas.setAdapter(adapter);
        }catch (JSONException e){
            e.printStackTrace();
        }
    }
}

