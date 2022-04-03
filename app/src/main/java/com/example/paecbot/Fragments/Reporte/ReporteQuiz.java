package com.example.paecbot.Fragments.Reporte;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.paecbot.Adapters.AdapterChatbotsQuizzes;
import com.example.paecbot.Codigo.UsuarioDAO;
import com.example.paecbot.Codigo.iComunicaFragments;
import com.example.paecbot.Objetos.Informacion;
import com.example.paecbot.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ReporteQuiz extends Fragment implements SearchView.OnQueryTextListener{

    List<Informacion> practicasList;
    RecyclerView recyclerView;
    SearchView search;
    private View quizview;
    private Dialog loadingDialog;
    AdapterChatbotsQuizzes adapte;
    Activity actividad;
    iComunicaFragments iComunicaFragments;

    public ReporteQuiz() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        quizview = inflater.inflate(R.layout.fragment_reporte_quiz, container, false);

        recyclerView = (RecyclerView) quizview.findViewById(R.id.lista_reporte_quiz);
        search = (SearchView) quizview.findViewById(R.id.txt_buscar);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        search.setQueryHint("Buscar fecha");
        search.setOnQueryTextListener(this);

        loadingDialog = new Dialog(getContext());
        loadingDialog.setContentView(R.layout.loading);
        loadingDialog.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        loadingDialog.setCancelable(false);

        return  quizview;
    }

    @Override
    public void onStart() {
        super.onStart();
        loadingDialog.show();
        practicasList = new ArrayList<>();
        Mostrar();

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof Activity)
        {
            this.actividad = (Activity) context;
            iComunicaFragments = (iComunicaFragments)this.actividad;
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    void Mostrar ()
    {

        StringRequest request  = new StringRequest(Request.Method.GET, "https://fcmusic.000webhostapp.com/paecbot/MostrarQuizzesRealizados.php?iduser="+ UsuarioDAO.getInstancia().getKeyUsuario(), new Response.Listener<String>() {
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
                            String id = object.getString("id");
                            String tipo = object.getString("quiz");
                            String correctas = object.getString("correctas");
                            String incorrectas = object.getString("incorrectas");
                            String puntaje = object.getString("puntaje");
                            String fecha = object.getString("fecha");
                            practicasList.add(new Informacion(id,tipo,correctas,incorrectas,puntaje,fecha,true));

                        }
                    }

                    if(practicasList.size()>0)
                    {
                        adapte = new AdapterChatbotsQuizzes(getContext(),practicasList);
                        recyclerView.setAdapter(adapte);

                        adapte.Click(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                iComunicaFragments.eiquiz(practicasList.get(recyclerView.getChildAdapterPosition(v)));
                            }
                        });
                    }
                    else {
                        practicasList.add(new Informacion(false));
                        adapte = new AdapterChatbotsQuizzes(getContext(),practicasList);
                        recyclerView.setAdapter(adapte);
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


    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        adapte.filter(newText);
        return false;
    }

}
