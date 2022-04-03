package com.example.paecbot.Codigo;

import com.google.firebase.auth.FirebaseAuth;

public class UsuarioDAO {

    public  static  UsuarioDAO usuarioDAO;

    public  static  UsuarioDAO getInstancia()
    {
        if(usuarioDAO==null) usuarioDAO = new UsuarioDAO();
        return usuarioDAO;
    }

    public static String getKeyUsuario()
    {
        return FirebaseAuth.getInstance().getUid();
    }

}
