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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.restaurant.Modelos.Comandas;
import com.example.restaurant.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListaComandasActivity extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener {

    private ListView listaComandas;
    private Button btnNueva;
    private JsonObjectRequest jsonObjectRequest;
    private MyArrayAdapter adapter;
    private RequestQueue request;
    private String serverip = "http://examen.searvices.com/ws/";
    String perfil;
    String idUsuario;
    int tipo;
    boolean consulta;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_comandas);
        listaComandas = (ListView) findViewById(R.id.lstComandas);
        perfil= getIntent().getStringExtra("perfil");
        idUsuario= getIntent().getStringExtra("idUsuario");
        request = Volley.newRequestQueue(this);
        btnNueva = (Button) findViewById(R.id.btnNuevaComanda);
        btnNueva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Intent intent = new Intent(ListaComandasActivity.this,NuevaComandaActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("perfil",perfil);
            bundle.putString("idUsuario",idUsuario);
            bundle.putString("tipo","Nueva Comanda");
            intent.putExtras(bundle);
            startActivityForResult(intent,0);
            }
        });

        if(perfil.equals("cajero")) {
            tipo = 2;
            btnNueva.setVisibility(View.GONE);
        }else if(perfil.equals("cocinero")) {
            tipo = 1;
            btnNueva.setVisibility(View.GONE);
        }
        consultarComandas();
    }

    public void consultarComandas() {
        String url = serverip + "wsJSONCargarComandas.php";
        consulta=true;
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        request.add(jsonObjectRequest);
    }
    public void cambiarStatusComandas(String id,String status) {
        String url = serverip + "wsJSONCambiarStatusComanda.php?id="+id+"&status="+status;
        consulta=false;
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        request.add(jsonObjectRequest);
    }

    class MyArrayAdapter extends ArrayAdapter<Comandas> {
        Context context;
        int textViewRecursoId;
        ArrayList<Comandas> objects;

        public MyArrayAdapter(Context context, int textViewResourceId, ArrayList<Comandas> objects) {
            super(context, textViewResourceId, objects);
            this.context = context;
            this.textViewRecursoId = textViewResourceId;
            this.objects = objects;
        }

        public View getView(final int position, View convertView, ViewGroup viewGroup) {

            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(this.textViewRecursoId, null);

            TextView lblId = (TextView) view.findViewById(R.id.lblIdComanda);
            TextView lblFecha = (TextView) view.findViewById(R.id.lblFechaComanda);
            TextView lblMesero = (TextView) view.findViewById(R.id.lblMeseroComanda);
            TextView lblTotal = (TextView) view.findViewById(R.id.lblTotalComanda);
            TextView lblMesa = (TextView) view.findViewById(R.id.lblMesaComanda);
            TextView lblEstado = (TextView) view.findViewById(R.id.lblEstadoComanda);

            lblId.setText("#" + objects.get(position).getId());
            lblFecha.setText("Fecha:" + objects.get(position).getFecha());
            lblMesero.setText("Mesero:" + objects.get(position).getMesero());
            lblTotal.setText("Total:$" + objects.get(position).getTotal());
            lblMesa.setText("Mesa:" + objects.get(position).getMesa());

            if (objects.get(position).getStatus().equals("0"))
                lblEstado.setText("Estado:Rechazada");
            else if (objects.get(position).getStatus().equals("1"))
                lblEstado.setText("Estado:Nueva");
            else if (objects.get(position).getStatus().equals("2"))
                lblEstado.setText("Estado:Entregada");
            else if (objects.get(position).getStatus().equals("3"))
                lblEstado.setText("Estado:Pagada");

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new AlertDialog.Builder(getContext())
                            .setTitle("Editar")
                            .setMessage("Status Comanda")
                            .setPositiveButton("Entregada", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                cambiarStatusComandas(objects.get(position).getId(),"2");
                                }
                            }).setPositiveButton("Pagada",  new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                cambiarStatusComandas(objects.get(position).getId(),"3");
                                }
                            }).setPositiveButton("Rechazada",  new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                cambiarStatusComandas(objects.get(position).getId(),"0");
                                }
                            }).show();
                }
            });
            return view;
        }

    }


    @Override
    public void onErrorResponse(VolleyError error) {}

    @Override
    public void onResponse(JSONObject response) {
        if(consulta){
            Comandas comandas = null;
            final ArrayList<Comandas> lista = new ArrayList<>();
            JSONObject jsonObject;
            JSONArray json = response.optJSONArray("comandas");
            try {
                for (int i = 0; i < json.length(); i++) {
                    comandas = new Comandas();
                    jsonObject = json.getJSONObject(i);
                    if (jsonObject.optInt("status") != tipo && (!perfil.equals("1") || !perfil.equals("2")))
                        continue;
                    comandas.setId(jsonObject.optString("id"));
                    comandas.setIdMesero(jsonObject.optString("id_mesero"));
                    comandas.setIdMesa(jsonObject.optString("id_mesa"));
                    comandas.setTotal(jsonObject.optString("total"));
                    comandas.setObservaciones(jsonObject.optString("observaciones"));
                    comandas.setFecha(jsonObject.optString("fecha"));
                    comandas.setHora(jsonObject.optString("hora"));
                    comandas.setStatus(jsonObject.optString("status"));
                    comandas.setMesero(jsonObject.optString("mesero"));
                    comandas.setMesa(jsonObject.optString("mesa"));
                    lista.add(comandas);
                }
                adapter = new MyArrayAdapter(this, R.layout.layout_comanda, lista);
                listaComandas.setAdapter(adapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else{
            consultarComandas();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        consultarComandas();
    }
}

