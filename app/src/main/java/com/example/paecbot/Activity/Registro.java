package com.example.paecbot.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Pair;
import android.util.Patterns;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.paecbot.Codigo.UsuarioDAO;
import com.example.paecbot.Objetos.Chatbots;
import com.example.paecbot.Objetos.Usuario;
import com.example.paecbot.Requests.MensajeRequest;
import com.example.paecbot.Requests.RegistroRequest;
import com.example.paecbot.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Pattern;

public class Registro extends AppCompatActivity {

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseDatabase database , fida ;
    private DatabaseReference dare;

    TextInputEditText txt_correo , txt_contraseña, txt_nombres,txt_apellidos;
    MaterialButton btnRegistrar;
    TextView label_bienvenido, label_Regitrar, btnRegistro ;
    ImageView LogoAplicacion;
    TextInputLayout label_correo,label_contraseña, label_nombre, label_apellido;

    int  verifico = 0;
    String idusuario="", nombres = "", apellidos = "", correo = "", contraseña = "";

    private static final Pattern PASSWORD_PATTERN = Pattern.compile(".{6,}");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        txt_nombres = (TextInputEditText) findViewById(R.id.txt_nombre);
        txt_apellidos = (TextInputEditText)findViewById(R.id.txt_apellidos);
        txt_correo = (TextInputEditText)findViewById(R.id.txt_correo);
        txt_contraseña = (TextInputEditText)findViewById(R.id.txt_contraseña);
        label_nombre = (TextInputLayout) findViewById(R.id.label_nombre);
        label_apellido = (TextInputLayout) findViewById(R.id.label_apellido);
        label_correo = (TextInputLayout) findViewById(R.id.label_correo);
        label_contraseña = (TextInputLayout) findViewById(R.id.label_contraseña);
        label_bienvenido = (TextView) findViewById(R.id.label_bienvido);
        label_Regitrar = (TextView) findViewById(R.id.label_RegistrarUsuario);
        btnRegistrar = (MaterialButton) findViewById(R.id.btnRegistrar);
        btnRegistro = (TextView) findViewById(R.id.btnRegistro);
        LogoAplicacion = (ImageView) findViewById(R.id.LogoAplicacion);

        database = FirebaseDatabase.getInstance();
        fida = FirebaseDatabase.getInstance();

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(txt_correo.getText().toString().isEmpty()&& txt_contraseña.getText().toString().isEmpty() && txt_nombres.getText().toString().isEmpty()&& txt_apellidos.getText().toString().isEmpty())
                {
                    label_nombre.setError("Debe llenar este campo");
                    label_apellido.setError("Debe llenar este campo");
                    label_correo.setError("Debe ingresar un correo");
                    label_contraseña.setError("Debe ingresar una contraseña");
                }
                else
                {
                    if(!txt_nombres.getText().toString().isEmpty() && txt_correo.getText().toString().isEmpty()&& txt_contraseña.getText().toString().isEmpty()&& txt_apellidos.getText().toString().isEmpty() )
                    {
                        label_nombre.setError(null);
                        label_apellido.setError("Debe llenar este campo");
                        label_correo.setError("Debe ingresar un correo");
                        label_contraseña.setError("Debe ingresar una contraseña");
                    }
                    else
                    {
                        if(txt_nombres.getText().toString().isEmpty() && txt_correo.getText().toString().isEmpty()&& txt_contraseña.getText().toString().isEmpty()&& ! txt_apellidos.getText().toString().isEmpty() )
                        {
                            label_nombre.setError("Debe llenar este campo");
                            label_apellido.setError(null);
                            label_correo.setError("Debe ingresar un correo");
                            label_contraseña.setError("Debe ingresar una contraseña");
                        }
                        else
                        {
                            if(txt_nombres.getText().toString().isEmpty() && ! txt_correo.getText().toString().isEmpty()&& txt_contraseña.getText().toString().isEmpty()&&  txt_apellidos.getText().toString().isEmpty() )
                            {
                                label_nombre.setError("Debe llenar este campo");
                                label_apellido.setError("Debe llenar este campo");
                                label_correo.setError(null);
                                label_contraseña.setError("Debe ingresar una contraseña");
                                ValidarCorreo();
                            }
                            else
                            {
                                if(txt_nombres.getText().toString().isEmpty() && txt_correo.getText().toString().isEmpty()&& ! txt_contraseña.getText().toString().isEmpty()&&  txt_apellidos.getText().toString().isEmpty() )
                                {
                                    label_nombre.setError("Debe llenar este campo");
                                    label_apellido.setError("Debe llenar este campo");
                                    label_correo.setError("Debe ingresar un correo");
                                    label_contraseña.setError(null);
                                    ValidarContraseña();
                                }
                                else
                                {
                                    if(! txt_nombres.getText().toString().isEmpty() && txt_correo.getText().toString().isEmpty()&& txt_contraseña.getText().toString().isEmpty()&& ! txt_apellidos.getText().toString().isEmpty() )
                                    {
                                        label_nombre.setError(null);
                                        label_apellido.setError(null);
                                        label_correo.setError("Debe ingresar un correo");
                                        label_contraseña.setError("Debe ingresar una contraseña");
                                    }
                                    else
                                    {
                                        if(! txt_nombres.getText().toString().isEmpty() && ! txt_correo.getText().toString().isEmpty()&& txt_contraseña.getText().toString().isEmpty()&& txt_apellidos.getText().toString().isEmpty() )
                                        {
                                            label_nombre.setError(null);
                                            label_apellido.setError("Debe llenar este campo");
                                            label_correo.setError(null);
                                            label_contraseña.setError("Debe ingresar una contraseña");
                                            ValidarCorreo();
                                        }
                                        else
                                        {
                                            if(! txt_nombres.getText().toString().isEmpty() && txt_correo.getText().toString().isEmpty()&& ! txt_contraseña.getText().toString().isEmpty()&& txt_apellidos.getText().toString().isEmpty() )
                                            {
                                                label_nombre.setError(null);
                                                label_apellido.setError("Debe llenar este campo");
                                                label_correo.setError("Debe ingresar un correo");
                                                label_contraseña.setError(null);
                                                ValidarContraseña();
                                            }
                                            else
                                            {
                                                if(txt_nombres.getText().toString().isEmpty() && ! txt_correo.getText().toString().isEmpty()&& txt_contraseña.getText().toString().isEmpty()&& ! txt_apellidos.getText().toString().isEmpty() )
                                                {
                                                    label_nombre.setError("Debe llenar este campo");
                                                    label_apellido.setError(null);
                                                    label_correo.setError(null);
                                                    label_contraseña.setError("Debe ingresar una contraseña");
                                                    ValidarCorreo();
                                                }
                                                else
                                                {
                                                    if(txt_nombres.getText().toString().isEmpty() && txt_correo.getText().toString().isEmpty()&& ! txt_contraseña.getText().toString().isEmpty()&& ! txt_apellidos.getText().toString().isEmpty() )
                                                    {
                                                        label_nombre.setError("Debe llenar este campo");
                                                        label_apellido.setError(null);
                                                        label_correo.setError("Debe ingresar un correo");
                                                        label_contraseña.setError(null);
                                                        ValidarContraseña();
                                                    }
                                                    else
                                                    {
                                                        if(txt_nombres.getText().toString().isEmpty() && ! txt_correo.getText().toString().isEmpty()&& ! txt_contraseña.getText().toString().isEmpty()&& txt_apellidos.getText().toString().isEmpty() )
                                                        {
                                                            label_nombre.setError("Debe llenar este campo");
                                                            label_apellido.setError("Debe llenar este campo");
                                                            label_correo.setError(null);
                                                            label_contraseña.setError(null);

                                                            if(ValidarCorreo()&& ValidarContraseña())
                                                            {
                                                            }
                                                        }
                                                        else
                                                        {
                                                            if(!txt_nombres.getText().toString().isEmpty() && ! txt_correo.getText().toString().isEmpty()&& txt_contraseña.getText().toString().isEmpty()&& ! txt_apellidos.getText().toString().isEmpty() )
                                                            {
                                                                label_nombre.setError(null);
                                                                label_apellido.setError(null);
                                                                label_correo.setError(null);
                                                                label_contraseña.setError("Debe ingresar una contraseña");
                                                                ValidarCorreo();
                                                            }
                                                            else
                                                            {
                                                                if(txt_nombres.getText().toString().isEmpty() && ! txt_correo.getText().toString().isEmpty()&& ! txt_contraseña.getText().toString().isEmpty()&& ! txt_apellidos.getText().toString().isEmpty() )
                                                                {
                                                                    label_nombre.setError("Debe llenar este campo");
                                                                    label_apellido.setError(null);
                                                                    label_correo.setError(null);
                                                                    label_contraseña.setError(null);

                                                                    if(ValidarCorreo()&& ValidarContraseña())
                                                                    {
                                                                    }
                                                                }
                                                                else
                                                                {
                                                                    if(!txt_nombres.getText().toString().isEmpty() && ! txt_correo.getText().toString().isEmpty()&& ! txt_contraseña.getText().toString().isEmpty()&& txt_apellidos.getText().toString().isEmpty() )
                                                                    {
                                                                        label_nombre.setError(null);
                                                                        label_apellido.setError("Debe llenar este campo");
                                                                        label_correo.setError(null);
                                                                        label_contraseña.setError(null);

                                                                        if(ValidarCorreo()&& ValidarContraseña())
                                                                        {
                                                                        }
                                                                    }
                                                                    else
                                                                    {
                                                                        if(!txt_nombres.getText().toString().isEmpty() && txt_correo.getText().toString().isEmpty()&& ! txt_contraseña.getText().toString().isEmpty()&& ! txt_apellidos.getText().toString().isEmpty() )
                                                                        {
                                                                            label_nombre.setError(null);
                                                                            label_apellido.setError(null);
                                                                            label_correo.setError("Debe ingresar un correo");
                                                                            label_contraseña.setError(null);

                                                                            ValidarContraseña();
                                                                        }
                                                                        else
                                                                        {
                                                                            label_nombre.setError(null);
                                                                            label_apellido.setError(null);
                                                                            label_correo.setError(null);
                                                                            label_contraseña.setError(null);

                                                                            if(ValidarCorreo()&& !ValidarContraseña())
                                                                            {

                                                                            }
                                                                            else
                                                                            {
                                                                                if(!ValidarCorreo()&&ValidarContraseña())
                                                                                {

                                                                                }
                                                                                else
                                                                                {
                                                                                    if(ValidarCorreo()&&ValidarContraseña())
                                                                                    {
                                                                                        verifico =1;
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                if(verifico ==1)
                {
                    nombres = txt_nombres.getText().toString();
                    apellidos = txt_apellidos.getText().toString();
                    correo = txt_correo.getText().toString();
                    contraseña = txt_contraseña.getText().toString();

                    mAuth.createUserWithEmailAndPassword(correo, contraseña).addOnCompleteListener(new OnCompleteListener<AuthResult>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            if (task.isSuccessful())
                            {
                                Usuario user = new Usuario();
                                user.setNombres(nombres);
                                user.setApellidos(apellidos);
                                user.setCorreo(correo);

                                FirebaseUser currentUser = mAuth.getCurrentUser();
                                DatabaseReference reference = database.getReference("Usuarios/"+currentUser.getUid());
                                reference.setValue(user);

                                dare = fida.getReference("Chatbot/"+ UsuarioDAO.getInstancia().getKeyUsuario());


                                dare.child("Combinación").setValue(new Chatbots("",""));
                                dare.child("Cambio").setValue(new Chatbots("",""));
                                dare.child("Comparación").setValue(new Chatbots("",""));
                                dare.child("Igualación").setValue(new Chatbots("",""));

                                Response.Listener<String> respuesta = new Response.Listener<String>()
                                {
                                    @Override
                                    public void onResponse(String response)
                                    {
                                        if(response.equalsIgnoreCase("Usuario registrado"))
                                        {
                                            IdUsuario();
                                            Toast.makeText(Registro.this, response, Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(Registro.this, Tutorial.class);
                                            startActivity(intent);
                                            limpiar();
                                            finish();
                                        }
                                        else
                                        {
                                            Toast.makeText(Registro.this, response, Toast.LENGTH_SHORT).show();
                                            limpiar();
                                        }

                                    }
                                };

                                RegistroRequest r = new RegistroRequest(currentUser.getUid()+"",nombres,apellidos,correo,"0",respuesta);
                                RequestQueue cola = Volley.newRequestQueue(Registro.this);
                                cola.add(r);
                            }
                            else
                            {
                                Response.Listener<String> respuesta = new Response.Listener<String>()
                                {
                                    @Override
                                    public void onResponse(String response)
                                    {
                                        if(response.equalsIgnoreCase("Usuario registrado"))
                                        {
                                            Toast.makeText(Registro.this, response, Toast.LENGTH_SHORT).show();
                                            limpiar();
                                        }
                                        else
                                        {
                                            Toast.makeText(Registro.this, response, Toast.LENGTH_SHORT).show();
                                            limpiar();
                                        }

                                    }
                                };

                                RegistroRequest r = new RegistroRequest(idusuario,nombres,apellidos,correo,"0",respuesta);
                                RequestQueue cola = Volley.newRequestQueue(Registro.this);
                                cola.add(r);
                            }
                        }
                    });
                }
            }
        });

        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                transitionBack();
                finish();
            }
        });

    }

    @Override
    public void onBackPressed()
    {
        transitionBack();
    }


    public void transitionBack(){

        Intent intent = new Intent(Registro.this, Login.class);

        Pair[] pairs = new Pair[7];
        pairs[0] = new Pair <View, String >(LogoAplicacion,"logoImageTrans");
        pairs[1] = new Pair <View, String >(label_bienvenido,"textTrans");
        pairs[2] = new Pair <View, String >(label_Regitrar,"registroTextTrans");
        pairs[3] = new Pair <View, String >(label_correo,"emailInputTextTrans");
        pairs[4] = new Pair <View, String >(label_contraseña,"tpasswordInputTextTrans");
        pairs[5] = new Pair <View, String >(btnRegistrar,"buttonSignInTrans");
        pairs[6] = new Pair <View, String >(btnRegistro,"newUserTrans");

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Registro.this,pairs);
            startActivity(intent,options.toBundle());
        }
        else
        {
            startActivity(intent);
        }

    }

    public void limpiar(){
        txt_nombres.setText("");
        txt_apellidos.setText("");
        txt_correo.setText("");
        txt_contraseña.setText("");
    }


    private boolean ValidarCorreo ()
    {
        if(! Patterns.EMAIL_ADDRESS.matcher(txt_correo.getText().toString().trim()).matches())
        {
            label_correo.setError("Ingrese un correo valido");
            return false;
        }
        else
        {
            label_correo.setError(null);
            return true;
        }
    }


    private boolean ValidarContraseña ()
    {
        if(! PASSWORD_PATTERN.matcher(txt_contraseña.getText().toString().trim()).matches())
        {
            label_contraseña.setError("La contraseña debe tener al menos 6 caracteres");
            return false;
        }
        else
        {
            label_contraseña.setError(null);
            return true;
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
                    Ingresar1("Combinación");
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Registro.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue cola = Volley.newRequestQueue(Registro.this);
        cola.add(request);
    }

    void Ingresar1(String tipo)
    {

        String emisor = tipo+"";
        String hora = "00:04";
        String usuario = idusuario+"";

        Response.Listener<String> respuesta = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equalsIgnoreCase("Datos insertados")){
                }
                Ingresar2("Cambio");
            }
        };

        MensajeRequest r = new MensajeRequest(emisor,hora,usuario,respuesta);
        RequestQueue cola = Volley.newRequestQueue(Registro.this);
        cola.add(r);
    }

    void Ingresar2(String tipo)
    {

        String emisor = tipo+"";
        String hora = "00:03";
        String usuario = idusuario+"";

        Response.Listener<String> respuesta = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equalsIgnoreCase("Datos insertados")){
                }
                Ingresar3("Comparación");
            }
        };

        MensajeRequest r = new MensajeRequest(emisor,hora,usuario,respuesta);
        RequestQueue cola = Volley.newRequestQueue(Registro.this);
        cola.add(r);
    }

    void Ingresar3(String tipo)
    {
        String emisor = tipo+"";
        String hora = "00:02";
        String usuario = idusuario+"";

        Response.Listener<String> respuesta = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equalsIgnoreCase("Datos insertados")){
                }
                Ingresar4("Igualación");
            }
        };

        MensajeRequest r = new MensajeRequest(emisor,hora,usuario,respuesta);
        RequestQueue cola = Volley.newRequestQueue(Registro.this);
        cola.add(r);
    }

    void Ingresar4(String tipo)
    {
        String emisor = tipo+"";
        String hora = "00:01";
        String usuario = idusuario+"";

        Response.Listener<String> respuesta = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equalsIgnoreCase("Datos insertados")){
                }
            }
        };

        MensajeRequest r = new MensajeRequest(emisor,hora,usuario,respuesta);
        RequestQueue cola = Volley.newRequestQueue(Registro.this);
        cola.add(r);
    }
}