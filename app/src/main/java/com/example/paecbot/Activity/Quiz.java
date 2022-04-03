package com.example.paecbot.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.Animator;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.paecbot.Adapters.AdapterMensajes;
import com.example.paecbot.Codigo.UsuarioDAO;
import com.example.paecbot.Objetos.ListaDePreguntas;
import com.example.paecbot.Objetos.ListaDeProblemas;
import com.example.paecbot.Objetos.ModeloPreguntas;
import com.example.paecbot.R;
import com.example.paecbot.Requests.DQuizzesRequest;
import com.example.paecbot.Requests.QuizRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class Quiz extends AppCompatActivity {

    private TextView txt_pregunta_examen,txt_numero_pregunta, textView;
    private LinearLayout Seccion_Preguntas ;
    private Button btn_Opcion_A, btn_next,btn_Opcion_B,btn_Opcion_C,btn_Opcion_D;
    private Chronometer cronometro ;
    private Dialog loadingDialog;

    private List<ModeloPreguntas> list;
    private List<ListaDePreguntas> aux = new ArrayList<>(), identi1 = new ArrayList<>(), identi2 = new ArrayList<>();

    private int position = 0,count;
    public static final  String totalpreguntas = "totalpreguntas";
    public static final  String tip = "tip";
    public static final  String niv = "niv";
    public static final  String canti = "canti";

    boolean avanzar =false;
    long parar;

    int cantida = 0 , acti = 0, buenas = 0, malas = 0, score = 0;
    int preres [] , newop [] = new int [4] ;
    String opciones [][] ;
    String  idusuario = "", auxtiempo = "", inicio = "", termino = "", nive = "", nquiz = "", totalp = "", abunas = "", amalas = "", arespuesta = "", totpregunt = "", puntaj = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        txt_pregunta_examen = findViewById(R.id.txt_pregunta_examen);
        txt_numero_pregunta = findViewById(R.id.txt_numero_pregunta);
        Seccion_Preguntas = findViewById(R.id.Seccion_Preguntas);
        btn_Opcion_A = findViewById(R.id.btn_Opcion_A);
        btn_Opcion_B = findViewById(R.id.btn_Opcion_B);
        btn_Opcion_C = findViewById(R.id.btn_Opcion_C);
        btn_Opcion_D = findViewById(R.id.btn_Opcion_D);
        textView = findViewById(R.id.txtnive);
        cronometro = (Chronometer)findViewById(R.id.txt_cronometro);
        btn_next = findViewById(R.id.btn_next);
        btn_next.setEnabled(false);

        inicio = getIntent().getStringExtra("tip")+getIntent().getStringExtra("totalpreguntas")+"1";
        termino = getIntent().getStringExtra("tip")+getIntent().getStringExtra("totalpreguntas")+getIntent().getStringExtra("totalpreguntas");

        cantida = Integer.parseInt(getIntent().getStringExtra("totalpreguntas"));
        nive = getIntent().getStringExtra("niv");
        totpregunt = getIntent().getStringExtra("canti");
        opciones  = new String[cantida][4];

        loadingDialog = new Dialog(Quiz.this);
        loadingDialog.setContentView(R.layout.loading);
        loadingDialog.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        loadingDialog.setCancelable(false);

        llenado();

        for (int i = 0 ; i < 4;i++)
        {
            Seccion_Preguntas.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    VerificarPregunta(((Button)v));
                }
            });
        }

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_next.setEnabled(false);
                btn_next.setAlpha(0.7f);
                enableOption(true);
                position++;
                reset();

                if(position==list.size()-1)
                {
                    btn_next.setText("Puntuacion");
                }

                if(position == list.size() || list.size() == 1 )
                {

                    if(acti==1)
                    {
                        totalp = (Integer.parseInt(totalp)+identi2.size())+"";
                    }

                    BuscarBuenas();

                    score = buenas*10;

                    Intent intent = new Intent(Quiz.this, ResultadosQuiz.class);
                    intent.putExtra(ResultadosQuiz.scor,score+"");
                    intent.putExtra(ResultadosQuiz.tpreguntas,cantida+"");
                    intent.putExtra(ResultadosQuiz.correcto,buenas+"");
                    intent.putExtra(ResultadosQuiz.incorrecto,malas+"");
                    startActivity(intent);
                    finish();
                    return;
                }
                count=0;
                playAnim(txt_pregunta_examen,0,list.get(position).getPregunta());
            }
        });

    }


    void llenado ()
    {
        loadingDialog.show();
        StringRequest request  = new StringRequest(Request.Method.GET, "https://fcmusic.000webhostapp.com/paecbot/PreguntasQuiz.php?inicio="+inicio+"&&termino="+termino, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try
                {
                    start();
                    list = new ArrayList<>();
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("datos");

                    if(success.equals("1"))
                    {
                        for(int i=0;i<jsonArray.length();i++)
                        {
                            JSONObject object = jsonArray.getJSONObject(i);
                            identi1.add(new ListaDePreguntas(object.getString("id")));
                            aux.add(new ListaDePreguntas(object.getString("pregunta"),object.getString("respuesta")));
                            opciones[i][0] = object.getString("opciona");
                            opciones[i][1] = object.getString("opcionb");
                            opciones[i][2] = object.getString("opcionc");
                            opciones[i][3] = object.getString("opciond");
                        }
                    }


                    preres  = new int [aux.size()];
                    for (int a=0;a<aux.size();a++)
                    {
                        preres[a] = (int) (Math.random()*aux.size());
                        for (int j =0 ; j<a;j++)
                        {
                            if(preres[a]==preres[j])
                            {
                                a--;
                            }
                        }
                    }

                    for (int a=0;a<aux.size();a++)
                    {
                        chocolateo();
                        identi2.add(new ListaDePreguntas(identi1.get(preres[a]).getIden()));
                        list.add(new ModeloPreguntas(aux.get(preres[a]).getPregunta()+"",opciones[preres[a]][newop[0]]+"",opciones[preres[a]][newop[1]]+"",opciones[preres[a]][newop[2]]+"",opciones[preres[a]][newop[3]]+"",aux.get(preres[a]).getRespuesta()+""));
                    }

                    if (list.size()==1)
                    {
                        btn_next.setText("Puntuacion");
                    }

                    IdUsuario();

                    playAnim(txt_numero_pregunta,0,list.get(position).getPregunta());
                    playAnim(txt_pregunta_examen,0,list.get(position).getPregunta());

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
                Toast.makeText(Quiz.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue cola = Volley.newRequestQueue(this);
        cola.add(request);
    }

    private void playAnim(final View view , final int value, final String data)
    {
        view.animate().alpha(value).scaleX(value).scaleY(value).setDuration(500).setStartDelay(100).setInterpolator(new DecelerateInterpolator()).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

                if(value == 0 && count < 4)
                {
                    String opcion = "";
                    if(count == 0)
                    {
                        opcion = list.get(position).getOpcionA();
                    }
                    else if (count == 1)
                    {
                        opcion = list.get(position).getOpcionB();
                    }
                    else  if (count == 2)
                    {
                        opcion = list.get(position).getOpcionC();
                    }
                    else if (count == 3)
                    {
                        opcion = list.get(position).getOpcionD();
                    }
                    playAnim(Seccion_Preguntas.getChildAt(count),0,opcion);
                    count++;
                }
            }

            @Override
            public void onAnimationEnd(Animator animation) {

                if(value == 0)
                {
                    try
                    {
                        ((TextView) view).setText(data);
                        txt_numero_pregunta.setText(position+1+"/"+list.size());

                    }
                    catch (ClassCastException ex)
                    {
                        ((Button) view).setText(data);
                    }
                    view.setTag(data);
                    playAnim(view,1,data);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    private void VerificarPregunta (Button selectedOption)
    {
        btn_next.setEnabled(true);
        btn_next.setAlpha(1);
        enableOption(false);
        if(selectedOption.getText().toString().equals(list.get(position).getCorrectans()))
        {
            selectedOption.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#4CAF50")));
            arespuesta="Correcta";
            buenas++;
            stop();
        }
        else
        {
            selectedOption.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#ff0000")));
            Button correctoption = (Button) Seccion_Preguntas.findViewWithTag(list.get(position).getCorrectans());
            correctoption.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#4CAF50")));
            arespuesta="Incorrecta";
            malas++;
            stop();
        }

        Ingresar_DQuiz();
    }

    private void enableOption (boolean enable)
    {
        for (int i = 0 ; i < 4;i++)
        {
            Seccion_Preguntas.getChildAt(i).setEnabled(enable);
            if(enable)
            {
                Seccion_Preguntas.getChildAt(i).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#989898")));
            }
        }
    }

    void chocolateo ()
    {

        for (int a = 0; a < 4; a++)
        {
            newop[a] = (int) (Math.random()*4);
            for (int j = 0 ; j< a; j++)
            {
                if(newop[a]==newop[j])
                {
                    a--;
                }
            }
        }
    }

    public  void IdUsuario()
    {
        StringRequest request  = new StringRequest(Request.Method.GET, "https://fcmusic.000webhostapp.com/paecbot/IdUsuario.php?iduser="+ UsuarioDAO.getInstancia().getKeyUsuario()+"", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try
                {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.optJSONArray("datos");
                    jsonObject = jsonArray.getJSONObject(0);
                    idusuario = jsonObject.optString("id");
                    BuscarQuiz();
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Quiz.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue cola = Volley.newRequestQueue(Quiz.this);
        cola.add(request);
    }


    public  void BuscarQuiz ()
    {
        StringRequest request  = new StringRequest(Request.Method.GET, "https://fcmusic.000webhostapp.com/paecbot/MaxIdQuiz.php?iduser="+UsuarioDAO.getInstancia().getKeyUsuario()+"&&quiz="+nive+"", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try
                {

                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.optJSONArray("datos");
                    jsonObject = jsonArray.getJSONObject(0);
                    nquiz = jsonObject.optString("MAX(quiz.id)");

                    if(nquiz.equals("null"))
                    {
                        IngresaQuiz();
                    }
                    else
                    {
                        LlenaDatos();
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
                Toast.makeText(Quiz.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue cola = Volley.newRequestQueue(Quiz.this);
        cola.add(request);

    }

    public  void MAXIdQuiz ()
    {
        StringRequest request  = new StringRequest(Request.Method.GET, "https://fcmusic.000webhostapp.com/paecbot/MaxIdQuiz.php?iduser="+ UsuarioDAO.getInstancia().getKeyUsuario()+"&&quiz="+nive+"", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try
                {

                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.optJSONArray("datos");
                    jsonObject = jsonArray.getJSONObject(0);
                    nquiz = jsonObject.optString("MAX(quiz.id)");

                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Quiz.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue cola = Volley.newRequestQueue(Quiz.this);
        cola.add(request);
    }

    void IngresaQuiz ()
    {
        Date date = new Date();
        SimpleDateFormat fechac = new SimpleDateFormat("dd/MM/yyyy");

        String quiz = nive+"";
        String correctas = "0";
        String incorrectas = "0";
        String puntaje = "0";
        String tpreguntas = "0";
        String fecha = fechac.format(date);
        String usuario = idusuario+"";

        abunas = "0";
        amalas = "0";
        totalp = "0";

        Response.Listener<String> respuesta = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                MAXIdQuiz();
                if(response.equalsIgnoreCase("Datos insertados")){

                }
            }
        };

        QuizRequest r = new QuizRequest(quiz,correctas,incorrectas,puntaje,tpreguntas,fecha,usuario,respuesta);
        RequestQueue cola = Volley.newRequestQueue(Quiz.this);
        cola.add(r);
    }

    public  void LlenaDatos ()
    {
        StringRequest request  = new StringRequest(Request.Method.GET, "https://fcmusic.000webhostapp.com/paecbot/DatosQuiz.php?id="+nquiz, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try
                {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.optJSONArray("datos");
                    jsonObject = jsonArray.getJSONObject(0);
                    totalp = jsonObject.optString("tpreguntas");

                    if(totalp.equals(totpregunt))
                    {
                        IngresaQuiz();
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
                Toast.makeText(Quiz.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue cola = Volley.newRequestQueue(Quiz.this);
        cola.add(request);
    }


    void Ingresar_DQuiz ()
    {

        String nquizzes = nquiz+"";
        String nproblema = identi2.get(position).getIden()+"";
        String respuestau = arespuesta+"";
        String tiempo = auxtiempo+"";

        Response.Listener<String> respuesta = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equalsIgnoreCase("Datos insertados")){
                    acti = 1;
                }

                if(response.equalsIgnoreCase("Ya registrado"))
                {
                    ActualizarDQuiz();
                }
            }
        };

        DQuizzesRequest r = new DQuizzesRequest(nquizzes,nproblema,respuestau,tiempo,respuesta);
        RequestQueue cola = Volley.newRequestQueue(Quiz.this);
        cola.add(r);
    }


    void ActualizarDQuiz ()
    {

        final String nquizzes = nquiz+"";
        final String nproblema= identi2.get(position).getIden()+"";
        final String respuestau = arespuesta+"";
        final String tiempo = auxtiempo+"";

        StringRequest request  = new StringRequest(Request.Method.POST, "https://fcmusic.000webhostapp.com/paecbot/UpdateQDetalle.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Quiz.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){

            @Override
            protected Map<String,String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<>();

                params.put("nquiz",nquizzes);
                params.put("nproblema",nproblema);
                params.put("respuestau",respuestau);
                params.put("tiempo",tiempo);

                return params;
            }
        };

        RequestQueue cola = Volley.newRequestQueue(Quiz.this);
        cola.add(request);
    }



    public  void BuscarBuenas()
    {
        StringRequest request  = new StringRequest(Request.Method.GET, "https://fcmusic.000webhostapp.com/paecbot/CantidadRespuesta.php?respuestau=Correcta&&nquiz="+nquiz+"", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try
                {

                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.optJSONArray("datos");
                    jsonObject = jsonArray.getJSONObject(0);
                    abunas = jsonObject.optString("COUNT(respuestau)");
                    BuscarMalas();
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Quiz.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue cola = Volley.newRequestQueue(Quiz.this);
        cola.add(request);
    }

    public  void BuscarMalas()
    {
        StringRequest request  = new StringRequest(Request.Method.GET, "https://fcmusic.000webhostapp.com/paecbot/CantidadRespuesta.php?respuestau=Incorrecta&&nquiz="+nquiz+"", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try
                {

                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.optJSONArray("datos");
                    jsonObject = jsonArray.getJSONObject(0);
                    amalas = jsonObject.optString("COUNT(respuestau)");
                    ActualizarQuiz();
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Quiz.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue cola = Volley.newRequestQueue(Quiz.this);
        cola.add(request);
    }

    void ActualizarQuiz ()
    {
        puntaj = (Integer.parseInt(abunas)*10)+"";

        final String id = nquiz+"";
        final String correctas = Integer.parseInt(abunas)+"";
        final String incorrectas = Integer.parseInt(amalas)+"";
        final String puntaje = puntaj+"";
        final String tpreguntas = totalp+"";

        StringRequest request  = new StringRequest(Request.Method.POST, "https://fcmusic.000webhostapp.com/paecbot/UpdateQuiz.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Quiz.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){

            @Override
            protected Map<String,String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<>();

                params.put("id",id);
                params.put("correctas",correctas);
                params.put("incorrectas",incorrectas);
                params.put("puntaje",puntaje);
                params.put("tpreguntas",tpreguntas);

                return params;
            }
        };

        RequestQueue cola = Volley.newRequestQueue(Quiz.this);
        cola.add(request);
    }


    void start ()
    {
        if(!avanzar)
        {
            cronometro.setBase(SystemClock.elapsedRealtime());
            cronometro.start();
            avanzar=true;
        }
    }

    void stop ()
    {
        if(avanzar)
        {
            cronometro.stop();
            parar = SystemClock.elapsedRealtime();
            avanzar=false;
            auxtiempo= cronometro.getText().toString();

        }
    }

    void reset ()
    {
        cronometro.setBase(SystemClock.elapsedRealtime());
        parar=0;
        start();
    }
}
