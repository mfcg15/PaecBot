package com.example.paecbot.Fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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

import java.util.HashMap;
import java.util.Map;

public class EditarDatos extends Fragment {

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser usuario = FirebaseAuth.getInstance().getCurrentUser();
    private DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference actudata = FirebaseDatabase.getInstance().getReference();

    EditText txt_nombres,txt_apellidos;
    TextInputEditText txt_correo;
    TextInputLayout label_correo, label_nombres, label_apellidos;
    Button btn_actualizar;

    int  verico =0 ;

    String  nombres = "", apellidos = "", correo = "", contraseña, auxcorreo = "";

    public EditarDatos() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_editar_datos, container, false);

        txt_nombres = (EditText) view.findViewById(R.id.txt_nombre);
        txt_apellidos = (EditText) view.findViewById(R.id.txt_apellidos);
        txt_correo = (TextInputEditText) view.findViewById(R.id.txt_correo);
        label_nombres = (TextInputLayout) view.findViewById(R.id.label_nombre);
        label_apellidos = (TextInputLayout) view.findViewById(R.id.label_apellido);
        label_correo = (TextInputLayout) view.findViewById(R.id.label_correo);
        btn_actualizar = (Button) view.findViewById(R.id.btnActualizar) ;

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
                    txt_nombres.setText(snapshot.child("nombres").getValue().toString());
                    txt_apellidos.setText(snapshot.child("apellidos").getValue().toString());
                    txt_correo.setText(snapshot.child("correo").getValue().toString());
                    auxcorreo = snapshot.child("correo").getValue().toString();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btn_actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(txt_correo.getText().toString().isEmpty()&& txt_nombres.getText().toString().isEmpty()&& txt_apellidos.getText().toString().isEmpty())
                {
                    label_nombres.setError("Debe llenar este campo");
                    label_apellidos.setError("Debe llenar este campo");
                    label_correo.setError("Debe ingresar un correo");
                }
                else
                {

                    if(!txt_nombres.getText().toString().isEmpty() && txt_correo.getText().toString().isEmpty()&& txt_apellidos.getText().toString().isEmpty() )
                    {
                        label_nombres.setError(null);
                        label_apellidos.setError("Debe llenar este campo");
                        label_correo.setError("Debe ingresar un correo");
                    }
                    else
                    {
                        if(txt_nombres.getText().toString().isEmpty() && !txt_correo.getText().toString().isEmpty()&& txt_apellidos.getText().toString().isEmpty() )
                        {
                            label_nombres.setError("Debe llenar este campo");
                            label_apellidos.setError("Debe llenar este campo");
                            label_correo.setError(null);
                            if(validarCorreo(txt_correo));
                        }
                        else
                        {
                            if(txt_nombres.getText().toString().isEmpty() && txt_correo.getText().toString().isEmpty()&& ! txt_apellidos.getText().toString().isEmpty() )
                            {
                                label_nombres.setError("Debe llenar este campo");
                                label_apellidos.setError(null);
                                label_correo.setError("Debe ingresar un correo");
                            }
                            else
                            {
                                if(!txt_nombres.getText().toString().isEmpty() && !txt_correo.getText().toString().isEmpty()&& txt_apellidos.getText().toString().isEmpty() )
                                {
                                    label_nombres.setError(null);
                                    label_apellidos.setError("Debe llenar este campo");
                                    label_correo.setError(null);
                                    if(validarCorreo(txt_correo));
                                }
                                else
                                {
                                    if(!txt_nombres.getText().toString().isEmpty() && txt_correo.getText().toString().isEmpty()&& !txt_apellidos.getText().toString().isEmpty() )
                                    {
                                        label_nombres.setError(null);
                                        label_apellidos.setError(null);
                                        label_correo.setError("Debe ingresar un correo");
                                    }
                                    else
                                    {
                                        if(txt_nombres.getText().toString().isEmpty() && !txt_correo.getText().toString().isEmpty()&& !txt_apellidos.getText().toString().isEmpty() )
                                        {
                                            label_nombres.setError("Debe llenar este campo");
                                            label_apellidos.setError(null);
                                            label_correo.setError(null);
                                            if(validarCorreo(txt_correo));
                                        }
                                        else
                                        {
                                            label_nombres.setError(null);
                                            label_apellidos.setError(null);
                                            label_correo.setError(null);
                                            if(validarCorreo(txt_correo))
                                            {
                                                verico = 1;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                if(verico==1)
                {
                    nombres = txt_nombres.getText().toString();
                    apellidos = txt_apellidos.getText().toString();
                    correo = txt_correo.getText().toString();
                    pedricontraseña();
                }
            }
        });
    }


    private  void pedricontraseña()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        LayoutInflater inflater = getLayoutInflater();

        View mview = inflater.inflate(R.layout.validar_contrasena,null);

        builder.setView(mview);
        final AlertDialog dialog = builder.create();
        dialog.show();


        final EditText contra = (EditText) mview.findViewById(R.id.txt_valida_contraseña);
        final TextInputLayout label_contra = (TextInputLayout) mview.findViewById(R.id.label_contraseña_actual);
        Button btn_aceptar = (Button) mview.findViewById(R.id.btnAceptar);

        btn_aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                contraseña = contra.getText().toString();
                if(!contraseña.isEmpty())
                {
                    mAuth.signInWithEmailAndPassword(auxcorreo, contraseña).addOnCompleteListener(new OnCompleteListener<AuthResult>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful())
                            {
                                usuario.updateEmail(correo).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful())
                                        {
                                            ActualizarUsuario();
                                            Map<String,Object> usuario = new HashMap<>();
                                            usuario.put("nombres",nombres);
                                            usuario.put("apellidos",apellidos);
                                            usuario.put("correo",correo);
                                            actudata.child("Usuarios").child(UsuarioDAO.getInstancia().getKeyUsuario()).updateChildren(usuario);
                                            Toast.makeText(getContext(), "Los datos fueron actualizados", Toast.LENGTH_LONG).show();
                                            dialog.dismiss();
                                            txt_nombres.setEnabled(false);
                                            txt_apellidos.setEnabled(false);
                                            txt_correo.setEnabled(false);
                                            btn_actualizar.setEnabled(false);
                                        }
                                    }
                                });
                            }
                            else
                            {
                                label_contra.setError("Contraseña incorrecta");
                                contra.setText("");
                            }
                        }
                    });

                }
                else
                {
                    label_contra.setError("Debe ingresar su contraseña");
                }
            }
        });
    }


    void ActualizarUsuario ()
    {

        final String iduser = UsuarioDAO.getInstancia().getKeyUsuario();
        final String nombres = txt_nombres.getText().toString();
        final String apellidos = txt_apellidos.getText().toString();
        final String correo = txt_correo.getText().toString();

        StringRequest request  = new StringRequest(Request.Method.POST, "https://fcmusic.000webhostapp.com/paecbot/UpdateUsuario.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){

            @Override
            protected Map<String,String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<>();

                params.put("iduser",iduser);
                params.put("nombres",nombres);
                params.put("apellidos",apellidos);
                params.put("correo",correo);

                return params;
            }
        };

        RequestQueue cola = Volley.newRequestQueue(getContext());
        cola.add(request);
    }


    private boolean validarCorreo (TextInputEditText correo)
    {
        String emailInput = correo.getText().toString();

        if(!emailInput.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(emailInput).matches())
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
