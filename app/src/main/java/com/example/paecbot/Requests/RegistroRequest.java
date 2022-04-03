package com.example.paecbot.Requests;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegistroRequest extends StringRequest {

    private static final String ruta ="https://fcmusic.000webhostapp.com/paecbot/Registro.php";
    private Map<String,String> params;

    public RegistroRequest(String iduser, String nombres, String apellidos, String correo, String puntajet, Response.Listener<String> listener) {
        super(Method.POST, ruta, listener, null);

        params = new HashMap<>();
        params.put("iduser",iduser+"");
        params.put("nombres",nombres+"");
        params.put("apellidos",apellidos+"");
        params.put("correo",correo+"");
        params.put("puntajet",puntajet+"");
    }

    @Override
    protected Map<String, String> getParams(){
        return params;
    }

}