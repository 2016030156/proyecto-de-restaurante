package com.example.restaurant.Gerentes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
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
import com.example.restaurant.Modelos.Platillos;
import com.example.restaurant.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListaPlatillosActivity extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener {


    ListView lstPlatillos;
    Button btnNuevo;
    private JsonObjectRequest jsonObjectRequest;
    private MyArrayAdapter adapter;
    private RequestQueue request;
    private String serverip = "http://examen.searvices.com/ws/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_platillos);
        lstPlatillos = (ListView) findViewById(R.id.lstPlatillos);
        request = Volley.newRequestQueue(this);
        btnNuevo = (Button) findViewById(R.id.btnNuevoPlatillo);
        btnNuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListaPlatillosActivity.this,NuevoPlatilloActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("tipo","Agregar Platillo");
                intent.putExtras(bundle);
                startActivityForResult(intent,0);
            }
        });
        consultarPlatillos();
    }

    public void consultarPlatillos() {
        String url = serverip + "wsJSONCargarPlatillos.php";
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        request.add(jsonObjectRequest);
    }

    class MyArrayAdapter extends ArrayAdapter<Platillos> {
        Context context;
        int textViewRecursoId;
        ArrayList<Platillos> objects;

        public MyArrayAdapter(Context context, int textViewResourceId, ArrayList<Platillos> objects) {
            super(context, textViewResourceId, objects);
            this.context = context;
            this.textViewRecursoId = textViewResourceId;
            this.objects = objects;
        }

        public View getView(final int position, View convertView, ViewGroup viewGroup) {

            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(this.textViewRecursoId, null);

            TextView lblId = (TextView) view.findViewById(R.id.lblIdPlatillo);
            TextView lblNombre = (TextView) view.findViewById(R.id.lblNombrePlatillo);
            TextView lblPrecio = (TextView) view.findViewById(R.id.lblPrecio);
            TextView lblEstado = (TextView) view.findViewById(R.id.lblEstadoPlatillo);

            lblId.setText("#" + objects.get(position).getId());
            lblNombre.setText("Nombre:" + objects.get(position).getNombre());
            lblPrecio.setText("Precio:" + objects.get(position).getPrecio());
            if (objects.get(position).getStatus().equals("1"))
                lblEstado.setText("Estado:Activo");
            else
                lblEstado.setText("Estado:Inactivo");
            return view;
        }

    }


    @Override
    public void onErrorResponse(VolleyError error) {}

    @Override
    public void onResponse(JSONObject response) {
        Platillos platillo = null;
        ArrayList<Platillos> lista = new ArrayList<>();
        JSONObject jsonObject;
        JSONArray json = response.optJSONArray("platillos");
        try {
            for (int i = 0; i < json.length(); i++) {
                platillo = new Platillos();
                jsonObject = json.getJSONObject(i);
                platillo.setId(jsonObject.optString("id"));
                platillo.setNombre(jsonObject.optString("nombre"));
                platillo.setPrecio(Float.parseFloat(jsonObject.optString("precio")));
                platillo.setFecha(jsonObject.optString("create_at"));
                platillo.setStatus(jsonObject.optString("status"));
                lista.add(platillo);
            }
            adapter = new MyArrayAdapter(this, R.layout.layout_platillo, lista);
            lstPlatillos.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        consultarPlatillos();
    }
}
