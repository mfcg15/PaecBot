package com.example.paecbot.Requests;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class DChatbotsRequest extends StringRequest {

    private static final String ruta ="https://fcmusic.000webhostapp.com/paecbot/RegistroCDetalle.php";
    private Map<String,String> params;

    public DChatbotsRequest(String nchatbot, String nproblema, String respuestau, String tiempo, Response.Listener<String> listener) {
        super(Method.POST, ruta, listener, null);

        params = new HashMap<>();
        params.put("nchatbot",nchatbot+"");
        params.put("nproblema",nproblema+"");
        params.put("respuestau",respuestau+"");
        params.put("tiempo",tiempo+"");

    }

    @Override
    protected Map<String, String> getParams(){
        return params;
    }
}
