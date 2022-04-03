package com.example.paecbot.Fragments.Reporte.DetalleReporte;


import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.paecbot.Adapters.AdapterDetalleChatbotsQuizzes;
import com.example.paecbot.Objetos.InfoDetalles;
import com.example.paecbot.Objetos.Informacion;
import com.example.paecbot.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DetalleChatbot extends Fragment {

    List<InfoDetalles> practicasList;
    RecyclerView recyclerView;
    private Dialog loadingDialog;
    String id="";

    public DetalleChatbot() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detalle_chatbot, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.lista_detalle_chat);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        Bundle objetoPersona = getArguments();
        Informacion personas ;
        personas = (Informacion) objetoPersona.getSerializable("objeto");
        id = personas.getId();

        loadingDialog = new Dialog(getContext());
        loadingDialog.setContentView(R.layout.loading);
        loadingDialog.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        loadingDialog.setCancelable(false);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        loadingDialog.show();
        practicasList = new ArrayList<>();
        Mostrar();
    }


    void Mostrar ()
    {
        StringRequest request  = new StringRequest(Request.Method.GET, "https://fcmusic.000webhostapp.com/paecbot/MostrarDetallesChatbot.php?id="+id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try
                {

                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("datos");

                    if(success.equals("1"))
                    {
                        for(int i=0;i<jsonArray.length();i++)
                        {
                            JSONObject object = jsonArray.getJSONObject(i);
                            String numero = i+1+"";
                            String url = object.getString("url");
                            String respuestau = object.getString("respuestau");
                            String tiempo = object.getString("tiempo");


                            practicasList.add(new InfoDetalles(numero,url,respuestau,tiempo));

                        }
                    }

                    if(practicasList.size()>0)
                    {
                        AdapterDetalleChatbotsQuizzes adapterPracticas = new AdapterDetalleChatbotsQuizzes(practicasList,getContext());
                        recyclerView.setAdapter(adapterPracticas);
                    }

                    loadingDialog.dismiss();
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue cola = Volley.newRequestQueue(getContext());
        cola.add(request);
    }

}
