package com.example.paecbot.Fragments.Juego;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.paecbot.Adapters.AdapterListaChatbot;
import com.example.paecbot.Codigo.UsuarioDAO;
import com.example.paecbot.Objetos.Chatbots;
import com.example.paecbot.Objetos.UltimoMensaje;
import com.example.paecbot.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ListaChats extends Fragment {

    private View chatview;
    RecyclerView recyclerView ;
    List<Chatbots> chatbots;
    List<UltimoMensaje> ultimo;
    String idusuario ="";

    public ListaChats() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        chatview = inflater.inflate(R.layout.fragment_lista_chats, container, false);
        recyclerView = (RecyclerView) chatview.findViewById(R.id.chatbots);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        return  chatview;
    }

    @Override
    public void onStart() {
        super.onStart();
        ultimo = new ArrayList<>();
        chatbots = new ArrayList<>();
        Mostrar();
    }

    void Mostrar()
    {
        StringRequest request  = new StringRequest(Request.Method.GET, "https://fcmusic.000webhostapp.com/paecbot/MostrarMensaje.php?iduser="+ UsuarioDAO.getInstancia().getKeyUsuario(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try
                {

                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("datos");

                    if(success.equals("1"))
                    {
                        for(int i=0;i<jsonArray.length();i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            String tipo = object.getString("emisor");
                            ultimo.add(new UltimoMensaje(tipo));
                        }
                    }

                    llenado();
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

    void llenado()
    {

        for (int i = 0; i < ultimo.size();i++)
        {
            switch (ultimo.get(i).getEmisor())
            {
                case "Combinación":
                    chatbots.add(new Chatbots("https://i.ibb.co/HKcGzdR/ccomb.jpg","Cesar","1","19","Combinación"));
                    break;

                case "Cambio":
                    chatbots.add(new Chatbots("https://i.ibb.co/4FJLm0y/ccam.jpg","Camila","19","37","Cambio"));
                    break;

                case "Comparación":
                    chatbots.add(new Chatbots("https://i.ibb.co/t8MTqNL/ccom.jpg","Claudia","37","55","Comparación"));
                    break;

                case "Igualación":
                    chatbots.add(new Chatbots("https://i.ibb.co/9WG5Lbj/cigu.jpg","Ignacio","55","73","Igualación"));
                    break;

            }
        }

        AdapterListaChatbot practicasList = new AdapterListaChatbot(chatbots,getContext());
        recyclerView.setAdapter(practicasList);
    }

}
