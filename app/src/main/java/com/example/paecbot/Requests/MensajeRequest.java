package com.example.paecbot.Requests;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class MensajeRequest extends StringRequest {

    private static final String ruta ="https://fcmusic.000webhostapp.com/paecbot/RegistroMensaje.php";
    private Map<String,String> params;

    public MensajeRequest(String emisor, String hora, String usuario, Response.Listener<String> listener) {
        super(Method.POST, ruta, listener, null);

        params = new HashMap<>();
        params.put("emisor",emisor+"");
        params.put("hora",hora+"");
        params.put("usuario",usuario+"");

    }

    @Override
    protected Map<String, String> getParams(){
        return params;
    }
}