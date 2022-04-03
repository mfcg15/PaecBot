package com.example.paecbot.Fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.paecbot.Codigo.UsuarioDAO;
import com.example.paecbot.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CambiarContrasena extends Fragment {

    EditText txt_contraseña, txt_contraseñaC, txt_contraseña_actual;
    Button btn_actualizar;
    TextInputLayout label_contraseña_actual, label_contraseña_nueva, label_contraseña_confirmar;

    String correo = "", contraactual = "", contranueva = "", contraconfirma = "";

    int vali = 0 , valid = 0;
    private DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser usuario = FirebaseAuth.getInstance().getCurrentUser();

    public CambiarContrasena() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_cambiar_contrasena, container, false);

        txt_contraseña = (EditText) view.findViewById(R.id.txt_contraseña_nueva);
        txt_contraseñaC = (EditText) view.findViewById(R.id.txt_contraseña_confirmar);
        txt_contraseña_actual = (EditText) view.findViewById(R.id.txt_contraseña_actual);
        label_contraseña_actual = (TextInputLayout) view.findViewById(R.id.label_contraseña_actual);
        label_contraseña_confirmar = (TextInputLayout) view.findViewById(R.id.label_contraseña_confirmar);
        label_contraseña_nueva = (TextInputLayout) view.findViewById(R.id.label_contraseña_nueva);
        btn_actualizar = (Button) view.findViewById(R.id.btnActualizarContraseña) ;

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        database.child("Usuarios").child(UsuarioDAO.getInstancia().getKeyUsuario()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    correo = snapshot.child("correo").getValue().toString();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btn_actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(txt_contraseña.getText().toString().isEmpty() && txt_contraseñaC.getText().toString().isEmpty() && txt_contraseña_actual.getText().toString().isEmpty())
                {
                    label_contraseña_nueva.setError("Debe ingresar su nueva contraseña");
                    label_contraseña_actual.setError("Debe ingresar su contraseña actual");
                    label_contraseña_confirmar.setError("Debe ingresar nuevamente su nueva contraseña");
                }
                else
                {

                    if(!txt_contraseña.getText().toString().isEmpty() && txt_contraseñaC.getText().toString().isEmpty() && txt_contraseña_actual.getText().toString().isEmpty() )
                    {
                        label_contraseña_nueva.setError(null);
                        label_contraseña_actual.setError("Debe ingresar su contraseña actual");
                        label_contraseña_confirmar.setError("Debe ingresar nuevamente su nueva contraseña");
                        if(ValidarContraseñaN());
                    }
                    else
                    {
                        if(txt_contraseña.getText().toString().isEmpty() && !txt_contraseñaC.getText().toString().isEmpty() && txt_contraseña_actual.getText().toString().isEmpty() )
                        {
                            label_contraseña_nueva.setError("Debe ingresar su nueva contraseña");
                            label_contraseña_actual.setError("Debe ingresar su contraseña actual");
                            label_contraseña_confirmar.setError(null);
                            if(ValidarContraseñaC());
                        }
                        else
                        {
                            if(txt_contraseña.getText().toString().isEmpty() && txt_contraseñaC.getText().toString().isEmpty() && !txt_contraseña_actual.getText().toString().isEmpty() )
                            {
                                label_contraseña_nueva.setError("Debe ingresar su nueva contraseña");
                                label_contraseña_actual.setError(null);
                                label_contraseña_confirmar.setError("Debe ingresar nuevamente su nueva contraseña");
                                if(ValidarContraseñaA());
                            }
                            else
                            {
                                if(!txt_contraseña.getText().toString().isEmpty() && !txt_contraseñaC.getText().toString().isEmpty() && txt_contraseña_actual.getText().toString().isEmpty() )
                                {
                                    label_contraseña_nueva.setError(null);
                                    label_contraseña_actual.setError("Debe ingresar su contraseña actual");
                                    label_contraseña_confirmar.setError(null);

                                    if(!ValidarContraseñaC() && ValidarContraseñaN())
                                    {

                                    }
                                    else
                                    {
                                        if(ValidarContraseñaC() && !ValidarContraseñaN())
                                        {

                                        }
                                        else
                                        {
                                            if(ValidarContraseñaC() && ValidarContraseñaN());
                                        }
                                    }
                                }
                                else
                                {
                                    if(!txt_contraseña.getText().toString().isEmpty() && txt_contraseñaC.getText().toString().isEmpty() && !txt_contraseña_actual.getText().toString().isEmpty() )
                                    {
                                        label_contraseña_nueva.setError(null);
                                        label_contraseña_actual.setError(null);
                                        label_contraseña_confirmar.setError("Debe ingresar nuevamente su nueva contraseña");

                                        if(!ValidarContraseñaN() && ValidarContraseñaA())
                                        {

                                        }
                                        else
                                        {
                                            if(ValidarContraseñaN() && !ValidarContraseñaA())
                                            {

                                            }
                                            else
                                            {
                                                if(ValidarContraseñaN()  && ValidarContraseñaA());
                                            }
                                        }

                                    }
                                    else
                                    {
                                        if(txt_contraseña.getText().toString().isEmpty() && !txt_contraseñaC.getText().toString().isEmpty() && !txt_contraseña_actual.getText().toString().isEmpty() )
                                        {
                                            label_contraseña_nueva.setError("Debe ingresar su nueva contraseña");
                                            label_contraseña_actual.setError(null);
                                            label_contraseña_confirmar.setError(null);

                                            if(!ValidarContraseñaC() && ValidarContraseñaA())
                                            {

                                            }
                                            else
                                            {
                                                if(ValidarContraseñaC() && !ValidarContraseñaA())
                                                {

                                                }
                                                else
                                                {
                                                    if(ValidarContraseñaC()  && ValidarContraseñaA());
                                                }
                                            }
                                        }
                                        else
                                        {
                                            label_contraseña_nueva.setError(null);
                                            label_contraseña_actual.setError(null);
                                            label_contraseña_confirmar.setError(null);

                                            if(ValidarContraseñaC()&& !ValidarContraseñaN() && ValidarContraseñaA())
                                            {

                                            }
                                            else
                                            {
                                                if(!ValidarContraseñaC()&&ValidarContraseñaN() && ValidarContraseñaA())
                                                {

                                                }
                                                else
                                                {
                                                    if(ValidarContraseñaC()&&ValidarContraseñaN() && ! ValidarContraseñaA())
                                                    {

                                                    }
                                                    else
                                                    {
                                                        if(!ValidarContraseñaC()&& !ValidarContraseñaN() && ValidarContraseñaA())
                                                        {

                                                        }
                                                        else
                                                        {
                                                            if(!ValidarContraseñaC()&& ValidarContraseñaN() && !ValidarContraseñaA())
                                                            {

                                                            }

                                                            else
                                                            {
                                                                if(ValidarContraseñaC()&& !ValidarContraseñaN() && !ValidarContraseñaA())
                                                                {

                                                                }

                                                                else
                                                                {
                                                                    if(ValidarContraseñaC()&&ValidarContraseñaN() && ValidarContraseñaA())
                                                                    {
                                                                        if(ValidarContraseñas())
                                                                        {
                                                                            vali = 1;
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

                if(vali==1)
                {
                    contraactual = txt_contraseña_actual.getText().toString();

                    mAuth.signInWithEmailAndPassword(correo, contraactual).addOnCompleteListener(new OnCompleteListener<AuthResult>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful())
                            {
                                usuario.updatePassword(contranueva).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful())
                                        {
                                            txt_contraseña_actual.setText("");
                                            txt_contraseña.setText("");
                                            txt_contraseñaC.setText("");
                                            Toast.makeText(getContext(), "Contraseña actualizada", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                            else
                            {
                                label_contraseña_actual.setError("Contraseña actual es incorrecta");
                                txt_contraseña_actual.setText("");
                            }
                        }
                    });
                }
            }
        });
    }


    public  boolean ValidarContraseñaA()
    {
        contraactual = txt_contraseña_actual.getText().toString();

        if(contraactual.length()>=6)
        {
            return  true;
        }
        else
        {
            label_contraseña_actual.setError("Las contraseña debe tener al menos 6 caracteres");
            return false;
        }
    }


    public  boolean ValidarContraseñaN()
    {
        contranueva = txt_contraseña.getText().toString();

        if(contranueva.length()>=6)
        {
            return  true;
        }
        else
        {
            label_contraseña_nueva.setError("Las contraseña debe tener al menos 6 caracteres");
            return false;
        }
    }

    public  boolean ValidarContraseñaC()
    {
        contraconfirma = txt_contraseñaC.getText().toString();

        if(contraconfirma.length()>=6)
        {
            return  true;
        }
        else
        {
            label_contraseña_confirmar.setError("Las contraseña debe tener al menos 6 caracteres");
            return false;
        }
    }

    public  boolean ValidarContraseñas()
    {

        contranueva = txt_contraseña.getText().toString();
        contraconfirma = txt_contraseñaC.getText().toString();

        if(contranueva.equals(contraconfirma))
        {
            return  true;
        }
        else
        {
            label_contraseña_confirmar.setError("Las contraseñas deben coincidir");
            txt_contraseña.setText("");
            txt_contraseñaC.setText("");
            return false;
        }
    }

}
