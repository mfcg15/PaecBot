package com.example.paecbot.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.paecbot.Codigo.UsuarioDAO;
import com.example.paecbot.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ResultadosQuiz extends AppCompatActivity {

    TextView puntaje , buenas, malas, totalpreguntas;
    Button btnvolver;

    public static final  String tpreguntas = "tpreguntas";
    public static final  String correcto = "correcto";
    public static final  String incorrecto = "incorrecto";
    public static final  String scor = "scor";

    String score = "", good = "", bad = "", totalquestion = "", pquiz="", pchat="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultados_quiz);

        puntaje = (TextView) findViewById(R.id.txt_puntaje);
        buenas = (TextView) findViewById(R.id.txt_correctas);
        malas = (TextView) findViewById(R.id.txt_incorractas);
        totalpreguntas = (TextView) findViewById(R.id.txt_totalpreguntas);
        btnvolver = (Button) findViewById(R.id.btn_volver);


        score = getIntent().getStringExtra("scor");
        good = getIntent().getStringExtra("correcto");
        bad = getIntent().getStringExtra("incorrecto");
        totalquestion = getIntent().getStringExtra("tpreguntas");

        puntaje.setText(score);
        buenas.setText(good);
        malas.setText(bad);
        totalpreguntas.setText(totalquestion);

        btnvolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PuntajeQuiz();
                Intent intent = new Intent(ResultadosQuiz.this, Menu.class);
                startActivity(intent);
                finish();
            }
        });
    }

    void PuntajeQuiz ()
    {
        StringRequest request  = new StringRequest(Request.Method.GET, "https://fcmusic.000webhostapp.com/paecbot/MostrarPuntajeQuiz.php?iduser="+UsuarioDAO.getInstancia().getKeyUsuario(), new Response.Listener<String>() {
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
                            pquiz = object.getString("SUM(puntaje)");
                        }
                    }

                    PuntajeChatbot();
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ResultadosQuiz.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue cola = Volley.newRequestQueue(ResultadosQuiz.this);
        cola.add(request);
    }

    void PuntajeChatbot()
    {
        StringRequest request  = new StringRequest(Request.Method.GET, "https://fcmusic.000webhostapp.com/paecbot/MostrarPuntajeChatbot.php?iduser="+UsuarioDAO.getInstancia().getKeyUsuario(), new Response.Listener<String>() {
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
                            pchat = object.getString("SUM(puntaje)");
                        }
                    }

                    if(pchat.equals("null"))
                    {
                        pchat = "0";
                    }

                    ActualizarPuntajeT();

                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ResultadosQuiz.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue cola = Volley.newRequestQueue(ResultadosQuiz.this);
        cola.add(request);
    }

    void ActualizarPuntajeT ()
    {

        final String iduser = UsuarioDAO.getInstancia().getKeyUsuario();
        final String puntajet = Integer.parseInt(pquiz)+Integer.parseInt(pchat)+"";


        StringRequest request  = new StringRequest(Request.Method.POST, "https://fcmusic.000webhostapp.com/paecbot/UpdatePuntaje.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ResultadosQuiz.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){

            @Override
            protected Map<String,String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<>();

                params.put("iduser",iduser);
                params.put("puntajet",puntajet);

                return params;
            }
        };

        RequestQueue cola = Volley.newRequestQueue(ResultadosQuiz.this);
        cola.add(request);
    }
}
