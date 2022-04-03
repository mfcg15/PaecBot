package com.example.paecbot.Requests;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class QuizRequest extends StringRequest {

    private static final String ruta ="https://fcmusic.000webhostapp.com/paecbot/RegistroQuiz.php";
    private Map<String,String> params;

    public QuizRequest(String quiz, String correctas, String incorrectas, String puntaje, String tpreguntas, String fecha, String usuario, Response.Listener<String> listener) {
        super(Method.POST, ruta, listener, null);

        params = new HashMap<>();
        params.put("quiz",quiz+"");
        params.put("correctas",correctas+"");
        params.put("incorrectas",incorrectas+"");
        params.put("puntaje",puntaje+"");
        params.put("tpreguntas",tpreguntas+"");
        params.put("fecha",fecha+"");
        params.put("usuario",usuario+"");
    }

    @Override
    protected Map<String, String> getParams(){
        return params;
    }
}
