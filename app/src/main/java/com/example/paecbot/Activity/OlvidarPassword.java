package com.example.paecbot.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.example.paecbot.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

public class OlvidarPassword extends AppCompatActivity {

    TextInputEditText txt_correo;
    MaterialButton btnRecuperar;
    TextInputLayout label_correo;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    String email = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_olvidar_password);

        txt_correo = (TextInputEditText) findViewById(R.id.txt_correo_rc);
        btnRecuperar = (MaterialButton) findViewById(R.id.btnRecuperar);
        label_correo = (TextInputLayout) findViewById(R.id.label_correo) ;

        btnRecuperar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = txt_correo.getText().toString().trim();

                if(email.isEmpty())
                {
                    label_correo.setError("Debe ingresar un correo");
                }
                else
                {
                    if(validarCorreo(email))
                    {

                        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task)
                            {
                                if(task.isSuccessful())
                                {
                                    Toast.makeText(getApplicationContext(),"Correo enviado",Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(OlvidarPassword.this, Login.class);
                                    startActivity(intent);
                                    txt_correo.setText("");
                                    finish();
                                }
                                else
                                {
                                    Toast.makeText(getApplicationContext(),"Correo no registrado",Toast.LENGTH_SHORT).show();
                                    txt_correo.setText("");
                                }
                            }
                        });
                    }
                    else
                    {
                        txt_correo.setText("");
                    }
                }
            }
        });

    }

    private boolean validarCorreo (String correo)
    {

        if(!correo.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(correo).matches())
        {
            return true;
        }
        else
        {
            label_correo.setError("Debe Ingresar un correo valido");
            return false;
        }
    }
}
