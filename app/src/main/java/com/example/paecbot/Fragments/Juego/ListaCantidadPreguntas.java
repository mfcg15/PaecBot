package com.example.paecbot.Fragments.Juego;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.example.paecbot.Adapters.AdapterListaCantidadPreguntas;
import com.example.paecbot.Objetos.Quizzes;
import com.example.paecbot.R;

public class ListaCantidadPreguntas extends Fragment {

    private GridView gridView;
    TextView nombrequiz;

    String quiz = "", ti = "" , ca = "";

    public ListaCantidadPreguntas() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_lista_cantidad_preguntas, container, false);

        gridView = (GridView) v.findViewById(R.id.gridview);
        nombrequiz = (TextView) v.findViewById(R.id.nombrequiz);

        Bundle recoge = getArguments();
        Quizzes q ;
        q = (Quizzes) recoge.getSerializable("envia");
        quiz = q.getIdquiz();
        nombrequiz.setText(quiz);
        ti = q.getTipotest();
        ca = q.getCantidapreguntas();

        if(ti.equals("comb"))
        {
            AdapterListaCantidadPreguntas adapter = new AdapterListaCantidadPreguntas(4,ti+"",quiz+"",ca+"");
            gridView.setAdapter(adapter);
        }
        else
        {
            AdapterListaCantidadPreguntas adapter = new AdapterListaCantidadPreguntas(6,ti+"",quiz+"",ca+"");
            gridView.setAdapter(adapter);
        }

        return v ;
    }

}
