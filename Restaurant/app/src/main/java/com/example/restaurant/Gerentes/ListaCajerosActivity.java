package com.example.restaurant.Gerentes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.restaurant.Modelos.Usuarios;
import com.example.restaurant.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListaCajerosActivity extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener {


    ListView lstCajeros;
    Button btnNuevo;
    private JsonObjectRequest jsonObjectRequest;
    private MyArrayAdapter adapter;
    private RequestQueue request;
    private String serverip = "http://192.168.0.16/WebServices/";
    private boolean consulta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_cajeros);
        lstCajeros =(ListView) findViewById(R.id.lstCajeros);
        btnNuevo = (Button) findViewById(R.id.btnNuevoCajero);
        btnNuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListaCajerosActivity.this,NuevoUsuarioActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("tipo","Agregar Cajero");
                bundle.putString("perfil","3");
                intent.putExtras(bundle);
                startActivityForResult(intent,0);
            }
        });
        request = Volley.newRequestQueue(this);
        consultarCajeros();
    }

    public void consultarCajeros() {
        String url = serverip + "wsJSONCargarCajeros.php";
        consulta=true;
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        request.add(jsonObjectRequest);
    }

    class MyArrayAdapter extends ArrayAdapter<Usuarios> {
        Context context;
        int textViewRecursoId;
        ArrayList<Usuarios> objects;

        public MyArrayAdapter(Context context, int textViewResourceId, ArrayList<Usuarios> objects) {
            super(context, textViewResourceId, objects);
            this.context = context;
            this.textViewRecursoId = textViewResourceId;
            this.objects = objects;
        }

        public View getView(final int position, View convertView, ViewGroup viewGroup) {

            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(this.textViewRecursoId, null);

            TextView lblId = (TextView) view.findViewById(R.id.lblId);
            TextView lblNombre = (TextView) view.findViewById(R.id.lblNombre);
            TextView lblUsuario = (TextView) view.findViewById(R.id.lblUsuario);
            TextView lblEstado = (TextView) view.findViewById(R.id.lblEstado);

            lblId.setText("#" + objects.get(position).getId());
            lblNombre.setText("Nombre:" + objects.get(position).getNombre() + " " + objects.get(position).getPaterno() + " " + objects.get(position).getMaterno());
            lblUsuario.setText("Usuario:" + objects.get(position).getUsuario());
            if (objects.get(position).getEstado().equals("1"))
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
        if(consulta) {
            Usuarios cajero = null;
            final ArrayList<Usuarios> lista = new ArrayList<>();
            JSONObject jsonObject;
            JSONArray json = response.optJSONArray("cajeros");
            try {
                for (int i = 0; i < json.length(); i++) {
                    cajero = new Usuarios();
                    jsonObject = json.getJSONObject(i);
                    cajero.setId(jsonObject.optString("id"));
                    cajero.setNombre(jsonObject.optString("nombre"));
                    cajero.setPaterno(jsonObject.optString("paterno"));
                    cajero.setMaterno(jsonObject.optString("materno"));
                    cajero.setUsuario(jsonObject.optString("usuario"));
                    cajero.setContrasena(jsonObject.optString("contra"));
                    cajero.setTipo("Cajero");
                    cajero.setEstado(jsonObject.optString("status"));
                    cajero.setFecha(jsonObject.optString("create_at"));
                    lista.add(cajero);
                }
                adapter = new MyArrayAdapter(this, R.layout.layout_usuario, lista);
                lstCajeros.setAdapter(adapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else{
            consultarCajeros();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        consultarCajeros();
    }
}
