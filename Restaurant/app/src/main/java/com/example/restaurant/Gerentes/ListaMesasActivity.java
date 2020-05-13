package com.example.restaurant.Gerentes;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.restaurant.Modelos.Mesas;
import com.example.restaurant.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListaMesasActivity extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener {


    private ListView listaMesas;
    private Button btnNueva;
    private JsonObjectRequest jsonObjectRequest;
    private MyArrayAdapter adapter;
    private RequestQueue request;
    private String serverip = "http://examen.searvices.com/ws/";
    String perfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_mesas);
        listaMesas = (ListView) findViewById(R.id.lstMesas);
        request = Volley.newRequestQueue(this);
        perfil= getIntent().getStringExtra("perfil");
        btnNueva = (Button) findViewById(R.id.btnNuevaMesa);
        btnNueva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListaMesasActivity.this,NuevaMesaActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("tipo","Agregar Mesa");
                intent.putExtras(bundle);
                startActivityForResult(intent,0);
            }
        });
        consultarMesas();
    }

    public void consultarMesas() {
        String url = serverip + "wsJSONCargarMesas.php";
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        request.add(jsonObjectRequest);
    }

    class MyArrayAdapter extends ArrayAdapter<Mesas> {
        Context context;
        int textViewRecursoId;
        ArrayList<Mesas> objects;

        public MyArrayAdapter(Context context, int textViewResourceId, ArrayList<Mesas> objects) {
            super(context, textViewResourceId, objects);
            this.context = context;
            this.textViewRecursoId = textViewResourceId;
            this.objects = objects;
        }

        public View getView(final int position, View convertView, ViewGroup viewGroup) {

            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(this.textViewRecursoId, null);

            TextView lblId = (TextView) view.findViewById(R.id.lblIdMesa);
            TextView lblTipo = (TextView) view.findViewById(R.id.lblTipoMesa);
            TextView lblEstado = (TextView) view.findViewById(R.id.lblEstadoMesa);

            lblId.setText("#" + objects.get(position).getId());
            if (objects.get(position).getTipo().equals("1"))
                lblTipo.setText("Tipo:Mesa de Dos");
            else
                lblTipo.setText("Tipo:Mesa de Cuatro");
            if (objects.get(position).getStatus().equals("0"))
                lblEstado.setText("Estado:Fuera de Servicio");
            else if (objects.get(position).getStatus().equals("1"))
                lblEstado.setText("Estado:Disponible");
            else if (objects.get(position).getStatus().equals("2"))
                lblEstado.setText("Estado:Ocupado");
            return view;
        }

    }

    @Override
    public void onErrorResponse(VolleyError error) {}

    @Override
    public void onResponse(JSONObject response) {
        Mesas mesas = null;
        ArrayList<Mesas> lista = new ArrayList<>();
        JSONObject jsonObject;
        JSONArray json = response.optJSONArray("mesas");
        try {
            for(int i=0;i<json.length();i++){
                mesas = new Mesas();
                jsonObject = json.getJSONObject(i);
                mesas.setId(jsonObject.optString("id"));
                mesas.setTipo(jsonObject.optString("tipo_mesa"));
                mesas.setStatus(jsonObject.optString("status"));
                lista.add(mesas);
            }
            adapter = new MyArrayAdapter(this, R.layout.layout_mesa, lista);
            listaMesas.setAdapter(adapter);
        }catch(JSONException e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        consultarMesas();
    }
}

