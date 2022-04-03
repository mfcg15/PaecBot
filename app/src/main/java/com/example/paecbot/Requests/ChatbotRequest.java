package com.example.paecbot.Requests;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ChatbotRequest extends StringRequest {

    private static final String ruta ="https://fcmusic.000webhostapp.com/paecbot/RegistroChatbot.php";
    private Map<String,String> params;

    public ChatbotRequest(String chatbot, String correctas, String incorrectas, String puntaje, String fecha, String usuario, Response.Listener<String> listener) {
        super(Method.POST, ruta, listener, null);

        params = new HashMap<>();
        params.put("chatbot",chatbot+"");
        params.put("correctas",correctas+"");
        params.put("incorrectas",incorrectas+"");
        params.put("puntaje",puntaje+"");
        params.put("fecha",fecha+"");
        params.put("usuario",usuario+"");
    }

    @Override
    protected Map<String, String> getParams(){
        return params;
    }
}
