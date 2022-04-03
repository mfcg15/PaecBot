package com.example.paecbot.Fragments.Juego;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
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
import com.example.paecbot.Adapters.AdapterListaQuiz;
import com.example.paecbot.Codigo.UsuarioDAO;
import com.example.paecbot.Codigo.iComunicaFragments;
import com.example.paecbot.Objetos.Quizzes;
import com.example.paecbot.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ListaQuiz extends Fragment {

    private View quizview;
    private RecyclerView recyclerView ;
    List<Quizzes> quizzes;
    AdapterListaQuiz quizList;
    int pro1 = 0 ,  pro2 = 0, pro3 = 0 , pro4 = 0;
    String prog1 = "",  prog2 = "", prog3 = "" , prog4 = "" , idq1 = "", idq2 = "" , idq3 = "" , idq4 = "";
    Activity actividad;
    iComunicaFragments iComunicaFragments;

    public ListaQuiz() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        quizview = inflater.inflate(R.layout.fragment_lista_quiz, container, false);

        recyclerView = (RecyclerView) quizview.findViewById(R.id.quizzes);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        return  quizview;
    }

    @Override
    public void onStart() {
        super.onStart();
        quizzes = new ArrayList<>();
        MAXIdQuiz();
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


    public  void MAXIdQuiz ()
    {
        StringRequest request  = new StringRequest(Request.Method.GET, "https://fcmusic.000webhostapp.com/paecbot/MaxIdQuiz.php?iduser="+ UsuarioDAO.getInstancia().getKeyUsuario()+"&&quiz=Quiz N° 1", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try
                {

                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.optJSONArray("datos");
                    jsonObject = jsonArray.getJSONObject(0);
                    idq1= jsonObject.optString("MAX(quiz.id)");

                    if(idq1.equals("null"))
                    {
                        pro1 = 0;
                        quizzes.add(new Quizzes("https://i.ibb.co/28WMKrJ/q1.png","Quiz N° 1","comb","10",pro1));
                        MAXIdQuiz2();
                    }
                    else
                    {
                        Buscar();
                    }

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


    void Buscar()
    {
        StringRequest request  = new StringRequest(Request.Method.GET, "https://fcmusic.000webhostapp.com/paecbot/Progreso.php?idquiz="+idq1, new Response.Listener<String>() {
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
                            prog1 = object.getString("tpreguntas");
                        }

                        if (prog1.equals("10"))
                        {
                            pro1 = 0;
                        }
                        else
                        {
                            pro1 = (Integer.parseInt(prog1)*100)/10;
                        }
                    }

                    quizzes.add(new Quizzes("https://i.ibb.co/28WMKrJ/q1.png","Quiz N° 1","comb","10",pro1));
                    MAXIdQuiz2();

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


    public  void MAXIdQuiz2 ()
    {
        StringRequest request  = new StringRequest(Request.Method.GET, "https://fcmusic.000webhostapp.com/paecbot/MaxIdQuiz.php?iduser="+UsuarioDAO.getInstancia().getKeyUsuario()+"&&quiz=Quiz N° 2", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try
                {

                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.optJSONArray("datos");
                    jsonObject = jsonArray.getJSONObject(0);
                    idq2= jsonObject.optString("MAX(quiz.id)");

                    if(idq2.equals("null"))
                    {
                        pro2 = 0;
                        quizzes.add(new Quizzes("https://i.ibb.co/VCxP2wn/q2.png","Quiz N° 2","cam","21",pro2));
                        MAXIdQuiz3();
                    }
                    else
                    {
                        Buscar2();
                    }
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

    void Buscar2()
    {
        StringRequest request  = new StringRequest(Request.Method.GET, "https://fcmusic.000webhostapp.com/paecbot/Progreso.php?idquiz="+idq2, new Response.Listener<String>() {
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
                            prog2 = object.getString("tpreguntas");
                        }


                        if (prog2.equals("21"))
                        {
                            pro2 = 0;
                        }
                        else
                        {
                            pro2 = (Integer.parseInt(prog2)*100)/21;
                        }
                    }

                    quizzes.add(new Quizzes("https://i.ibb.co/VCxP2wn/q2.png","Quiz N° 2","cam","21",pro2));
                    MAXIdQuiz3();
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

    public  void MAXIdQuiz3()
    {
        StringRequest request  = new StringRequest(Request.Method.GET, "https://fcmusic.000webhostapp.com/paecbot/MaxIdQuiz.php?iduser="+UsuarioDAO.getInstancia().getKeyUsuario()+"&&quiz=Quiz N° 3", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try
                {

                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.optJSONArray("datos");
                    jsonObject = jsonArray.getJSONObject(0);
                    idq3= jsonObject.optString("MAX(quiz.id)");

                    if(idq3.equals("null"))
                    {
                        pro3 = 0;
                        quizzes.add(new Quizzes("https://i.ibb.co/YhkR85G/q3.png","Quiz N° 3","comp","21",pro3));
                        MAXIdQuiz4();
                    }
                    else
                    {
                        Buscar3();
                    }

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

    void Buscar3()
    {
        StringRequest request  = new StringRequest(Request.Method.GET, "https://fcmusic.000webhostapp.com/paecbot/Progreso.php?idquiz="+idq3, new Response.Listener<String>() {
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
                            prog3 = object.getString("tpreguntas");
                        }

                        if (prog3.equals("21"))
                        {
                            pro3 = 0;
                        }
                        else
                        {
                            pro3 = (Integer.parseInt(prog3)*100)/21;
                        }
                    }

                    quizzes.add(new Quizzes("https://i.ibb.co/YhkR85G/q3.png","Quiz N° 3","comp","21",pro3));

                    MAXIdQuiz4();
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


    public  void MAXIdQuiz4()
    {
        StringRequest request  = new StringRequest(Request.Method.GET, "https://fcmusic.000webhostapp.com/paecbot/MaxIdQuiz.php?iduser="+UsuarioDAO.getInstancia().getKeyUsuario()+"&&quiz=Quiz N° 4", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try
                {

                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.optJSONArray("datos");
                    jsonObject = jsonArray.getJSONObject(0);
                    idq4= jsonObject.optString("MAX(quiz.id)");

                    if(idq4.equals("null"))
                    {
                        pro4 = 0;
                        quizzes.add(new Quizzes("https://i.ibb.co/7nmWQBq/q4.png","Quiz N° 4","igu","21",pro4));
                        termino();
                    }
                    else
                    {
                        Buscar4();
                    }
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

    void Buscar4()
    {
        StringRequest request  = new StringRequest(Request.Method.GET, "https://fcmusic.000webhostapp.com/paecbot/Progreso.php?idquiz="+idq4, new Response.Listener<String>() {
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
                            prog4 = object.getString("tpreguntas");
                        }

                        if (prog4.equals("21"))
                        {
                            pro4 = 0;
                        }
                        else
                        {
                            pro4 = (Integer.parseInt(prog4)*100)/21;
                        }

                    }

                    quizzes.add(new Quizzes("https://i.ibb.co/7nmWQBq/q4.png","Quiz N° 4","igu","21",pro4));

                    termino();
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

    void termino()
    {
        quizList = new AdapterListaQuiz(quizzes,getContext());
        recyclerView.setAdapter(quizList);

        quizList.Click(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iComunicaFragments.einfoq(quizzes.get(recyclerView.getChildAdapterPosition(v)));
            }
        });
    }

}


