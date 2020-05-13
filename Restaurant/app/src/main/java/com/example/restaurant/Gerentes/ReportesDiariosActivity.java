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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.restaurant.Modelos.Reportes;
import com.example.restaurant.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ReportesDiariosActivity extends AppCompatActivity  implements Response.Listener<JSONObject>, Response.ErrorListener {


    ListView lstReporteDiario;
    private JsonObjectRequest jsonObjectRequest;
    private MyArrayAdapter adapter;
    private RequestQueue request;
    private String serverip = "http://examen.searvices.com/ws/";
    EditText txtFecha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportes_diarios);
        lstReporteDiario = (ListView) findViewById(R.id.lstReporteDiario);
        request = Volley.newRequestQueue(this);
        txtFecha = (EditText) findViewById(R.id.txtFechaReporteDiario);
        txtFecha.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {
                consultarReporteDiario(txtFecha.getText().toString());
            }
        });
    }

    public void consultarReporteDiario(String fecha) {
        String url = serverip + "wsJSONCargarHistorialCaja.php?fecha="+fecha;
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        request.add(jsonObjectRequest);
    }

    class MyArrayAdapter extends ArrayAdapter<Reportes> {
        Context context;
        int textViewRecursoId;
        ArrayList<Reportes> objects;

        public MyArrayAdapter(Context context, int textViewResourceId, ArrayList<Reportes> objects) {
            super(context, textViewResourceId, objects);
            this.context = context;
            this.textViewRecursoId = textViewResourceId;
            this.objects = objects;
        }

        public View getView(final int position, View convertView, ViewGroup viewGroup) {

            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(this.textViewRecursoId, null);

            TextView lblId = (TextView) view.findViewById(R.id.lblIdReporte);
            TextView lblMesa = (TextView) view.findViewById(R.id.lblMesaReporte);
            TextView lblTotal = (TextView) view.findViewById(R.id.lblTotalReporte);
            TextView lblObservaciones = (TextView) view.findViewById(R.id.lblObservacionesReporte);
            TextView lblMesero = (TextView) view.findViewById(R.id.lblMeseroReporte);
            TextView lblFechaHora = (TextView) view.findViewById(R.id.lblFechaHoraReporte);
            TextView lblStatus = (TextView) view.findViewById(R.id.lblStatusReporte);

            lblId.setText("#" + objects.get(position).getId());
            lblMesa.setText("Mesa:" + objects.get(position).getMesa());
            lblTotal.setText("Total:$" + objects.get(position).getTotal());
            lblObservaciones.setText("Observaciones:" + objects.get(position).getObservaciones());
            lblMesero.setText("Mesero:" + objects.get(position).getMesero());
            lblFechaHora.setText("Fecha y Hora:" + objects.get(position).getFecha() + " " + objects.get(position).getHora());
            lblStatus.setText("Status:Pagada");
            return view;
        }

    }


    @Override
    public void onErrorResponse(VolleyError error) {
    }

    @Override
    public void onResponse(JSONObject response) {
        Reportes reportes = null;
        ArrayList<Reportes> lista = new ArrayList<>();
        JSONObject jsonObject;
        JSONArray json = response.optJSONArray("historial");
        try {
            for (int i=0;i<json.length();i++){
                reportes = new Reportes();
                jsonObject = json.getJSONObject(i);
                reportes.setId(jsonObject.optString("id"));
                reportes.setMesa(jsonObject.optString("mesa"));
                reportes.setTotal(jsonObject.optString("total"));
                reportes.setObservaciones(jsonObject.optString("observaciones"));
                reportes.setMesero(jsonObject.optString("mesero"));
                reportes.setFecha(jsonObject.optString("fecha"));
                reportes.setHora(jsonObject.optString("hora"));
                reportes.setStatus(jsonObject.optString("status"));
                lista.add(reportes);
            }
            adapter = new MyArrayAdapter(this,R.layout.layout_reporte,lista);
            lstReporteDiario.setAdapter(adapter);
        }catch (JSONException e){
            e.printStackTrace();
        }
    }
}

