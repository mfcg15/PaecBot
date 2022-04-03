package com.example.paecbot.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Pair;
import android.util.Patterns;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.paecbot.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Pattern;

public class Login extends AppCompatActivity {

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private SharedPreferences mPrefs;
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

    private static final String PREFS_NAME = "PrefsFIle";

    TextInputEditText txt_correo , txt_contraseña;
    MaterialButton btnIniciarSesion , btnCrearCuenta;
    TextView btnOlvidatesContraseña, label_bienvenido, label_iniciar;
    TextInputLayout label_correo,label_contraseña;
    ImageView LogoAplicacion;
    CheckBox btnRecuerdame;

    private static final Pattern PASSWORD_PATTERN = Pattern.compile(".{6,}");

    String correo="" , contraseña="", auxcorreo = "";
    int verifico = 0 , existeusu = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txt_correo = (TextInputEditText) findViewById(R.id.txt_correo);
        txt_contraseña = (TextInputEditText) findViewById(R.id.txt_contraseña);
        label_bienvenido = (TextView) findViewById(R.id.label_bienvenido);
        label_iniciar = (TextView) findViewById(R.id.label_inicia);
        label_correo = (TextInputLayout) findViewById(R.id.label_correo);
        label_contraseña = (TextInputLayout) findViewById(R.id.label_contraseña);
        btnRecuerdame = (CheckBox) findViewById(R.id.btnRecuerdame);
        btnIniciarSesion = (MaterialButton) findViewById(R.id.btnIniciarSesion);
        btnCrearCuenta = (MaterialButton) findViewById(R.id.btnCrearCuenta);
        btnOlvidatesContraseña = (TextView) findViewById(R.id.btnOlvidatesContraseña);
        LogoAplicacion = (ImageView) findViewById(R.id.LogoAplicacion);

        mPrefs = getSharedPreferences(PREFS_NAME,MODE_PRIVATE);

        getPreferencesData();

        btnOlvidatesContraseña.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, OlvidarPassword.class);
                startActivity(intent);
            }
        });


        btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txt_correo.getText().toString().isEmpty()&& txt_contraseña.getText().toString().isEmpty())
                {
                    label_correo.setError("Debe ingresar un correo");
                    label_contraseña.setError("Debe ingresar una contraseña");
                }
                else
                {
                    if(!txt_correo.getText().toString().isEmpty()&&  txt_contraseña.getText().toString().isEmpty())
                    {
                        label_correo.setError(null);
                        label_contraseña.setError("Debe ingresar una contraseña");
                        ValidarCorreo();
                    }
                    else
                    {
                        if(txt_correo.getText().toString().isEmpty()&& ! txt_contraseña.getText().toString().isEmpty())
                        {
                            label_correo.setError("Debe ingresar un correo");
                            label_contraseña.setError(null);
                            ValidarContraseña();
                        }
                        else
                        {
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

                if(verifico ==1)
                {
                    correo = txt_correo.getText().toString();
                    contraseña= txt_contraseña.getText().toString();

                    mDatabase.child("Usuarios").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists())
                            {
                                for( DataSnapshot ds : snapshot.getChildren())
                                {
                                    auxcorreo = ds.child("correo").getValue().toString();

                                    if(txt_correo.getText().toString().equals(auxcorreo))
                                    {
                                        existeusu=1;
                                        break;
                                    }
                                    else
                                    {
                                        existeusu=0;
                                    }
                                }
                            }

                            if(existeusu==1)
                            {
                                mAuth.signInWithEmailAndPassword(correo, contraseña).addOnCompleteListener(new OnCompleteListener<AuthResult>()
                                {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful())
                                        {

                                            if(btnRecuerdame.isChecked())
                                            {
                                                Boolean boolIsChecked = btnRecuerdame.isChecked();
                                                SharedPreferences.Editor editor = mPrefs.edit();
                                                editor.putString("pref_name",txt_correo.getText().toString());
                                                editor.putString("pref_pass",txt_contraseña.getText().toString());
                                                editor.putBoolean("pref_check",boolIsChecked);
                                                editor.apply();
                                            }
                                            else
                                            {
                                                mPrefs.edit().clear().apply();
                                            }

                                            Intent intent = new Intent(Login.this, Menu.class);
                                            startActivity(intent);
                                            finish();
                                            limpiar();
                                        }
                                        else
                                        {
                                            Toast.makeText(Login.this, "Datos incorrectos", Toast.LENGTH_SHORT).show();
                                            limpiar();
                                        }
                                    }
                                });
                            }
                            else
                            {
                                Toast.makeText(Login.this, "Usuario no registrado", Toast.LENGTH_SHORT).show();
                                limpiar();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
                else
                {
                    limpiar();
                }
            }
        });

        btnCrearCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Login.this, Registro.class);

                Pair[] pairs = new Pair[7];
                pairs[0] = new Pair <View, String >(LogoAplicacion,"logoImageTrans");
                pairs[1] = new Pair <View, String >(label_bienvenido,"textTrans");
                pairs[2] = new Pair <View, String >(label_iniciar,"iniciaSesionTextTrans");
                pairs[3] = new Pair <View, String >(label_correo,"emailInputTextTrans");
                pairs[4] = new Pair <View, String >(label_contraseña,"tpasswordInputTextTrans");
                pairs[5] = new Pair <View, String >(btnIniciarSesion,"buttonSignInTrans");
                pairs[6] = new Pair <View, String >(btnCrearCuenta,"newUserTrans");

                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                {
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Login.this,pairs);
                    startActivity(intent,options.toBundle());
                }
                else
                {
                    startActivity(intent);
                }
            }
        });
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

    public void limpiar(){
        txt_contraseña.setText("");
        txt_correo.setText("");
    }

    private void getPreferencesData()
    {
        SharedPreferences sp = getSharedPreferences(PREFS_NAME,MODE_PRIVATE);
        if(sp.contains("pref_name"))
        {
            String u = sp.getString("pref_name","not found");
            txt_correo.setText(u.toString());
        }
        if(sp.contains("pref_pass"))
        {
            String p = sp.getString("pref_pass","not found");
            txt_contraseña.setText(p.toString());
        }
        if(sp.contains("pref_check"))
        {
            Boolean b = sp.getBoolean("pref_check",false);
            btnRecuerdame.setChecked(b);
        }

    }

    @Override
    protected  void onResume()
    {
        super.onResume();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser!=null)
        {
            Intent intent = new Intent(Login.this,Menu.class);
            startActivity(intent);
            finish();
        }
    }
}