package com.example.paecbot.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Display;
import android.view.View;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.bumptech.glide.Glide;
import com.example.paecbot.Adapters.AdapterMensajes;
import com.example.paecbot.Codigo.Constantes;
import com.example.paecbot.Codigo.UsuarioDAO;
import com.example.paecbot.Objetos.Chatbots;
import com.example.paecbot.Objetos.ListaDeProblemas;
import com.example.paecbot.Objetos.Mensaje;
import com.example.paecbot.R;
import com.example.paecbot.Requests.ChatbotRequest;
import com.example.paecbot.Requests.DChatbotsRequest;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

public class Chatbot extends AppCompatActivity {

    private RecyclerView rvMensajes;
    private ImageButton btnEnviar;
    private CircleImageView imagenperfil;
    private TextView nombreniño;
    private EditText txtMensaje;
    private Chronometer cronometro ;
    private AdapterMensajes adapter;

    private DatabaseReference mDatabase, databaseReference, dr, dare, datare;
    private FirebaseDatabase database, fd , fida, firedata;

    public static final  String nombrechatbot = "nombrechatbot";
    public static final  String fotoperfil = "fotoperfil";
    public static final  String inic = "inic";
    public static final  String tern = "tern";
    public static final  String tipo = "tipo";

    String nom = "", fotop="" ,input="", workspaceId = "", urlAssistant="", authentication="", nombreusuario = "", idusuario="",nchat="",resp="", auxtiempo="", abunas = "", amalas = "", pquiz = "", pchat = "", msm = "", indicador = "" , ter = "" ;
    int contador = 1 , varesponer=0 , pregunta = 0, guardar = 0, inicio=0, termino=0  , c = 0, prinego = 0;
    boolean avanzar = false;
    long parar;
    int cantidad =18;
    int arreglo [] = new int [cantidad];

    List<ListaDeProblemas> listaproblemas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatbot);

        rvMensajes = (RecyclerView) findViewById(R.id.rvMensajes);
        txtMensaje = (EditText) findViewById(R.id.txtMensaje);
        nombreniño = (TextView) findViewById(R.id.nombre);
        btnEnviar = (ImageButton) findViewById(R.id.btnEnviar);
        imagenperfil = (CircleImageView) findViewById(R.id.imagen_perfil);
        cronometro = (Chronometer) findViewById(R.id.txt_cronometro);

        nom = getIntent().getStringExtra("nombrechatbot");
        fotop = getIntent().getStringExtra("fotoperfil");
        inicio =  Integer. parseInt (getIntent().getStringExtra("inic"));
        termino =  Integer. parseInt (getIntent().getStringExtra("tern"));


        if(nom.equals("Cesar")|| nom.equals("Camila"))
        {
            workspaceId = "1921e15d-e53e-40b4-a125-11f838328a61";
        }

        if(nom.equals("Claudia")|| nom.equals("Ignacio"))
        {
            workspaceId = "029fdf2b-0c57-4afd-aa1c-8fe382a4d9ed";
        }

        urlAssistant = "https://api.us-south.assistant.watson.cloud.ibm.com/instances/d5c753c1-19b4-4826-9a38-ff406de2a0b7/v1/workspaces/" + workspaceId + "/message?version=2020-04-01";
        authentication = "YXBpa2V5OkxrQVdKSEl2dEJFZExNRS1LZDlZZXpuUDJ4MnRxSFJ2aUVjSGNhbGpaaG9C";


        adapter = new AdapterMensajes(this);
        LinearLayoutManager l = new LinearLayoutManager(Chatbot.this);
        l.setStackFromEnd(true);
        rvMensajes.setLayoutManager(l);
        rvMensajes.setAdapter(adapter);


        fd = FirebaseDatabase.getInstance();
        firedata = FirebaseDatabase.getInstance();
        dr = fd.getReference();
        fida = FirebaseDatabase.getInstance();
        datare = firedata.getReference("Usuarios/"+UsuarioDAO.getInstancia().getKeyUsuario());


        mDatabase = FirebaseDatabase.getInstance().getReference();
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference(Constantes.NOdo_MENSAJES+"/"+UsuarioDAO.getInstancia().getKeyUsuario()+"/"+getIntent().getStringExtra("tipo"));
        dare = fida.getReference("Chatbot/"+UsuarioDAO.getInstancia().getKeyUsuario());


        nombreniño.setText(nom);
        Glide.with(Chatbot.this).load(fotop).into(imagenperfil);

        pruebita();

        Usuario();

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                Date d = new Date();
                SimpleDateFormat h = new SimpleDateFormat("HH:mm ");

                input = txtMensaje.getText().toString();
                databaseReference.push().setValue(new Mensaje(input,"Usuario",h.format(d)+"",true));//constructor 2
                Mensaje(getIntent().getStringExtra("tipo"),h.format(d)+"",input);
                ActualizarMensaje(getIntent().getStringExtra("tipo"));
                getResponse(input);

                if(varesponer==1)
                {
                    getResponseD(input+" respuesta"+listaproblemas.get(pregunta).getProblema()+"");
                    varesponer=0;
                }

                txtMensaje.setText("");

            }
        });


        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                setScrollbar();
            }
        });

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Mensaje m = dataSnapshot.getValue(Mensaje.class);
                adapter.addMensaje(m);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void setScrollbar(){
        rvMensajes.scrollToPosition(adapter.getItemCount()-1);
    }

    private void getResponse(String input) {

        JSONObject inputJsonObject = new JSONObject();
        try {
            inputJsonObject.put("text", input);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("input", inputJsonObject);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        AndroidNetworking.post(urlAssistant)
                .addHeaders("Content-Type", "application/json")
                .addHeaders("Authorization", "Basic " + authentication)
                .addJSONObjectBody(jsonBody)
                .setPriority(Priority.HIGH)
                .setTag(getString(R.string.app_name))
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            String outputJsonObject = response.getJSONObject("output").getJSONArray("text").getString(0);

                            switch (outputJsonObject)
                            {
                                case "saludo":
                                    Date d = new Date();
                                    SimpleDateFormat h = new SimpleDateFormat("HH:mm ");
                                    databaseReference.push().setValue(new Mensaje("Hola "+nombreusuario,fotop,"Chatbot",h.format(d)+"",false));
                                    databaseReference.push().setValue(new Mensaje("Te formularé 18 preguntas. ¿Podemos comenzar? (Si, para comenzar)",fotop,"Chatbot",h.format(d)+"",false));
                                    Mensaje(getIntent().getStringExtra("tipo"),h.format(d)+"","Te formularé 18 preguntas. ¿Podemos comenzar? (Si, para comenzar)");
                                    ActualizarMensaje(getIntent().getStringExtra("tipo"));
                                    break;

                                case "confirmo":
                                    Date a = new Date();
                                    SimpleDateFormat z = new SimpleDateFormat("HH:mm ");

                                    if(guardar==0)
                                    {
                                        IngresarChatbot();
                                        guardar=1;
                                        databaseReference.push().setValue(new Mensaje("¡Comenzamos!",fotop,"Chatbot", z.format(a)+"",false));
                                    }
                                    databaseReference.push().setValue(new Mensaje("Pregunta "+contador,fotop,"Chatbot", z.format(a)+"",false));
                                    getResponseC("pregunta"+listaproblemas.get(pregunta).getProblema()+"");
                                    prinego = 1;
                                    break;

                                case "nego":

                                    if(prinego==0)
                                    {
                                        Date aa = new Date();
                                        SimpleDateFormat zz = new SimpleDateFormat("HH:mm ");
                                        databaseReference.push().setValue(new Mensaje("Puedes empezar a responder las preguntas cuando lo desees escribiendo comenzar.",fotop,"Chatbot",zz.format(aa)+"",false));
                                        Mensaje(getIntent().getStringExtra("tipo"),zz.format(aa)+"","Puedes empezar a responder las preguntas cuando lo desees escribiendo comenzar.");
                                        ActualizarMensaje(getIntent().getStringExtra("tipo"));
                                        AlertDialog.Builder builder = new AlertDialog.Builder(Chatbot.this);
                                        builder.setTitle("Mensaje");
                                        builder.setMessage("Vuelve cuando lo desees.").setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent intent = new Intent(Chatbot.this, Menu.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                        }).show();
                                    }
                                    else
                                    {
                                        BuscarBuenas();

                                        for(int zx=0;zx<pregunta;zx++)
                                        {
                                            dr.child("Lista").child(UsuarioDAO.getInstancia().getKeyUsuario()).child(getIntent().getStringExtra("tipo")).child("valor"+zx).setValue(listaproblemas.get(zx).getProblema());
                                        }

                                        Date aa = new Date();
                                        SimpleDateFormat zz = new SimpleDateFormat("HH:mm ");
                                        databaseReference.push().setValue(new Mensaje("Puede continuar con las preguntas restantes cuando lo desee escribiendo continuar.",fotop,"Chatbot",zz.format(aa)+"",false));
                                        Mensaje(getIntent().getStringExtra("tipo"),zz.format(aa)+"","Puede continuar con las preguntas restantes cuando lo desee escribiendo continuar.");
                                        ActualizarMensaje(getIntent().getStringExtra("tipo"));

                                        if(pregunta==1)
                                        {
                                            ter = "pregunta";
                                        }
                                        else
                                        {
                                            ter = "preguntas";
                                        }

                                        AlertDialog.Builder builder = new AlertDialog.Builder(Chatbot.this);
                                        builder.setTitle("Mensaje");
                                        builder.setMessage("Contesto "+pregunta+" "+ter+" de 18. Revise sus resultados obtenidos en Menú Reportes.").setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                PuntajeQuiz();
                                                Intent intent = new Intent(Chatbot.this, Menu.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                        }).show();
                                    }
                                    break;

                                case "sigue":
                                    MAXIdChatbot();
                                    getResponse("si");
                                    break;

                                case "error":
                                    Date aaas = new Date();
                                    SimpleDateFormat zzzs = new SimpleDateFormat("HH:mm ");
                                    databaseReference.push().setValue(new Mensaje("Por favor responda la pregunta que fue formulada con anterioridad",fotop,"Chatbot",zzzs.format(aaas)+"",false));
                                    Mensaje(getIntent().getStringExtra("tipo"),zzzs.format(aaas)+"","Por favor responda la pregunta que fue formulada con anterioridad");
                                    ActualizarMensaje(getIntent().getStringExtra("tipo"));
                                    break;
                            }

                        }

                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }


                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(Chatbot.this, "Error de conexion", Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void getResponseC(String input) {

        JSONObject inputJsonObject = new JSONObject();
        try {
            inputJsonObject.put("text", input);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("input", inputJsonObject);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        AndroidNetworking.post(urlAssistant)
                .addHeaders("Content-Type", "application/json")
                .addHeaders("Authorization", "Basic " + authentication)
                .addJSONObjectBody(jsonBody)
                .setPriority(Priority.HIGH)
                .setTag(getString(R.string.app_name))
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            reset();
                            Date d = new Date();
                            SimpleDateFormat h = new SimpleDateFormat("HH:mm ");
                            String outputJsonObject = response.getJSONObject("output").getJSONArray("text").getString(0);
                            databaseReference.push().setValue(new Mensaje(outputJsonObject,fotop,"Chatbot", h.format(d)+"",listaproblemas.get(pregunta).getProblema()+"",false));
                            Mensaje(getIntent().getStringExtra("tipo"),h.format(d)+"",outputJsonObject);
                            ActualizarMensaje(getIntent().getStringExtra("tipo"));
                            varesponer=1;
                            start();
                        }

                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }


                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(Chatbot.this, "Error de conexion", Toast.LENGTH_LONG).show();
                    }
                });
    }


    private void getResponseD(String input) {

        JSONObject inputJsonObject = new JSONObject();
        try {
            inputJsonObject.put("text", input);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("input", inputJsonObject);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        AndroidNetworking.post(urlAssistant)
                .addHeaders("Content-Type", "application/json")
                .addHeaders("Authorization", "Basic " + authentication)
                .addJSONObjectBody(jsonBody)
                .setPriority(Priority.HIGH)
                .setTag(getString(R.string.app_name))
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            String outputJsonObject = response.getJSONObject("output").getJSONArray("text").getString(0);

                            if(outputJsonObject.equals("Respuesta correcta"))
                            {
                                resp="Correcta";
                                stop();
                                Ingresar_DChatbot();
                                Date d = new Date();
                                SimpleDateFormat h = new SimpleDateFormat("HH:mm ");


                                if (pregunta==17)
                                {
                                    BuscarBuenas();

                                    FirebaseDatabase databas = FirebaseDatabase.getInstance();
                                    DatabaseReference ref = databas.getReference("Lista").child(UsuarioDAO.getInstancia().getKeyUsuario()).child(getIntent().getStringExtra("tipo"));
                                    ref.removeValue();

                                    databaseReference.push().setValue(new Mensaje(outputJsonObject,fotop,"Chatbot", h.format(d)+"",false));

                                    AlertDialog.Builder builder = new AlertDialog.Builder(Chatbot.this);
                                    builder.setTitle("Mensaje");
                                    builder.setMessage("¡ Terminaste las 18 preguntas !\n" + "¿Desea realizar una nueva conversación?\n" + "(Los mensajes seran eliminados))").setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            FirebaseDatabase databas = FirebaseDatabase.getInstance();
                                            DatabaseReference ref = databas.getReference("Mensajes").child(UsuarioDAO.getInstancia().getKeyUsuario()).child(getIntent().getStringExtra("tipo"));
                                            ref.removeValue();
                                            reinicia();
                                        }
                                    }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            Mensaje(getIntent().getStringExtra("tipo"),"","");
                                            ActualizarMensaje(getIntent().getStringExtra("tipo"));

                                            FirebaseDatabase databas = FirebaseDatabase.getInstance();
                                            DatabaseReference ref = databas.getReference("Mensajes").child(UsuarioDAO.getInstancia().getKeyUsuario()).child(getIntent().getStringExtra("tipo"));
                                            ref.removeValue();
                                            PuntajeQuiz();
                                            Intent intent = new Intent(Chatbot.this, Menu.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    }).show();

                                }

                                else
                                {
                                    databaseReference.push().setValue(new Mensaje(outputJsonObject,fotop,"Chatbot", h.format(d)+"",false));
                                    databaseReference.push().setValue(new Mensaje("¿Desea continuar?",fotop,"Chatbot", h.format(d)+"",false));
                                    Mensaje(getIntent().getStringExtra("tipo"),h.format(d)+"","¿Desea continuar?");
                                    ActualizarMensaje(getIntent().getStringExtra("tipo"));
                                    contador++;
                                    pregunta++;
                                }
                            }

                            else
                            {

                                if(outputJsonObject.equals("error"))
                                {
                                    varesponer=1;
                                }
                                else
                                {
                                    resp="Incorrecta";
                                    stop();

                                    Ingresar_DChatbot();

                                    Date d = new Date();
                                    SimpleDateFormat h = new SimpleDateFormat("HH:mm ");

                                    if (pregunta==17)
                                    {
                                        BuscarBuenas();

                                        FirebaseDatabase databas = FirebaseDatabase.getInstance();
                                        DatabaseReference ref = databas.getReference("Lista").child(UsuarioDAO.getInstancia().getKeyUsuario()).child(getIntent().getStringExtra("tipo"));
                                        ref.removeValue();

                                        databaseReference.push().setValue(new Mensaje(outputJsonObject,fotop,"Chatbot", h.format(d)+"",false));


                                        AlertDialog.Builder builder = new AlertDialog.Builder(Chatbot.this);
                                        builder.setTitle("Mensaje");
                                        builder.setMessage("¡ Terminaste las 18 preguntas !\n" + "¿Desea realizar una nueva conversación?\n"+ "(Los mensajes seran eliminados)").setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                                FirebaseDatabase databas = FirebaseDatabase.getInstance();
                                                DatabaseReference ref = databas.getReference("Mensajes").child(UsuarioDAO.getInstancia().getKeyUsuario()).child(getIntent().getStringExtra("tipo"));
                                                ref.removeValue();
                                                reinicia();
                                            }
                                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                                Mensaje(getIntent().getStringExtra("tipo"),"","");
                                                ActualizarMensaje(getIntent().getStringExtra("tipo"));

                                                FirebaseDatabase databas = FirebaseDatabase.getInstance();
                                                DatabaseReference ref = databas.getReference("Mensajes").child(UsuarioDAO.getInstancia().getKeyUsuario()).child(getIntent().getStringExtra("tipo"));
                                                ref.removeValue();
                                                PuntajeQuiz();
                                                Intent intent = new Intent(Chatbot.this, Menu.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                        }).show();
                                    }


                                    else
                                    {
                                        databaseReference.push().setValue(new Mensaje(outputJsonObject,fotop,"Chatbot", h.format(d)+"",false));
                                        databaseReference.push().setValue(new Mensaje("¿Desea continuar?",fotop,"Chatbot", h.format(d)+"",false));
                                        Mensaje(getIntent().getStringExtra("tipo"),h.format(d)+"","¿Desea continuar?");
                                        ActualizarMensaje(getIntent().getStringExtra("tipo"));
                                        contador++;
                                        pregunta++;
                                    }

                                }
                            }
                        }

                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }


                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(Chatbot.this, "Error de conexion", Toast.LENGTH_LONG).show();
                    }
                });
    }


    void Usuario()
    {
        mDatabase.child("Usuarios").child(UsuarioDAO.getInstancia().getKeyUsuario()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    nombreusuario = snapshot.child("nombres").getValue().toString()+" "+snapshot.child("apellidos").getValue().toString();
                }

                PrimerMensaje();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    void PrimerMensaje()
    {
        mDatabase.child("Chatbot").child(UsuarioDAO.getInstancia().getKeyUsuario()).child(getIntent().getStringExtra("tipo")).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    indicador = snapshot.child("lastmensaje").getValue().toString();
                }

                IdUsuario();

                if(indicador.equals(""))
                {
                    getResponse("hola");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Chatbot.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue cola = Volley.newRequestQueue(Chatbot.this);
        cola.add(request);
    }

    void Mensaje (String tipo, String hora, String ultimom)
    {

        if(ultimom.length()>32)
        {
            for(int d=0;d<32;d++)
            {
                msm += ultimom.charAt(d);
            }
            msm = msm+"...";
        }
        else
        {
            msm = ultimom;
        }


        switch (tipo)
        {
            case "Combinación":
                dare.child("Combinación").setValue(new Chatbots(msm+"",hora+""));

                break;

            case "Cambio":
                dare.child("Cambio").setValue(new Chatbots(msm+"",hora+""));
                break;

            case "Comparación":
                dare.child("Comparación").setValue(new Chatbots(msm+"",hora+""));
                break;

            case "Igualación":
                dare.child("Igualación").setValue(new Chatbots(msm+"",hora+""));
                break;
        }
        msm = "";
    }

    void IngresarChatbot ()
    {
        Date date = new Date();
        SimpleDateFormat fechac = new SimpleDateFormat("dd/MM/yyyy");

        String chatbot = nom+"";
        String correctas = "0";
        String incorrectas = "0";
        String puntaje = "0";
        String fecha = fechac.format(date);
        String usuario = idusuario+"";

        Response.Listener<String> respuesta = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                MAXIdChatbot();
                if(response.equalsIgnoreCase("Datos insertados")){
                }
            }
        };

        ChatbotRequest r = new ChatbotRequest(chatbot,correctas,incorrectas,puntaje,fecha,usuario,respuesta);
        RequestQueue cola = Volley.newRequestQueue(Chatbot.this);
        cola.add(r);
    }


    void ActualizarMensaje(String tipo)
    {

        Date d = new Date();
        SimpleDateFormat h = new SimpleDateFormat("HH:mm ");


        final String hora = h.format(d);
        final String usuario = idusuario+"";
        final String emisor = tipo+"";

        StringRequest request  = new StringRequest(Request.Method.POST, "https://fcmusic.000webhostapp.com/paecbot/UpdateMensaje.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Chatbot.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){

            @Override
            protected Map<String,String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<>();

                params.put("hora",hora);
                params.put("usuario",usuario);
                params.put("emisor",emisor);

                return params;
            }
        };

        RequestQueue cola = Volley.newRequestQueue(Chatbot.this);
        cola.add(request);
    }


    public  void MAXIdChatbot ()
    {
        StringRequest request  = new StringRequest(Request.Method.GET, "https://fcmusic.000webhostapp.com/paecbot/MaxIdChatbot.php?usuario="+idusuario+"&&chatbot="+nom, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try
                {

                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.optJSONArray("datos");


                    jsonObject = jsonArray.getJSONObject(0);
                    nchat = jsonObject.optString("MAX(id)");


                    if(nchat.equals("null"))
                    {
                        nchat = "1";
                    }
                    else
                    {
                        int aux = Integer.parseInt(nchat);
                        nchat=aux+"";
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
                Toast.makeText(Chatbot.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue cola = Volley.newRequestQueue(Chatbot.this);
        cola.add(request);
    }

    void Ingresar_DChatbot ()
    {
        String nchatbot = nchat+"";
        String nproblema =  listaproblemas.get(pregunta).getProblema()+"";
        String respuestau = resp+"";
        String tiempo = auxtiempo+"";

        Response.Listener<String> respuesta = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if(response.equalsIgnoreCase("Datos insertados")){

                }
            }
        };

        DChatbotsRequest r = new DChatbotsRequest(nchatbot,nproblema,respuestau,tiempo,respuesta);
        RequestQueue cola = Volley.newRequestQueue(Chatbot.this);
        cola.add(r);
    }

    public  void BuscarBuenas()
    {
        StringRequest request  = new StringRequest(Request.Method.GET, "https://fcmusic.000webhostapp.com/paecbot/CantidadRespuestasC.php?respuestau=Correcta&&nchatbot="+nchat+"", new Response.Listener<String>() {
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
                Toast.makeText(Chatbot.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue cola = Volley.newRequestQueue(Chatbot.this);
        cola.add(request);
    }

    public  void BuscarMalas()
    {
        StringRequest request  = new StringRequest(Request.Method.GET, "https://fcmusic.000webhostapp.com/paecbot/CantidadRespuestasC.php?respuestau=Incorrecta&&nchatbot="+nchat+"", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try
                {

                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.optJSONArray("datos");
                    jsonObject = jsonArray.getJSONObject(0);
                    amalas = jsonObject.optString("COUNT(respuestau)");

                    ActualizarChatbot();
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Chatbot.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue cola = Volley.newRequestQueue(Chatbot.this);
        cola.add(request);
    }

    void ActualizarChatbot ()
    {

        final String id = nchat+"";
        final String correctas = Integer.parseInt(abunas)+"";
        final String incorrectas = Integer.parseInt(amalas)+"";
        final String puntaje = Integer.parseInt(abunas)*10+"";

        StringRequest request  = new StringRequest(Request.Method.POST, "https://fcmusic.000webhostapp.com/paecbot/UpdateChatbot.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Chatbot.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){

            @Override
            protected Map<String,String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<>();

                params.put("id",id);
                params.put("correctas",correctas);
                params.put("incorrectas",incorrectas);
                params.put("puntaje",puntaje);

                return params;
            }
        };

        RequestQueue cola = Volley.newRequestQueue(Chatbot.this);
        cola.add(request);
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

                        if(pquiz.equals("null"))
                        {
                            pquiz = "0";
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
                Toast.makeText(Chatbot.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue cola = Volley.newRequestQueue(Chatbot.this);
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
                Toast.makeText(Chatbot.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue cola = Volley.newRequestQueue(Chatbot.this);
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
                Toast.makeText(Chatbot.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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

        RequestQueue cola = Volley.newRequestQueue(Chatbot.this);
        cola.add(request);
    }

    void reinicia ()
    {
        listaproblemas.remove(ListaDeProblemas.class);
        contador = 1 ;
        pregunta = 0;
        databaseReference = database.getReference(Constantes.NOdo_MENSAJES+"/"+ UsuarioDAO.getInstancia().getKeyUsuario()+"/"+getIntent().getStringExtra("tipo"));

        pruebita();

        Date a = new Date();
        SimpleDateFormat z = new SimpleDateFormat("HH:mm ");
        IngresarChatbot();
        databaseReference.push().setValue(new Mensaje("¡Comenzamos!",fotop,"Chatbot", z.format(a)+"",false));
        databaseReference.push().setValue(new Mensaje("Pregunta "+contador,fotop,"Chatbot", z.format(a)+"",false));
        getResponseC("pregunta"+listaproblemas.get(pregunta).getProblema()+"");
    }


    void pruebita() {


        mDatabase.child("Lista").child(UsuarioDAO.getInstancia().getKeyUsuario()).child(getIntent().getStringExtra("tipo")).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    for( DataSnapshot ds : snapshot.getChildren())
                    {
                        String df = ds.getValue().toString();
                        listaproblemas.add(new ListaDeProblemas(Integer.parseInt(df)));
                    }


                    contador = listaproblemas.size()+1;
                    pregunta = listaproblemas.size();


                    for (int a=0;a<cantidad;a++)
                    {
                        arreglo[a] = (int) (Math.random()*(termino-inicio)+inicio);
                        for (int j =0 ; j<a;j++)
                        {
                            if(arreglo[a]==arreglo[j])
                            {
                                a--;
                            }
                        }
                    }

                    for (int g=0;g<cantidad;g++)
                    {
                        for (int a = 0; a < listaproblemas.size(); a++)
                        {
                            if (arreglo[g] == listaproblemas.get(a).getProblema())
                            {
                                c = 1;
                            }
                        }

                        if (c == 1)
                        {
                            c = 0;
                        }
                        else
                        {
                            listaproblemas.add(new ListaDeProblemas(arreglo[g]));
                        }

                    }

                    guardar = 1;
                }
                else
                {
                    for (int a=0;a<cantidad;a++)
                    {
                        arreglo[a] = (int) (Math.random()*(termino-inicio)+inicio);
                        for (int j =0 ; j<a;j++)
                        {
                            if(arreglo[a]==arreglo[j])
                            {
                                a--;
                            }
                        }
                    }

                    for (int a=0;a<cantidad;a++)
                    {
                        listaproblemas.add(new ListaDeProblemas(arreglo[a]));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

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
